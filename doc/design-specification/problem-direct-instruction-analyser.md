# ProblemDirectInstructionAnalyser design specification.

Is subclass of [Critic](critics.md)

## Entry criteria

Goal = ClassifyIncident

## Exit criteria

[Critics rule](critics.md#rule) fired and completed.

## Rule

### HasIsntruction
Should check if there is instruction.
Ex.: contains: install IE, get access to, etc

### HasNoAnythingElse
Should check that there is no description of problem, only instruction.


### Approximate formal example
```
{
 START IncidentDescription
 MATCH action
 WHERE not(subject)
 RETURN true
}
```
