# SemanticNetwork making sense analysis

## Word2ConceptLinksAnalyser

After the [Preprocessing](design-specification.md#Activity_diagram) is done, the KnowledgeBaseAnnotator crated the links to
[Domain concepts](knowledge.md#Domain). KnowledgeBaseAnnotator stores words without concepts mapping to [Unknown KLine](knowledge.md).
Word2ConceptLinksAnalyser analyses the Unknown KLine and start the
[DoNotUnderstand Way2Think](design-specification.md#Activity_diagram) in case if any unknown word found.

## SituationConsistencyAnalyser

After the [Simulation](way2Think.md) SituationConsistencyAnalyser checks if all the concepts of
AnnotatedText are mapped in the [Domain model instance](knowledge.md#Domain) and all links established during simulation conforms
with Domain model.
[DoNotUnderstand Way2Think](design-specification.md#Activity_diagram) in case if any invalid link or orphan concept found.

## ProblemConsistencyAnalyser

After [Reformulation](way2Think.md) or Simulation in case of DirectInstruction ProblemConsistencyAnalyser check all mandatory concepts
of [Problem in Domain model](knowledge.md#Domain) and all links to fit the Problem Model.

[DoNotUnderstand Way2Think](design-specification.md#Activity_diagram) in case if any invalid link or orphan concept found.