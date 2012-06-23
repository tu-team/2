# Knowledge structure

Based on [Marvin Minsky: Emotion Machine: Hierarchy of Representations](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305131)

![Knowledge structure](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/KnowledgeClass.png)

## <a name="Narrative">Narrative</a>

Sequence of Rules.

## <a name="Rule">Rule</a>

Implication Rule with Antecedent(Logical Expression) and Consequent, also used in [Critics](critics.md).

![Critic Class](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/CriticRuleClass.png)

Rules syntax is based on:

 1. [Neo4j Cypher Query Language](http://docs.neo4j.org/chunked/stable/cypher-query-lang.html)
 1. Logical operators:
   2. Implication.
   2. Negation.
   2. Conjunction.
   2. Comparison:
     3. Less.
     3. Equals.
     3. Greater
     3. LessEquals
     3. GreaterEquals

## <a name="AnnotatedNarrative">AnnotatedNarrative</a> or <a name="AnnotatedText">AnnotatedText</a>

Narrative with KLines to the concepts of local Domain Knowledge Base.

## <a name="SemanticNetwork">SemanticNetwork</a>

The Graph structure based on SemanticNetworkNode-s and SemanticNetworkLinks.

## <a name="AnnotatedSemanticNetwork">AnnotatedSemanticNetwork</a>

SemanticNetwork with KLines to domain concepts in local Domain Knowledge base.

## <a name="SemanticNetwork">SemanticNetwork</a>

Is Graph based structure to store knowledge organised by some semantic links.
Example: SE domain terms hierarchy.

## <a name="Frame">Frame</a>

Is universal container to store different knowledge grouped by same semantics.

## <a name="TransFrame">TransFrame</a>

Similar to Frame, but contains two states initial and final and [Actions](action.md) that was used to transfer from initial to final state.

## <a name="SelectorRequestRulePair">SelectorRequestRulePair</a>

Frame with the SelectorRequest and Rule of [Critic](critics.md).

## Panalogy
Panalogy is used to draw cross domains analogies, parallel analogies.
See [The concept of a ‘Panalogy.’ from Emotion Machine of Marvin Minsky](http://web.media.mit.edu/~minsky/E6/eb6.html#_Toc446663337)

![Panalogy from the Emotion machine book](http://web.media.mit.edu/~minsky/E6/eb6_files/image005.png)

## Microneme(Domain)
Then the present state of your micronemes could represent much of your current mental context—and the states of those fibers are changed, your far-reaching bundle of micronemes will broadcast that information to many other mental resources—so that this will change some of your attitudes, outlooks, and states of mind.
In other words, this system could switch you into other, different ways to think.
I think that this concept of micronemes could help to replace many old and vague ideas about ‘association of ideas.’
We could interpret Microneme as the domain.

## Controlled Common Knowledge.
Implemented via Probability class and Reflective Critics.
When some Knowledge is produced by Way2Think it has zero confidence. Then some Reflective Critics (MakeSenseAnalyser) checks the Knowledge and
assigns confidence property of probability Class of checked Knowledge instances.

## Processes Data structures(should be placed in processes description)

Taking in account the main [Perceiving workflow](perceiving-modelling.md#Approximate_workflow). When the Incident comes in the System,
System crates KLine for the Conversation(Incident Context), Narrative(SolutionWorkflow) to store each step of processing for machine learning and
analysis. Data structures listed below are parts of the Conversation KLine.

### <a name="Context">Incident Context</a>

Is the [KLine](knowledge.md#KLine) with named Resource-s, first element is Request.

### <a name="Input_Output_Context">Input and Output Context</a>

In contrast to Incident Context is the [Frame](knowledge.md#Frame) that contains input or output [Action](action.md) information.

### Goal

![Goal](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/Training.png)

Implements SemanticTreeNode and KLine, thus is organised in Goal-s three and contains context(Critics, Way2Think and their results regarding this Goal).

### Tag

Has same structure as the Goal but is used for different purposes.

### <a name="Request">Inbound Request</a>

Incident processing or training request of the system.

#### Incident processing Request:

 1. Domain: Microneme
 1. Date
 1. Author
 1. Priority
 1. Category
 1. Tags
 1. Description
 1. Other information dependant on integrated platform.


#### Training Request:

 1. Domain: Microneme
 1. [Training Tree](training-tree.md)
 1. Reflective and Self Reflective constraints, ex.: processing time.

#### Training mode
See [Training tree](training.md#Training_tree)

#### Production mode
1. Request: Frame
  2. Description: StringResource
  2. IncidentCategory: KLine (Contains already defined Tag, ex.: Software problem).

### Preliminary annotation
SemanticNetwork of the Incident description.

### <a name="KnowledgeBase_annotation"> KnowledgeBase annotation</a>
SemanticNetwork of the Incident description with KLine-s that links to KnowledgeBase resources.

### <a name="Reformulation_Way2Think">Reformulation Way2Think = Formalisation result</a>
TransFrame in case of the UserProblem

### SolutionGeneration Way2Think
SemanticNetwork of HowTo-s

## <a name="domain">Domain model</a>

![Domain class diagram](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/domainClass.png)

Everything in the domain is concept. Instantiating an concept system creates copy with properties specified in Specialiser.
Concepts are organized in hierarchy with specialisations and generalisations links.

## <a name="problem">Problem model</a>

The subset of Domain model created for the current Incident problem description.

## <a name="problem">Situation model</a>

The subset of Domain model created for the current Incident [simulated](simulation-way2Think.md) situation description.

## <a name="Incident">Incident</a>

Main inbound information is Incident that happens on User machine, consists of:

 1. Author
 1. Author machine address
 1. ID
 1. Description
 1. Type: Critical, Major, Minor, ...

## <a name="EmotionalEvent">Emotional Event</a>

Event that holds emotional state as a int where 5 is the best emotion and -5 is a worst emotion.

  1. Emotional State (predefined marker for emotional state from -5 to 5)
  1. Request ID   (uid of request in system)
  1. Event Description (human readable description of event)
  1. Additional Info (filled out with caller info). ID of module that was caller (for example TimeControl)
