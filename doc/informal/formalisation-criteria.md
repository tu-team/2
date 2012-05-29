#Formalisation criteria
Perceiving mechanism has main output the formalised incident description.

## Direct instruction formalisation

### In
Please install Firefox version 7.0.1 on machine agentSmith on drive C.

### Out

 1. Action = install
   - Software:
     - Product = Mozilla FireFox.
     - Version = 7.0.1
   - Destination:
     - Network path = agentSmith
     - Drive = C
   - Operating system = Micrsoft Windows

## Problem description
Problem description contain huge amount of variations of the descriptions, thus system must be capable of processing
(perceiving) them based on concepts from Knowledge base and thinking process, [see section Formalisation Production Workflow](https://github.com/development-team/2/blob/master/doc/informal/emotion-machine.md).

### In
I ordered Wordfinder Business Economical. However I received wrong version, I received Wordfinder Tehcnical instead of Business Economical.
Please assist.

### Out

#### Formalized model

 1. Desired:
   - Software:
     - Product = Wordfinder
     - Version = Business Economical
 1. Wrong(currently installed):
   - Software:
     - Product = Wordfinder
     - Version = Tehcnical

#### Derived model

 1. Action = install
   - Software:
     - Product = Wordfinder.
     - Version = Business Economical
   - Destination:
     - Network path = to be clarified.
     - Drive = to be clarified.
   - Operating system = to be clarified.
 1. Action = uninstall.
   - Software:
     - Product = Wordfinder.
     - Version = Tehcnical
   - Destination:
     - Network path = to be clarified(from extract above).
     - Drive = to be clarified(from extract above).
   - Operating system = to be clarified(from extract above).

