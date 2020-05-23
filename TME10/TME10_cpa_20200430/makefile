.PHONY: clean doc test

TARGET = bin
SOURCE = src
PACKAGE = algorithms
TEST = test
DOC = doc
JAR = steiner
DATA = Varoumas_benchmark
SOURCE_FILES = $(wildcard $(SOURCE)/**/*.java $(SOURCE)/*.java)


file: $(patsubst $(SOURCE)/%.java,$(TARGET)/%.class,$(SOURCE_FILES))

$(TARGET)/%.class: $(SOURCE)/%.java
	javac -sourcepath $(SOURCE) $^ -d $(TARGET)

run: file
	cd $(TARGET); java $(PACKAGE).Main

clean:
	rm -frv $(TARGET)/$(PACKAGE)