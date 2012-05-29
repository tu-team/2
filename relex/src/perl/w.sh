#! /bin/sh

cat /home/linas/src/novamente/data/simple-wiki/simplewiki-20080629-pages-articles.xml.bz2 | bunzip2 | ./wiki-scrub.pl
