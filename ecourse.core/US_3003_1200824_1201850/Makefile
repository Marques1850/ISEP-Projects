prog: main.o
	gcc   -Wall -g main.o  -o prog.exe -pthread -lrt


main.o : main.c
	gcc   -Wall -g -c main.c -o main.o -pthread -lrt

run:  prog
	./prog.exe

vg:  prog
	valgrind ./prog.exe

clean:
	rm -f prog.exe *.o
   