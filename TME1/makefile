.PHONY: clean

SRC = TME1
DIR_SRC = src
DIR_OBJ = bin
TARGET = $(DIR_OBJ)/bin
EX2 = $(DIR_OBJ)/ex2.o
EX4 = $(DIR_OBJ)/ex4.o
EX5 = $(DIR_OBJ)/ex5.o
EX6 = $(DIR_OBJ)/ex6.o
EX7 = $(DIR_OBJ)/ex7.o
EX8 = $(DIR_OBJ)/ex8.o
EX9 = $(DIR_OBJ)/ex9.o

$(TARGET): $(DIR_OBJ)/main.o $(DIR_OBJ)/graph.o $(EX2) $(EX4) $(EX5) $(EX6) $(EX7) $(EX8) $(EX9)
	g++ -o $@ $^

$(DIR_OBJ)/main.o: ${DIR_SRC}/main.cpp
	g++ -o $@ -c $^  

$(DIR_OBJ)/graph.o: $(DIR_SRC)/graph/graph.cpp
	g++ -o $@ -c $^

$(EX2): ${DIR_SRC}/ex2/ex2.cpp 
	g++ -o $@ -c $^

$(EX4): ${DIR_SRC}/ex4/ex4.cpp
	g++ -o $@ -c $^

$(EX5): $(DIR_SRC)/ex5/ex5.cpp
	g++ -o $@ -c $^

$(EX6): $(DIR_SRC)/ex6/ex6.cpp
	g++ -o $@ -c $^

$(EX7): $(DIR_SRC)/ex7/ex7.cpp
	g++ -o $@ -c $^

$(EX8): $(DIR_SRC)/ex8/ex8.cpp
	g++ -o $@ -c $^

$(EX9): $(DIR_SRC)/ex9/ex9.cpp
	g++ -o $@ -c $^

ex3:
	./src/ex3/cleandata.sh

run:
	./$(TARGET) > out;
	more out

init:
	mkdir src;
	mkdir bin;
	mkdir doc;

clean:
	rm -rf $(DIR_OBJ)
	rm *.txt