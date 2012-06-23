# Extensive search Way2Think design specification.

## Entry criteria

Request formalized.

## Exit criteria

Solution found, otherwise [Cry4Help](cry4help.md) raised

## Input

Formal problem description.

## Output

[Solution](knowledge.md#Solution)

## Workflow

 1. Receives formalized request
 1. Run [Generaliser](generalizer.md) and get generalized solution.
 1. Search in Knowledge Base by generalised solution, using semantic search
 1. Return  solution if found , otherwise [cry4Help](cry4help.md)

### Semantic search
Search not only complete word, but word links too. (e.g. synonymous)

## Sample

 1. Knowledge base contains Install soft How-to. Install has link to Absence
 1. Request:{Problem: Absence of Adobe Acrobat} (Install soft should be found)
 1. Run extensive search
 1. Run generaliser. Generaliser return "Absence of soft"
 1. Search in knowledge base by links (not only absence, but install too)
 1. Solutions found
