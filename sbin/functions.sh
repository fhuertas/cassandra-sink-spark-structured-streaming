#!/usr/bin/env bash


setup_git() {
  git config user.email "travis@travis-ci.org"
  git config user.name "Travis CI"
}
un_setup_git() {
  git config user.email "francisco@fhuertas.com"
  git config user.name "Francisco Huertas"
}


my_test() {
echo "--$1--"

}

read_version_no_snapshot() {
    VERSION=$(cat version.sbt)
    VERSION=${VERSION/-SNAPSHOT\"/}
    VERSION=${VERSION#*\"}
    echo ${VERSION/-SNAPSHOT/}
}

write_version() {
    VERSION=$1
    echo "version := \"${VERSION}\"" > version.sbt
}
create_mayor_version() {
    # TODO en travis!
    GH_REPO=github.com/fhuertas/cassandra-sink-spark-structured-streaming
    VERSION=$(read_version_no_snapshot)
    IFS='.' read -r -a array <<< ${VERSION}
    NEXT_BRANCH="branch-${array[0]}.${array[1]}"
    NEXT_VERSION=$((${array[0]} + 1 )).${array[1]}.${array[2]}
    PREV_BRANCH=$(git rev-parse --abbrev-ref HEAD)
    echo "Current version: ${VERSION}"
    echo "Current branch"
    echo "Next mayor version: ${NEXT_VERSION}"
    echo "Creating new branch: ${NEXT_BRANCH}"
    set -x
    git checkout -b ${NEXT_BRANCH}

    write_version "${VERSION}-RC0-SNAPSHOT"
    setup_git
#    git commit -a -m "Travis: Version branch"
#    git push origin ${NEXT_BRANCH}
    git checkout ${PREV_BRANCH}
    write_version "${NEXT_VERSION}-SNAPSHOT"
    git commit -a -m "Travis: Up master version"
    git push "https://6b8e18657700683c6b9e4f3b9cd86a640dd133b6@${GH_REPO}" ${PREV_BRANCH}
    un_setup_git
    set +x
}

publish() {
   VERSION=$(read_version_no_snapshot)
}
