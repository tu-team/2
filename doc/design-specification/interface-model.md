# Interface model design
## Description

Interface model contains classes and interfaces to interact with user

## Model

![Menta Web Service Interface](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/InterfaceModel.png)

### RefObject

Represent common object that stored in KnowledgeBase.

 1. ObjectID unique ID among table
 1. Reference unique among all database
 1. Name string name of object

### Request

Object for holding user request.

 1. SubscriptionID - id of user subscription
 1. RequestText - raw view of user request
 1. Solution - link to fond solution
 1. State - request state (Searching solution, Executing, Error, Done)
 1. FormalizedRequest - network of formalized request

### User

Object for holding user info.

### Subscription

Holding user subscription info

 1. Endpoints - list of endpoints, that will be called when state will be changed

### UserEndpoint

 1. Type - endpoint type (email, webservice)
 1. Address - address of endpoint