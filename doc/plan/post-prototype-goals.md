# Post prototype development goals

## Main target
System should be capable of:

 1. Support Neo4j storage = 0
 1. Support dialog to clarify new concept and phrases = 0
 1. ThinkingLifeCycle to support multy layer for Controllers = 0
 1. Support simple trainig: = 0
 1. Whole set of Way2Think-s
 1. Whole set of Critic-s

## Plan

 1. Neo4j storage
   2. Integrate with system
   2. Import KBPrototype data in to Neo4j
 1. Dialog
   2. Translate clarification Cry4Help to human readible form
   2. Transfer clarification Cry4Help-s to client
   2. Process human operator response
   2. Support dialog context
 1. ThinkingLifeCycle to support 6 layer approach
   2. Support registerProcess for the layer
   2. Support layerStop operation
 1. Simple training
   2. Domain:
     3. Add new concept
     3. Add subconcept
     3. Add phrase
   2. Symptomes - solution pairs
 1. Whole set of Way2Think-s
   2. UpdateDomainModelConfidence
   2. AssignGoal
   2. CommunicateToUser
   2. CreateContext
   2. StartTimeControl
   2. SetEmotionalState
 1. Whole set of Critic-s
   2. Refactor GoalManager
   2. TimeControl
   2. SituationConsistencyAnalyser
   2. ProblemConsistencyAnalyser
   2. SolutonCompletenessManager
   2. ProcessManager
   2. EmotionalStateManager


