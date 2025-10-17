#!/bin/bash
cd src
find . -name "*.java" -not -path "./__MACOSX/*" > sources.txt
javac @sources.txt
rm sources.txt
cd ..
echo "Compilation complete!"
