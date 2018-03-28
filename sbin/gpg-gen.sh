#!/usr/bin/env bash

gpg --no-default-keyring \
  --primary-keyring `pwd`/project/.gnupg/pubring.gpg \
  --secret-keyring `pwd`/project/.gnupg/secring.gpg \
  --keyring `pwd`/project/.gnupg/pubring.gpg \
  --fingerprint \
  --import `pwd`/project/.gnupg/my-key-all.asc
