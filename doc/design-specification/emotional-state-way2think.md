# Set emotional state Way2Think design specification.

## Entry criteria

Emotional event occurred
Called by [Emotional State Manager](emotional-state-manager.md)

## Exit criteria

Emotional state successful set, otherwise [Cry4Help](cry4help.md) raised

## Input

[Emotional Event](knowledge.md)

## Output

None

## Workflow

 1. Recieved [Emotional Event](knowledge.md#EmotionalEvent)
 1. Increase current request context emotional indicator for this request with EmotionalEvent.RequestID (for example increase by -3)
 1. If emotional indicator less then -1 set all threads using thread pool to high priority. 
Find this request info in thread pool and set message types to HIGH
 1. If emotional indicator less then -3 stop execution of the request and invoke [cry for help manager](sla-timeout-critic.md)
 1. If emotional indicator greater then 2 decrease thread priority to lower then normal
 1. Otherwise do nothing

## Sample

 1. Received SLA loosing for request 1241
 1. Increase EmotionState of request. So, Now it is -5
 1. Find all threads of 1241 and stop them and Cry for help
