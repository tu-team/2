# Extensive search Way2Think design specification.

Used to search solution based on formal [direct instruction](problem-direct-instruction-analyser.md)
or [problem without desired state description]((problem-WO-desired-state-analyser.md))
or [problem with desired state description]((problem-W-desired-state-analyser.md)).

## Entry criteria

Request formalized.

## Exit criteria

Solution found, otherwise [Cry4Help](cry4help.md) raised

## Input

Formal problem description/direct instruction.

## Output

[Solution](knowledge.md#Solution)

## Workflow

 1. Receives problem description/direct instruction (formal)
   2. In case direct instruction:
     3. Run [Generaliser](generalizer.md) and get generalized solution.
     3. Search in Knowledge Base by generalised solution, using semantic search
     3. Return  solution if found , otherwise [cry4Help](cry4help.md)
   2. In case of problem description:
     3. Search for symptoms descriptions in Knowledge Base.
     3. If not found generalised symptoms
     3. Search for generalised symptoms
     3. Return  solution if found , otherwise [cry4Help](cry4help.md)


### Semantic search
Search not only concept, but concept links too. (e.g. synonymous)

## Sample

 1. Knowledge base contains Install soft HowTo. Install HowTo has link to Lack of software problem symptoms.
 1. Request:{Problem: Lack of Adobe Acrobat Reader} (Install soft should be found).
 1. Run extensive search.
 1. Run generaliser. Generaliser return "Lack of soft"
 1. Search in Knowledge Base by links (not only Lack, but Install too)
 1. Solutions found
