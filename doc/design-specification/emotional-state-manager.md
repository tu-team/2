# Emotional State Manager design specification

Emotional State Manager is a critic that call [Set Emotional State](emotional-state-way2think.md).
This critic could be called by Time Control. The emotion state activate additional resources: stop all threads except this for example.

## Input

Call from other Critics to change emotional state with provided emotional event id, caller info and request id.

```NOTE:Event list should be loaded once from database and cached```

## Emotional Event

[Emotional Event](knowledge.md)

## Output

[SelectorRequest](selector.md#action) with point to [Set Emotional State](emotional-state-way2think.md)

## Workflow

 1. Recieves event id, caller info, request id
 1. Find suitable Emotional Event by event id in KnowledgeBase
 1. Fill event's Additional Info with caller info
 1. Call [Set Emotional State](emotional-state-way2think.md)

## Sample
We are going to SLA. TimeControl will call Emotional State Manager with event id SLA_GOING_TO_DIE and caller info "Time control"