#summary

True: bad estimate - 13, real estimate less then 13
False: 5
Precision = need recheck.


Following feature may be interesting (for example to detect software names):

http://incubator.apache.org/opennlp/documentation/1.5.2-incubating/manual/opennlp.html#tools.namefind

Tried parsing issues. Performed following steps (the issues are in samples.txt):
1) First I separated sentences by the following command:

opennlp SentenceDetector en-sent.bin < samples.txt > samples-sent.txt

2) then I tokenized previous file:

opennlp TokenizerME en-token.bin < samples-sent.txt > samples-tokenized.txt

3) and then I parsed

opennlp Parser en-parser.bin en-parser-chunking.bin < samples-tokenized.txt > samples-parsed.txt

Below is results:

### 1. samplex.txt (примеры отделены друг от друга пустой строкой)
User has started using Lotus Notes on his his Vista PC this week and now he can't use Lotus Notes on his XP PC any more. He wants to restore Lotus Notes directory to status of 13/2 so he can use it on his XP PC. Please assist user.

Mapping of shared drive W:\ fails, user is using \\gbw9061105.gothenburg.vcc.ford.com\NetLogon\seclogon.bat and has restarted PC, but the problem persists

C3PNG - User's Catia V5 does not work after C3PNG update.

Ysoderqv is also missing IE8.

User is missing Internet Explorer 8!PLEASE NOTE!, the user reports that it's very important that this is solved BEFORE or AFTER 19th-20th January, since she's doing some imporant work business at the specified date!

installed, teamcenter and cnext5, seems to work.Seems to CatiaV5 aren¿t installed correcly.errormessenge: a program cannot display messge. the program need a promission or information to complete a task.It¿s a Vista-PC.

Flash Player - User reports problems with his current version of flash player.User reports that he has two computers, one with a working (and newer) flash player that he uses to work with and the other computer seems to have a older flash version which does not work properly.Could you take a look at this and see if there is any flash update that can (re)sent to his computer?

User is uanble to start KDP web, please reinstall Java.

Hi NAS Admin,Please connect following groups to the shared disk listed below and configure security permissions.

PV2C - Cisco IP communicator. Please see if you can fix the problem with the ip phone, it's stuck on configuring ip + sometimes Server error rejected: Security etc.

User cannot access external sites.Using VCC proxy.

Outlook Web Access (VCC) - 403 - Forbidden: Access is denied'C:\WINDOWS\system32\CCM\Cache\A0000B72.1.System\install.vbs'

Add new alias Host name on host that alias is wanted to: hrportal.volvocars.biz IP adress on host that alias is wanted to: 212.181.102.99 Wanted Alias:    webadviser.volvocars.net

Failed LOT OrderReciever: bmattss9

User needs document portal uppdate

user is not able to use the wirelsess function on his laptop.

User cannot access external sites, please change proxy settings.

User is unable to access external pages when connected via VPN, IE just stops responding.Confirmed proxy settings, which seemed correct and cleared cookies/temp internet files, but problem persists.Also tried to check "automatically detect settings", but it didn't solve the issue.Can you please assist?

### 2. gedit samples-sent.txt

User has started using Lotus Notes on his his Vista PC this week and now he can't use Lotus Notes on his XP PC any more.
He wants to restore Lotus Notes directory to status of 13/2 so he can use it on his XP PC.
Please assist user.

Mapping of shared drive W:\ fails, user is using \\gbw9061105.gothenburg.vcc.ford.com\NetLogon\seclogon.bat and has restarted PC, but the problem persists

C3PNG - User's Catia V5 does not work after C3PNG update.

Ysoderqv is also missing IE8.

User is missing Internet Explorer 8!PLEASE NOTE!, the user reports that it's very important that this is solved BEFORE or AFTER 19th-20th January, since she's doing some imporant work business at the specified date!

installed, teamcenter and cnext5, seems to work.Seems to CatiaV5 aren¿t installed correcly.errormessenge:
a program cannot display messge.
the program need a promission or information to complete a task.It¿s a Vista-PC.

