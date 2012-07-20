#Q1

I have error UnsatisfiedLinkError in Ubuntu while trying to compile project

A: Ubuntu has problem with LD_LIBRARY_PATH. Please execute in command prompt
```export LD_LIBRARY_PATH=/usr/local/lib```
or add this to .bashrc file   (/etc/bash.bashrc)
To run tests from idea add following parameter to VM parameters.
```-Djava.library.path=/usr/local/lib```