# Spike generator Way-To-Think

## Entry criteria

Activated by [SpikeCritics](SpikeCritics.md).

## Exit criteria

All spikes processed.

## Input

Input context with parameters. Extracted from [ShortTermMemory](UniversalMemory.md)
```
family: hand, foot, e.t.c.
period: in second
```
TODO: Create class diagramm

Family represents part of activity and should be translated by Family detection critics like
HandFamily, FootFamily according to input channel.

## Output

Generate series of spikes into pipeline, so NEUCOGAR can process it.
TODO: make class diagram.
TODO: integration with NEST.
TODO: use time as data parameter.
```
family: hand
data: 10:21,10:22,..
```
## Workflow



## Example
