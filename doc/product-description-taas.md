#Pandora product and prototype description.


##Introduction

Pandora product is problem processing system. It represents new approach to service oriented business model. It also provides remote infrastructure managment like RIM, IaaS model, e.t.c. However, in different way: Pandora inroduces Technology as a Services. 

##Product Description

Pandora - system that leverage Machine Understanding theory into the main application of such a theory in order to develop solutions with IT infrastructure capabilities. Main goal is to create machine understanding mechanism for explaining what is to comprehend, to understand and to mean, in a IT domain with delivered solution for  a wide variety of core Infrastructure utility Services, such as hosted cloud, storage, server, collaboration and more, governed by an advanced Pandora  IT Knowledge Base. 

Pandora is not software product in common sense as it suppose to be. Pandora is a technology that provided as a Service to your customers. For example, if you support remote infrastructure of your customer we will provide Pandora system to you, which was trained by your support personal and will act as technology support specialist. 

Pandora support nexts functions:

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

Pandora can be trained with knowledges by human specialists, can interacts with human specialist while problem solving, if it can solve it by itself.

Pandora collaborates with several human specialists:

 1. Domain user - virtual personality user, that uses system in the boundaries of one domain.
 1. Domain trainer - domain expert capable of training the virtual personality domain concepts and their links.
 1. How-to trainer - human technical support specialist capable to train virtual personality methods and resources to solve domain user problems.

###Key Product Features and Capabilities

Pandora is capable of three main activities:
 1. Communicate and collaborate with human specialist in dialog mode.
 1. Be trained and train domain knowledge and problem solution how-to-s.
 1. Find most probable solution.
 1. Apply found solution over target system.

Pandora can be compatible with modern RIM systems like:
 1. HP Open View
 1. LANDesk Management Suite
 1. System Center Configuration Manager 
 1. others
 
###Major Components (Hardware/Software)

![Main component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/MainComponent.png)

 1. [Thinking life cycle](design-specification/thinking-life-cycle.md)
 1. [Selector](design-specification/selector.md)
 1. [Critics](design-specification/critics.md)
 1. [Way to think](design-specification/way2Think.md)

ThinkingLifeCycle starts and stops internal processes. Selector retrieves Resources(Critic, Way to think) using Critic request. Critic is main analytical module of short term memory. Way to think actually changes contents of short term memory.

###Target Market/Customer Base

 1. Remote Infrastructure Managment
 1. Remote Infrastructure Support
 1. Companies that do not want to support they infrastuctures by they employies

##Pandora Product Prototype Description

TU prototype is feasibility study for simple incident processing workflow:

 1. Understand incident description.
 1. Classify and simulate described in incident situation.
 1. Find proper solution from trained knowledge base.

and training workflow:

 1. Understand training request text.
 1. Find corresponding concepts in trained knowledge base.
 1. Attach concepts of training request to concepts in knowledge base.

To process two types of request following ways to think and critics were created:

 1. Natural language processing based on [RelEx](http://wiki.opencog.org/w/RelEx).
 1. Incident classification critics.
 1. Simulation.
 1. Reformulation.
 1. Correlation.
 1. Solution search.

To process [KMOT]() following ways to think and critics to be designed and developed:

 1. Probabilistic reasoner based on [PLN](http://wiki.opencog.org/w/PLN)
 1. Analytical way to think.
 1. Smart solution applicator.
 1. Recommendation generator.
 1. Project documentation generator.

Current prototype could be used as the base for end product but requires architectural refactoring as well as technical extensions of components.


###Prototype Functional Goals and Objectives

Specific functional objectives the prototype will demonstrate and why these are significant Environment- show customer how we can do it, easy to use, intuitive, etc.  Input, Output.  Table of comparison between Current systems and TU Prototype either in here or next section.

TU prototype will cover next functions:

 1. Incident processing in natural language
 1. Training mode
 1. Solution applicator
 1. Ability to integrate with existing incident systems
 1. Ability to integrate with existing solution application systems 

#### All System Interfaces

All operations and training is done via dialog with the system in English, grammatically and logically correct, though system has several mechanisms for grammatical and semantical auto-correction.

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

Client GUI for the prototype is done as Instant Manager to communicate with virtual personality.

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

Most significant challenge expected is performance that should be human specialist like, this means that system can not deal with IS incident longer than ordinary IS specialist. Second is training, that should take approximately same time as training of human IS specialist .


