# Design specification 3.0

The purpose of this design to make TU as a Universal framework.


## Application
Will be used for translation from the robotics system to NEUCOGAR (b) system for direct and indirect translation [Robot Dream project] (https://github.com/research-team/robot-dream/blob/master/doc/architecture.md).

![High level overview](RD_Life_cycle.png)
Overall robotic system life-cycle is divided into two phases: *wake* (day) and *dream* (night).

- [**a**] In this position a robotic system transfers the accumulated during *wake* phase experience into spiking neural network of the *dreaming brain*.
- [**b**] Processing of *dreaming phase* is done as follows:
  - The accumulated experience is transferred from a robotic system to the dreaming brain;
  - Then simulation starts producing a set of updated rules to a robotic system;
  - Finally update is transferred to a robotic system.
- [**c**] The updated behavior strategies is transferred to a robotic system and applied to it.
- [**d**] The robotic system continues it's wake phase with updated control system strategies, adjusted emotional reactions and accumulating new experience, storing all sensory inputs, to be processed again starting from **a**.

## Translation

The source data for this system is array of data, channel, time. Data will represented as JSON object.
```
{channel: 0,
data: [ {2313, 2017-02-02 13:33:21:122121},
{3223, 2017-02-02 13:33:21:122121},
{123,2017-02-02 13:33:21:122121},
{12331,2017-02-02 13:33:21:122121}]}
```
![Input data class digram](RoboticData.png)

Output will be spike generation. Spike represents by family name and and spikes time. See [SpikeGeneratorWayToThink](SpikeGeneratorWayToThink.md).

## Thinking life-cycle modification
Thinking life-cycle should support new Goal - Resolve request, with new Critic - [UnderstandRequestCritic](UnderstandRequestCritic.md).

## New critics
New critics to classify inbound signal will be introduced:

1. [HandClassifier](SpikeCritics.md);
1. [DistanceClassifier](SpikeCritics.md);
1. [PainDetectorCritics](CheckMyState.md).

## New Goals
1. [CheckMyState](CheckMyState.md);
1. [UnderstandSpike](SpikeCritics.md).

## New Way2Think
1. [SpikeGeneratorWayToThink](SpikeGeneratorWayToThink.md);
1. [ContextChangedWayToThink](ContextChangedWayToThink.md);
1. [PainWay2Think](PainWay2Think.md).

## General Workflow
1. TLC activated with apply of request;
1. Set Goal to UnderstandRequest;
1. If request for 1.0 version use as previously, if Spike detected run UnderstandSpike;
1. Specific for channel spikes activated.
