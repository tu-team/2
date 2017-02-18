# Check my state goal and critic design specification.

Available Critic types:
1. DistanceCloser;
1. ChannelOverflow.


## Entry criteria

Goal is set to CheckMyState.

## Exit criteria

## Input

[Context](../design-specification/knowledge.md#Context) Current data.

## Output

Activate Way-To-Think or do nothing according to input context and time.
## DistanceCloser
Check is distance channel changed too fast.

## ChannelOverflow
Too much channels activated simultaneously.


## General Rule

```
{
 MATCH Goal
 WHERE CheckMyState
 RETURN true
}
=>
{
 START Pain.Way2Think
}
```
