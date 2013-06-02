# Training tree specification

## Tree is based on [Goals-Subgoals](goal.md) structure

![Goal](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/GoalConceptClass.png)

Goals could be inferred from:

 1. Explicitly from Goals -> SubGoal-s structure
 1. Implicitly appended from Goals parameter of Incident handling training

## Training multiple goals on the same level

### Training:
But if Training process find several goals on the same level, when adds new subgoal, it should try to add classification as previous step.
Classification is delta data based. If there is no option to find delta(same data triggers two goals) then use MostProbableWay2Think.

### Production:
MostProbableWay2Think Critics returns most probable Way to think, all the rest are stored in MostProbableWay2Think: Frame according to probability. If on further steps of production process Reflective/SelfReflective Critics hits the failure of the choice, it should use MostProbableWay2Think: Frame to use less probable and recalculate probability of the goal.

##Example

```
 1. SubGoal = Resolve incident
   2. SubGoal = ParseIncidentDescription, Way2Think = ProcessText: KnowingHow, SemanticNetWorkWithKLines =
{
nsubj(received-3, User-1)
aux(received-3, had-2)
root(ROOT-0, received-3)
amod(application-5, wrong-4)
dobj(received-3, application-5)

advmod(received-3, However-1)
nsubj(received-3, user-2)
ccomp(received-8, received-3)
amod(version-5, wrong-4)
dobj(received-3, version-5)
nsubj(received-8, user-7)
root(ROOT-0, received-8)
nn(Tehcnical-10, Wordfinder-9)
dobj(received-8, Tehcnical-10)
advmod(of-12, instead-11)
prep(Tehcnical-10, of-12)
nn(Economical-14, Business-13)
pobj(of-12, Economical-14)
}
   2. SubGoal = UnderstandIncidentType, Critics = Deliberative, Type = ProblemDescription with DesiredState
     3. SubGoal = ModelCurrentSituation using ProjectDomain Model, Way2Think = Simulate, Model =
{
User Desired(ordered) Soft(Wordfinder Business Economical)
Operator Installed Soft(Wordfinder Tehcnical) - wrongly
}
     3. SubGoal = FormalizeProblemDescription using ProblemModel(Wrong state, Desired state), Way2Think = Reformulate, Model=
{
WrongState = Soft.installed(Wordfinder Tehcnical), Soft.notInstalled(Wordfinder Business Economical)
DesiredState = Soft.installed(Wordfinder Business Economical), Soft.unInstalled(Wordfinder Tehcnical)
}
     3. SubGoal = Find solution, Way2Think = ExtendedSearch, Solution =
     { Install(Wordfinder Business Economical), UnInstall(Wordfinder Tehcnical)}
```
