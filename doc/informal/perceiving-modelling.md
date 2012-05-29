# Simple machine perceiving process modelling.

## Inbound data example
```
User had received wrong application.
User has ordered Wordfinder Business Economical.
However she received wrong version, she received Wordfinder Tehcnical instead of Business Economical.
Please assist
```

## <a name="Approximate_workflow">Approximate workflow</a>
See [Ways to think](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc451324833)

 1. PreliminaryAnnotator creates word links.
 1. KnowledgeBaseAnnotator creates concept references.
 1. EmotionMachine runs:
   2. SelfReflective Critics selects and starts SelfControl [Way2Think](way2Think.md) with
   [Goal](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/GoalConceptClass.png): Help user.
   2. Reflective Critics selects and starts ProblemSolving [Way2Think](way2Think.md) with
   [Goal](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/GoalConceptClass.png): ResolveIncident.
   2. Reflective Critics selects KnowingHow(Perceiving) Way2Think:
   2. KnowingHow(Perceiving) Way2Think:
     3. Incident Classification Deliberate Critics starts in parallel (Direct Instruction, Problem description with desired state, Problem description without desired state)
     3. Selector selects most probable Way2Think according to Critics estimates, current example is Problem description with desired state.
     3. _KnowingHow(Perceiving)_ stores variants and the Selector choice.
     3. Simulation Way2Think with CurrentSituation model => crates model of CurrentSituation:
         4. User,
         4. Software,
         4. ...
     3. Reformulation according to UserProblem template creates UserProblem model(CurrentState and DesiredState delta(_software wrongly installed, software lack on the User computer_)).
     instance from CurrentSituation model.
         4. DesiredState if not mentioned explicitly could be inferred as following:
             5. System has a [goal](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/GoalConceptClass.png) to help User.
             5. To help user System has to satisfy User needs:
             5. User has a goal to get rid of problem.(If ProblemSource mentioned explicitly (need Software) => DesiredState = Software installed, Else initiate Deliberate Critics to find out the ProblemSource.)
     3. _Reflective Critics_ estimates does the System got closer to the goal of the first step, if does carry on with Way2Think, if not try less probable stored by KnowingHow(Perceiving).
     3. Solution Generation Deliberate Critics searches among (KnowingHow Way2Think or ExtensiveSearch-es).
     3. Selector selects most probable Way2Think according to Critics estimates, current example is ExtensiveSearch.
     3. ExtensiveSearch searches for HowTo-s to get from CurrentState to DesiredState(_get rid of wrongly installed software, install desired software_).
         4. If found => reports success.
         4. If fails => activate Cry4Help Way2Think.
   2. _Reflective Critics_ checks if the System goal reached (Problem is exterminated)
         4. If satisfied => reports success.
         4. If fails => activate Cry4Help Way2Think.
   2. __SelfReflective Critics__ checks the energy spent on the task if exceeds the maximum allowed starts Cry4Help Way2Think.


## PreliminaryAnnotator

### [Stanford Parser](http://nlp.stanford.edu:8080/parser/index.jsp)

Typed dependencies, collapsed

```
nsubj(received-3, User-1)
aux(received-3, had-2)
root(ROOT-0, received-3)
amod(application-5, wrong-4)
dobj(received-3, application-5)

nsubj(ordered-3, User-1)
aux(ordered-3, has-2)
root(ROOT-0, ordered-3)
nn(Economical-6, Wordfinder-4)
nn(Economical-6, Business-5)
dobj(ordered-3, Economical-6)

advmod(received-3, However-1)
nsubj(received-3, she-2)
ccomp(received-8, received-3)
amod(version-5, wrong-4)
dobj(received-3, version-5)
nsubj(received-8, she-7)
root(ROOT-0, received-8)
nn(Tehcnical-10, Wordfinder-9)
dobj(received-8, Tehcnical-10)
nn(Economical-14, Business-13)
prep_instead_of(Tehcnical-10, Economical-14)

root(ROOT-0, Please-1)
dep(Please-1, assist-2)
```

### [RelEx](http://wiki.opencog.org/w/Walkthrough#Natural_Language_Processing)

