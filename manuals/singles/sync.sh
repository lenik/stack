#!/bin/bash

for d in ../../sem/*/*/doc; do
    sem_x_y_doc=${d#../../}
    sem_x_y=${sem_x_y_doc%/doc}
    mkdir -p "$sem_x_y"
    cp -a "$d" "$sem_x_y_doc"
done

cp -a ../profiles profiles
