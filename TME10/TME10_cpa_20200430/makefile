.PHONY: clean doc test

TARGET = bin
SOURCE = src
PACKAGE = algorithms
TEST = test
DOC = doc
JAR = steiner
SOURCE_FILES = $(wildcard $(SOURCE)/**/*.java $(SOURCE)/*.java)


file:
	@find . -name "*.java" > sourcefiles
	javac -cp jars/* -s $(SOURCE)/ $^ -d $(TARGET)/ @sourcefiles
	@rm sourcefiles 

run: file
	java -cp jars/supportGUI.jar:bin/ supportGUI.ShortestPathsViewer
	@rm sourcefiles;

clean:
	rm -frv $(TARGET)/$(PACKAGE)