See [XML Semantic parsing results](https://github.com/development-team/2/blob/master/doc/analysis/incident_7.res.xml) and
[compact version](https://github.com/development-team/2/blob/master/doc/analysis/incident_7.compact.txt).

Examples of SemanticRelation-s

```
<BinaryRelation leftWord="receive" rightWord="User" label="_subj" />
<UnaryRelation label="verb" word="receive" type="" />
```

### Data structures:

```
SemanticLink {
  name,
  probability,
}

BinaryRelation extends SemanticLink {
  _1,
  _2
}

UnaryRelation extends SemanticLink {
  _1,
  type
}
```

## [KnowledgeBaseAnnotator](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#KnowledgeBaseAnnotator)

### [WolframAlpha](http://www.wolframalpha.com/input/?i=what+is+the+meaning+of+life)

```
Request = what is user
Response =
1 | noun | a person who makes use of a thing; someone who uses or employs something
2 | noun | a person who uses something or someone selfishly or unethically
3 | noun | a person who takes drugs
```
```
Request = what is received
Response =
1 | adjective | conforming to the established language usage of educated native speakers
2 | adjective | widely accepted as true or worthy
```
```
Request = what is Wordfinder Business Economical
Response =
noun | a thesaurus organized to help you find the word you want but cannot think of
```

### [TrueKnowledge](http://www.trueknowledge.com/q/what_is_the_meaning_of_life)

```
Request = what is user
Response =
user, a computer identity used to access a particular computer network
class
Parent Class = technology, object, computing concept, concept that can be classified by sector of human endeavor, conceptual object,  …
```
```
Request = what is received
Response =
is someone who won as prize money
relation
```
```
Request = what is Wordfinder
Response =
The term 'wordfinder' means a thesaurus organized to help you find the word you want but cannot think of
```

### [ConceptNet](http://csc.media.mit.edu/conceptnet)

```
Request = what is user
Response =
	a user is a kind of person.
	users can use.
	A user is someone who takes drugs
	Users can train Open Mind
	users can explain that verb
	software is used for users.
	a user can complete a form
	A user can mine a database
```
```
Request = what is received
Response =
	Beggers are used to recieving
	getting something requires being there to receive it
	The effect of getting something is receiving
	Something that might happen when you get something is receiving it
	receive is action
	opening a gift requires receiving one
```
```
Request = what is Wordfinder Business Economical
Response =
Hmm. I don't know anything about that concept.
```

### [ConceptNet5](http://conceptnet5.media.mit.edu/)

```
Request = user
Response =
...
    {
      "end": "/concept/en/user",
      "weight": 1,
      "start": "/concept/en/user/n/one_who_uses_something,_a_consumer",
      "score": 1257.3005135859114,
      "key": "senseOf /concept/en/user/n/one_who_uses_something,_a_consumer /concept/en/user",
      "start_url": "http://conceptnet5.media.mit.edu/data/concept/en/user/n/one_who_uses_something,_a_consumer",
      "type": "senseOf"
    },
...
```
```
Request = receive
Response =
...
    {
      "end": "/concept/en/receive",
      "weight": 1,
      "start": "/assertion/[/relation/Causes/,/concept/en/get_something/,/concept/en/receive/]",
      "score": 250.98328321790424,
      "key": "arg2 /assertion/[/relation/Causes/,/concept/en/get_something/,/concept/en/receive/] /concept/en/receive",
      "start_url": "http://conceptnet5.media.mit.edu/data/assertion/[/relation/Causes/,/concept/en/get_something/,/concept/en/receive/]",
      "position": 2,
      "type": "arg"
    },
...
```
```
Request = Wordfinder
Response =
{"error": "invalid uri /concept/en/Wordfinder"}
```

### [WordNet](http://wordnet.princeton.edu/)

```
Request = user
Response =
S: (n) user (a person who makes use of a thing; someone who uses or employs something)
S: (n) exploiter, user (a person who uses something or someone selfishly or unethically)
S: (n) drug user, substance abuser, user (a person who takes drugs)
```
```
Request = receive
Response =
S: (v) receive, have (get something; come into possession of) "receive payment"; "receive a gift"; "receive letters from the front"
S: (v) receive, get, find, obtain, incur (receive a specified treatment (abstract)) "These aspects of civilization do not find expression or receive an interpretation"; "His movie received a good review"; "I got nothing but trouble for my good intentions"
S: (v) pick up, receive (register (perceptual input)) "pick up a signal"
S: (v) experience, receive, have, get (go through (mental or physical states or experiences)) "get an idea"; "experience vertigo"; "get nauseous"; "receive injuries"; "have a feeling"
S: (v) receive, take in, invite (express willingness to have in one's home or environs) "The community warmly received the refugees"
S: (v) receive (accept as true or valid) "He received Christ"
S: (v) welcome, receive (bid welcome to; greet upon arrival)
S: (v) receive (convert into sounds or pictures) "receive the incoming radio signals"
S: (v) meet, encounter, receive (experience as a reaction) "My proposal met with much opposition"
S: (v) receive (have or give a reception) "The lady is receiving Sunday morning"
S: (v) get, receive (receive as a retribution or punishment) "He got 5 years in prison"
S: (v) receive (partake of the Holy Eucharist sacrament)
S: (v) receive (regard favorably or with disapproval) "Her new collection of poems was not well received"
```
```
Request = Wordfinder
Response =
S: (n) word finder, wordfinder (a thesaurus organized to help you find the word you want but cannot think of)
```

### [OpenCyc](http://www.opencyc.org/)

```
Request = user-theprogram
Response =
GAF Arg : 1

Mt : UniversalVocabularyMt
isa :	Individual

Mt : ComputerSoftwareDataMt
isa :	UserInterfaceProgram MicrosoftComputerProgram UnversionedProgram LocalProgram MSWindowsApplication
comment :	"The Win16 User for Win16 application compatibility."

Mt : EnglishMt
prettyString :	"Windows User-interface core component"
prettyString-Canonical :	"User"
```
```
Request = receive
Response =
---> 	toreceive would be here.
```
```
Request = Wordfinder
Response =
---> 	Wordfinder would be here.
```

### Data structures
Based no [K-lines](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305131).

```
Concept extends Resource {
  uri
  resource // resource structure depends on KB is going to be used
  similar // list of similar concepts
  superConcepts
  subConcepts
  links: List[Link]
}

Link[SrcResource, DestResource] extends Resource {
  uri
  source: SrcResource
  destination: DestResource
}

K-line[SrcResource, DestResource] {
  type
  links: List[Link]
}

```

## EmotionMachine
see [Workflow](https://github.com/development-team/2/blob/master/doc/analysis/perceiving-modelling.md#Approximate_workflow)

## Reflective Critics selects KnowingHow(Perceiving) Way2Think: and KnowingHow(Perceiving) Way2Think:

Main control lifecycle that starts perceiving workflow elements.

### Inbound data structure

Based on [Narrative Scripts/Stories](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305131).

```
AnnotatedText extends Frames for Including Additional Slots {
   Narrative Story
   K-lines: List[Kline[Word, Concept]]
}
```

### Outbound data structure

```
Solution {
  Narrative Story[HowTo]
}
```

## Incident Classification Deliberate Critics

### Outbound data

```
Pair {
  Resource
  probability: Double
}
```

### Workflow

Following Critics started concurrently:

  1. Detect Direct instruction
  1. Detect Problem description
    2. Detect desired state in problem description

They end up with analysis results: Way2Think and probability pair.

## Selector chooses most probable variant according to Critics estimates, current example is _Problem description with desired state case_

## Problem description with desired state
see [alternative](https://github.com/development-team/2/blob/master/doc/analysis/perceiving-modelling.md#Direct_instruction_case)

### Inbound data

```
Pair {
  Resource
  probability: Double
}
```

### Outbound data

```
Resource
```

## Simulation Way2Think with Problem model

### Outbound data structure

```
Semantic network[Concept]
```

### Workflow

  1. Reading each word
  1. via K-lines creates model(picture) of current state ex.:
    2. User: Actor
      3. has:
        4. Software
          5. name = Wordfinder
          5. version = Tehcnical
      3. ordered:
        4. Software
          5. name = Wordfinder
          5. version = Business Economical

### Exceptions

  1. Mandatory parameters of encapsulating concept was not detected => Clarification request for parameters to be clarified.

## Reformulation Way2Think with UserProblem model with desired state
see [alternative](https://github.com/development-team/2/blob/master/doc/analysis/perceiving-modelling.md#Reformulation_Way2Think_without_desired_state_case)

### Outbound data structure

```
Semantic network[Concept]
```

### Workflow

creates UserProblem model

  1. CurrentState
  1. DesiredState delta:
    2. software wrongly installed,
    2. software lack on the User computer.

### Exceptions

  1. Current state lacks what's wrong description
  1. System was unable to infer the desired state.

## Solution Generation Deliberate Critics searches among (KnowingHow Way2Think or ExtensiveSearch-es).

### Outbound data

```
Pair {
  Resource
  probability: Double
}
```

### Workflow

Following Critics started concurrently:

  1. Detect KnowingHow Way2Think to get from Current to Desired state
  1. Detect ExtensiveSearch if no complete Solution was generated

They end up with analysis results: Way2Think and probability pair.

## Selector chooses most probable variant according to Critics estimates of the completeness of the Solution, current example is _ExtensiveSearch_

### Inbound data

```
Pair {
  Resource
  probability: Double
}
```

### Outbound data

```
Resource
```

## ExtensiveSearch Way2Think
to get from CurrentState to DesiredState(_get rid of wrongly installed software, install desired software_).

  1. If found => reports success.
  1. If fails => activate Cry4Help Way2Think.

### Outbound data

```
Solution {
  Narrative Story[HowTo]
}
```

### Exceptions

  1. Fails to find the HowTo to get rid of problem. => Escalate.

## Alternative workflow

## <a name="Direct_instruction_case">Direct instruction case</a>
Activates Simulation Way2Think with Instruction model.

## Simulation Way2Think with Instruction model

### Workflow
via K-lines creates model(picture) of current state with participants:

  1. Instruction HowTo
  1. Parameters of the Type set in HowTo

### Exceptions

  1. If Instruction HowTo was not found => Unknown action requested.
  1. If Instruction HowTo mandatory parameter of the specified type was not found => Clarification request for the parameter to be clarified.


## <a name="Reformulation_Way2Think_without_desired_state_case">Reformulation Way2Think with UserProblem model without desired state case</a>

### Workflow

  1. DesiredState is inferred taking in account following goals:
    2. System goal: to help user
    2. User goal: get rid of the problem
    2. Problem source analysis:
      3. If problem source mentioned explicitly (need Software) => DesiredState = Software installed ==> Install needed software
      3. Else initiate Deliberate Critics to find out the ProblemSource.)
  1. DesiredState delta
    2. software wrongly installed,
    2. software lack on the User computer.

### Exceptions

  1. Current state lacks what's wrong description
  1. System was unable to infer the desired state.

