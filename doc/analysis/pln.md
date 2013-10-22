# PLN analyzis report
## Documentation links
Documentaion about PLN could be found here:

[PLN usage](http://wiki.opencog.org/w/PLN_usage), [PLN Details](wiki.opencog.org/w/PLN_Details), [List_of_PLN_rules](http://wiki.opencog.org/w/List_of_PLN_rules), [Probabilistic_Logic_Networks](http://wiki.opencog.org/w/Probabilistic_Logic_Networks), [OpenCog_Core](http://wiki.opencog.org/w/OpenCog_Core), [PLN_Scheme_Wrapper](http://wiki.opencog.org/w/PLN_Scheme_Wrapper), [OpenCog_shell](http://wiki.opencog.org/w/OpenCog_shell)

Also [Link_types](http://wiki.opencog.org/w/Link_types)<br>
[List of Rules](http://wiki.opencog.org/w/List_of_PLN_Rules)

## Run PLN
1. `cd /OpenCog/opencog`
2. `sudo bin/opencog/server/cogserver -c lib/opencog.conf`
3. `rlwrap telnet localhost 17001`
4. `scm`

## Short summary
Better than NARS, because user can set target to be found. <br>
Rather slow when using forward chaining, but it works fine as it should be. It works slower when any other statements were loaded into openCog. <br>
Backward chaining goes well.

## Logical operations under consideration
All the operations have different modifications with their own implementation. Full list is here [List of Rules](http://wiki.opencog.org/w/List_of_PLN_Rules).

* Conjunction <br>
(AndLink (stv 0.25 0.56)
   (EvaluationLink (stv 0.5 0.80000001)
      (PredicateNode "AboutGreaterThan")
      (ListLink
         (ConceptNode "a")
         (ConceptNode "b")
      )
   )
   (EvaluationLink (stv 0.5 0.69999999)
      (PredicateNode "AboutGreaterThan")
      (ListLink
         (ConceptNode "b")
         (ConceptNode "c")
      )
   )
)
* Negation
  * Sample: /OpenCog/opencog/tests/reasoning/pln/scm/new
  * NotRule
* Implication 
  * Sample: /OpenCog/opencog/tests/reasoning/pln/scm/new/implications.scm 
  * implemented via ImplicationLink

## Performed examples 
The followong examples were run from OpenCog/opencog/tests/reasoning/pln/scm/new:

* Not.scm
* Implication.scm
* cheeseMaking.scm � nice sample when target have no step to be counted
* `/OpenCog/opencog/tests/reasoning/pln/scm/new/transitiveRelation.scm` � works with (pln-bc t 10000)
* `/OpenCog/opencog/tests/reasoning/pln/targets/bc/AnotBdemo_full_test.conf`<br>
  `/OpenCog/opencog/tests/reasoning/pln/scm/AnotBdemo.scm` - works for backward chaining

## Comments
 1. Please place this page in https://github.com/development-team/2/tree/master/doc/analysis
 1. I can not see the Conjunction, is it implemented.
 1. Please add the conclusion, what we need to add in PLN?