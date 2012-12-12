# TU Web interface

Represents UI for user to interact with system. UI iteracts with system using the Web Service

![UI sketch](https://github.com/development-team/2/raw/master/doc/design-specification/ui-prototype/images/TU-Web.png)

# General requirements

	1. Interfaces should be implemented in dashboard style
	1. All authentification should be perfomaed using standart tools
	1. Interface should be fully AJAX enabled
	
# Use cases

## Create request
	1. User LogIn to system
	1. When nothing select in My Requests Request dialog is ready for new request, Request type is shown
	1. User selects request type Train or Operation
	1. User enters request
	1. User clicks Send button
	1. System saves request to internal DB
	1. System sends request to TU Web service using send request method
	1. System saves returned TU ID

## Monitor request
	1. Using the timer system refreshes user request state using the findRequest method of Web Service
	
## Existing request iteraction
	1. User clicks required request
	1. System updates Request dialog window with selected request info