#! /bin/sh 
#
cat /home/linas/src/novamente/data/simple-wiki/simplewiki-20080629-parsed-gz/Zwischbergen.xml.bz2 |bunzip2 | ./cff-to-opencog.pl
