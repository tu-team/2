# Spikes Critic design specification.

Available types:
1. HandClassifier;
1. DistanceClassifier.


## Entry criteria

Goal is set to UnderstandSpike.

## Exit criteria
Selector request contains raw data in JSON for the request.
```
{channel: 0,
data: [2313,3223,123,12331]}
```
Activate [SpikeGeneratorWayToThink](SpikeGeneratorWayToThink.md) Way2Think with params for family according to Critic type.

## Input

[Context](../design-specification/knowledge.md#Context) TODO: add data.

## Output

Activate Way-To-Think.

## Rule

```
{
 MATCH Goal
 WHERE UnderstandSpike
 RETURN true
}
=>
{
 START SpikeGeneratorWayToThink.Way2Think, ContextChangedWayToThink.Way2Think
}
```
