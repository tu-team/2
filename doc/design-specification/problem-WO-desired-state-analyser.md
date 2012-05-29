# ProblemWODesiredStateAnalyser design specification.

Is subclass of [Critic](critics.md)

## Entry criteria

Goal = ClassifyIncident

## Exit criteria

[Critics rule](critics.md#rule) fired and completed.

## Rule

### IsProblem
Should check if there is indication that some peace of software is wrong or works in improper way.
Ex.: contains: software is wrongly installed, no internet connection, etc

### HasNoDesiredState
Should check that there is no indication of desired state.


### Approximate formal example
```
{
 START IncidentDescription
 MATCH subject -[:negative]-> action
 WHERE  not(subject -[:should] -> X or subject -[:must] -> X)
 RETURN true
} => {
 START DomainModel.WayToThink
 WHERE Name = SimulateAndReformulate
}
```
