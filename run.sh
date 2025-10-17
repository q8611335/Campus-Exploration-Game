#!/bin/bash
# Compile first
./compile.sh

# Run with example arguments
cd src
java CampusNavigatorEngine 7 8 123 ../data/maps/hawthorn.txt ../data/events/events1.txt
