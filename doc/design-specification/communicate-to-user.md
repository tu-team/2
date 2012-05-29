# CommunicateToUser [Way2Think](way2Think.md)

Communicate to user Way2Think sends solution for request which system found for request

###Entry criteria

System found [Solution](solution.md) for the [Request](knowledge.md#Request).

###Exit criteria

Immediatelly.

###Input

- [Solution](solution.md).
- [Request ID](knowledge.md#EmotionalEvent).

###Output

Confirmation request.

###Workflow

- To form confirmation request for [Solution](solution.md).
- To send message with confirmation request to [MessageBus](message-bus.md) using [Request ID] (knowledge.md#EmotionalEvent).

