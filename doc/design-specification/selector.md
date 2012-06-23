# Selector design specification.

Selector is main component to retrieve Actions in  [Critics](critics.md) -> Selector -> [Way2Think](way2Think.md) triple.
Selector is used to retrieve proper Actions from KBServer according to the inbound parameters that are treated as Selector request.

## Entry criteria

[ThinkingLifeCycle](thinking-life-cycle.md) starts Selector with different parameters, see below.

## Exit criteria

Selector retrieves Action descendants: [Way2Think](way2Think.md) or [Critic](critics.md).

## Interface

![Selector interface](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/SelectorInterface.png)

## <a name="action">Action class</a>

![Action class](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/ActionClass.png)


## Workflow

### apply(request : Request) : Action

![apply(request : Request) : Action](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/applyrequestRequestActionActivity.png)

### apply(goal: Goal): Action

![apply(request : Request) : Action](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/applygoalGoalActionActivity.png)

### apply(criticResult : ActionProbabilityRule) : Action

![apply(criticResult : ActionProbabilityRule) : Action](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/applycriticResultActionProbabilityRulePairActionActivity.png)

### apply(criticResults : List[ActionProbabilityPair]) : Action

![apply(criticResults : List[ActionProbabilityPair]) : Action](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/applycriticResultsListofActionProbabilityRulePairActionactivity.png)

### apply(criteria : KLine) : Action

![apply(criteria: KLine) : Action](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/applycriteriaKLineActionActivity.png)

## <a name="Selector_Context">Selector context Activity diagrams</a>

### Classification Workflow

 1. [ThinkingLifeCycle](thinking-lifeCycle.md) starts inbound [Critics](critics.md) in parallel.
 1. When Critics returns their ActionProbabilityRuleTriple, ThinkingLifeCycle starts one Selector with the List of results.
 1. Selector starts [GetMostProbableWay2Think](design-specification.md#Activity_diagram) that takes in account following aspects:
   2. One Critic is the part of another.
   2. One Critic if triggered is more probable than another.
 1. Encapsulating KnowingHow of current Selector could trigger the Selector to choose less probable variant,
 this could be used in several cases:
   2. If Reflective Critics realises that current Way2Think does not leads to expected result.
   2. If User is not satisfied with the results.

### <a name="request_processing">Start Request processing activity</a>

![start Request processing activity](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/startRequestProcessingActivity.png)

Actions are started by ThinkingLifeCycle in parallel in different threads.

### <a name="classify_incident">Classify incident activity</a>

![Classify incident activity](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/classifyIncidentActivity.png)

[ThinkingLifeCycle](thinking-life-cycle.md) start Critics in parallel with join and collect their results to process them by one Selector.

