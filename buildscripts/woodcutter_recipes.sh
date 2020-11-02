#!/bin/bash
mkdir -p ../src/main/resources/data/villagelife/recipes/generated

declare -A wood=(
    [oak]=wood
    [birch]=wood
    [acacia]=wood
    [spruce]=wood
    [jungle]=wood
    [dark_oak]=wood
    [crimson]=hyphae
    [warped]=hyphae
)

declare -A log=(
    [oak]=log
    [birch]=log
    [acacia]=log
    [spruce]=log
    [jungle]=log
    [dark_oak]=log
    [crimson]=stem
    [warped]=stem
)

for type in "oak" "birch" "acacia" "spruce" "jungle" "dark_oak" "warped" "crimson"; do \
    for file in ../src/templates/recipes/woodcutting/*.*; do \
        name=$(basename $file)
        wood=${wood[$type]}
        log=${log[$type]}
        name=${name//WOODTYPE/$type}
        name=${name//WOOD/$wood}
        name=${name//LOG/$log}
        dest=../src/main/resources/data/villagelife/recipes/generated/$name
        cp $file $dest
        sed -i -e "s/WOODTYPE/$type/g" $dest
        sed -i -e "s/WOOD/$wood/g" $dest
        sed -i -e "s/LOG/$log/g" $dest
    done
done