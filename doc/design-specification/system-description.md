# Thinking and understanding (TU).
Problem solving automation system based on machine understanding and solution search.

## Preface

There are several industrial systems for support and maintenance in IT: RMOSs (Remote management operating system),  NSMs (Network and system management),
SMS (Server management  system), DM (database management), etc.
They are human-operated tools usually used as monitoring, classification and tracing help desk requests.
These systems are capable of: server/storage/network operation and errors analysis and they provides with total system control and operational stability that includes storage and network as well as servers.
IT infrastructure operations staff then use outputs produced by  RMS-es and NMS-es, DM and SMS by monitoring  the whole system and pin-point server, environment problems.
Gathering information and analysis via management console for farther decision making and operating over target system.
TU is end–to–end solution automation system not as classical system control and operational stability platform but dedicated to decision making, problem solving and operating in target environment.
It is limited human-aided system, within which TU humans-operators are capable of training, obtaining clarification of problem description and solution confirmation.
Not require many technical support specialist which deals with the nitty-gritty of troubleshooting and problem solving, using specialized technical knowledge to provide technical help.

## Introduction

TU provides automation(problem finding and resolution mechanism via integration with third party executable tools) and reporting facility for problem processing in SE(Software engineering)
and IT-Telecom in integration with RMS-es and NMS-es.

Primary function is to provide end–to–end problem’s resolution mechanism for businesses, its infrastructure, system and all the technology needed to run it.
Common problems that TU address are: connectivity-the user cannot reach data or gain access to it; missing data-data cannot be found; slow performance-excessive amounts of users are slowing down the system;
overload-lack of space on a machine for data; or program problems-the program is not running efficiently; and etc.

### Purpose

The purpose of this document is to describe TU-functional operation and capabilities and identify top level information a prospective
Companies would require support for their hardware and equipment in order to manage and operate its IT business environment.

Identified below are several technology areas TU was designed to support.

 1. RIM(Remote Infrastructure Management)
 1. NOC(Network operations center)
 1. SE(Software engineering) maintenance
   2. Telecom maintenance
   2. Data storage maintenance

### Scope

The scope of this document covers functional description and architectural model of the TU and corresponding systems. It provides information necessary for training and operation of TU system.

## TU system overview

TU system is comprised of operational units such Training subsystem, Operating subsystem and Knowledge Base. TU Operating subsystem is integrated with target(customer) system
via smart operating mechanism described below.

Training subsystem is capable of machine learning of a system of domain and common sense knowledge. Domain information is: problem symptoms, possible solutions,
problem solution time limits (SLAs), main domain concepts like software, network, internet, browser, data base table etc.

Operating subsystem is capable of understanding problem descriptions and retrieving proper solution, clarifying problem description and processing such clarification,
implementing found solution over target(customer) system via smart operating mechanism.

Knowledge base is the main storage that contains all data and is capable of reporting of every
action performed/executed by the system.

![System overview](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/system-overview.png)

TU is designed to operate with RMOS, SMS, DM, and NSM systems via integration with standard systems interfaces, machine understanding of problem description,
using trained data, solution search of trained solutions, application of a solution taking into an account target system feedback.

### Smart operating mechanism

Smart operating mechanism is a way TU system implements found solution over target system taking into account operating environment feedback. For example, solution is
to Install FireFox on James Bond's machine. System attempts to use install software script with FireFox parameter over James Bond machine.
If the script fails with "can not access James Bond machine" error, TU attempts to clarify the address with a human operator.

### Features

 0. Machine understanding of problem description.
 0. Clarification and confirmation of unclear and ambiguous data.
 0. Solution search based on problem description.
 0. Confirmation of found solution.
 0. Domain terms and concepts training.
 0. Time control.
 0. Solution relevance control.
 0. Emotion state management and control.

### Components

![Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/components-highlevel.png)

TU system comprises Interface components that provide web-service interface to TU system, Core components that support Training and Operating capabilities,
and Natural language processing components that provide basis for machine understanding of problem description text.

