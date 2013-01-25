#!/bin/bash

for e in asta tab bak aux lof log lot out pdf toc csv; do
    find -name "*.$e" -delete
done
find -name "*~" -delete
