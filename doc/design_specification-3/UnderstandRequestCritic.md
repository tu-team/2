# UnderstandRequest Critic design specification.

## Entry criteria

Goal is set to UnderstandRequest. See [Goal Manager](../design-specification/GoalManager.md).

## Exit criteria
Selector request contains raw data in JSON. If it corresponding to
```
{channel: 0,
data: [2313,3223,123,12331]}
```
Activate ClassifySpike Way2Think.

## Input

[Context](knowledge.md#Context) Plain text of issue.

## Output

Set new goal ClassifySpike by ClassifySpike.Way2Think  and stop processing other critics.

## Rule

```
{
 MATCH Goal
 WHERE UnderstandRequest
 RETURN true
}
=>
{
 START ClassifySpike.Way2Think
}
```
