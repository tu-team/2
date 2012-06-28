# Critic design specification

Critic is main analysis element in the triple Critic -> [Selector](selector.md) -> [Way2Think](way2Think.md).
Critic is used to: choose way2Think, classify inbound information, reflection, self-analysis and so on.

## Entry criteria

[ThinkingLifeCycle](thinking-life-cycle.md) starts Critics according to [Goal](goal.md) or [Request](knowledge.md#Request). See also [ThinkingLifeCycle](thinking-life-cycle.md#Collaboration).

## Exit criteria

Critic generates [Selector](selector.md) request: SelectorRequest.

## Input (members of inbound [Context](context.md))

 1. [Critic Rules](knowledge.md#Rule).
 1. [DomainModel](knowledge.md#domain): [SemanticNetwork](knowledge.md#semanticnetwork).
 1. Incident description ([KBAnnotator](annotator.md) Way2Think result): as [SemanticNetwork](knowledge.md#semanticnetwork).

## <a name="Critic_output">Output: Pair</a> (members of inbound [Context](context.md))
 1. List of :
   2. [SelectorRequest](selector.md#action).
   2. [Critic Rule](knowledge.md#Rule) = the logical predicate triggered(see below).

### SelectorRequest

Request to database for resource allocating in KB terms (currently neo4j)

### Options
 1. List of SelectorRequest-s for the Critics.
 1. List of SelectorRequest-s for Way2Think and it's parameters

## Critic interface

![Critics Class](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/CriticInterface.png)

## Activity

![Critics Class](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/CriticApply.png)

## Main Critic types

 1. Manager - most simple type of critic, works as [Goal](goal.md) trigger to start [Way2Think](way2Think.md), ex.: GoalManager, PreprocessManager.
 1. Control - listener that monitors some event: time exceeds, energy exceeds etc, usually starts [Cry4Help](cry4Help.md) [Way2Think](way2Think.md), ex.: TimeControl, EnergyControl.
 1. Analyser - most complex critic, exploits extensive analysis of the previous Actions results, usually used in classification:
 DirectInstructionAnalyser, ProblemWODesiredStateAnalyser, GetMostProbableActionAnalyser.

## <a name="Critics_links">Main Critic links types.</a>

 1. Include = One Critics include another, this way the enclosing Critics if triggered is more probable than the one included.
 1. Exclude = One Critics if triggered excludes another; If two mutually excluding Critics are triggered then Selector should
 mark this situation and return most probable if Critics are weighted or random if weights are equal if the check of
 Perceiving Way2Think invokes the reset() method should switch to the opposite Critic.
 1. ReturnToSameSelector = In case several Critics are run in parallel [ThinkingLifeCycle](thinking-life-cycle.md) waits for all linked Critics and sends all results in one Selector.

## Main Critics

 1. Learned:
   2. [Preprocess manager.](processing-manager.md)
   2. Incident classifier:
     3. DirectInstructionAnalyser.
     3. [ProblemWODesiredStateAnalyser.](problem-WO-desired-state-analyser.md)
     3. [ProblemWithDesiredStateAnalyser.](problem-W-desired-state-analyser.md)
   2. [SolutionCompletenessManager.](solution-completeness-manager.md)
 1. Deliberative:
   2. [Most probable selection analyser.](get-most-probable-action-analizer.md)
 1. Reflective:
   2. [Goal manager.](GoalManager.md)
   2. Energy control. = not going to be used in this version.
   2. [Making sense analyser.](making-sense-analyser.md)
     3. Word2ConceptLinksAnalyser.
     3. SituationConsistencyAnalyser.
     3. [ProblemConsistencyAnalyser.](problem-consistency-analyser.md)
 1. SelfReflective:
   2. [Processing manager.](processing-manager.md)
   2. [Time control.](time-controller.md)
   2. [DoNotUnderstandManager.](SelfReflective-Do_not_understand.md)
 1. SelfConscious:
   2. [Emotional state manager.](emotional-state-manager.md)
