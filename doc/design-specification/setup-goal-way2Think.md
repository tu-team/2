# Setup goal Way2Think design specification.

## Entry criteria

[Goal manager](goal-manager.md) returned SelectorRequest with SetupGoal Way2Think with [Goal](goal.md) parameter.

## Exit criteria

Workflow is completed.

## Postcondition

Goal is setup.

## Input

[Context.](knowledge.md#Context)

## Output

Current Goal is setup to [trained value](training.md#Goals).

## Workflow

Depending on the latest result the current Goal is set. Example: if the preliminary processing is done the IncidentClassification Goal must be set.
