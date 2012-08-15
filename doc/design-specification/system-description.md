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

### Components

![Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Component.png)

TU system comprises of:

 0.

 1. [TU webservice](tu-web-service.md)
 1. CoreService
   2. [ThinkingLifeCycle](thinking-life-cycle.md)
   2. [Selector](selector.md)
   2. [Critics](critics.md)
   2. [Way2Think](way2Think.md)
   2. [PreliminaryAnnotator](preliminary-annotator.md)
   2. [KnowledgeBaseAnnotator](annotator-way2Think.md)
   2. [Link parser](link-parser.md)
 1. [DataService](data-services.md)
   2. [KnowledgeBase](knowledge-base.md)
 1. [Reasoner](reasoner.md)
   2. PLN
 1. [ClientAgent](client-agent.md)
 1. [MessageBus](message-bus.md)


## Features

 0. Machine understanding of problem description.
 0. Clarification and confirmation of unclear and ambiguous data.
 0. Solution search based on problem description.
 0. Confirmation of found solution.
 0. Domain terms and concepts training.
 0. Time control.
 0. Solution relevance control.
 0. Emotion state management and control.

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



