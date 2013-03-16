# Training mode design specification.

Based on http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc465796531

## Training incident description example
```
User had received wrong application.
User has ordered Wordfinder Business Economical.
However she received wrong version, she received Wordfinder Tehcnical instead of Business Economical.
Please assist
```

## Traninig strategy

Training is based on "inborn" abilities Critic and Way2Think-s of the system:
 
 1. Read the document (Lexical parsing)
 1. Simulate
 1. Reformulate
 1. Correlate
 1. Logical Reasoning: 
   2. Inductive reasoninig
   2. Abductive reasoning
   2. Deductive reasoning
   2. Reasoning by analogy
 1. Does it get new useful information? Critic

Combining and recombining those abilities system is capable of constructing(training) the Training HowTo. This is self-learninig capability to train simple HowTo.
Recombining Critic and Way2Think is done automatically as part of Curiosity ability.

## Curiosity

Makes system try first single abilities, then pairs then triplets. All successive: according to Does it get new useful information? Critic are memorized via Inductive reasoninig and Abductive reasoning mechanisms.

## Information to be trained

This shold be estimated based on the domain incident content, and training strategy document should be created. Then traing cource should be created and tested over the prototype.

 1. Domain concepts dictionary = Comes from TSS training courses + WordNet + Other KBs. Ex.:
    2. User.
    2. Software.
    2. Installation(install).
 1. Meta concepts, that operate over Domain concepts, ex.: 'instead', 'but', 'action negation indicates erroneous situation', ...
 1. HowTo-s the incident solution algorithms = from Project workbooks.
 1. Incident handling process, see [Goals data example](training.md#Goals_data_example) = Specified by Knowledge engineer.
    2. Goals structure, could be defined explicitly or inferred during Incident handling training = Specified by Knowledge engineer and Project induction.
    2. Solution training.

## Domain concepts

 1. First explicitly from specified Domain SemanticNetWork
 1. SemanticNetWork could be appended by subsequent Domain training sessions.

See [example](training-example.md#Domain).

## HowTo-s training

See [example](training-example.md#Incident).

## Train Incident handling process

Training activity diagram example: (incident handling of the Problem with desired state)
[Input example](training-tree.md#Example)

![Incident handling training example](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/TrainingActivity.png)

 1. [Process Goal's parameters](goal.md): Parameters, Precondition, Entry criteria, Exit criteria, Postcondition
   2. Create [Narrative of the Critics, Way2Think sequence](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/HowToNarrativeActivity.png).
   2. Correlate all intermediate knowledge: ProblemDescription with DesiredState, DirectInstruction with domain model via SemanticNetworkLinks. 
   2. Correlate all solution terms with domain model via KLine-s
     3. Correlate each parameter of Solution HowTo with domain model.
     3. Set domain model as context of KLine and store Solution in frames parameter of KLine.
 1. Iduction and [Abuction](abduction.md) reasoning over the set relative HowTo-s
   2. Induction creates new generalised concepts creating parameters of parent Goal, comparing several similar Goals(Precondition, Entry criteria, Exit criteria, Postcondition, HowTo-s).
   2. (Abduction](abduction.md) creates generalisation links based on gerenalibility of the (HowTo sequences, Precondition, Entry criteria, Exit criteria, Postcondition) of inbound incident processing [Goal](goal.md) to domain model goal.
   2. Similar Goals (same: Parameters, Entry criteria, Exit criteria) combines HowTo-s, during application different Preconditions are processed by [Critics](critic.md).
      
See [example](training-example.md#Incident).

## Incident processing goal hierarchy

See [Training tree document](training-tree.md)

## Training workflow

 1. Domain concepts training.
 1. Meta concepts training.
 1. HowTo training.
 1. Incident handling training:
   2. Solution training.
   2. Goals structure training.

## Training examples
[Training examples](training-example.md)
