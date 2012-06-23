# Training Examples

## <a name="Domain">Domain Concept training</a>

### Domain Concepts
```
Action:{

}
Object:{

}
Link:{

}
```

### Textual input

```
Firefox is a soft. Soft can be installed, reinstalled, removed. Instead of soft term we can use application, program.
```

### Domain Data:

```
Soft:Object {
 synonyms:[Application, Program]
 appliedActions:[Install, ReInstall]
}

Action:{
 LinkedLinguisticTerm:[
  verb
 ]
}

AppliedTo:Link{
  linguisticTerm:[
   dobj
  ]
}

Install:Action{
}

ReInstall:Action{
}

Remove:Action{
}

```

### Linguistic Data:

```

subject:Object{

}

verb:Object{

}

dobj:Link{

}
```

## <a name="Incident">Incident handling training</a>
3 incidents examples:

```
1.User is missing "Outlook pst file backup" icon on her desktopName
2.Users VPN client 4.0.4 is corrupt and needs to be reinstalled
3.User is unable to access external pages when connected via VPN,
IE just stops responding. Confirmed proxy settings, which seemed
correct and cleared cookies/temp internet files, but problem persists.
Also tried to check "automatically detect settings", but it didn't
solve the issue.Can you please assist?
```

### Training Goals structure example:

1. ResolveIncident
  2. Missing backup link
1. ResolveIncident
  2. CorruptedSoftware
1. ResolveIncident
  2. AccessProblem
    3. VPN

### Training Goals with Solution KLines.
Example:

1. ResolveIncident
  2. CorruptedSoftware

```
Solution:{
 how-tos:[remove,install]
}

remove:howto{
  Parameters:[

    {Key:'SoftwareName',
    Value:'VPN'},
    {Key:'ServerName',
    Value:'TestServer'}
  ]

  InputParameters:[
    {Key:'SoftwareName',
    Value:'VPN'},
    {Key:'ServerName',
    Value:'TestServer'}
  ]

  OutputParameters:[
    {Key:'Result',
    Value:''},

  ]
}

install:howto{
  Parameters:[

    {Key:'SoftwareName',
    Value:'VPN'},
    {Key:'ServerName',
    Value:'TestServer'}
  ]

  InputParameters:[
    {Key:'SoftwareName',
    Value:'VPN'},
    {Key:'ServerName',
    Value:'TestServer'}
  ]

  OutputParameters:[
    {Key:'Result',
    Value:''},

  ]
}
```
