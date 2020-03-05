.PHONY: clean

SRC = TME1
DIR_SRC = src
DIR_OBJ = bin
TARGET = $(DIR_OBJ)/bin

$(TARGET): ${DIR_OBJ}/$(SRC).o
	g++ -o $@ $^

$(DIR_OBJ)/TME1.o: ${DIR_SRC}/$(SRC).cpp
	g++ -o $@ -c $^


run:
	./$(TARGET) > out;
	more out

init:
	mkdir src;
	mkdir bin;
	mkdir doc;

clean:
	rm -rf $(DIR_OBJ)