Flash Player - User reports problems with his current version of flash player.User reports that he has two computers, one with a working (and newer) flash player that he uses to work with and the other computer seems to have a older flash version which does not work properly.Could you take a look at this and see if there is any flash update that can (re)sent to his computer?

User is uanble to start KDP web, please reinstall Java.

Hi NAS Admin,Please connect following groups to the shared disk listed below and configure security permissions.

PV2C - Cisco IP communicator.
Please see if you can fix the problem with the ip phone, it's stuck on configuring ip + sometimes Server error rejected: Security etc.

User cannot access external sites.Using VCC proxy.

Outlook Web Access (VCC) - 403 - Forbidden: Access is denied'C:\WINDOWS\system32\CCM\Cache\A0000B72.1.System\install.vbs'

Add new alias Host name on host that alias is wanted to: hrportal.volvocars.biz
IP adress on host that alias is wanted to: 212.181.102.99
Wanted Alias:    webadviser.volvocars.net

Failed LOT OrderReciever: bmattss9

User needs document portal uppdate

user is not able to use the wirelsess function on his laptop.

User cannot access external sites, please change proxy settings.

User is unable to access external pages when connected via VPN, IE just stops responding.Confirmed proxy settings, which seemed correct and cleared cookies/temp internet files, but problem persists.Also tried to check "automatically detect settings", but it didn't solve the issue.Can you please assist?

### 3. samples-tokenized.txt


User has started using Lotus Notes on his his Vista PC this week and now he ca n't use Lotus Notes on his XP PC any more .
He wants to restore Lotus Notes directory to status of 13/2 so he can use it on his XP PC .
Please assist user .

Mapping of shared drive W: \ fails , user is using \\gbw9061105.gothenburg .vcc.ford.com\NetLogon\seclogon.bat and has restarted PC , but the problem persists

C3PNG - User 's Catia V5 does not work after C3PNG update .

Ysoderqv is also missing IE8 .

User is missing Internet Explorer 8!PLEASE NOTE! , the user reports that it 's very important that this is solved BEFORE or AFTER 19th-20th January , since she 's doing some imporant work business at the specified date !

installed , teamcenter and cnext5 , seems to work .Seems to CatiaV5 aren¿t installed correcly .errormessenge :
a program cannot display messge .
the program need a promission or information to complete a task .It¿s a Vista-PC.

Flash Player - User reports problems with his current version of flash player.User reports that he has two computers , one with a working ( and newer ) flash player that he uses to work with and the other computer seems to have a older flash version which does not work properly .Could you take a look at this and see if there is any flash update that can ( re )sent to his computer ?

User is uanble to start KDP web , please reinstall Java .

Hi NAS Admin,Please connect following groups to the shared disk listed below and configure security permissions .

PV2C - Cisco IP communicator .
Please see if you can fix the problem with the ip phone , it 's stuck on configuring ip + sometimes Server error rejected : Security etc .

User cannot access external sites.Using VCC proxy .

Outlook Web Access (VCC ) - 403 - Forbidden : Access is denied'C :\WINDOWS\system32\CCM\Cache\A0000B72.1.System\install .vbs '

Add new alias Host name on host that alias is wanted to : hrportal .volvocars .biz
IP adress on host that alias is wanted to : 212.181.102.99
Wanted Alias : webadviser .volvocars .net

Failed LOT OrderReciever : bmattss9

User needs document portal uppdate

user is not able to use the wirelsess function on his laptop .

User cannot access external sites , please change proxy settings .

User is unable to access external pages when connected via VPN , IE just stops responding.Confirmed proxy settings , which seemed correct and cleared cookies/temp internet files , but problem persists .Also tried to check "automatically detect settings " , but it did n't solve the issue .Can you please assist ?

### 4. samples-parsed.txt


