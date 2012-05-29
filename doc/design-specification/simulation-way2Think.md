# Simulation Way2Think design specification.

## Entry criteria

DirectInstruction, ProblemWithDesiredState, ProblemWODesiredState [Critics](critics.md) returned [Selector Request](selector.md)
for Simulation Way2Think.

## Exit criteria

Domain model of Incident description, partly or completely is created.

## Input

[SemanticNetwork](knowledge.md) with [KLine-s](knowledge.md) of Incident description.

## Output

Domain SemanticNetwork that models current situation from Incident description.

## Workflow

Each node of inbound Incident description [SemanticNetwork] mapped to a node in the [Domain](knowledge.md#Domain) SemanticNetwork via(through)
concepts of the KnowledgeBase, modeling current situation described in Incident via Domain terms.
Default values are set using Domain concepts, and then clarified/confirmed via [Clarification process](clarification-process.md)

## OWL example, see Simulation concept

[Example in OWL](https://raw.github.com/development-team/2/master/doc/design-specification/owl/SemanticNetwork_UserReceivedWrongApplication.owl)
