# Computational model of emotion

1. [Marsella, S. & Gratch, J. Computational Models of Emotion. Chapter to be published in Scherer, K.R., Bänziger, T., & Roesch, E. (Eds.) A blueprint for an affectively competent agent: Cross-fertilization between Emotion 
Psychology, Affective Neuroscience, and Affective Computing. Oxford: Oxford University Press, in press](http://people.ict.usc.edu/~marsella/publications/MarGraPet_Review.pdf)

## Appraisal theory

Appraisal theorists typically view appraisal as the cause of emotion, or at least of the physiological, behavioral and cognitive changes associated with emotion. 

## Dimensional Theories 

Interestingly, we are not aware of any computational models that follow the suggestion from Zajonc and Russell that appraisal is a post hoc explanation of core affect.

*ZAJONC, R. B. (1980) Feeling and thinking: Preferences need no inferences. American Psychologist, 35, 151-175.*

## Anatomic approaches:

Computational models inspired by the anatomic tradition often focus on low-level, perceptual-motor tasks and encode a two-process view of emotion that argues for a **fast, automatic, undifferentiated emotional response** and a **slower, more differentiated response** that relies on higher level reasoning processes (e.g., Armony et al., 1997).

*ARMONY, J. L., SERVAN-SCHREIBER, D., COHEN, J. D. & LEDOUX, J. E. (1997) Computational modeling of emotion: Explorations through the anatomy and physiology of fear conditioning. Trends in Cognitive Science, 1, 28-34.*

## Rational approaches

AI and logic based.

## Communicative approaches

Communicative theories of emotion argue that emotion processes 
function as a communicative system; both as a mechanism for informing other individuals of one’s mental state –  and thereby facilitate social coordination  –  and as a mechanism for requesting/demanding changes in the behavior of others – as in threat displays (Keltner and Haidt, 1999, Parkinson, 2009). 

## Component model

![Component model of computational appraisal](component_model_of_computational_appraisal_models.png)

### Person-environment relationship

Lazarus (1991) introduced this term to refer to some representation 
of the agent’s relationship with its environment. This representation should allow an agent, in principle, 
to derive the relationship between external events (real or hypothetical) and the beliefs, desires and 
intentions of the agent or other significant entities in the (real or hypothetical) social environment.

*LAZARUS, R. (1991) Emotion and Adaptation, NY, Oxford University Press*

### Appraisal-derivation model
  
An appraisal-derivation model transforms some representation of the 
person-environment relationship into a set of appraisal variables. Affect-derivation Model: An affect-derivation model maps between appraisal variables and an affective state, and specifies how an individual will react emotionally once a pattern of appraisals has been determined.

*Smith and Kirby SMITH, C. A. & KIRBY, L. D. (2009) Putting appraisal in context: Toward a relational model of 
appraisal and emotion. Cognition and Emotion, 00.* 

Propose the term relational model to refer to this mapping,building on Lazarus’ idea that appraisal is a relational construct relating the person and the environment. They introduced the term to draw attention to the fact that many appraisal theories emphasize the mapping from appraisal variable to emotion but neglect the situational and dispositional antecedents of appraisal. As “relation” and “relational” often has a very different meaning within computer science, we prefer a different term.

### Appraisal variables:  

Appraisal variables correspond to the set of specific judgments that the agent can use to produce different emotional responses and are generated as a result of an appraisal-derivation model. Different computational appraisal models adopt different sets of appraisal variables, depending on their favorite appraisal theorist. For example, many approaches utilize the set of variables proposed by Ortony, Clore and Collins (1988) including AR (Elliott, 1992), EM (Neal Reilly, 1996), FLAME (El Nasr et al., 2000) and ALMA (Gebhard, 2005). Others favor the variables proposed by Scherer (Scherer, 2001) including WASABI (Becker-Asano and Wachsmuth, 2008) and PEACTIDM (Marinier et al., 2009).

*ORTONY, A., CLORE, G. & COLLINS, A. (1988) The Cognitive Structure of Emotions, Cambridge University Press.*
* SCHERER, K. R. (2001) Appraisal Considered as a Process of Multilevel Sequential Checking. IN SCHERER, K. R., SCHORR, A. & JOHNSTONE, T. (Eds.) Appraisal Processes in Emotion: Theory, Methods, Research. Oxford University Press.*

### Affect-derivation Model
 
An affect-derivation model maps between appraisal variables and an affective 
state, and specifies how an individual will react emotionally once a pattern of appraisals has been 
determined.

*Smith and Kirby Ibid. use the term structural model to refer to this mapping, drawing analogy to structural 
equation modeling KLINE, R. B. (2005) Principles and Practice of Structural Equation Modeling, The Guilford Press.,*

The statistical technique for estimating the causal relationships between variables that appraisal theorists often  use to derive these mappings. As the term “structural model” is often used to contrast with “process models” (a distinction we ourselves use later), we prefer the different term.

### Affect-Intensity model  

An affect-intensity model specifies the strength of the emotional response 
resulting from a specific appraisal. There is a close association between the affect-derivation model and 
intensity model (the intensity computation is often implemented as part of the affect-derivation model), 
however it is useful to conceptualize these separately as they can be independently varied – indeed 
computational systems with the same affect-derivation model often have quite different intensity 
equations (Gratch et al., 2009a)

*GRATCH, J., MARSELLA, S. & PETTA, P. (2009a) Modeling the Antecedents and Consequences of Emotion. Journal of Cognitive Systems Research, 10, 1-5.*

### Emotion/Affect
  
Affect is a representation of the agent’s current emotional state. This could be a 
discrete emotion label, a set of discrete emotions, core affect (i.e., a continuous dimensional space), or 
even some combination of these factors.

### Affect-consequent model 

An affect-consequent model maps affect (or its antecedents) into some behavioral or cognitive change.  Consequent models can be usefully described in terms of two dimensions, one distinguishing if the consequence is inner or outer directed (cognitive vs. behavioral), and the other describing whether or not the consequence feeds into a cycle (i.e., is closed-loop). Emotion can be directed outward into the environment or inward, shaping a person’s thoughts. Reflecting this, behavior consequent models summarize how affect alters an agent’s observable physical behavior and cognitive consequent models that determine how affect alters the nature or content of cognitive processes. Most embodied computational systems model the mapping between affect and physical display, such as facial expressions.

## Processing Assumptions

### Representational Specificity

Some further decompose appraisal checks into the representational details (e.g., domain propositions, actions, and the causal relationships 
between them) that are necessary for an agent to appraise its relationship to the environment (e.g., El 
Nasr et al., 2000, Gratch and Marsella, 2004a, Neal Reilly, 1996, Si et al., 2008, Mao and Gratch, 2006, 
Dias and Paiva, 2005, Becker-Asano, 2008).

Sander and colleagues (2005) provide a detailed neural network model of how appraisals are derived from the 
person-environment relationship, but the person-environment relationship itself is only abstractly 
represented. 

### Domain specific vs. Domain independent

Computational appraisal models differ in terms of how domain-specific knowledge is encoded and which components require domain-specific  input. Most systems incorporate domain-independent affect-derivation models (Marinier, 2008, Becker-Asano, 2008, Gratch and Marsella, 2004a, Gebhard, 2005, Neal Reilly, 1996, Bui, 2004). Fewer systems provide 
domain-independent algorithms for appraisal-derivation (e.g., Gratch and Marsella, 2004a, Neal Reilly, 1996, Si et al., 2008, El Nasr et al., 2000).  

# Example applications of this framework

Table 1 indicates that EMA is most interesting cognitive archintecture as it uses domain independent person-enviroment relationship, appraisal variables most complete list, see below, behavioral-concequences: most-intense emotion alters behavior display and action selection, cognitive-concequences: closed loop.  

1. Desirability
1. Likehood
1. Expectedness
1. Causal attribution
1. Controllability
1. Changeability 

EMA and FearNot! are two architectures that processes all 4 base emotions:

1. Hope
1. Joy
1. Fear
1. Sadness

Most interesting cognitive architectures:

1. EMA = Richard Lazarus (Lazarus, 1991)
1. ALMA is intended as a general programming tool to allow application developers to more easily construct computational models of emotion for a variety of applications (Gebhard, 2005).
1. FearNot! (Dias and Paiva, 2005), 

Other researchers have explored how emotions might improve the decision making capabilities of general models of intelligence (Scheutz and Sloman, 2001, Ito et al., 2008)

*LAZARUS, R. (1991) Emotion and Adaptation, NY, Oxford University Press.*
*GEBHARD, P. (2005) ALMA - A Layered Model of Affect. Fourth International Joint Conference on Autonomous Agents and Multiagent Systems. Utrecht.*
*DIAS, J. & PAIVA, A. (2005) Feeling and Reasoning: a Computational Model for Emotional Agents. Proceedings of 12th Portuguese Conference on Artificial Intelligence, EPIA 2005. Springer.*
*SCHEUTZ, M. & SLOMAN, A. (2001) Affect and agent control: experiments with simple affective states. IAT. World Scientific Publisher.*
*MAO, W. & GRATCH, J. (2006) Evaluating a computational model of social causality and responsibility. 5th International Joint Conference on Autonomous Agents and Multiagent Systems. Hakodate, Japan.*