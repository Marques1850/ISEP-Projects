#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <time.h>
#include <sys/mman.h>
#include <string.h>

#define NUM_CLIENTS 200


typedef struct {              //share Board struct,board variable represents the board object were thing are to be written
    int row;                                             //row and collum represent the position where the user  will write or read
    int collum;
    char board[20][10][18];
} share_Board;

void init_board(share_Board *board) {                  //function to structure the board
    for (int i = 0; i < 20; i++) {
        for (int j = 0; j < 10; j++) {
            board->board[i][j][0] = '|';
            for(int g = 1; g < 17; g++){
                board->board[i][j][g] = ' ';
            } 
        }
    }
}

void random_string(int n, share_Board *board) {                 //this function create a random string of n words
    char *words[] = {"mesa", "aula", "cadeira", "quadro", "Isep"};   //The string is created with a random combination of these words
    int num_words = sizeof(words) / sizeof(words[0]);
    char *str = malloc((50) * sizeof(char));
    srand(time(NULL));
    for (int i = 0; i < n; i++) {                //the string is created with a '-' separating the words if there is more than one
        strcat(str, words[rand() % num_words]);
        if(i != n-1){
            strcat(str, "-");
        }
    }
    int num = strlen(str);         //counting the number of characters in the string
    for (int j = num+2; j <= 17; j++)    //due to how strcpy words we need to fill the rest of the string with spaces to preserve board structure
    {
        strcat(str, " ");
    }
    strcpy(board->board[board->row][board->collum]+1, str);  //copying the word to the board
}


void update_board(int client_id, share_Board *board, pid_t pid) {
    
    random_string(rand() % 2 + 1, board);      //calling the function to fill the board position
    printf("Client %d, with PID=%d is updating the board\n", client_id, pid);
    fflush(stdout);
    usleep(200000);
    printf("Wha was added to the board: %s\n", board->board[board->row][board->collum]);  //show what was written
    printf("Client %d, with PID=%d finished updating the board in row:%d and collum:%d\n", client_id, pid, board->row, board->collum);
    fflush(stdout);
}

void read_board(int client_id, share_Board *board, pid_t pid) {  //function to print the whole board
    printf("Client %d, with PID=%d is reading the board\n", client_id, pid);
    fflush(stdout);
    printf("The board is:\n");
    for (int i = 0; i < 20; i++) {
        for (int j = 0; j < 10; j++) {
            if (strcmp(board->board[i][j], " ") == 0) {
                printf(" ");
            } else {
                printf("%s ", board->board[i][j]);
            }
        }
        printf("\n");
    }

    usleep(200000);
    printf("Client %d, with PID=%d finished reading the board\n", client_id, pid);
    fflush(stdout);
}

void client(int client_id, share_Board *board, sem_t *sem, pid_t pid) {


    sem_wait(sem);      // process gains access to the shared memory area
    board->row = rand() % 20 + 1;           //randomly chooses a row and collum to write or read
    board->collum = rand() % 10 + 1;

    if(client_id % 9 == 0){              //some clients read and others write
        read_board(client_id, board, pid); 
    }
    else{
        update_board(client_id, board, pid);
    }

    sem_post(sem);  // process lets other process access the shared memory area
}

int main() {

    int fd;
    share_Board *shareboard;
    int data_size = sizeof(share_Board);
    
    fd = shm_open("/shm", O_CREAT|O_EXCL|O_RDWR, S_IRUSR|S_IWUSR);
    if(fd == -1){
        perror("Error creating shared memory");
        exit(1);
    }

    if(ftruncate(fd, data_size) == -1){
        perror("ftruncate did not work");
        exit(1);
    }

    shareboard = (share_Board*)mmap(NULL, data_size, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0);
    if(shareboard == MAP_FAILED){
        perror("Error mapping shared memory object");
        exit(1);
    }

    sem_t *barreira;

    shareboard->row = 0;               //initializing the board's row and collumn
    shareboard->collum = 0;
    init_board(shareboard);

    barreira = sem_open("/sem", O_CREAT|O_EXCL, S_IRUSR | S_IWUSR, 1);  //semaphore to control the access to the shared memory
    if (barreira == SEM_FAILED) {
        perror("sem_open did not work");
        exit(1);
    }

    pid_t pid[NUM_CLIENTS];

    for (int i = 0; i < NUM_CLIENTS; i++) {   //creating the client processes
        pid[i] = fork();
        if (pid[i] == -1) {
            perror("fork did not work");
            exit(1);
        } else if (pid[i] == 0) {
            srand(time(NULL) ^ (getpid()<<16));
            client(i, shareboard, barreira, getpid());
            exit(0);
        }
    }

    for (int i = 0; i < NUM_CLIENTS; i++) {
        wait(NULL);
    }

    int r;

    r = sem_unlink("/sem");
    if (r == -1) {
        perror("sem_unlink did not work");
        exit(1);
    }

    r = munmap(shareboard, data_size);
    if (r == -1) {
        perror("munmap did not work");
        exit(1);
    }

    if(shm_unlink("/shm")==-1){
        perror("Error unlinking shared memory object");
        exit(1);
    }

    return 0;
}