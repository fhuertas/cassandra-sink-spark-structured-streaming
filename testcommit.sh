git commit -a --amend -m "Test"
git tag -a tt${1} -m ${1}
git push origin tt${1}
