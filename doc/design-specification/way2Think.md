# Way2Think design specification.

Way2Think is main operating unit of the [Critic](critics.md) -> [Selector](selector.md) -> Way2Think triple.
Way2Think is widely used to: update, transform, add knowledge in the system, and even Cry4Help.

## Entry criteria

[ThinkingLifeCycle](thinking-life-cycle.md) starts Way2Think, see also [ThinkingLifeCycle](thinking-life-cycle.md#Collaboration)..

## Exit criteria

Way2Think completed the processing.

## Inbound data

 1. Input Context:[Frame](knowledge.md#Frame), that contains Way2Think parameters, aso [see](thinking-life-cycle.md#Input_Output_Context).

## <a name="Outbound_data">Outbound data</a>

 1. Delta of previous and current state:[TransFrame](knowledge.md) stored in Output Context [Frame](knowledge.md#Frame),
 aso [see](thinking-life-cycle.md#Input_Output_Context).
 1. In case of HowTo way2think [Execution state](how-to-specification.md#ExecutionState) return.

## <a name="Interface">Interface</a>

![Way2Think interface](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Way2ThinkInterface.png)

## Basic and compound Way2Think

The Way2Think is the workflow that could be represented as [SemanticNetwork](knowledge.md). There are two types of Way2Think: basic and compound.
Builtin Way2Think listed below are implemented directly in the System, compounds are created combining [Critics](critics.md),
[Selectors](selector.md), and HowTo Way2Think. Builtin way2Think rough descriptions could be found in: [Emotion Machine](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708573).

## Builtin Way2Think

 1. [Create Context.](create-context-way2Think.md)
 1. [Set Emotional State.](emotional-state-way2think.md)
 1. [Set Goal.](setup-goal-way2Think.md)
 1. Inbound description preprocessing:
   2. [PreliminarySplitter.](splitting-text-to-words.md)
   2. [KnowledgeBaseAnnotator.](annotator-way2Think.md)
   2. [LinkParser](link-parser-way2Think.md)
 1. [Cry4Help.](cry4help-way2Think.md)
 1. [Store less probable Way2Think.](store-less-probable-way2Think.md)
 1. [Simulate.](simulation-way2Think.md)
 1. [Reformulate.](reformulate-way2Think.md)
 1. [Extensive search.](extensive-search-way2think.md)
 1. Return learned HowTo.
 1. [Communicate to user](communicate-to-user.md).
 1. [Stop processes.](stop-process.md)

## Knowing How

Knowing How Way2Think-s are implemented as interpretation of [HowTo-s](how-to-specification.md)

### Activity HowTo way2think

![Way2Think interface](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/way2thinkHowToActivity.png)