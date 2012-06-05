# Problems solution ideas list.
Problems from [Informal vision](https://github.com/development-team/2/blob/master/doc/informal/vision-informal.md) solutions ideas.


## Knowledge

 1. Technical requirements of the TSS including IT common sense.
 1. Training course.
 1. Project induction.
 1. Workbooks.

## Naive (kids) perception

When people start to communicate they start using words to denote real world objects. This way words are references to
the objects that we already know. When kid do not understand some phrases, adults explains the logical connections and
logical operations to understand and process the phrase correctly. This way we learn to think logically during understanding process.

### Self learning IT common sense knowledge

From the system perspective the IT world is completely visible, regardless to access right of the system user.
This way the system could learn some common sense knowledge based on it's own experiments.
For example: system could/should try to copy then move files from source to destination directory,
this way it should learn that copying files creates one more instance in destination directory, but moving does not.

This way we need:

 1. Some system understandable *flexible* descriptions of words/phrases in common IT lexicon.
 1. Some logical mechanism to infer.

#### Discussion

- we can not count instances of files if we not understand what is file = no we can file is just some container for something in computer and it is line in ```ll``` result.
- categorization is important point of understanding as the [concept mining](http://en.wikipedia.org/wiki/Concept_mining) technique.
- main categories is some ok or not ok = do not understand this
- conflict is key of solution or key of new knowledge =

#### Dialectical example:

 - tutorial 1: you must put semicolon for delimit operators
 - tutorial 2: you must put semicolon at end of operator

If we see conflict, we can decide "tutorial 2 is wrong" or "tutorial 2 about different language" = this is bad example, expressions as predicates are ok in probabilistic logic(there is no conflict)
and they do not contain context.

## <a name="Synonym_processing">Synonym processing</a>
Possibly we could use some of public knowledge base:

 1. [WolframAlpha](http://www.wolframalpha.com/input/?i=what+is+the+meaning+of+life)
 1. [TrueKnowledge](http://www.trueknowledge.com/q/what_is_the_meaning_of_life)
 1. [ConceptNet](http://csc.media.mit.edu/conceptnet)
 1. [ConceptNet5](http://conceptnet5.media.mit.edu/)
 1. [WordNet](http://wordnet.princeton.edu/)

## <a name="Mistypes_processing">Mistypes processing</a>

 OpenOffice uses [Autocorrect](http://www.openoffice.org/api/docs/common/ref/com/sun/star/util/PathSettings.html#AutoCorrect)
 and [Autocomplete](http://www.openoffice.org/api/docs/common/ref/com/sun/star/awt/UnoControlComboBoxModel.html#Autocomplete) options that we can reuse.

 OpenCog has grammatical correction option too  http://wiki.opencog.org/w/Grammatical_correction

## Technical solutions:

 1. [Emotion Machine Solution](https://github.com/development-team/2/blob/master/doc/informal/emotion-machine.md)
 1. [Annotation-Interpretation-Validation Solution](https://github.com/development-team/2/blob/master/doc/informal/annotation-interpretation-validation.md)
 1. [OpenCog Solution](https://github.com/development-team/2/blob/master/doc/informal/openCog.md).


