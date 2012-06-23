# TUWebService

Represents Web Service that serve User requests. Requests contain subscription information (callbacks) of clients. All work will be processed by CoresService

# Interface

![Web Service Interface](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/WebService.png)

  1. createRequest(request:[Request](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#Request)):[RefObject]((https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject) - create request from user. SubscriptionID passed to this method as a key to validate request.
  1. subscribe(user:[User](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#User),subscription:[Subscription](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#Subscription)) - create subscription for user.
  1. unsubscribe(user:[RefObject](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject),subscription:[RefObject](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject)) - remove subscription of user.
  1. updateSubscription(user:[RefObject](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject),subscription:[Subscription](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#Subscription)) - update user subscription data
  1. getSubscription(subscriptionID:[RefObject](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject)):List<[Request](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#Request)> - return subscription data
  1. findRequests(user:[RefObject](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject)) - get list of requests with current state
  1. createUser(user:[User](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#User)):[RefObject](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject) - create new user.
  1. updateUser(user:[User](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#User))- update user
  1. removeUser(user:[RefObject](https://github.com/development-team/2/blob/master/doc/design-specification/interface-model.md#RefObject)) - remove user

For class definition see [Interface Model](interface-model.md)

# Workflow

## Request

 1. User invokes WebService.createRequest and provides request info
 1. System saves request to database and start processing
 1. When request.state changes system notifies all user's subscription endpoints

