#!/bin/bash

if [ ! -z "$(git status --porcelain)" ]; then
    echo "working directory is dirty, unable to continue"
    exit 1
fi

mvn site:site
if [ ${?} -ne 0 ]; then
    echo "error building the site, unable to continue"
    exit 2
fi

git checkout gh-pages
if [ ${?} -ne 0 ]; then
    echo "error checking out gh-pages branch, unable to continue"
    exit 2
fi

rm -Rf maven/*
cp -a target/site/* maven/
git add .
git commit -m "- automatic import of generated maven site"
git checkout master
echo "operation complete. you need to manually git push origin gh-pages"

