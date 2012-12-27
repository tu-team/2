#Pandora product and prototype description.


##Introduction

Pandora product is problem processing system. 
It represents virtual personality with only one goal to help user. Virtual personality are trained to resolve only ITSM problems now, starting from monitoring of WINS, Posix, Storage systems till simple investigation/analysis of infrastructure incidents. Virtual personality is new type of service that reacts as human specialist in social environment and has cognition mechanism similar to human, therefore has emotions and exploits human thinking model. Virtual personality could be trained to solve problems in different domains except for ITSM, for example: accounting, health care, enterprise resource processing.

According to our estimates approximately 60 percent of problems processed daily by IS support specialists are primitive and could be processed by systems with machine understanding. To build machined understanding we exploit human thinking model with base approach described in Marvin Minsky Book the [Emotion Machine](http://en.wikipedia.org/wiki/Emotion_machine).

Current prototype is feasibility study of the human thinking model approach. It was created to test the virtual personality capabilities to process problems with no human interaction.

##Product Description

Pandora virtual personality is new type of product. It is not peace of software it is human mind build in stored and distributed in cloud with following functions:

 1. Dialog support
   2. Clarification requests.
   2. Confirmation requests.
   2. Escalations of to hard to solve problems.
 1. Training:
   2. Training domain(s) concepts.
   2. Training problem solving how-to-s.
 1. Operation:
   2. Find solution.
   2. Apply found solution.

Virtual personality was created to perform repetitive and primitive operations over IT systems. Virtual personality is capable of human like thinking thus understanding and collaboration with human operators in dialog mode. Virtual personality behaviour is based on one goal to help user solve his/her problem as much effective as possible.

Virtual personality collaborate with several human experts:

 1. Domain user - virtual personality user, that uses system in the boundaries of one domain.
 1. Domain trainer - domain expert capable of training the virtual personality domain concepts and their links.
 1. How-to trainer - human technical support specialist capable to train virtual personality methods and resources to solve domain user problems.

###Key Product Features and Capabilities

Pandora virtual personality is capable of three main activities:
 1. Communicate and collaborate with human specialist in dialog mode.
 1. Be trained and train domain knowledge and problem solution how-to-s.
 1. Find most probable solution.
 1. Apply found solution over target system.

Virtual personality uses human thinking model to understand and collaborate with human users, experts and specialists. Emotions and feelings play significant role in social collaboration and thinking. For example training uses emotions to emphasize solution application effect and is used as natural machine learning reinforcement. Emotions are used to control the virtual personality behaviour, for example in time control: to switch from neutral to anxious state and get more thinking resources. Virtual personality behaviour is mainly defined in Knowledge Base, that consists of two main parts: short term memory, and long term memory. Short term is used to store current problem context and long term is persistence storage.

###Major Components (Hardware/Software)

![Main component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/MainComponent.png)

[ThinkingLifeCycle](thinking-life-cycle.md)
 2. [Selector](design-specification/selector.md)
 2. [Critics](design-specification/critics.md)
 2. [Way2Think](design-specification/way2Think.md)



Overview of how it is structured to do whatever it does – based on, and breakdown of software algorithms and flow component’s processes.

###Target Market/Customer Base

Who/what is if for – i.e. individuals, industry, environment – and why. Live for A.K input

##Pandora Product Prototype Description

Top level description of the TU prototype as it relates to the end product TU 0.1 prom release – goal.  Are capabilities reduced or eliminated? Simulated – modeled? Readiness for final development.

###Prototype Functional Goals and Objectives

Specific functional objectives the prototype will demonstrate and why these are significant Environment- show customer how we can do it, easy to use, intuitive, etc.  Input, Output.  Table of comparison between Current systems and TU Prototype either in here or next section.

###Prototype Architecture (Hardware/Software)

How will the prototype be structured to demonstrate key features of the TU 0.1 product.  Prototype TU provided and described.
Including:

1. All System Interfaces
1. Logical view (UMLenough)
1. Architecturally significant design packages: major - boundary classes, realization (logic view), process /task view (for example show Process view for end user submission  incident and RealTime threads and mechanism for problem solving and realization on UML diagrams (patterns) with steps descriptions
1. Deployment view (UMLenough)
1. System description (Node 1-2-3…etc)
1. Implementation view (overview, component description, KB model and etc)
1. General design decisions (architectural goals and constrains) – example: The architectural goals are to make the TU system modifiable and provide high performance and blah blah…  The TU system should be encapsulated so it's easy to port the system to different platform blah…blah…
1. Architectural patterns
1. User interface design
1. Implementation languages and tools
1. Installation and executing

###Prototype Features and Capabilities

What does the prototype demonstrate – why is that significant in showing how the problem is solved – or how we can demonstrate success. How does the prototype address the TU prom release 0.1 project risk mitigation?

###Prototype Development Challenges solved

Challenges expected solved such completing the objectives of the prototype – ie knowledge missing, capability missing, supporting technology issues, etc.

##Glossary

##References


