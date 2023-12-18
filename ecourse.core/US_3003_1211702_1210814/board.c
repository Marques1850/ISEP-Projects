#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <stdlib.h>
#include <time.h>
#include <semaphore.h>
#include <sys/wait.h>

#define SEMAPHORE_NAME "/boardLock"
#define SHAREDMEM_NAME "/sh_board"

#define BOARD_LINES 10
#define BOARD_COLUMNS 10
#define NUM_USERS 1000
#define MAX_CONTENT 100

// shared memory structure for shared board
typedef struct {
    int content[BOARD_LINES][BOARD_COLUMNS];
} board;

sem_t * lock; 
                
void printBoard(board * sharedBoard){    // function to print the board
    int i,k;
    for ( i = 0; i< BOARD_LINES; i++){
        for ( k = 0; k < BOARD_COLUMNS; k++){
            printf("%4d   ", sharedBoard->content[i][k]);
        }
        printf("\n");
    }
    printf("\n");
    fflush(stdout);
}

void readContent(int line, int column, board * sharedBoard){  // function to read the board
    sem_wait(lock);         // wait for semaphore to be available
    printf("User %d requested to read the board\n",getpid());
    fflush(stdout);
    printBoard(sharedBoard); 
    fflush(stdout);
    sem_post(lock);         // unlock semaphore
}

void writeContent(int line, int column, board * sharedBoard, int new_content){   // function to write in the board
    sem_wait(lock);         // wait for semaphore to be available
    printf("User %d requested to write: %d, in the board[%d][%d]\n",getpid(),new_content,line,column);
    fflush(stdout);
    sharedBoard->content[line][column]=new_content;
    printBoard(sharedBoard);
    sem_post(lock);         // unlock semaphore
}


int main() {
    int error_check, board_size=sizeof(board),i,j;
    board * sharedBoard;    // board for testing

    // setup shared memory
    int fd = shm_open(SHAREDMEM_NAME, O_CREAT|O_RDWR, S_IRUSR|S_IWUSR);
    if (fd == -1){
        perror("Erro na abertura do ficheiro de memória partilhada!\n");
        exit(EXIT_FAILURE);
    }
    error_check = ftruncate(fd,board_size);
    if (error_check){
        perror("Erro no dimensionamento do espaço de memória partilhada!\n");
        exit(EXIT_FAILURE);
    }
    sharedBoard = (board*)mmap(NULL, board_size,PROT_READ | PROT_WRITE,MAP_SHARED,fd,0);
    if (sharedBoard == MAP_FAILED){
        perror("Erro no mapeamento de memória!\n");
        exit(EXIT_FAILURE);
    }

    // setup semaphore
    sem_unlink(SEMAPHORE_NAME); // if semaphore already exists, unlink it
    lock = sem_open(SEMAPHORE_NAME, O_CREAT|O_EXCL, S_IRUSR|S_IWUSR, 0);
    if (lock == SEM_FAILED){
        perror("Erro na criação do semáforo!\n");
        exit(EXIT_FAILURE);
    }

    // initialize board with 0's to avoid unitialized or garbage values
    for (  i = 0; i < BOARD_LINES; i++)
        for (  j = 0; j < BOARD_COLUMNS; j++)
            sharedBoard->content[i][j] = 0;

    // parent process after intializing the board "unlocks" the semaphore
    sem_post(lock);         


    // parent process creates users
    pid_t pid;
    for (  i = 1; i < NUM_USERS; i++ ){
        pid = fork();
        if (pid == -1){
            perror("Erro na criação do processo!\n");
            exit(EXIT_FAILURE);
        } else if (pid == 0){
            srand((unsigned) time(NULL) ^ (getpid()<<16)); // seed for random number generator
            // child processes read/write the board randomly and exit
            int line = rand()%BOARD_LINES;
            int column = rand()%BOARD_COLUMNS;
            
            // random read/write according to the process id (User number)
            if (getpid()%2)
                readContent(line, column, sharedBoard);
            else
                writeContent(line,column, sharedBoard, rand()%MAX_CONTENT+1);
            exit(EXIT_SUCCESS);
        }
    }

    // parent process waits for all users to finish reading/writing
    for ( i = 0; i < NUM_USERS; i++) {      
        wait(NULL);
    }
    // parent process prints the final board
    printf("Final board:\n");
    printBoard(sharedBoard);


    // parent process closes and unlinks the shared memory
    error_check = munmap(sharedBoard, board_size);
    if (error_check){
        perror("Erro no desmapeamento de memória!\n");
        exit(EXIT_FAILURE);
    }
    error_check = shm_unlink(SHAREDMEM_NAME);
    if (error_check){
        perror("Erro na remoção do ficheiro de memória partilhada!\n");
        exit(EXIT_FAILURE);
    }
    // parent process closes and unlinks the semaphore
    error_check = sem_close(lock);
    if (error_check){
        perror("Error closing the boardLock semaphore!\n");
        exit(EXIT_FAILURE);
    }
    error_check = sem_unlink(SEMAPHORE_NAME);
    if (error_check){
        perror("Error unlinking the boardLock semaphore!\n");
        exit(EXIT_FAILURE);
    }
    return 0;
}