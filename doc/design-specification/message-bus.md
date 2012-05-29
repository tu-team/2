# MessageBus

Adapter to 3rd party component, with messaging functionality:
Consider to use [Glassfish MessageBus](http://today.java.net/pub/a/today/2008/01/22/jms-messaging-using-glassfish.html#message-driven-beans)


## Interface

![MessageBus Interface](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/MessageBusInterface.png)


## unSubscribe

![unSubscribe](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/unSubscribe.png)

## subscribe(action : Action, goal : Goal), subscribe(action : Action, request : Request)

![Subscribe](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/subscribeactionActiongoalGoal.png)

## publish(goal : Goal) : Action, publish(request : Request) : Action

![publish](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/publishgoalGoalAction.png)