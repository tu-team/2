# GetMostProbableActionAnalyser design specification.

Is subclass of [Critic](critics.md)

## Entry criteria

Several critics have calculated their SelectorRequestRulePair with probability and confidence. 

## Input

List of critics results(SelectorRequestRulePair) with probability and confidence.

## Exit criteria

Immediately. 

## Rule

MostProbable SelectorRequestRulePair is chosen from list of critic result with maximal probability and confidence taking in account [Critic Links](critics.md#Critics_links).
If there are several critics results(SelectorRequestRulePair) with same probability and confidence, 
random critic result is chosen from list. 
Other critics results are stored into LessProbable list via StoreLessProbable Way2Think. 
If MostProbable critic result couldn't create acceptable further solution, next less probable will be used.

