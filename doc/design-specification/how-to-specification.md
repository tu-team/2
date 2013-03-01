# How-to specification
![Knowledge structure](https://github.com/development-team/2/raw/master/doc/design-specification/uml/images/HowTo.png)

# Idea
There are 2 types of HowTo:

1. ValueHowTo
1. FunctionalHowTo

## ValueHowTo
Holds simple value. Such as string, integer e.t.c.

## FunctionalHowTo 
Holds script execution

1. FunctionBody is a set of how-to described function body
1. InputParameters - obfuscate input parameters for script
1. OutputParameters - pbfuscate output data from script

## Mapping mechanism

Solution actioally is sequence (Narrative) of HowTo-s. Solution is mapped to set of problems via [Training](training.md)

## <a name="ExecutionState">Execution State</a>
1. Success
2. Error

## Samples

Each solution is used by Applicator. For example request to fix the Problem:

```
1.  Login to Domain\Server 

2.	Launch Utility 12 for Windows Servers

3.	Open TAB1

4.	Navigate to All Managed Servers, find necessary Server from right panel, open Server properties

5.	Click on Backup Exec Services…

6.	Select necessary Server Cluster node, then press button Restart all Services

7.	Wait few seconds, then check Server Status

```

In this example we will have 7 how-to which can be represented by diffrent amount of scripts

```
login:howto{
  Parameters:[
    
    {Key:'ScriptName',
    Value:'LogonScript.bat'},
    {Key:'Description',
    Value:'Logon to server'}
  ]
  
  InputParameters:[
    {Key:'ServerName',
    Value:'Domain\Server'},
    {Key:'UserName',
    Value:'MyUser'}
  ]
  
  OutputParameters:[
    {Key:'SessionID',
    Value:'SSSE12'},
    
  ]
}

launch:howto{
  Parameters:[
    
    {Key:'ScriptName',
    Value:'LaunchScript.bat'},
    {Key:'Description',
    Value:'Launch the application'}
  ]
  
  InputParameters:[
    {Key:'ExecName',
    Value:'Utility12.exe'},
  ]
  
  OutputParameters:[
    {Key:'SessionID',
    Value:'SSSE12'},
    
  ]
}

```




# Comments

## 1

```
[18:12:43] Talanov Max: let's do it flexible

[18:13:12] Talanov Max: for example each how to should have some formalized decription of the situation it could be used

[18:13:31] Talanov Max: and then extended search could find it using this parameter

[18:13:40] Talanov Max: what about ithis?

[18:13:59] Talanov Max: for example using K-line
```