(TOP (S (NP (NNP User)) (VP (VP (VBZ has) (VP (VBN started) (S (VP (VBG using) (NP (NNP Lotus) (NNP Notes)) (PP (IN on) (NP (PRP$ his) (PRP$ his) (NNP Vista) (NN PC))) (NP (DT this) (NN week)))))) (CC and) (ADVP (RB now)) (SBAR (S (NP (PRP he)) (VP (MD ca) (RB n't) (VP (VB use) (NP (NNP Lotus) (NNP Notes)) (PP (IN on) (NP (PRP$ his) (JJ XP) (NN PC))) (ADVP (DT any) (JJR more))))))) (. .)))
(TOP (S (NP (PRP He)) (VP (VBZ wants) (S (VP (TO to) (VP (VB restore) (NP (NNP Lotus) (NNP Notes) (NN directory)) (PP (TO to) (NP (NP (NN status)) (PP (IN of) (NP (CD 13/2))))) (SBAR (IN so) (S (NP (PRP he)) (VP (MD can) (VP (VB use) (NP (PRP it)) (PP (IN on) (NP (PRP$ his) (NNP XP) (NN PC))))))))))) (. .)))
(TOP (S (INTJ (VB Please)) (VP (VB assist) (NP (NN user))) (. .)))

(TOP (NP (NP (NP (NNP Mapping)) (PP (IN of) (NP (NP (VBN shared) (NN drive)) (PP (IN W:) (NP (JJ \) (NNS fails))) (, ,) (PRN (S (NP (NN user)) (VP (VP (VBZ is) (VP (VBG using) (NP (NNP \\gbw9061105.gothenburg) (NNP .vcc.ford.com\NetLogon\seclogon.bat)))) (CC and) (VP (VBZ has) (VP (VBN restarted) (NP (NN PC)))))))))) (, ,) (CC but) (NP (DT the) (NN problem) (NNS persists))))

(TOP (NP (NP (NNP C3PNG)) (: -) (S (NP (NP (NNP User) (POS 's)) (NNP Catia) (NNP V5)) (VP (VBZ does) (RB not) (VP (VB work) (PP (IN after) (NP (NNP C3PNG) (NN update)))))) (. .)))

(TOP (S (NP (NNP Ysoderqv)) (VP (VBZ is) (ADVP (RB also)) (VP (VBG missing) (NP (PRP IE8)))) (. .)))

(TOP (S (S (NP (NNP User)) (VP (VBZ is) (VP (VBG missing) (NP (NNP Internet) (NNP Explorer) (JJ 8!PLEASE) (NNP NOTE!))))) (, ,) (NP (DT the) (NN user)) (VP (VBZ reports) (SBAR (IN that) (S (NP (PRP it)) (VP (VBZ 's) (ADJP (RB very) (JJ important)) (SBAR (IN that) (S (NP (DT this)) (VP (VBZ is) (VP (VBN solved) (NP (NNP BEFORE) (CC or) (NNP AFTER) (JJ 19th-20th) (NNP January)) (, ,) (SBAR (IN since) (S (NP (PRP she)) (VP (VBZ 's) (VP (VBG doing) (NP (DT some) (JJ imporant) (NN work) (NN business)) (PP (IN at) (NP (DT the) (JJ specified) (NN date))))))))))))))) (. !)))

(TOP (S (S (VP (VBN installed))) (, ,) (PRN (S (NP (NP (NN teamcenter)) (CC and) (NP (CD cnext5))) (, ,) (VP (VBZ seems) (S (VP (TO to) (VP (VB work) (ADVP (RB .Seems)) (S (VP (TO to) (VP (VB CatiaV5) (NP (ADJP (RB aren¿t) (VBN installed)) (JJ correcly) (NN .errormessenge))))))))))) (: :)))
(TOP (NP (NP (DT a) (NN program)) (PP (IN cannot) (NP (NN display) (NN messge))) (. .)))
(TOP (S (NP (DT the) (NN program)) (VP (VBP need) (NP (DT a) (NN promission) (CC or) (NN information)) (S (VP (TO to) (VP (VB complete) (NP (DT a) (NN task)) (PP (IN .It¿s) (NP (DT a) (NNP Vista-PC.)))))))))

(TOP (NP (NP (NNP Flash) (NNP Player)) (: -) (NP (NP (NN User) (NNS reports)) (SBAR (S (NP (NP (NNS problems)) (PP (IN with) (NP (NP (PRP$ his) (JJ current) (NN version)) (PP (IN of) (NP (NN flash) (NN player.User)))))) (VP (VBZ reports) (SBAR (IN that) (S (NP (PRP he)) (VP (VBZ has) (NP (NP (CD two) (NNS computers)) (, ,) (NP (NP (CD one)) (PP (IN with) (NP (NP (DT a) (NN working)) (PRN (-LRB- -LRB-) (CC and) (NP (JJR newer)) (-RRB- -RRB-)) (NN flash) (NN player))) (SBAR (IN that) (S (S (NP (PRP he)) (VP (VBZ uses) (S (VP (TO to) (VP (VB work) (PP (IN with))))))) (CC and) (S (NP (DT the) (JJ other) (NN computer)) (VP (VBZ seems) (S (VP (TO to) (VP (VB have) (NP (NP (DT a) (JJR older) (NN flash) (NN version)) (SBAR (WHNP (WDT which)) (S (VP (VBZ does) (RB not) (VP (VB work) (ADVP (RB properly)) (VP (VBN .Could) (S (NP (PRP you)) (VP (VP (VB take) (NP (NP (DT a) (NN look)) (PP (IN at) (NP (DT this))))) (CC and) (VP (VB see) (SBAR (IN if) (S (NP (EX there)) (VP (VBZ is) (NP (NP (DT any) (NN flash) (NN update)) (SBAR (WHNP (WDT that)) (S (VP (MD can) (PRN (-LRB- -LRB-) (NP (NN re)) (-RRB- -RRB-)) (VP (VBD sent) (PP (TO to) (NP (PRP$ his) (NN computer)))))))))))))))))))))))))))))))))))) (. ?)))

(TOP 
  (S 
    (NP 
      (NNP User)
    )
    (VP (VBZ is) 
        (ADJP 
          (JJ uanble)
          (S (VP (TO to) 
             (VP 
                (VP 
                   (VB start) 
                   (NP (NNP KDP) (NN web))
                ) (, ,) (VP (VB please) (NP (NN reinstall) (NNP Java)))))))) (. .)))

(TOP (S (NP (NNP Hi) (NNP NAS) (NNP Admin,Please)) (VP (VBP connect) (NP (VBG following) (NNS groups)) (PP (TO to) (NP (NP (DT the) (VBN shared) (NN disk)) (VP (VBN listed) (PP (IN below) (NP (DT and) (NN configure) (NN security) (NNS permissions))))))) (. .)))

(TOP (NP (NP (NNP PV2C)) (: -) (NP (NNP Cisco) (NN IP) (NN communicator)) (. .)))
(TOP (S (ADVP (RB Please)) (VP (VB see) (SBAR (S (SBAR (IN if) (S (NP (PRP you)) (VP (MD can) (VP (VB fix) (NP (DT the) (NN problem)) (PP (IN with) (NP (DT the) (NN ip) (NN phone))))))) (, ,) (NP (PRP it)) (VP (VBZ 's) (VP (VBN stuck) (PP (IN on) (S (VP (VBG configuring) (NP (NN ip) (NNS +)) (ADVP (RB sometimes)) (S (NP (NNP Server) (NN error)) (VP (VBD rejected) (: :) (NP (NN Security) (NN etc)))))))))))) (. .)))

(TOP (NP (NP (NNP User)) (PP (IN cannot) (NP (NN access) (JJ external) (NN sites.Using) (NNP VCC) (NN proxy))) (. .)))

(TOP (NP (NP (NP (NP (NP (NNP Outlook) (NNP Web) (NNP Access)) (PRN (-LRB- -LRB-) (NP (NNP VCC)) (-RRB- -RRB-))) (: -) (NP (CD 403))) (: -) (NP (NNP Forbidden))) (: :) (S (NP (NNP Access)) (VP (VBZ is) (VP (VBN denied'C) (NP (JJ :\WINDOWS\system32\CCM\Cache\A0000B72.1.System\install) (NNS .vbs))))) ('' ')))

(TOP (VP (VB Add) (NP (JJ new) (JJ alias) (JJ Host) (NN name)) (PP (IN on) (NP (NN host))) (SBAR (IN that) (S (NP (NNS alias)) (VP (VBZ is) (VP (VBN wanted) (S (VP (TO to) (: :) (NP (JJ hrportal) (NNS .volvocars)))))))) (. .biz)))
(TOP (NP (NP (NN IP) (NN adress)) (PP (IN on) (NP (NN host))) (SBAR (IN that) (S (NP (NNS alias)) (VP (VBZ is) (VP (VBN wanted) (S (VP (TO to) (VP (VB :) (NP (CD 212.181.102.99)))))))))))
(TOP (NP (NP (JJ Wanted) (NNP Alias)) (: :) (NP (NN webadviser) (NNS .volvocars)) (. .net)))

(TOP (NP (NP (JJ Failed) (NNP LOT) (NNP OrderReciever)) (: :) (NP (CD bmattss9))))

(TOP (S (NP (NNP User)) (VP (VBZ needs) (NP (NN document) (JJ portal) (NN uppdate)))))

(TOP (S (NP (NN user)) (VP (VBZ is) (RB not) (ADJP (JJ able) (S (VP (TO to) (VP (VB use) (NP (DT the) (NN wirelsess) (NN function)) (PP (IN on) (NP (PRP$ his) (NN laptop)))))))) (. .)))

(TOP 
   (S 
      (NP 
        (NP (NNP User)) 
        (PP (IN cannot) 
            (NP (NN access) 
                (JJ external) 
                (NNS sites)
            )
        )
      ) 
      (, ,) 
      (VP (VB please) 
          (NP (NN change) (NN proxy) (NNS settings))) (. .)))

(TOP (S (S (S (S (NP (NNP User)) (VP (VBZ is) (ADJP (JJ unable) (S (VP (TO to) (VP (VB access) (NP (JJ external) (NNS pages)) (SBAR (WHADVP (WRB when)) (S (VP (VBN connected) (PP (IN via) (NP (NNP VPN)))))))))))) (, ,) (NP (NNP IE)) (ADVP (RB just)) (VP (VBZ stops) (NP (NP (JJ responding.Confirmed) (NN proxy) (NNS settings)) (, ,) (SBAR (WHNP (WDT which)) (S (VP (VBD seemed) (NP (ADJP (JJ correct) (CC and) (JJ cleared)) (NN cookies/temp) (NN internet) (NNS files)))))))) (, ,) (CC but) (S (NP (NN problem) (NNS persists)) (ADVP (RB .Also)) (VP (VBD tried) (S (VP (TO to) (VP (VB check) (RB "automatically) (VP (VB detect) (NP (NNS settings)) (ADVP (RB "))))))))) (, ,) (CC but) (S (NP (PRP it)) (VP (VBD did) (RB n't) (VP (VB solve) (NP (DT the) (NN issue)) (SBAR (IN .Can) (S (NP (PRP you)) (VP (VB please) (VP (VB assist)))))))) (. ?)))

### Results

Flash Player - User reports problems with his current version of flash player [reports – not a verb, problems with his current version of flash player don't match to reports]. User reports that he has two computers, one with a working (and newer) flash player that he uses to work with and the other computer seems to have a older flash version which does not work properly. Could you take a look at this and see if there is any flash update that can (re)sent to his computer?

Hi NAS Admin,Please connect following groups to the shared disk listed below and configure security permissions[configure is not on the same level as the connect].

PV2C - Cisco IP communicator. Please see if you can fix the problem with the ip phone, it's stuck on configuring ip + sometimes Server error[with ip phone don't match to the problem]

User cannot access external sites.Using VCC proxy. [external sites not a phrase]
User cannot access external sites, please change proxy settings. [The same]

total: 5/18 ~ 28% errors

## Comments

 1. Please place this page in https://github.com/development-team/2/tree/master/doc/analysis
 1. The internal language of the project is English.

