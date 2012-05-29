# Store less probable Way2Think design specification.

After classification process, system stores the list of [SelectorRequestRulePair-s](knowledge.md#SelectorRequestRulePair) in the [Context](knowledge.md#Context)

## Entry criteria

[GetMostProbableActionAnalyser](get-most-probable-action-analyser.md) invokes StoreLessProbableWay2Think.

## Exit criteria

Less probable [SelectorRequestRulePair-s](knowledge.md#SelectorRequestRulePair) stored in the [Context](knowledge.md#Context) in Classification [KLine](knowledge.md#KLine).

## Input

[Narrative](knowledge.md#Narrative) of sorted [SelectorRequestRulePair-s](knowledge.md#SelectorRequestRulePair) according to [Frequency and Confidence](knowledge.md)

## Output

Stored in [Context](knowledge.md#Context) [Narrative](knowledge.md#Narrative) of sorted [SelectorRequestRulePair-s](knowledge.md#SelectorRequestRulePair) according to [Frequency and Confidence](knowledge.md)

## Workflow

System stores the [Narrative](knowledge.md#Narrative) of sorted [SelectorRequestRulePair-s](knowledge.md#SelectorRequestRulePair) according to [Frequency and Confidence](knowledge.md)
in the Incident the [Context](knowledge.md#Context) in Classification [KLine](knowledge.md#KLine).