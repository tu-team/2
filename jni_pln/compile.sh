#!/bin/sh
mvn clean compile -P linux
LD_PRELOAD=../lib/libatomspace.so:../lib/libpln.so:../lib/libserver.so LD_LIBRARY_PATH=$LD_LIBRARY_PATH:../lib/:../native_lib/linux/target/ mvn install -P linux

