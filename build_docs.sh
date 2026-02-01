#!/bin/bash

set -ex

# Generate the API docs
./gradlew dokkaGenerate

mkdir -p docs/api
mv mvvmframe/build/dokka/html/* docs/api

# Copy in special files that GitHub wants in the project root.
GITHUB_URL=https://github.com/jenly1314/MVVMFrame/
echo $GITHUB_URL
sed "/<!-- end -->/q" README.md > docs/index.md
sed -i "s|app/src/main/ic_launcher-web.png|ic_logo.png|g" docs/index.md
sed -i "s|](app|](${GITHUB_URL}blob/master/app|g" docs/index.md
sed -i "s|](mvvmframe|](${GITHUB_URL}blob/master/mvvmframe|g" docs/index.md
cat CHANGELOG.md | grep -v '## 版本日志' > docs/changelog.md

cp app/src/main/ic_launcher-web.png docs/ic_logo.png
mkdir -p docs/art
cp art/mvvm_architecture.jpg docs/art/mvvm_architecture.jpg

# Build the site locally
mkdocs build
