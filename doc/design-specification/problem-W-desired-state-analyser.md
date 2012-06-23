# ProblemWDesiredStateAnalyser design specification.

Is subclass of [Critic](critics.md)

## Entry criteria

Goal = ClassifyIncident

## Exit criteria

[Critics rule](critics.md#rule) fired and completed.

## Rule

### IsProblem
Should check if there is indication that some peace of software is wrong or works in improper way.
Ex.: contains: software is wrongly installed, no internet connection, etc



### HasNoDesiredStateNegation
Should check that there is indication of desired state. Use HasNoDesiredState with Negation. See [Problem without desired state description](problem-WO-desired-state-analyser.md)

## Approximate example:

```
{
 START IncidentDescription
 MATCH subject -[:negative]-> action
 WHERE  subject -[:should] -> X or subject -[:must] -> X
 RETURN true
}
=>
{
 START DomainModel.WayToThink
 WHERE Name = SimulateAndReformulate
}
```
