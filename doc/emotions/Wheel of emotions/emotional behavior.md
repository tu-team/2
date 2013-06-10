# Emotional behavior

## Abstract

## Background

Our model is based mainly on three approaches: thinking model described in Marvin Minsky book [The emotion machine](http://en.wikipedia.org/wiki/The_Emotion_Machine), Plutchik's [Wheel of emotions](http://en.wikipedia.org/wiki/Plutchik%27s_Wheel_of_Emotions#Plutchik.27s_wheel_of_emotions) and [Hourglass of emotions](http://link.springer.com/chapter/10.1007%2F978-3-642-34584-5_11#page-1) by Erik Cambria, Andrew Livingstone, Amir Hussain.

_TBD_

## The emotion machine.

According to Marvin Minsky the emotions are inseparable part of human thinking. Even human is bored with some task or talk it is very important for him or her to switch to another task. He exploits evolutionary approach to emotion development and supposes that emotions evolved for one reason human intellect could not exist in form and power as it is now without emotions.

Marvin Minsky suggested human thinking model based on [six levels](http://web.media.mit.edu/~minsky/E5/eb5.html):

 1. Instinctive reactions
 1. Learned reactions
 1. Deliberative thinking
 1. Reflective thinking
 1. Self-reflective thinking
 1. Self-conscious reflection

## Plutchik´s wheel of emotions

[Plutchik´s wheel of emotions](http://en.wikipedia.org/wiki/Plutchik%27s_Wheel_of_Emotions#Plutchik.27s_wheel_of_emotions) contains 8 basic emotions grouped in pairs:

 * Joy - Sadness
 * Trust - Disgust
 * Fear - Anger
 * Surprise - Anticipation

![Plutchik's wheel of emotions](Plutchik wheel 3D.gif)

## Hourglass of emotions

_TBD_

### Limitations

## Emotional behavior and emotions influence on computational processes

There are 4 sentic dimensions where 2 basic emotions and 4 supplemental are situated.According to [Sentic computing: 3.2.2 The Hourglass of Emotions](http://sentic.net/senticcomputing.pdf) Plutchik's wheel could be represented in 4 sentic dimensions:

 1. **Sensitivity** - the user is comfortable with interaction dynamics: rage, *anger*, annoyance, apprehension, *fear*, terror.  
 1. **Attention** - the user is interested in interaction contents: vigilance, *anticipation*, interest, distraction, *surprise*, amazement.
 1. **Pleasantness** - the user is amused by interaction modalities: ecstasy, *joy*, serenity, pensiveness, *sadness*, grief.
 1. **Aptitude** - the user is confident in interaction benefits: admiration, *trust*, acceptance, boredom, *disgust*, loathing. 

![Sentic axis](sentic axis Plutchik-wheel.png)

### Emotions influence on computational processes

Each emotional state on one axis has similar nature but strength of emotion changes from center to periphery.
According to assumption above there are 4 methods emotions could influence computational processes in intelligent system. Above this each emotional state defines highlight that is memorized during training. For example: joy experienced by intelligent system while reading positive feedback from human user for system's solution highlights solution and makes system choose this solution more probably.

Extending example above to whole 4 axis:

 1. Sensitivity makes system act reckless: as quick as possible, taking in account less variants. This way in state of *rage* deliberative thinking should take in account only 1 possible most probable variant, in state of *anger*: 20%-30% most probable variants, *annoyance* slightly reduces number of variants processed by deliberative thinking processes for 10%-20%, *apprehension*: makes system take in account all passible variants and 10%-20% less probable variants, *fear*: makes system take in account almost impossible variants 20%-30%, *terror*: stops system for a while with +100% all impossible variants taken in account. 
 1. Attention makes system allocate as much resources as possible to current process: *vigilance* makes system allocate 100% of available computation threads, *anticipation*: 50% of available processes, *interest*: 30% , *distraction*: 10, *surprise*: 5% , *amazement* stops system for a while with 0 computation threads.

also according to [Sentic computing: 3.2.2 The Hourglass of Emotions](http://sentic.net/senticcomputing.pdf) emotion strength is determined by Gaussian function
G(x) = -1*e^((-x^2)/(2*σ^2)):

![Gauss 3D](Gauss 3D axis.png)

Emotions of one axis has same nature that determines the way they affect the system.
According to the way emotions affect the system behaviour sentic axises are grouped in a following way:

 1. Resource distribution is affected by:
   2. Sensitivity
   2. Attention
 1. Emotional highlight is affected by:
  2. Pleasantness
  2. Aptitude 

## Resource distribution

Resource distribution is influenced in 2 dimensions: Sensitivity manages speed of processes and Attention manges number of resources, for example thinking threads.
 * Sensitivity:Rage makes system act as quick as possible taking in account as less variants as possible.
 * Sensitivity:Terror makes system struck almost stopping all current computations.
 * Attention:Vigilance makes system concentrate all computational resources on current task. All available threads should be distributed to current process.
 * Attention:Amazement releases as much computational resources as possible leaving current process with minimum threads.

##Emotional highlight

Emotional highlight is two dimensional: Pleasantness to Aptitude.
Each training act, such as: domain concept explanation, feedback and so on is highlighted in two dimensions according to current positive impression (pleasantness) and future benefit expectations(aptitude).



