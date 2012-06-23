# SolutionCompletenessManager design specification.

Is subclass of [Critic](critics.md). Used to trigger [Communicate To User Way2Think](communicate-to-user.md).

## Entry criteria

[Solution](knowledge.md#Solution) in the [Context](knowledge.md#Context) triggers the SolutionCompletenessManager.

## Exit criteria

[Critics rule](critics.md#rule) fired and completed.

## Rule

```
{
 START Context.Solution
 RETURN true
} => {
 START DomainModel.WayToThink
 WHERE Name = CommunicateToUser
}
```
