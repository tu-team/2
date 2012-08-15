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

 1. RIM
 1. SE maintenance
   2. Telecom maintenance
   2. FIS system maintenance
   2. HIS systems maintenance
 1. Accounting systems
 1. ...

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
   1. TU webservice
   1. ClientAgent
 0. Core components
   1. MessageBus
   1. CoreService
     2. ThinkingLifeCycle
     2. [Selector](selector.md)
     2. [Critics](critics.md)
     2. [Way2Think](way2Think.md)
   1. [Reasoner](reasoner.md)
     2. PLN
 0. Natural language processing components
   1. Lexical Parser
   1. Preliminary Splitter
   1. KnowledgeBase Annotator
 0. Data components
   1. [DataService](data-services.md)
     2. [KnowledgeBase](knowledge-base.md)


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

Main thinking processes implemented in TU:

 0. Problem description text pre-processing
 0. Problem classification
 0. Solution search/generation
 0. Context management
 0. Goal management
 0. Clarification requests processing
 0. Making sense analyser
 0. Emotional state control

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

//TODO

[Lifecycle example, activity diagram.](https://github.com/development-team/2/blob/master/doc/design-specification/lifecycle-activity.md)

## System description.

### Environment requirements.

Linux compatible OS (Ubuntu recommended).

At least 6 GB of RAM, x64 architecture, according to user load number of servers could be increased. Recommended Intel Core I7 processor.

Minimal Requirements: Core i3, 2 Gb Ram, x86 architecture.

### Components description

![Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Component.png)

For complete components description see: [design specification document](https://github.com/development-team/2/blob/master/doc/design-specification/design-specification.md#component-diagram).

## Systems integrations.

### Integrations of third party systems list.

 0. Incident processing:
   1. [Nagios](http://www.nagios.org/)
 0. Change requests processing:
   1. Development environment integrations.

### System connections

System could be used via web service interface provided by [TU web service component](https://github.com/development-team/2/blob/master/doc/design-specification/tu-web-service.md).



