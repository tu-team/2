# ThinkingLifeCycle specification

ThinkingLifeCycle is main managing component to start in parallel and stop threads.
Runs workflow specified in [design specification](design-specification.md).
Invokes [Selectors](selector.md), [Critics](critics.md), [Way2Think](way2Think.md) and manages threads to run actions in parallel.

ThinkingLifeCycle represents factory of [Scala Actors](http://www.scala-lang.org/node/242), that uses
[publisher subscriber pattern](http://en.wikipedia.org/wiki/Publish%E2%80%93subscribe_pattern)

## Description
Objects that represents working threads used Scala Actor pattern.

1. ThinkingLifeCycle
1. SelectorImpl
1. CriticImpl

![Component diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/ThinkingLifeCycle.png)

## Interface

![Interface diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/ThinkingLifeCycleInterface.png)

## onMessage(message : Message) : Boolean

![onMessage activity diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkingLifeCycleonMessagemessageMessageBoolean.png)

## sendMessage(publisher: Publisher, message: Message): Boolean

![sendMessage activity diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkingLifeCyclesendMessagepublisherPublishermessageMessageMessage.png)

## apply(request : Request) : List[Action]

![apply(request : Request) activity diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecycleapplyrequestRequestListAction.png)

## apply(actions : List[Action]) : TransFrame

![apply(actions : List[Action]) : TransFrame](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecycleapplyactionsListActionTransFrame.png)

## processWay2Think(inputContext: Context, outputContext: Context): TransFrame

![processWay2Think(inputContext: Context, outputContext: Context)](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecycleprocessWay2ThinkcontextContext.png)

## processCritic(context: Context):List[SelectorRequestRulePair]

![processCritic(context: Context)](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecycleactivityprocessCriticcontextContext.png)

## init(): Boolean

![init()](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecycleinitBoolean.png)

## start(): Boolean

![start()](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecyclestartBoolean.png)

# stop(): Boolean

![stop()](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecyclestopBoolean.png)

## registerProcess(process : Process,level : Level) : Process

![registerProcess(process : Process,level : Level) : Process](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecycleregisterProcessprocessProcesslevelLevelProcess.png)

## stop(processLevel : Level) : List[Process]

![stop(processLevel : Level)](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/thinkinglifecyclestopprocessLevelLevelListProcess.png)


## Processes registrations, Thread pools

Every set of [Actions](action.md) are grouped in [processes](process.md). See [life cycle activity](lifecycle-activity.md).
Each process is placed in the [Thinking Level](thinking-level.md) [KLine](knowledge.md#KLine). SelfReflective processes: Learned.stop(), Deliberative.stop() controls Learned and Deliberative levels process.

Every actor should be placed to global thread pool to be under control. Every thread of request has unique id of request
See http://stackoverflow.com/questions/1597899/how-to-designate-a-thread-pool-for-actors

There are different messages with different priorities (like thread priority) http://docs.scala-lang.org/overviews/core/actors.html

## Activity overview

### Start and stop

 1. When application starts it initializes ThinkingLifeCycle that activates [Critics](critics.md) based on [Goal](goal.md) Critic is subscribed, as Scala actors.
 1. When application stops it stops all Actions, Selectors and ThinkingLifeCycle.

#### Component diagram.

![Activity diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/ThinkingLifeCycleInit.png)

#### Communication

Every actor communicate with each other using messages

#### Messages Overview

[Message overview](http://docs.oracle.com/javaee/1.4/api/javax/jms/Message.html)

#### Sample
Thinking lifecycle process the request come from [bus](message-bus.md), according to workflow.

### <a name="Collaboration">[Critics](critics.md)/[Selector](selector.md)/[Way2Think](way2Think.md) collaboration.</a>

 1. Critics returns List of SelectorRequestRule:
   2. ThinkingLifeCycle starts Selector-s with the SelectorRequest-s.
   2. [Selector-s](selector.md) returns [Action-s](action.md)(Selected from KB) and parameters.
   2. ThinkingLifeCycle starts [Action-s](action.md) in parallel:
     3. If Action is Critic:
         4. ThinkingLifeCycle crates [Input Context](knowledge.md#Input_Output_Context) and copies all data from Incident [Context](knowledge.md#Context).
             5. If Action-s are Critic-s with [ReturnToSameSelector links](critics.md#Critics_links) ThinkingLifeCycle waits for the results and sends list of SelectorRequestRule,
             returned by Critics to new Selector.
             5. Otherwise Thinking start all Actions in separate thread.
     3. If Action is Way2Think:
         4. ThinkingLifeCycle crates [Input Context](knowledge.md#Input_Output_Context) and copies all parameters returned by [Selector-s](selector.md).
         4. Starts Way2Think.
         4. Stores all returned parameters in [Output Context](knowledge.md#Input_Output_Context)
         4. Merges finalState form Way2Think result [TransFrame](knowledge.md#TransFrame)

### <a name="Input_Output_Context">Way2Think Input and Output Context.</a>

ThinkingLifeCycle crates the [Frame](knowledge.md#Frame) Input and Output Context and copies all Way2Think parameters in Input Context.
Way2Think selects the parameters from the Input Context and stores results in Output Context.

## Selector collaboration examples

See [Selector context diagrams.](selector.md#Selector_Context)
