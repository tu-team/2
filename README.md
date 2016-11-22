2
=

thinking understanding project repository

#Mac OS X Installation notes#
* Install xQuartz
* Install LinkGrammar (make install, not checkinstall)
* Make symbolic links for java.path and wordnet
 mkdir /Users/toschev/Library/Java/
 mkdir /Users/toschev/Library/Java/Extensions
 sudo ln -s /usr/local/lib/* /Users/toschev/Library/Java/Extensions

* sudo ln -s ''/usr/local/lib/* /usr/lib/java''
* sudo ln -s /usr/local/WordNet-3.0/dict/* /usr/share/wordnet/
* sudo ln -s /usr/local/WordNet-3.0/bin/* /usr/local/bin/


#1.2#
Version Notes
-Compatible with OpenCog Relex 1.4.1, Link Grammar 4.7.14, WordNet 3.0-3.1