*Interface components* provide proper way of communication of TU system with client systems, currently implemented as  [web-service](http://en.wikipedia.org/wiki/Web_service).
Interface components check and translate inbound information into TU internal representation and transfer data to Core components.
Internal representation is [Semantic network](http://en.wikipedia.org/wiki/Semantic_network).

*Core components* are part of main system implement features listed above. Core components use several external systems:
[NLP(Natural language processing)](#natural-language-processing-components) systems supplemental for machine understanding.
KB(Knowledge base) is the main TU system storage currently it is [neo4j](http://neo4j.org/)

![Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Component.png)

 0. Interface components
   1. [TU webservice](system-description.md#tu-webservice)
   1. [ClientAgent](system-description.md#tu-webservice)
 0. Core components
   1. [MessageBus](#message-bus)
   1. CoreService
     2. [ThinkingLifeCycle](#thinkinglifecycle)
     2. [Selector](#selector)
     2. [Critic](#critic)
     2. [Way2Think](#way2think)
   1. [Reasoner](#reasoner)
 0. [Natural language processing components](#natural-language-processing-components)
   1. Lexical Parser
   1. Preliminary Splitter
   1. KnowledgeBase Annotator
 0. Data components
   1. [KnowledgeBase](knowledge-base.md)


#### TU webservice
It is a main entry point of the system and supports following base functions:

 0. Create request
 0. Create clarification response
 0. Create confirmation response
 0. Train system
 0. Monitor solutions

#### ClientAgent

It is a software for service machines that have access to different locations and hold certain number of scripts. ClientAgent contains Executor -
an intellectual agent responsible implementation of a found solution by the operating target system to implement found solution.
Executor is capable of requesting help if gets stuck, or receives unexpected response.

#### Message bus

Is main component used for components collaboration. It uses messaging capabilities for components event notification.
Adapter to 3rd party component, with messaging functionality and it is implemented as a 3rd party component [Glassfish message bus](http://en.wikipedia.org/wiki/GlassFish).

### ThinkingLifeCycle

It is a main component that makes the entire understanding process and solution search work. It implements environment for thinking processes used by
machine understanding and solution search, as well as several control processes.

![ThinkingLifeCycle Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/ThinkingLifeCycleComponent.png)

Critic analyses the problem context and invokes Selector to find proper resource in the KB, Selector can retrieve two types of resources: Critic or a Way2Think(way to think).
Way2Think actually changes data in the context of a current problem.

ThinkingLifeCycle controls Critic - Selector collaboration and Way2Think start and stop. This approach is based on [Marvin Minsky Thinking model](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708572)

Current simplified machine understanding and solution search workflow is represented below:

![Simplified ThinkingLifeCycle activity](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/simplifiedactivity.png)

Main thinking processes implemented in TU:

 0. [Problem description text pre-processing](#problem-description-text-pre-processing)
 0. [Problem classification](#problem-classification)
 0. [Solution search/generation](#solution-searchgeneration)
 0. [Context management](#context-management)
 0. [Goal management](#goal-management)
 0. [Clarification requests processing](#clarification-request-processing)
 0. [Making sense analyser](#making-sense-analyser)
 0. [Emotional state control](#emotional-state-control)

#### Problem description text pre-processing

Via [NLP](#natural-language-processing-components) tools a problem description text is being translated in to the [semantic network](http://en.wikipedia.org/wiki/Semantic_network) construct.

#### Problem classification

Based on a text contained in a description a problem is classified as one of the following: Direct instruction, Problem description with desired state, Problem description without desired state.

#### Solution search/generation

According to a processed problem description (now as semantic network construct) trained Knowledge base is searched for a solution, or a compound solution is generated using partial solutions of
trained Knowledge Base.

#### Context management

It is a process capable of problem context creation and maintenance of problem context through clarification and confirmation dialogs with a human operator.

#### Goal management

TU uses goal oriented processes to implement machine understanding. Goal management is a process of searching proper goals for current state of the system.
There are some goals examples used in the system:

 0. Help user to solve the problem
   1. Understand problem source
     2. Classify problem
   1. Generate solution

#### Clarification request processing

TU is capable of requesting clarification of unclear and incomplete parts of problem description. Human operator specifies clarification
response which is then processed by the  system processes clarification response within original request context.

#### Making sense analyser

Once machine understanding process a problem description, TU checks whether current understanding makes sense according to trained domain information contained in
Knowledge base.
For example problem description: "I can not send e-mails via Google". This should be considered as description that doesn't make sense and should be clarified if Gmail is the one that request refers to.

#### Emotional state control

Time control and reinforcement learning uses emotional state of the system. Emotional state control changes entire state of the system according
to a feedback from human operator regarding a solution, received during training or time left to process a problem.

Emotions examples:

 0. Fear
 0. Alarm
 0. Anger
 0. Anxiety
 0. Affection
 0. Contentment

### Selector

It is component that retrieves resources(Critics or Way2Think) from Knowledge Base. Selector uses Critics requests and [Goals](system-description.md#goal-management).
Selector also uses storage. Currently it is No SQL database [Neo4j](http://neo4j.org/). Selector is tightly coupled with Critic.

### Critic

It is component is mainly used for analysing processed data in the context of a current problem solution. It's output is probability that checked condition is true.
There are several types of Critic:

 0. Analyser - is used to parse current context and return probability and confidence of positive result.
 0. Controller - is used to trace certain parameter for example time and alter system state, for example emotional state.
 0. Manager - is used to generate meaningful Selector request to retrieve proper resource.

### Way2Think

It is a group of components used to actually implement modifications over data in a current problem context.
Typical Ways to think:

 0. Simulation - creates a simulation model described in problem description
 0. Reformulation - translates simulated model into different representation
 0. Cry for help - applies to a human operator
 0. Logical Reasoning. - infers certain logical facts based on inbound data

### Reasoner

It is an interface to 3rd party component that implements Logical Reasoning (currently it is a 3rd party component [PLN](http://wiki.opencog.org/w/PLN)).


### Natural language processing components

This group of components is dedicated to processing a problem description into into a semantic network output. Preliminary splitter extracts sequence of words from inbound text.
Lexical parser creates lexical pair of words. KnowledgeBase Annotator maps domain concepts from Knowledge Base to words from inbound text.

Simplified natural language processing workflow.

![Simplified NLP](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/simplifiednlp.png)

### Data components

System stores all data as reusable objects in Knowledge Base. Knowledge Base is implemented via No SQL database Neo4j.

Main data types:

 0. Textual problem description
 0. Semantic network of machine understanding of problem
 0. Simulation semantic network
 0. Trained data
   1. Problem symptoms
   1. Problem solutions

## Environment requirements.

Linux compatible OS (Ubuntu is recommended).

At least 6 GB of RAM, x64 architecture, according to user load number of servers could be increased. Intel Core I7 processor is recommended.

Minimal Requirements: Core i3, 2 Gb Ram, x86 architecture.

## Systems integrations.

TU provide Integration with both 3rd party applications (actuating mechanism or problem executive tools) and end IT customer’s systems.
Analyst specifies applications, protocols, communication channels and other constraints to be integrated by TU.
TU according to constraints (protocols and application interfaces) generates optimal integration solution without human abstraction layers performance overhead.



