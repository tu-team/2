# Annotator Way2Think design specification.

## Entry criteria

[PreliminarySplitter](preliminary-splitter.md) returned [SemanticNetwork](knowledge.md#SemanticNetwork).

## Exit criteria

Terms and connections to concepts in Domain KB found.

## Input

Narratives of terms

## Output

References to internal term.

## Terms
External Knowledge Bases (External KBs)

 1. http://www.trueknowledge.com/q/
 1. http://wordnetweb.princeton.edu/perl/webwn
 1. http://wordnet.princeton.edu/wordnet/
 1. http://www.wolframalpha.com/

local KB - internal domain Knowledge Base
LIMIT - empirical value, External KBs search cycles upper limit

## Workflow

  1. Term Recieved
  1. Search in local KB
  1. If Match not found request external KB, otherwise go to last step
  1. External KB returns synonymous list, connected concepts list. 
  1. Search memebers of list from previous step in local KB
  1. If cycles count less than LIMIT Get back to step 3
  1. When the search succeeded store found synonymous and connected concepts in local KB  
  1. Return reference to internal term
  
## Example

Inboud request contains term 'program'. Knowledge Base contains term 'computer software'. 

First cycle:

 1. Found: computer software,computer program


After first cycle matching was found. Analogy program->computer software will be added to Knowledge Base