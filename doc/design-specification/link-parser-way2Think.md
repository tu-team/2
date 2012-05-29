# LinkParser Way2Think design specification.

Parser is based on [RelEx](http://wiki.opencog.org/w/RelEx_Install), should create the [AnnotatedText](knowledge.md#AnnotatedSemanticNetwork) of Incident description.

## Entry criteria

[KnowledgeBaseAnnotator](annotator-way2Think.md) returned [AnnotatedNarrative](knowledge.md#AnnotatedNarrative).

## Exit criteria

[AnnotatedText](knowledge.md#AnnotatedText) of Incident description is created.

## Input

[Narrative-s](knowledge.md#Narrative) of terms annotated with [KBAnnotator](annotator-way2Think.md)

## Output

[AnnotatedText](SemanticNetwork with KLines)](knowledge.md#AnnotatedSemanticNetwork) of Incident description.

## Research

Investigation of the [sources](https://launchpad.net/relex/), [online](http://bazaar.launchpad.net/~relex-dev/relex/trunk/files) should indicate most appropriate method to
take in account annotations of [KBAnnotator](annotator-way2Think.md).
There are three options(dictionaries):
 1. Direct RelEx API concepts setup.
 1. RelEx dictionaries setup.
 1. WordNet dictionary setup.

## Workflow

 1. Each annotation from [AnnotatedText](knowledge.md#AnnotatedText) is added in dictionary (see Research section).
 1. RelEx batch process run based with Narrative.
