# ProcessingManager Critic design specification.

Is subclass of [Critic](critics.md). Straightforward invoking the [CreateContext Way2Think](create-context-way2Think.md) Critic.

## Entry criteria

Inbound Request starts ProcessingManager.
Detailed workflow: [ThinkingLifeCycle](thinking-life-cycle.md) then [Selector](selector.md) that returns the ProcessingManager and ThinkingLifeCycle starts ProcessingManager in separate thread.
See [Selector request processing.](selector.md#request_processing)

## Exit criteria

Immediately.

## Input

List of critics results(SelectorRequestRulePair) with probability and confidence.

## Rule

```
{
 MATCH Request
 RETURN true
}
=>
{
 START CreateContext.Way2Think, StartTimeController.Way2Think
}
```