#!/bin/bash
find . -name "*.java" -not -path "./__MACOSX/*" > sources.txt
javac -d . @sources.txt
rm sources.txt
echo "Compilation complete!"
