#[Emotion machine](http://en.wikipedia.org/wiki/Emotion_machine)
Solution is based on Marvin Minsky [Emotion machine/7. Thinking chapter.](http://web.media.mit.edu/~minsky/E7/eb7.html)

## <a name="Definitions">Definitions</a>

### Production mode.

1. Entry criteria
  2. User provided new incident Text.
1. Exit criteria.
  2. System found proper formalisation for the inbound Text.
  2. System exceeded maximum energy allowed for the perceiving operation of the incident type.
  2. System needs further clarifications: if the inbound information is not sufficient.
1. Inbound data is Text
  2. Incidents description text.
  2. Incident category.
1. Outbound is AnnotatedText with formalisation results.

### <a name="Training_mode">Training mode</a>.

1. Entry criteria
  2. User provided new incidents Texts.
1. Exit criteria.
  2. System found proper formalisation workflow of the Interpreter for inbound Text and incident category, and found validation criteria.
  2. System fails to find proper formalisation workflow.
1. Inbound data is Text
  2. Incidents description text.
  2. Incident category.
  2. Incident formalized description.
  2. Incident solution.
1. Outbound is formalisation workflow of the Interpreter steps, validation criteria.

## <a name="Use cases">Use cases</a>

### Training

![Train](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/UseCaseTrain.png)

Train mainly is supervised automatic with the Request and Solution pairs provided by TSS. After some essential learning
TSS monitors the Solution-s of the system.

### Production
![Production](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/UseCaseProduction.png)
System supports the dialog with a customer starting with Request then through the clarifications/confirmations to Solution
applied to the target environment.

## <a name="Components">Components</a>

![Critics with Selectors](http://web.media.mit.edu/~minsky/E7/eb7_files/image003.png)

According to Marvin Minsky, mainly the Thinking is split on two sections Critics and Selectors(Way to Think):

Critics are applied sequentially and selects proper Selector(Way to Think).

### Component diagram

![Component](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/EmotionMachineComponent.png)

 1. Simplest case is the direct instruction processing.
The Learned Reactive Critics is activated and retrieve the Knowing How way of thinking.
 1. Complex case is the problem description processing.
Ex.: Learned Reactive Critics activates ReasoningByAnalogy, DivideAndConquer Way2Think, then Way2Think activates Deliberate Critics,
that activates Reformulation Way2Think, Reformulation changes the representation of inbound data to proper way and activates Learned Critics then KnowHow Way2Think.

`Learned -> [ReasoningByAnalogy|DivideAndConquer] -> Deliberate -> Reformulation -> Learned -> KnowHow`

## <a name="Formalisation_Production_Workflow">Formalisation Production Workflow</a>

### Workflow.

 1. Preliminary annotation:
   2. Lexical parser one of (see list below) creates the lexical structure of the sentences, that are added as annotations to inbound text.

      3. [StanfordParser](http://nlp.stanford.edu/software/lex-parser.shtml)
      3. [OpenCog RelEx](http://wiki.opencog.org/w/RelEx)
      3. [OpenNLP](http://incubator.apache.org/opennlp/index.html)
      
   2. Knowledge Base ([see KB list on Wikipedia page and the list below](http://en.wikipedia.org/wiki/Commonsense_knowledge_bases)) concepts are added to annotated text on the step above.
     
     3. [ConceptNet5](http://conceptnet5.media.mit.edu/)
     3. [NELL](http://rtw.ml.cmu.edu/rtw/resources)
     3. [WolframAlpha](http://www.wolframalpha.com/)
     3. [TrueKnowledge](http://www.trueknowledge.com/)
     3. [ConceptNet](http://csc.media.mit.edu/conceptnet)
     3. [Freebase](http://www.freebase.com/apps)
     3. [YAGO2](http://www.mpi-inf.mpg.de/yago-naga/yago/)
     3. [DBPedia](http://dbpedia.org/About)

 1. <a name="Main_perceiving_workflow">Main perceiving workflow</a> [Reflective Critics](http://web.media.mit.edu/~minsky/E7/eb7.html#_ednref6) starts the Perceiving Way2Think that controls following Critics-Selector-Way2Think
   2. Activates Critics from lower to upper level one by one to identify the problem type then Selector (if no Selector found activates Critics on the level above).
   2. Selector fetches proper Way2Think
   2. Runs found Way2Think that produces some additional annotations(ex.: some inference results could be added).
   2. Validate the Way2Think results(annotations), if annotations conforms the [formalisation criteria](https://github.com/development-team/2/blob/master/doc/informal/formalisation-criteria.md)
   stops the process, if not activates Critics again.
   2. Controls energy spent by the system, if exceeded maximum available for the perceiving operation stops.
     3. Each operation Critics or Way2Think is assigned some degree of energy that is requested for the operation to be performed.
     3. Each type of incident has maximum allowed energy to spent.

## <a name="Formalisation Training Workflow">Formalisation Training Workflow.</a>

### Workflow

 1. Preliminary annotation, see Formalisation Production Workflow.
 1. Perceiving Way2Think, see Formalisation Production Workflow, searches for the formalized description or is stopped if the maximum number of cycles exceeded.
 1. Machine Learning:
   2. Stores the formalized description validation rules regarding the incident description annotated text contents and Incident category.
   2. Stores the Incident Solution regarding formalized description and the Incident category.