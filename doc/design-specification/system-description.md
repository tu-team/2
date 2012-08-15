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


System is mainly dedicated for automation of Software engineering (SE), IT-Telecom systems.
System is targeted to solve incidents, bug-fixes, and administration of servers and networks.

System uses following approach:

 0. Production mode:
   1. Machine understanding of problem description.
     2. Clarification of unclear places of problem description.
   1. Problem situation modelling.
     2. Requests for incomplete information.
   1. Solution search
     2. Solution is searched in trained Knowledge base of solutions.
     2. Solution could be generated.

 0. Training mode:
   1. Training domain terms.
   1. Training domain concepts.
   1. Training solutions.

### Automation objects.

System was used to solve following types of tasks:

 0. Automation of typical actions in retail application maintenance.
 0. Incident processing automation.

### Features.

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



