# Reasoner design specification.

Reasoner is main probabilistic logic reasoning component. It is used mainly in Deliberative [critics](critics.md)

## Interface

![Interface diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/ReasonerInterface.png)

## apply(request : ReasonerRequestNode) : ReasonerRequestNode

![Interface diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/reasonerapplyrequestReasonerRequestNodeReasonerRequestNode.png)

## Input
ReasonerRequest as expressions list.

## Output
probability/confidence of the ReasonerNode that represents the Reasoner result.



## JNI-PLN development comments.
1. The POM files are currently done for Linux only. I don't have a good Windows environment to create similar ones for Windows.
04:19:31 PM
2. The POM files need to list all the Java JNI interface classes one-by-one. Of course there are already implemented examples.
04:20:04 PM
The Java side classes need "Handle" members to contain the C++ pointer to the object as a long value. Only works for 64-bit environments.
04:20:43 PM
So that when a C++ side is called, it can get the reference to the C++ object by reading the Java object "handle" attribute, and then casting it back to the pointer.
04:21:25 PM
Also, when C++ side objects are created, the pointers need to be stored to the same Java-side handle member.
04:21:46 PM
Again, there are lots of examples already.
04:22:00 PM
The memory management is interesting. We need a way to release memory. For this, we must always explicitly call C++ side destructors, or else the memory will leak.
04:22:46 PM
There is no implementation for this yet. But I was thinking using Java-side finalize methods to also guarantee that the Java garbage collector releases the C++ side objects also.
04:23:29 PM
And of course, using the JNI PLN has some examples in the automatic tests. Although currently they are not very extensive yet. The only thing that passes is the environment set up.
04:24:50 PM
