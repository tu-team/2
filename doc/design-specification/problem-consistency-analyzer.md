## Problem Consistency Analyzer design specification
is one of a [making sense analysers](making-sense-analyser.md).

## Entry criteria

[Reformulate way2think](reformulate-way2Think.md) has created [domain model of Problem description](knowledge.md#problem).

## Exit criteria

Analyzer complete the analysis of the [Problem description model](knowledge.md#problem)

## Input

1. [Situation description model](knowledge.md#situation).
 1. [Domain model](knowledge.md#domain), with all Standard links/relations. It's used for validating links of Situation model.
   2. [Domain model](knowledge.md#domain), Restrictions for nodes(mandatory nodes are marked). They are used for searching required links which are missed.
   2. List of rules for quantitative restrictions of the Domain model.
   For example: no orphaned nodes; SemanticNetwork of Domain model should be [connected](http://en.wikipedia.org/wiki/Connectivity_%28graph_theory%29).

## Output

Probability value of correctness of Problem's Model, (does Problem model match the Domain model [trained](training.md)).
Report of matching failures (what's wrong with Problem model).

## Workflow

  1. System checks if each link from Problem model, except [is-a relations](http://en.wikipedia.org/wiki/Is-a), is present in Domain ontology SemanticNetwork.
  I.e. link is valid for linked Domain model nodes or their [superclasses](http://en.wikipedia.org/wiki/Superclass_%28computer_science%29).
  1. System checks if each Domain model node or its superclass has enough corresponding links in Problem model.
  Mandatory links are enumerated in SemanticNetwork with restrictions. E.g. user account must have link to access rights.
  1. System checks if Problem models meets quantitative restrictions. E.g. count of orphaned nodes == 0.
  1. If Problem model is not valid, [Do not understand](SelfReflective-Do_not_understand.md) critic is fired.
