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

typedef struct
{
    int board[20][10];
    int row;
    int column;
} shareBoard;

int getRandomInt()
{
    return rand() % 15000 + 1;
}

void populateBoard(shareBoard *board)
{
    for (int i = 0; i < 20; i++)
    {
        for (int j = 0; j < 10; j++)
        {
            board->board[i][j] = 0;
        }
    }
}

void accessBoard(shareBoard *board)
{
    for (int i = 0; i < 20; i++)
    {
        for (int j = 0; j < 10; j++)
        {
            printf("%d ", board->board[i][j]);
        }
        printf("\n");
    }
}

void updateBoard(int index, shareBoard *board, pid_t pid)
{
    board->board[board->row][board->column] = getRandomInt();
    printf("Client: %d PID: %d performing Update\n", index, pid); // state of update
    fflush(stdout);                                               // force the output to be printed
    usleep(250000);                                               // sleep for 250ms
    printf("Client: %d PID: %d performed update. Row: %d Column: %d\n", index, pid, board->row, board->column);
    fflush(stdout); // force the output to be printed
}

int main()
{
    sem_unlink("/sem");

    int fd, numClientes = 150, data_size = sizeof(shareBoard);
    shareBoard *sBoard;
    sem_t *sem;

    fd = shm_open("/shm", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd == -1)
    {
        perror("shm_open error");
        exit(1);
    }

    if (ftruncate(fd, data_size) == -1)
    {
        perror("ftruncate error");
        exit(1);
    }
    sBoard = (shareBoard *)mmap(NULL, data_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
    if (sBoard == MAP_FAILED)
    {
        perror("mmap error");
        exit(1);
    }
    sem = sem_open("/sem", O_CREAT | O_EXCL, S_IRUSR | S_IWUSR, 1);
    if (sem == SEM_FAILED)
    {
        perror("sem_open error");
        exit(1);
    }

    sBoard->row = 0;
    sBoard->column = 0;
    populateBoard(sBoard); // initialize board

    pid_t pid;

    for (size_t i = 0; i < numClientes; i++)
    {
        pid = fork();
        
        if (pid == -1)
        {
            perror("fork error");
            exit(1);
        }
        else if (pid == 0)
        {
            srand(time(NULL)+getpid()); // seed for randomizer
            sem_wait(sem);

            sBoard->row = rand() % 20 + 1;
            sBoard->column = rand() % 10 + 1;

            if (i % 5 != 0)
            {
                updateBoard(i, sBoard, pid);
            }
            else
            {
                accessBoard(sBoard);
            }

            sem_post(sem);
            exit(0);
        }
    }

    if (sem_unlink("/sem") == -1)
    {
        perror("sem_unlink error");
        exit(1);
    }

    if (munmap(sBoard, data_size) == -1)
    {
        perror("munmap error");
        exit(1);
    }

    if (shm_unlink("/shm") == -1)
    {
        perror("shm_unlink error");
        exit(1);
    }

    return 0;
}