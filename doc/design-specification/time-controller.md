this critic control time of working.

## Entry criteria

notifier sent message

## Description

when new context created, critic call "notify every N minutes" event and check state of context after this time.

if state of context is resolved, notifier killed

## Action

  Select limit time from KB for current state
  if spent time more then limit time, kill all critics ant WayToThinks and change emotion state

## Approximate example:

```
{
 MATCH Notify event
 WHERE  spent time > limit time
 RETURN true
}
=>
{
 KILL all process in this context
 START DomainModel.WayToThink
 WHERE Name = ChangeEmotionState
}
```
