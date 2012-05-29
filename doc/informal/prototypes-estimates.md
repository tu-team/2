#Prototypes and estimates
The list of prototypes with WBS and estimates in man/hours with following layout.

```1. Prototype name = sunny day estimate m/h - rainy day estimate m/h [risks]{preconditions}```

## <a name="OpenCog">OpenCog</a>
[OpenCog Solution specification.](https://github.com/development-team/2/blob/master/doc/informal/openCog.md)

 1. [CogBuntu](http://wiki.opencog.org/w/CogBuntu)
   2. [Read documentation](http://wiki.opencog.org/w/CogBuntu) = 0.5 - 1 [need further reading]
   2. [Install CogBuntu](http://wiki.opencog.org/w/CogBuntu#How_to_Get_cogbuntu) = 1 - 4 [problems with iso installation on virtual machine]

 1. <a name="AtomSpace">AtomSpace</a>, [specification](https://github.com/development-team/2/blob/master/doc/informal/openCog.md#AtomSpace)
   2. [Read documentation](http://wiki.opencog.org/w/AtomSpace), [Cookbook](http://wiki.opencog.org/w/Cookbook) = 2 - 4 [need further reading]
   2. Test storage using [Cookbook](http://wiki.opencog.org/w/Cookbook#Importing_Data)
   and [test data to be loaded and requested](http://tu.googlecode.com/hg/test/OwlTest/src/main/resources/preload/storage.test.0.2.owl)
   =  4 - 8 [possible additional setup of the AtomSpace server and TCP connection]{CogBuntu is operational}
   2. Test weather storage is usable for our [knowledge representation](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305130). = 16 - 20 {Data structures are designed and prototyped, [see description](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Model)}

 1. <a name="PLN">[PLN](https://github.com/development-team/2/blob/master/doc/informal/openCog.md#PLN)</a>
   2. Read documentation: = 2 - 4
     3. [PLN](http://wiki.opencog.org/w/PLN)
     3. [PLN Details](http://wiki.opencog.org/w/PLN_Details)
     3. [Cookbook PLN section](http://wiki.opencog.org/w/Cookbook#Using_PLN)
     3. [PLN usage](http://wiki.opencog.org/w/PLN_usage)
     3. [PLN Scheme Wrapper](http://wiki.opencog.org/w/PLN_Scheme_Wrapper)
   2. Test PLN using following test data (see [NARS syntax](http://code.google.com/p/open-nars/wiki/InputOutputFormat)): = 4 - 8 [difficulties of the NARS -> PLN translation]
     3. [check linked modules example](http://code.google.com/p/tu/source/browse/lib/src/main/test2.narsese)
     3. [check operation to module duplicates, see simplified version](http://code.google.com/p/tu/source/browse/lib/src/main/test4.narsese)
   2. Search for following options see [Emotion Machine book description](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708573):
     3. Simplification.
     3. Elevation (generalization).
     3. Reformulation.
     3. Contradiction.
     3. Simulation.
     3. Correlation.
     3. Logical Reasoning.
   2. Estimate the integration effort into Scala project.

 1. <a name="Bayesian_inference">[Bayesian Inference](http://en.wikipedia.org/wiki/Bayesian_inference)</a>
   2. Find implementation or use [ci-bayes](https://github.com/jottinger/ci-bayes) and install them = 16 - 32 [possibly a lot of implementations]
   2. Compare implementations and PLN using following examples:
     3. [check linked modules example](http://code.google.com/p/tu/source/browse/lib/src/main/test2.narsese)
     3. [check operation to module duplicates, see simplified version](http://code.google.com/p/tu/source/browse/lib/src/main/test4.narsese)
   2. Comparison criteria:
     3. Performance
     3. Adapter development estimate
     3. Probabilistic logic options, use options of PLN analysis

 1. <a name="Embodiment">Embodiment</a>, see [specification](https://github.com/development-team/2/blob/master/doc/informal/openCog.md#Embodiment).
   2. Read documentation = 8 - 16 [possibly further understanding of the Embodiment could be required to test it]
     3. [Main](http://wiki.opencog.org/w/Embodiment)
     3. <a href="http://wiki.opencog.org/w/UserManual_(Embodiment)">User manual</a>
   2. Test Embodiment system to simulate [Intellix](https://github.com/development-team/2/blob/master/doc/informal/intellix.md)
   with following components: = 20 - 40 [possibly difficulties with understanding of [Intellix world](/development-team/2/blob/master/doc/informal//intellix.md) simulation]
     3. Files
     3. Directories
     3. Operating system commands: copy file, move file, delete file, create file
     3. Program distributives
     3. Installed programs

 1. <a name="Natural_language_processors">Natural language processors</a> use measures: [precision, recall and F-measure](http://en.wikipedia.org/wiki/Precision_and_recall)
   2. [RelEx](https://github.com/development-team/2/blob/master/doc/informal/openCog.md#RelEx)
     3. Read documentation = 4
       4. [Main](http://wiki.opencog.org/w/RelEx)
       4. [Sentence algorithm](http://wiki.opencog.org/w/Sentence_algorithms)
       4. See ```/OpenCog/nlgen/README``` for the instructions to run
     3. Test NLP using 10 simple examples of test incidents. = 4 - 8
   2. [StanfordParser](http://nlp.stanford.edu/software/lex-parser.shtml) = 4 - 5
     3. Use [online](http://nlp.stanford.edu:8080/parser/) or download and use Java command to test using same 10 simple examples of test incidents.
   2. [OpenNLP](http://incubator.apache.org/opennlp/index.html) = 6 - 8 [could be not strait-forward to use with no documentation reading]
     3. Read documentation and Test using same 10 simple examples of test incidents.
   2. Comparison criteria: precision and recall

 1. <a name="NLGen">[NLGen](https://github.com/development-team/2/blob/master/doc/informal/openCog.md#NLGen)</a>
   2. [Read documentation](https://launchpad.net/nlgen2), [SegSim](http://wiki.opencog.org/w/SegSim) = 4
   2. Test if we could use it to generate clarification/confirmation requests: 24 - 32 [probable difficulties with setup and run of the module]
     3. Is my understanding correct?
     3. What is Bla-bla-bla?
     3. Please clarify the term.

### <a name="Training">System training prototype</a>

 1. <a name="MOSES">MOSES</a>, [see specification](https://github.com/development-team/2/blob/master/doc/informal/openCog.md#MOSES)
   2. Read documentation: = 2 - 3
     3. [Main](http://wiki.opencog.org/w/MOSES)
     3. [Quick Start](http://wiki.opencog.org/wikihome/images/4/4a/MOSES-QuickGuide.pdf)
     3. [Tutorial](http://wiki.opencog.org/w/MOSES_Tutorial)
     3. [Examples](http://wiki.opencog.org/w/MOSES_example_programs)
   2. Search for following options (see [Emotion Machine book description](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708573)): = 16 - 40 [difficulties in understanding of MOSES mechanisms]
     3. Reasoning By Analogy.
     3. Reformulation.
     3. Use external representations.
     3. Simulation.
     3. Correlation.
   2. Estimate the integration effort into Scala project.

 1. <a name="Bayesian_network">[Bayesian Network](http://en.wikipedia.org/wiki/Bayesian_network)</a>
   2. Find implementation or use [javabayes](http://www.cs.cmu.edu/~javabayes/) and install them = 16 - 32 [possibly a lot of implementations]
   2. Compare implementations and MOSES using following examples (see [Emotion Machine book description](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708573)):
     3. Reasoning By Analogy.
     3. Reformulation.
     3. Use external representations.
     3. Simulation.
     3. Correlation.
   2. Comparison criteria:
     3. Performance
     3. Adapter development estimate

 1. Training approach
   2. Create Training data structures.
     3. Incidents description text. 1 - 2
     3. Incident category. 1 - 2
     3. Incident formalized description.
         4. Model = 10 - 12
     3. Incident solution (Migrate HowTo approach). 10 - 14
   2. Create formalized description generalisation mechanism, see formalization criteria [example](https://github.com/development-team/2/blob/master/doc/informal/formalisation-criteria.md).
     3. Create common parts selector and variable introducer
         4. Comparator = 6 - 8
         4. Recursive runner = 8 - 10
         4. Variable introducer
             5. Design variable structure =  4 - 8
             5. Implement and test variable = 8 - 16
             5. Named variables builder including variable HowTo = 8 - 10
             5. Unnamed variable builder = 8 - 10
   2. Create incident classification algorithm
     3. Create approach to classify the incidents according to the description + [KnowledgeBaseAnnotator](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#KnowledgeBaseAnnotator) annotations + [Simulation](https://github.com/development-team/2/blob/master/doc/informal/prototypes_estimates.md#Prototype_Way2Think) created model based on LSA algorithm. {Model}
         4. Create test data for KnowledgeBaseAnnotator
             5. Allowed Software list = 6 - 8
             5. Over Software actions = 4 - 6
             5. Software attributes = 8 - 16
             5. Software states = 4 - 8
   2. Create validation criteria inference approach
     3. Create <a name="validator">validator</a>
         4. Analyse validation criteria types(direct instruction or problem description or both) according to incident types and problem indications = 10 - 20
         4. Create ```what could be wrong``` descriptions via predicates
             5. Create logical operations [interpreter](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Interpretation). = 28 - 32
         4. Create validation mechanism possibly via [PLN](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#PLN) adapter
             5. Design adapter = 8 - 16
             5. Implement adapter = 24 -32
   2. Create Solution generation prototype based on trained data(solution) and [HowTo](http://code.google.com/p/tu/wiki/HowTo) approach:
     3. HowTo types = 16 - 40
     3. Solution description language = 32 - 40
     3. Create 10 Solution descriptions = 16 - 32
     3. Train Solutions
         4. Analyse machine learning algorithms (C 4.5, Bayes network) = 16 - 24
         4. Train 10 Solutions (in: formalised problem description, out Solution) = 16 - 24

## <a name="Emotion_Machine">Emotion Machine Solution</a>, for the specification see [page](https://github.com/development-team/2/blob/master/doc/informal/emotion-machine.md).

 1. <a name="Test_data">Test data example</a>:
   2. Incident description: Wrong software was installed. I have ordered Word Dictionary Technical, but the Word Dictionary Business was installed. Please help.
   2. [Perceiving workflow](https://github.com/development-team/2/blob/master/doc/informal/emotion-machine.md#Formalisation_Production_Workflow):
   Deliberative Critics -> Simulation Way2Think(creates the model: User ordered Software(Word Dictionary Technical), order was processed by TSS, TSS installed Software(Word Dictionary Business), this Software is Wrong)
   -> Deliberative Critics -> Elevation Way2Think (creates model: Wrong Software(Word Dictionary Business), Intended Software(Word Dictionary Technical))

 1. <a name="Test_data_analysis">Test data analysis</a>
   2. Analyse 100 incident descriptions, and create: = 25 - 30
     3. Incidents description text (correct)
     3. Incident category.
     3. Incident formalized description.
     3. Incident solution.
   2. Analyse workbook articles = 14.4 - 32.4
     3. Create formalized Background description = 0.1
     3. Create formalized Problem description: = 0.25
         4. Entry criteria.
         4. Exit criteria.
         4. Inbound data.
         4. Outbound data.
     3. Solution in HowTo terms = 0.25 - 1

 1. <a name="Model">Model data structures:</a>
   2. Inbound textual description [Text](https://github.com/development-team/2/blob/master/doc/informal/annotation-interpretation-validation.md#Component_diagram) = 1 - 2
     3. Incidents description text.
     3. Incident category.
     3. Incident formalized description.
     3. Incident solution.
   2. <a name="Internal_knowledge">Internal knowledge(atom)</a> [representations](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305130) and it's [hierarchy](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305131) = 8 - 10 [possibly several approaches required]
     3. Panalogy (Realm, Domain), should take in account several realms representation of knowledge see Panalogy [here](http://web.media.mit.edu/~minsky/E6/eb6.html#_Toc516744255), [here](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc465796529).
     3. Context see [Micronemes for Contextual Knowledge of the chapter](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305130)
     3. References to other Realms representations
     3. References to other detalization levels representations
     3. Links to other atoms in current Realm and Context
     3. Links from other atoms in current Realm and Context
     3. Textual representation.
   2. Formalisation outbound structures(modeling and simulation) = 6 - 10
     3. Must have links to textual representations.
     3. Must conform formalisation criteria.
     3. Must be one of [representations](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305130)
   2. Design Solution structures (update [HowTo](http://code.google.com/p/tu/wiki/HowTo)) = 16 - 24

 1. <a name="Design_Emotion_Machine">Design</a> [Emotion Machine Solution](https://github.com/development-team/2/blob/master/doc/informal/emotion-machine.md) = 18 - 28 [first solution could be not the best]
   2. [Critics](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708574),
   2. [Selector](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708572), could be understood as resources(Critics, Way2Think) selection strategy,
   2. [Way2Think](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708573)
 1. Prototype Critics, Way2Think, Selector
   2. Implement Critics
     3. Deliberative Critics
         4. Current activity goal representation = 6 - 8 [possibly should try several approaches]
         4. Current task state representation =  4 - 6 [possibly should try several representations]
         4. Reasoning over current state with Reasoner adapter development = 8 - 10
     3. Reflective Critic (self-control mechanism)
         4. Develop Energy control mechanism.
             5. Design Energy concept = 4
             5. Design Energy assignment mechanism during training = 2
             5. Implement Energy concept = 2
             5. Implement Energy assignment mechanism = 4
         4. Develop Sub-goal control mechanism.
             5. Design sub-goal control mechanism = 4
             5. Implement sub-goal control mechanism = 4
         4. Develop Evidence control mechanism.
             5. Design Evidence control mechanism = 4 - 10 [no clear understanding of evidence control mechanism, possibly further research required]
             5. Implement Evidence control mechanism = 4 - 10
     3. Self-Reflective Critic = not requested in the prototype
     3. Self-Conscious Critic = not requested in the prototype
 1. Prototype Selector = 6 - 8
 1. <a name="Prototype_Way2Think">Prototype Way2Think</a>
   2. Create base constructs(HowTo-s) = 2 - 4
     3. Copy file
     3. Move file
     3. Delete file
     3. Install program
     3. Uninstall program
     3. Add user to user group
     3. Remove user from user group
   2. Knowing How (select from KB) = 4 - 8
   2. Extensive Search (search in KB)  = 12 - 24 [graph search could be not strait-forward]
   2. Reasoning by Analogy = 32 - 40 [graph analogy API could be not strait forward]
   2. Divide and Conquer
     3. Add analytical HowTo-s to basic set:
         4. Wrong software was installed = 4
         4. Two direct instructions in one incident description = 4
     3. Add <a name="Interpretation">Interpretation</a> mechanism over current state of the system
         4. Variable and current state addressing = 12 - 16
         4. Logical operation execution = 16
           5. Exists
           5. Equals value
           5. Conjunction
           5. Implication
           5. Not

   2. Planning (will not be implemented in prototype).
   2. Simplification (will not be implemented in prototype).
   2. Elevation (will not be implemented in prototype).
   2. Reformulation (will not be implemented in prototype).
   2. Self-reflection (will not be implemented in prototype).
   2. Contradiction (will not be implemented in prototype).
   2. Use external representations (will not be implemented in prototype).
   2. Simulation (build the model based on inbound information) =
     3. Search for concept in the KB. = 16
     3. Search for properties in KB. = 4
     3. Create connections of found concepts. = 2
     3. Support context. = 2
   2. Correlation (will not be implemented in prototype).
   2. Logical Reasoning see [PLN](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#PLN)
   and [Bayesian inference](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Bayesian_inference)
   2. Wishful thinking (will not be implemented in prototype).
   2. Impersonation (will not be implemented in prototype).
   2. Cry for help (escalation) [see](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#NLGen)
   2. Resignation (will not be implemented in prototype).
 1. Test main perceiving workflow
   2. Prototype Validation criteria to be used in test = should be done as part of [Training prototype](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#MOSES)
   2. Prototype [Main Workflow](https://github.com/development-team/2/blob/master/doc/informal/emotion-machine.md#Main_perceiving_workflow). = 10 - 14 [some not well tested bugs could slow down the development]{Models, Critics, Way2Think ready to use}


## <a name="AIV">AIV Solution</a>, for the specification see [page.](https://github.com/development-team/2/blob/master/doc/informal/annotation-interpretation-validation.md)

 1. <a name="Training_data">Create training data set.</a>
   2. Analyse 100 incidents and select 10 most relative = 2 - 4

 1. <a name="Auto_correct">Auto-correct</a> analysis should use measures: [precision, recall and F-measure](http://en.wikipedia.org/wiki/Precision_and_recall)
   2. AbiWord
     3. Read [Syntax check module documentation](http://www.abisource.com/projects/enchant/) = 1
     3. Install [AbiWord](http://www.abisource.com/download/) = 1 - 2
     3. Test [10 incidents descriptions](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Training_data) = 3.5 - 4
   2. OpenOffice(LibreOffice)
     3. Read Auto-correct module documentation
     [OpenOffice](http://wiki.services.openoffice.org/wiki/Documentation/OOoAuthors_User_Manual/Writer_Guide/Using_AutoCorrect)
     [LibreOffice](http://help.libreoffice.org/Common/AutoCorrect) = 1
     3. Install [OpenOffice](http://www.openoffice.org/download/) or [LibreOffice](http://www.libreoffice.org/download/) = 1 - 2
     3. Test [10 incidents descriptions](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Training_data) = 3.5 - 4
   2. OpenCog
     3. Read Auto-correct module documentation [Grammatical correction](http://wiki.opencog.org/w/Grammatical_correction) = 1 - 2
     3. Install [OpenCog](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#OpenCog) = 1 - 2
     3. Test [10 incidents descriptions](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Training_data) = 3.5 - 4
   2. KOffice
     3. Read Auto-correct [module documentation](http://userbase.kde.org/KWord/1.5/Manual/Autocorrect) = 1 - 2
     3. Install [KOffice](http://www.koffice.org/download/) = 1 - 4 [Could be not strait forward under Win]
     3. Test [10 incidents descriptions](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Training_data) = 3.5 - 4

 1. Link parsers(Preliminary annotator) should use measures: [precision, recall and F-measure](http://en.wikipedia.org/wiki/Precision_and_recall)
 see [Natural Language processing](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Natural_language_processors)

 1. <a name="KnowledgeBaseAnnotator">KnowledgeBaseAnnotator</a>, see [specification](https://github.com/development-team/2/blob/master/doc/informal/solution-ideas.md#Synonym_processing).
 knowledge base integration analysis.
   2. [WolframAlpha](http://www.wolframalpha.com/input/?i=what+is+the+meaning+of+life)
     3. Explore API to integrate = 10 - 20 [could be not strait forward]
     3. Test information retrieval and annotation options = 20 - 30 [could contain information not from SE domain]
   2. [TrueKnowledge](http://www.trueknowledge.com/q/what_is_the_meaning_of_life)
     3. Explore API to integrate = 10 - 20 [could be not strait forward]
     3. Test information retrieval and annotation options = 20 - 30 [could contain information not from SE domain]
   2. [ConceptNet](http://csc.media.mit.edu/conceptnet)
      3. Explore API to integrate = 10 - 20 [could be not strait forward]
      3. Test information retrieval and annotation options = 20 - 30 [could contain information not from SE domain]
   2. [ConceptNet5](http://conceptnet5.media.mit.edu/)
     3. Explore API to integrate = 10 - 20 [could be not strait forward]
     3. Test information retrieval and annotation options = 20 - 30 [could contain information not from SE domain]
   2. [WordNet](http://wordnet.princeton.edu/) is integrated in ConceptNet5, but could be useful standalone
     3. Explore API to integrate = 10 - 20 [could be not strait forward]
     3. Test information retrieval and annotation options = 20 - 30 [could contain information not from SE domain]
   2. [OpenCyc](http://www.opencyc.org/)
        3. Explore API to integrate = 10 - 20 [could be not strait forward]
        3. Test information retrieval and annotation options = 20 - 30 [could contain information not from SE domain]

 1. Interpreter and Validator see [Emotion Machine Solution](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Emotion_Machine)
