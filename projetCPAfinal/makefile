.PHONY: clean doc test

TARGET = bin
SOURCE = src
PACKAGE = algorithms
TEST = test
DOC = doc
JAR = steiner

file:
	@find . -name "*.java" > sourcefiles
	javac -cp jars/* -s $(SOURCE)/ $^ -d $(TARGET)/ @sourcefiles
	@rm sourcefiles 

run: file
	java -cp jars/supportGUI.jar:bin/ supportGUI.SteinerDiskGraphsViewer

random:
	java -cp jars/supportGUI.jar:bin/ supportGUI.RandomPointsGenerator


clean:
	rm -frv $(TARGET)/$(PACKAGE)