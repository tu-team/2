# Reformulation Way2Think design specification.

## Entry criteria

DirectInstruction, ProblemWithDesiredState, ProblemWODesiredState [Critics](critics.md) returned [Selector Request](selector.md) for Reformulation Way2Think.

## Exit criteria

Domain model of Problem description, partly or completely is created.

## Input

[SemanticNetwork](knowledge.md) with [KLine-s](knowledge.md) of current situation (output of [Simulation](simulation-way2Think.md)) description.

## Output

Domain SemanticNetwork that models current situation from Incident description.

## Workflow
Similar to simulation Way2Think.

For each node in inbound current situation description SemanticNetwork node maps to node in the problem description SemanticNetwork.
All absent values and orphan nodes(not connected to the root) are clarified via DoNotKnow process.
