# Design specification 3.0

The purpose of this design to make TU as a Universal framework.


## Application
Will be used for translation from the robotics system to NEUCOGAR system for direct and indirect translation.


## Translation

The source data for this system is array of int with channel. Data will represented as JSON object
```
{channel: 0,
data: [2313,3223,123,12331]}
```
Output will be spike generation. Spike represents by family name and continious call. See [SpikeGeneratorWayToThink](SpikeGeneratorWayToThink).

## Thinking life-cycle modification
Thinking life-cycle should support new Goal - Resolve request, with new Critic - [UnderstandRequestCritic](UnderstandRequestCritic).

## New critics
New critics to classify inbound signal will be introduced:

1. [HandClassifier](SpikeCritics.md);
1. [DistanceClassifier](SpikeCritics.md);
1. [PainDetectorCritics](CheckMyState.md).

## New Goals
1. [CheckMyState](CheckMyState);
1. [UnderstandSpike](SpikeCritics).

## New Way2Think
1. [SpikeGeneratorWayToThink](SpikeGeneratorWayToThink);
1. [ContextChangedWayToThink](ContextChangedWayToThink);
1. [PainWay2Think](PainWay2Think).

## General Workflow
1. TLC activated with apply of request;
1. Set Goal to UnderstandRequest;
1. If request for 1.0 version use as previously, if Spike detected run UnderstandSpike;
1. Specific for channel spikes activated.
