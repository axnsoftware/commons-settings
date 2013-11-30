#!/bin/bash

if [ ! -z "$(git status --porcelain)" ]; then
    echo "working directory is dirty, unable to continue"
    exit 1
fi

cat >../pubmvnsite.$$ <<EOF

cd "\${1}"
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

git rm -rf maven/*
cp -a target/site/* maven/
git add maven
git commit -m "- automatic import of generated maven site"
git checkout master
echo "operation complete. you need to manually git push origin gh-pages"
(sleep 2 && rm \${0})&

EOF

chmod 755 ../pubmvnsite.$$
../pubmvnsite.$$ "$(pwd)" &

