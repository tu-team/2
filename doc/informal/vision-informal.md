# Menta 0.3 vision.
Problem/prototype centric view.

![Problems mind map](https://github.com/development-team/2/raw/master/doc/informal/mind-maps/problem%20highlevel.png)

## Overall

 1. *Fuzzy* system
  2. We face new kind of system with no crisp ages of the components.
  2. [Inbound information could contain contradictions and different layers of abstraction.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Internal_knowledge)
 1. Perceiving mechanisms are close to thinking mechanisms and still not so
 [formalised as should be](https://github.com/development-team/2/blob/master/doc/informal/formalisation-criteria.md).

### Mitigation

 1. The application should be designed in special way.


## Perceiving algorithms

 1. [Lexical processing - depends on parser and trained data could be erroneous.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Natural_language_processors)
 1. [Annotation depends on Knowledge base (KB), should have at least minimal trained knowledge of TSS + IT common sense.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#KnowledgeBaseAnnotator)
 1. Interpretation by [LSA](http://en.wikipedia.org/wiki/Latent_semantic_analysis) is not ideal and could be replaced by other proper algorithm, see [prototype](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Emotion_Machine)
 1. [Validation criteria still not clear and could be too detailed.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#validator)

## Textual processing

 1. [Word processing - NLP tool kits still need more analysis for: the applicability to SE domain.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Natural_language_processors)
 1. Synonym an Homonym problem still need industrial approach = possibly we could use some Knowledge Base see [solutions](https://github.com/development-team/2/blob/master/doc/informal/solution-ideas.md#Synonym_processing),
  see [prototype](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#KnowledgeBaseAnnotator)

## Out: Formal incident description

 1. [Still not clear the formalization level required to infer the solution.](https://github.com/development-team/2/blob/master/doc/informal/vision-informal.md#Model)

## Incident classification

 1. Still not clear the algorithms of training and production classification, could be not good idea to use word frequencies, possibly some models or at least taking in account the generalised concepts of terms used in text.

## Emotion machine Solution problems

 1. [Problem and Solution description structure - Solution should be formal in the domain context, possibly HowTo approach could be reused.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Model)
 1. The way TSS could monitor the System solutions. = The Reporting system should be crated, for now only the application reporting should be used.
 1. [The textual pre-processing including auto-correct based on domain dictionary.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Auto_correct)
 1. Rules and mechanisms of Critics, see [§7-5. What are some useful Critics?](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708574),
 see [prototype](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Design_Emotion_Machine)
 1. Rules and mechanisms of Selector(Way2Think) see [§7-4. What are some useful Ways to Think?](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708573),
 see [prototype](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Design_Emotion_Machine)
 1. [Inbound Critics data structure.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Model)
 1. [Inbound WayToThink data structure.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Model)
 1. [Outbound WayToThink data structure.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Model)
 1. [Exit criteria of Critics->WayToThink loop.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Test_data)
 1. [Clarification/Confirmation response processing.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#NLGen)
 1. [Context support.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Internal_knowledge)

## Annotation interpretation validation Solution problems

Emotion machine +

 1. [Knowledge Base annotator is dependant on current KB available.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#KnowledgeBaseAnnotator)
 1. Domain KB creation could be expensive.
   2.	TSS1 courses entry requirements.
   2.	TSS1 training courses materials.
   2.	TSS1 – TSS2 intro into a project to be automated.
   2.	TSS1 – TSS2 workbooks of a project to be automated.
 1. [Validation mechanisms still obscure.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#validator)

## OpenCog Solution problems

 Emotion machine, Annotation interpretation validation +

  1. OpenCog is created on C++, could be hard to integrate with Scala application, see [JCog](https://launchpad.net/jcog), see [PLN](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#PLN) and [MOSES](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#MOSES) prototypes
    2. Some parts of OpenCog is in Java: RelEx, NLGen2.
  1. [OpenCog is the framework, we have to understand how do we integrate and implement parts of Menta-0.3 perceiving system.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#OpenCog)
  1. AtomSpace could be not usable for our [knowledge representation](http://web.media.mit.edu/~minsky/E8/eb8.html#_Toc518305130), see [prototype](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#AtomSpace)
  1. [PLN could be hard to integrate in Scala project.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#PLN)
  1. [MOSES could be not usable for our knowledge](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#MOSES).
  1. Embodiment could be not capable of expressing [Intellix](https://github.com/development-team/2/blob/master/doc/informal/intellix.md), see [prototype](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Embodiment).
  1. [RelEx could be not so good to be used in the project.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#Natural_language_processors)
  1. [NLGen2 could be not good enough to generate the clarification requests.](https://github.com/development-team/2/blob/master/doc/informal/prototypes-estimates.md#NLGen)