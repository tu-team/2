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

0. Critical features:
 1. Neo4j storage
   2. Save and restore objects =16 (12 left)
   2. Integrate with system =10 (8 left)
   2. Import KBPrototype data in to Neo4j = 8 (7 left)
 1. Dialog
   2. Translate clarification Cry4Help to human readable form = 8h
   2. Transfer clarification Cry4Help-s to client (Simple stdout) = 8h
   2. Process human operator response (minimal file input)
      3. File format development = 2h
      3. Parser = 24h
   2. Support dialog context =
    3. Store Context if clarification = 3h
    3. Retrieve Context from KB = 3h
 1. Simple training
    2. Domain:
      3. Add new concept = 2h
      3. Add subConcept = 2h
      3. Add phrase = 2h
    2. Symptoms-solution pairs = 16h
0. Main features:
 1. ThinkingLifeCycle to support 6 layer approach
   2. Parallel different layers operations (Refactor ThinkingLifeCycle)
     3. Implement Sequence Way2Think = 6h
     3. Implement parallel processing of ordinary Way2Think = 16h
   2. Support registerProcess for the layer = 4h
   2. Support layerStop operation = 4h
 1. Whole set of Way2Think-s
   2. UpdateDomainModelConfidence = 32h
   2. AssignGoal = 16 h
     3. Refactor GoalManager to support assigned goals = 16 h
   2. CommunicateToUser (primitive stdout) = 4h
   2. StartTimeControl = 4h
   2. SetEmotionalState = 4h
 1. Whole set of Critic-s
   2. Refactor GoalManager (Use several probable Goal brunches, switch based on Context analysis) = 24h
   2. TimeControl = 2h
     3. ThinkingLifeCycle.stopProcessesOfLevel = 8h
   2. SituationConsistencyAnalyser = 32h
   2. ProblemConsistencyAnalyser = 8h
   2. SolutionCompletenessManager = 4h
   2. ProcessManager = 4h
   2. EmotionalStateManager = 4h

## Overall:

0. Critical features = 97h
0. Main features = 192h
0. Overall = 289h
