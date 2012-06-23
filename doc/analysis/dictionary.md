# Incidents dictionary

## Invalid Login
Incident class, subclass of Invalid Authorization.

## Connectivity problem
Incident class of all connections issues.

## Can not find
Incident class, that occurs when user can not find something, usually the icon on his/her desktop.

## Reinstall software
Incident class of installation/reinstallation/upgrade requests.

## Can not process...
Start of escalation/clarification response of the system.

## Wrong application has been installed
Subclass of _Reinstall software_ class with additional parameters:
  - Installed(current, wrong) software.
  - Desired software.

## Insufficient disk space
Incident class of the lack of disk space issues.
Parameters:
 - driveName
 - address (network address)

## application =
Parameter shortcut to:
```
Software {
  name =
}
```

## Shared disk group connection
Incident class or requests to assign access rights to user group.
Parameters:
 - group
 - disk

## Invalid time
_Invalid parameter_ subclass with parameters:
 - software

## Setup Wi-Fi
Subclass of all _Unable to setup_ issues.
Parameters:
 - machine(network address)

## Format of different version
User can't work with document because he have older or newer programm.

## restore data
User want restore backuped version of data.
parameter - target date of target tag or target id

## tried actions
User tried do something

## disabled period
period when any actions from support disabled 

## extends
Keyword to identify the reference to super Class.

## AND
Keyword to identify the sequence of superclasses.


