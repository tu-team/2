
# Pandora product and prototype description.


## Introduction

Pandora product is problem processing system. 
It represents virtual personality with only one goal to help user. Virtual personality are trained to resolve only IAS problems now, starting from monitoring of WINS, Posix, Storage systems till simple investigation/analysis of infrastructure incidents. Virtual personality is new type of service that reacts as human specialist in social environment and has cognition mechanism similar to human, therefore has emotions and exploits human thinking model. Virtual personality could be trained to solve problems in different domains except for IAS, for example: accounting, health care, enterprise resource processing.

According to our estimates approximately 60 percent of problems processed daily by IS support specialists are primitive and could be processed by systems with machine understanding. To build machined understanding we exploit human thinking model with base approach described in Marvin Minsky Book the [Emotion Machine](http://en.wikipedia.org/wiki/Emotion_machine).

Current prototype is feasibility study of the human thinking model approach. It was created to test the virtual personality capabilities to process problems with no human interaction.

## Product Description

Pandora virtual personality is new type of product. It is not peace of software it is human mind build in stored and distributed in cloud with following functions:

 1. Dialog support
     1. English request understanding.
     1. Clarification requests.
     1. Confirmation requests.
     1. Escalations of to hard to solve problems.
 1. Training:
     1. Training domain(s) concepts.
     1. Training problem solving how-to-s.
 1. Operation:
     1. Find solution.
     1. Apply found solution.

Virtual personality was created to perform repetitive and primitive operations over IT systems. Virtual personality is capable of human like thinking thus understanding and collaboration with human operators in dialog mode. Virtual personality behaviour is based on one goal to help user solve his/her problem as much effective as possible.

Virtual personality collaborate with several human experts:

 1. Domain user - virtual personality user, that uses system in the boundaries of one domain.
 1. Domain trainer - domain expert capable of training the virtual personality domain concepts and their links.
 1. How-to trainer - human technical support specialist capable to train virtual personality methods and resources to solve domain user problems.

### Key Product Features and Capabilities

Pandora virtual personality is capable of three main activities:

 1. Communicate and collaborate with human specialist in dialog mode.
 1. Be trained and train domain knowledge and problem solution how-to-s.
 1. Find most probable solution.
 1. Apply found solution over target system.

Virtual personality uses human thinking model to understand and collaborate with human users, experts and specialists. Emotions and feelings play significant role in social collaboration and thinking. For example training uses emotions to emphasize solution application effect and is used as natural machine learning reinforcement. Emotions are used to control the virtual personality behaviour, for example in time control: to switch from neutral to anxious state and get more thinking resources. Virtual personality behaviour is mainly defined in Knowledge Base, that consists of two main parts: short term memory, and long term memory. Short term is used to store current problem context and long term is persistence storage.

### Major Components (Hardware/Software)

![Main component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/MainComponent.png)

 1. [Thinking life cycle](design-specification/thinking-life-cycle.md)
 1. [Selector](design-specification/selector.md)
 1. [Critics](design-specification/critics.md)
 1. [Way to think](design-specification/way2Think.md)

ThinkingLifeCycle starts and stops internal processes. Selector retrieves Resources(Critic, Way to think) using Critic request. Critic is main analytical module of short term memory. Way to think actually changes contents of short term memory.

### Target Market/Customer Base

Who/what is if for – i.e. individuals, industry, environment – and why. Live for A.K input

## Pandora Product Prototype Description

Pandora prototype is feasibility study for simple incident processing workflow:

 1. Understand incident description.
 1. Classify and simulate described in incident situation.
 1. Find proper solution from trained knowledge base.

and training workflow:

 1. Understand training request text.
 1. Find corresponding concepts in trained knowledge base.
 1. Attach concepts of training request to concepts in knowledge base.

To process two types of request following [Ways to think](design-specification/way2Think.md) and [critics](design-specification/critics.md) were created:

 1. Natural language processing based on [RelEx](http://wiki.opencog.org/w/RelEx).
 1. Incident classification critics.
 1. Simulation.
 1. Reformulation.
 1. Correlation.
 1. Solution search.

To process [KMOT](kmot.md) following ways to think and critics to be designed and developed:

 1. Probabilistic reasoner based on [PLN](http://wiki.opencog.org/w/PLN)
 1. Analytical way to think.
 1. Smart solution applicator.
 1. Recommendation generator.
 1. Project documentation generator.

Current prototype could be used as the base for end product but requires architectural refactoring as well as technical extensions of components.

### Prototype Functional Goals and Objectives

Prototype is capable of following functions:

 1. Dialog support:
   2. English request understanding.
   2. Clarification requests.
 1. Training:
   2. Training domain(s) concepts.
   2. Training problem solving how-to-s.
 1. Operation:
   2. Find solution.

### Prototype Architecture (Hardware/Software)

#### All System Interfaces

All operations and training is done via dialog with the system in English, grammatically and logically correct, though system has several mechanisms for grammatical and semantical auto-correction.
Main interface of the system is natural language English. Both training and operation are implemented via one Instant Messenger like GUI. All communications with the system is done in dialog, system processes the inbound support request and clarifies all obscure unclear phrases and concepts. Human operator provides clarifications and then system process clarifications till it becomes able to find proper solution. Training is done in similar way: operator provides training request explaining new concepts, solution how-to-s. Clarification is used for training of the system also.

Server part is web-service with two methods:

 1. apply(Request)
 1. apply(TrainingRequest)

For operational and training modes.

#### Logical view

![GUI design](https://raw.github.com/development-team/2/master/doc/design-specification/uml/images/PrototypeComponent.png)

##### Architecturally significant design packages

 1. [Thinking life cycle](design-specification/thinking-life-cycle.md)
 1. [Selector](design-specification/selector.md)
 1. [Critics](design-specification/critics.md)
 1. [Way to think](design-specification/way2Think.md)

#### User request processing

[Lifecycle example, activity diagram.](https://github.com/development-team/2/blob/master/doc/design-specification/lifecycle-activity.md)

#### User interface design

Client GUI for the prototype is done like Instant Manager to communicate with virtual personality.

![GUI design](https://raw.github.com/development-team/2/master/doc/design-specification/ui-prototype/images/TU-Web.png)

#### Knowledge data model
[Knowledge model](https://github.com/development-team/2/blob/master/doc/design-specification/knowledge.md)

###### Memory

[Memory](https://github.com/development-team/2/blob/master/doc/design-specification/memory.md)

####  General design decisions

Whole design is build around concept of short living lose coupled parallel processes that collaborates via common short term memory.
Short term memory is merged in long term memory after confirmation via learning,
Machine learning is implemented in long term memory via [deductive](http://en.wikipedia.org/wiki/Deductive_reasoning), [inductive](http://en.wikipedia.org/wiki/Inductive_reasoning), and [abductive](http://en.wikipedia.org/wiki/Abductive_reasoning) reasoning.

#### Implementation languages and tools

##### Third party components

 1. [PLN](http://wiki.opencog.org/w/PLN) – Probabilistic Logic Networks. An logic system for uncertain inference. [GNU Affero license.](http://www.gnu.org/licenses/agpl-3.0.html)
 1. [RelEx](http://wiki.opencog.org/w/RelEx) – Extract grammatical parses and semantic knowledge from natural language (in English).[Apache License](http://bazaar.launchpad.net/~relex-dev/relex/trunk/view/head:/LICENSE)

#### Programming languages

 1. [Scala](http://www.scala-lang.org/)

#### Tools

 1. [IntelliJ Idea](http://www.jetbrains.com/idea/)
 1. [Maven](http://maven.apache.org/)
 1. [NEO4J](http://neo4j.org/) - No SQL data base.
 1. [GlassFish](http://glassfish.java.net/) server

###Prototype Development Challenges solved

New approach for the machine understanding based on thinking model and new type of software architecture was tested. Main architectural challenge was old-fashioned approaches to design and build application. This is new type of the application that could not be solved in traditional way and requires rethinking of architectural approaches.
Most significant challenge expected is performance that should be similar or higher than human specialist, 
this means that system can not deal with IS incident longer than ordinary IS specialist. 
Second challange is training, that should take approximately same time as training of human IS specialist .



