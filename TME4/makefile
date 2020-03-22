.PHONY: clean
SRC = tme4
DIR_SRC = src
DIR_DOC = doc
DIR_OBJ = bin
TARGET = $(DIR_OBJ)/bin

all: $(DIR_OBJ) $(TARGET)

$(TARGET): ${DIR_OBJ}/main.o $(DIR_OBJ)/graph.o $(DIR_OBJ)/ex1.o
	g++ -g $^ -o $@

$(DIR_OBJ)/main.o: ${DIR_SRC}/main.cpp
	g++ -o $@ -c $^

$(DIR_OBJ)/graph.o: ${DIR_SRC}/graph.cpp
	g++ -o $@ -c $^

$(DIR_OBJ)/ex1.o: ${DIR_SRC}/ex1.cpp
	g++ -o $@ -c $^

$(DIR_OBJ):
	mkdir bin

run: $(TARGET)
	./$(TARGET)
clean:
	rm -rf $(DIR_OBJ);

init:
	mkdir $(DIR_SRC);
	mkdir $(DIR_OBJ);
	mkdir $(DIR_DOC);