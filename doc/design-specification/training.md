# Training mode design specification.

## Training incident description example
```
User had received wrong application.
User has ordered Wordfinder Business Economical.
However she received wrong version, she received Wordfinder Tehcnical instead of Business Economical.
Please assist
```
## Information to be trained

 1. Domain concepts dictionary = Comes from TSS training courses + WordNet + Other KBs. Ex.:
    2. User.
    2. Software.
    2. Installation(install).
 1. Meta concepts, that operate over Domain concepts, ex.: 'instead', 'but', 'action negation indicates erroneous situation', ...
 1. HowTo-s the incident solution algorithms = Comes from Project workbooks.
 1. Incident handling process, see [Goals data example](training.md#Goals_data_example) = Specified by Knowledge engineer.
    2. Goals structure, could be defined explicitly or inferred during Incident handling training = Specified by Knowledge engineer and Project induction.
    2. Solution training.

## Domain concepts

 1. First explicitly from specified Domain SemanticNetWork
 1. SemanticNetWork could be appended by subsequent Domain training sessions.

See [example](training-example.md#Domain).

## HowTo-s training

 1. Explicitly from HowTo functional description
 1. Implicitly appended from Goals parameter of Incident handling training

See [example](training-example.md#Incident).

## Train Incident handling process

Training activity diagram example: (incident handling of the Problem with desired state)
[Input example](training-tree.md#Example)

![Incident handling training example](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/TrainingActivity.png)

_Please note that all data linked via Goal's KLine_

 1. Process Goals parameter
   2. Create Narrative of the Critics, Way2Think sequence.
   2. Connect all intermediate knowledge: SemanticNetWorkWithKLines, ProblemDescription with DesiredState, Model, Solution
   with KLine(context = Request)
   2. Connect all matching terms and concepts of intermediate knowledge with KLine-s
     3. Connect each parameter of Solution HowTo with Formal Model (of previous step)
     3. Set Formal Model as context of KLine and store Solution in frames parameter of KLine.

See [example](training-example.md#Incident).

## Train Goals

See [Training tree document](training-tree.md)

## Training Class diagram

![Goal](https://github.com/development-team/2/blob/master/doc/design-specification/uml/images/Training.png?raw=true)

 1. Goal - link to goal associated with this training node
 1. ParentNode -reference to Parent Training Node

## Training workflow

 1. Domain concepts training.
 1. Meta concepts training.
 1. HowTo training.
 1. Incident handling training:
   2. Solution training.
   2. Goals structure training.

## Training examples
[Training examples](training-example.md)