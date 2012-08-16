# Thinking and understanding (TU).
Problem solving automation system based on machine understanding and solution search.

## Preface

There are several industrial systems for support and maintenance in IT: RMS-es,  NMS-es, HP OpenView, etc.
They are usually used as synchronisation, monitoring, classification and tracing human operating tool.
This systems are capable of: network errors analysis, provides infrastructure for business coordination, etc.
Humans are capable of decision making and operating over target system using information and analysis of RMS-es and NMS-es.
TU in problem solution automation system dedicated for decision making and operating in target environment.
TU is human aided system, within TU humans are capable of training, clarification of problem description, solution confirmation.


This document describes the functional operation of the TU and provides a top level overview of information Also included
is an overview of the TU operating environment to provide the user a holistic understanding to utilize the TU.

## Introduction

TU project will provide automation and reporting facility for SE, IT-Telecom problem processing in integration with RMS-es and NMS-es.
TU comprises smart operating mechanism over target(customer).

### Purpose

The purpose of this document is to describe the functional operation and capabilities of the TU and identify top level information a prospective user would require in
order to manage and operate the system. Identified below are several technology areas the TU was designed to support.

 1. RIM(Remote Infrastructure Management)
 1. NOC(Network operations center)
 1. SE maintenance
   2. Telecom maintenance
   2. FIS system maintenance
   2. HIS systems maintenance
 1. Accounting systems

### Scope

The scope of this document covers the TU and corresponding systems. It provides the information necessary for training and operating TU system.

## TU system overview

TU system is comprised of Training subsystem, Operating subsystem and Knowledge Base. TU Operating subsystem is integrated with target(customer) system
via smart operating mechanism described below. Knowledge base is main storage of the TU system that stores all data and is capable of reporting of every
action done by the system.

![System overview](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/system-overview.png)

TU is designed to operate with RMS and NMS systems via integration with standard systems interfaces, machine understanding of problem description,
using trained data, solution search of trained solutions, application of solution taking an account feedback of target system.

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

![Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Component.png)

TU system comprises of Interface components that provide web-service interface to TU system, Core components implements Training and Operating capabilities,
Natural language processing components provides basis for machine understanding of problem description text.

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
Is main entry point of the system to implement base functions:

 0. Create request
 0. Create clarification response
 0. Create confirmation response
 0. Train system
 0. Monitor solutions

#### ClientAgent

Software for service machines, that have access for different locations and hold some amounts of scripts. Contains Executor.
Executor is intellectual agent responsible for the operating target system to implement found solution.
Executor is capable of requesting help if it's stuck, or received unexpected response.

#### Message bus

Is main component for collaboration of rest components. It uses messaging capabilities to deliver notification of internal events.
Adapter to 3rd party component, with messaging functionality: implemented as third party component Glassfish MessageBus.

### ThinkingLifeCycle

Main component that makes whole understanding and solution search work. It implements environment for thinking processes used by
machine understanding and solution search, and several control processes.

![ThinkingLifeCycle Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/ThinkingLifeCycleComponent.png)

ThinkingLifeCycle controls Critic - Selector collaboration and Way2Think start and stop. This approach is based on [Marvin Minsky Thinking model](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708572)

Current simplified machine understanding and solution search workflow see below:
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

Via NLP tools a problem description text is been translated in to the semantic network form.

#### Problem classification

Problem is been classified according ot the description text: Direct instruction, Problem description with desired state, Problem description without desired state.

#### Solution search/generation

According to processed problem description as semantic network the solution is searched in trained Knowledge base, or been generated as compound solution based on
trained Knowledge Base.

#### Context management

System is capable of creation and maintenance of request context through clarification and confirmation dialogs.

#### Goal management

TU uses goal oriented processes to implement machine understanding. Goal management is the process to find proper goal in current state of the system.

#### Clarification request processing

TU is capable of requesting clarification of unclear and not complete parts of problem description. Human specialist specifies clarification
response and system processes clarification response within original request context.

#### Making sense analyser

Right after machine understanding processed a problem description, TU checks weather current understanding does make sense, according to trained domain information in
Knowledge base.

#### Emotional state control

Time control and reinforcement learning uses emotional state of the system. Emotional state control switches entire state of the system according
to: feedback from human specialist for solution during training, time left to process the incident.

### Selector

Component that retrieves resources from Knowledge Base. Selector is capable of retrieving using Critics requests and [Goals](system-description.md#goal-management).
Selector uses storage, currently it is No SQL database [Neo4j](http://neo4j.org/). Selector is tightly coupled with Critic.

### Critic

Component is mainly used for analysis processed data in context of current problem solution. There are several types of Critic:

 0. Analyser - is used to parse current context and return probability and confidence of the positive result.
 0. Controller - is used to trace some parameter, for example time and alter for example emotional state of the TU system.
 0. Manager - is used to generate proper Selector request to retrieve proper resource.

### Way2Think

Group of components to actually implement some modifications over data in current problem context.
Typical Ways to think:

 0. Simulation
 0. Reformulation
 0. Cry for help
 0. Logical Reasoning.

### Reasoner

This is interface to third party component that implements Logical Reasoning, currently it is third party component [PLN](http://wiki.opencog.org/w/PLN).


### Natural language processing components

This group of components are dedicated to process problem description text with semantic network output. Preliminary splitter extracts sequence of words from inbound text.
Lexical parser creates lexical pair of words. KnowledgeBase Annotator maps domain concepts from Knowledge Base to words from inbound text.

Simplified natural language processing workflow.

![Simplified NLP workflow](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/simplifiednlp.png)

### Data components

It is Knowledge Base the main storage of all knowledge in the system. System stores all data as objects reusable in Knowledge Base and is implemented via No SQL database Neo4j.
Main data types:

 0. Textual problem description
 0. Semantic network of machine understanding of problem
 0. Simulation semantic network
 0. Trained data
   1. Problem symptoms
   1. Problem solutions

## Environment requirements.

Linux compatible OS (Ubuntu recommended).

At least 6 GB of RAM, x64 architecture, according to user load number of servers could be increased. Recommended Intel Core I7 processor.

Minimal Requirements: Core i3, 2 Gb Ram, x86 architecture.

## Systems integrations.

### Integrations of third party systems list.

 0. Incident processing:
   1. [Nagios](http://www.nagios.org/)
 0. Change requests processing:
   1. Development environment integrations.



