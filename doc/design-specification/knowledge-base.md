# KnowledgeBase server design specification.
KnowledgeBaseServer (KBServer) used neo4j database. In general KBServer utilize graph approach.

## Interface

![KnowledgeBase interface](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/KnowledgeBaseServer.png)

There are 2 type of objects:

 1. Object - standart knowledge object
 1. BusinessObject - object for [Web Services](tu-web-service.md). (User,Request)

Methods:
 1. save(obj:[Resource](knowledge.md#Resource)):[Resource](knowledge.md#Resource) - save resource on database
 1. remove(obj:[Resource](knowledge.md#Resource)) - remove object, reference should be passed
 1. select(obj:[Resource](knowledge.md#Resource)):[Resource](knowledge.md#Resource) - select object from database
 1. link(obj:List<[Resource](knowledge.md#Resource)>,linkName:String) -  link objects using provided link name
 1. selectLinkedObject(obj:[Resource](knowledge.md#Resource),linkName:String):Link<[Resource](knowledge.md#Resource)> - return list of linked as a linkName to specific object objects
 1. addLinkedObject(parent:[Resource](knowledge.md#Resource),toLink:[Resource](knowledge.md#Resource),linkName:String) - link object to parent as a linkName
 1. saveRequest(obj:[Request](tu-web-service.md#Request)) - save request to database
 1. selectRequest(obj:[RefObject](tu-web-service.md#RefObject)) - select request from KBServer
 1. saveBusinessObject(obj:[RefObject](tu-web-service.md#RefObject)):[RefObject](tu-web-service.md#RefObject) - save business object to database
 1. selectBusinessObject(obj:[RefObject](tu-web-service.md#RefObject)):[RefObject](tu-web-service.md#RefObject) - load business object from database

## ID of objects
Each object unique represents by GUID and stored in database