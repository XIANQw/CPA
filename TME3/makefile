.PHONY: clean
SRC = tme3
DIR_SRC = src
DIR_DOC = doc
DIR_OBJ = bin
TARGET = $(DIR_OBJ)/bin

All: $(DIR_OBJ) $(TARGET)

$(TARGET): ${DIR_OBJ}/$(SRC).o
	g++ -o $@ $^

$(DIR_OBJ)/$(SRC).o: ${DIR_SRC}/$(SRC).cpp
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