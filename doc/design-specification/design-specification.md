# Thinking understanding Design Specification.

## <a name="Use_cases">Use cases</a>

### Training use case
![Training use case](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/UseCaseTrain.png)

### Production use case
![Production use case](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/UseCaseProduction.png)

## <a name="Component_diagram">Component diagram</a>

![Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Component.png)

### Components list

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

### Collaboration

![Collaboration diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/MainComponentsCollaboration.png)

User interacts with system by WebService.

 1. WebService receives request, prepares it and stores information in KB using DataService
 1. WebService send message 'Request' with request information to MessageBus
 1. One of CoreService pick up message 'Request' and start processing. (CoreServices can be located on different machines)
 1. CoreService process request and stores additional info in KB using DataService
 1. CoreService put message 'RequestCompleted' with processed request and message with required actions 'ActionsToExecute' to MessageBus
 1. WebService receives message 'RequestCompleted' with completed request and notifies subscribers
 1. ClientAgent receives message with action 'ActionsToExecute' and execute them on servers.

#### Suggested Message Types
 1. Request
 1. RequestCompleted
 1. ActionsToExecute

## Knowledge data model
[Knowledge model](https://github.com/development-team/2/blob/master/doc/design-specification/knowledge.md)

## <a name="Activity_diagram">Activity diagram</a>

### Training
Training is complex set of processes to setup and customize system according to domain requirements.
[Training description](training.md)

### Production
Is actual work of the system to solve the incident according to a description.

[Approximate workflow](https://github.com/development-team/2/blob/master/doc/informal/perceiving-modelling.md#Approximate_workflow)

[Lifecycle example, activity diagram.](https://github.com/development-team/2/blob/master/doc/design-specification/lifecycle-activity.md)

### Third party components

 1. [PLN](http://wiki.opencog.org/w/PLN) – Probabilistic Logic Networks. An logic system for uncertain inference. [GNU Affero license.](http://www.gnu.org/licenses/agpl-3.0.html)
 1. [RelEx](http://wiki.opencog.org/w/RelEx) – Extract grammatical parses and semantic knowledge from natural language (in English).[Apache License](http://bazaar.launchpad.net/~relex-dev/relex/trunk/view/head:/LICENSE)
 1. [NLGen](https://launchpad.net/nlgen2) -  Do the inverse of RelEx. Convert semantic relationships to natural language. [Apache License](http://bazaar.launchpad.net/~relex-dev/relex/trunk/view/head:/LICENSE)
 1. [MessageBus](http://today.java.net/pub/a/today/2008/01/22/jms-messaging-using-glassfish.html#message-driven-beans) -
 [Dual License consisting of the Common Development and Distribution License (CDDL) v1.0 and GNU General Public License (GPL) v2.](http://glassfish.java.net/public/CDDL+GPL_1_1.html)
 1. [NEO4J](http://neo4j.org/) - [Advanced and Enterprise editions available under both the AGPLv3 and commercial licenses, supported by Neo Technology.](http://neo4j.org/licensing-guide/)


