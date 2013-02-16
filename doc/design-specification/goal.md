# Goal design specification.

Goals are the set of predicates and sequences of possible HowTo-s to reach this goal.
Goals and HowTo-s are tightly linked one with each other.

![Goal mind map](https://github.com/development-team/2/raw/master/doc/design-specification/mm/goal.png)

Goals has same additional extra Concepts that defines four important predicates:

 1. Precondition - could be assumed or even empty.
 1. Entry criteria.
 1. Exit criteria.
 1. Postcondition - could be assumed or even empty.

## Predicates

### Logical operations:

 1. and
 1. or
 1. not

### Concept addressing operations:

 1. (linkName) - defines link between concept
 1. *ancestorName - defines descendants according to their - ancestor name

Examples:

Concept0(HasA)Concept1 = Concept0 with a link HasA to Concept1.

Concept0(*HasA)Concept1 = Concept0 with descendant of link HasA to Concept1.

Concept0(HasA)*Concept1 = Concept0 with a link HasA to any of descendants to Concept1.


### HowTo narrative

![Goal class](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/HowToNarrativeActivity.png)

# Class diagram

Class Goal is a SemanticNode that represent goal of current learning node.

![Goal class](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Training.png)

1.  Resources - is a KLine that contains suitable Critics to be activated

# Goal hierarchy example

Goal is a part of TrainingAlgorithm. Actually represent concrete goal in resolving curve.

![Goals hierarchy example](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/GoalConceptClass.png)