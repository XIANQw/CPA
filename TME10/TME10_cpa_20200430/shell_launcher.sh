#!/bin/bash
rm -rf beans; mkdir beans
find . -name "*.java" > sourcefiles
javac -cp jars/* -s src/ -d beans/ @sourcefiles
rm sourcefiles
java -cp jars/supportGUI.jar:beans/ supportGUI.ShortestPathsViewer
