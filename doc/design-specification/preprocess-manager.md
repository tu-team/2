# PreprocessManager Critic design specification.

## Entry criteria

Goal is set to PreprocessIncident. See [Goal Manager](GoalManager.md).

## Exit criteria

[Selector request](knowledge.md#SelectorRequest) containing :[PreliminarySplitter](splitting-text-to-words.md), [KnowledgeBaseAnnotator](annotator-way2Think.md) and [LinkParser](link-parser-way2Think.md) created.

## Input

[Context](knowledge.md#Context) Plain text of issue.

## Output

[AnnotatedText](knowledge.md#AnnotatedText) in semantic tree.

## Rule

```
{
 MATCH Goal
 WHERE PreprocessIncident
 RETURN true
}
=>
{
 START PreliminarySplitter.Way2Think, KnowledgeBaseAnnotator.Way2Think, LinkParser.Way2Think
}
```