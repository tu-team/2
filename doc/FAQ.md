## Q1

I have error UnsatisfiedLinkError in Ubuntu while trying to compile project

A: Ubuntu has problem with LD_LIBRARY_PATH. Please execute in command prompt
```export LD_LIBRARY_PATH=/usr/local/lib```
or add this to .bashrc file   (/etc/bash.bashrc)
To run tests from idea add following parameter to VM parameters:```-Djava.library.path=/usr/local/lib```

## Q2 Proxy settings

How to setup proxy.

### A:

In host project create proxy.conf file with following content:
```
proxyHost = ru100279159
proxyPort =3128
useProxy = true
```
Replace values for suitable for you. If file doesn't exist direct connection will be used.
