#SelfReflective "Do not understand" design specification.

Is subclass of [Critic](critics.md)

##Entry criteria

Not make sense. (Called by MakeSense critic in negative case)

##Exit criteria

[Critics rule](critics.md#rule) fired and completed.

##Rule

If no any possibilities to continue call cry for help.

##Approximate formal example:
```
{
 START DomainModel.Critics
 WHERE Name = Cry4Help
}
```