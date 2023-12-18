
prog: board.o   
	gcc   -Wall -g board.o   -o prog -lrt -lpthread

board.o : board.c 
	gcc   -Wall -g -c board.c -o board.o  -lrt -lpthread

run:  prog 
	./prog 

vg:  prog 
	valgrind ./prog 

clean: 
	rm -f prog *.o 	
