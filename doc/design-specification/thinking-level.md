# Thinking level design specification.

Thinking levels concept based on the [Critic Selector Model of Mind](http://web.media.mit.edu/~minsky/E7/eb7.html#_Toc508708572) by Marvin Minsky.

![Thinking model](http://web.media.mit.edu/~minsky/E7/eb7_files/image003.png).

Each [process](process.md) is subscribed to some level and then placed in proper level [KLine](knowledge.md#KLine), by [ThinkingLifeCycle](thinking-life-cycle.md).
Each upper level process controls and manages processes of lover level, lover level processes reports the results in [short-term memory](memory.md).

See example of understanding processes ![Life cycle activity](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/LifecycleActivity.png).

## Processes control

 1. SelfConscious: IndividualManager(manages ProcessManager), EmotionalStateManager.
 2. SelfReflective: ProcessManager(manages TimeControl, ShortTermMemoryControl), DoNotUnderstandManager(starts Cry4Help, stops Learned and Deliberative processes).
 3. Reflective level processes track the results of Learned and Deliberative levels processes and manages these levels processes start stop.
 They are: GoalManager(setup current [Goal](goal.md)), TimeControl, Word2ConceptsLinksAnalyser, Situation ConsistencyAnalyser, ProblemConsistencyAnalyser, SolutionCompletenessManager.
 4. Learned and Deliberative levels processes are controlled by [GoalManager](GoalManager.md) and set of trained [goals](goal.md).

 SelfConscious, SelfReflective and Reflective processes are started as parallel processes starting from IndividualManager that start all the rest of ThinkingLifeCycle processes.
 Only Deliberative and Learned processes, managed by [goals](goal.md) create sequences.

