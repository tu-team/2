package tu.coreservice.annotator


import org.slf4j.LoggerFactory
import tu.coreservice.action.way2think.Way2Think
import tu.model.knowledge.communication.{ContextHelper, Context}
import tu.model.knowledge.{KnowledgeURI, Resource}
import tu.providers.AnnotatorRegistry
import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.primitive.KnowledgeString
import tu.coreservice.utilities.URIHelper

/**
 * Simple KBAnnotator implementation.
 * @author toschev alex
 *         Date: 15.06.12
 *         Time: 18:26
 *
 */


/*
александр тощев 4:06 PM
на примере software
max talanov 4:06 PM
вайт
александр тощев 4:06 PM
обхясни полный цикл
max talanov 4:06 PM
щас
06:56
щас
07:52
вот посмотри
08:17
TestDataGenerator.generateDomainModelConceptNetwork
08:36
это приходт тебе
08:53
вся модель предметной области
09:12
понятно что она будет как то лейзи лоадится из БД
09:18
но пока нампо фигу
09:31
мы ее потом прикрутим
09:32
ок ?
09:53
в ней ты ищеш software
10:08
именно концепцию а не фразу
10:48
однако мы можем сделать так
11:11
запомнить мапинг фраза - концепция
11:26
и проверять там тоже
11:48
для этого AnnotatedPhrase очень подходит
12:06
смотришь?
александр тощев 4:14 PM
так
15:01
мне приходит модель предметной области, а как мне приходит само Software?
15:04
это какой объект
15:05
?
15:16
я же должен искать Software в предметной области
15:22
по сути мне приходит только software
15:32
а всю сеть я беру из базы знаний
max talanov 4:15 PM
нет
16:15
мм тебе приходит разбитый на фразы текст
16:22
и модель знаний
александр тощев 4:16 PM
на предожения
16:27
ага
max talanov 4:16 PM
в виде ConceptNetwork
александр тощев 4:16 PM
после сплиттера
max talanov 4:16 PM
ага
александр тощев 4:16 PM
и?
max talanov 4:17 PM
далее берем фразу или слово
17:26
и ищем ее в AnnotatedPhreses
17:36
not found ->
17:52
search in external KB-s
18:07
not found -> cry for help
18:37
found -> search external concept in local KB
19:06
found -> hooray create mapping in AnnotatedPhrase
19:23
not found -> explore synonims
19:27
for 1 hop
19:36
found -> see hooray variant
19:50
not found -> cry for help
19:55
ок?
23:15
????
23:21
понятно ?
23:22
нет?
александр тощев 4:24 PM
втыкаю
max talanov 4:25 PM
щас позвню
александр тощев 4:25 PM
давй мне пару минут
max talanov 4:25 PM
ок
26:02
свистни
александр тощев 4:26 PM
в принципе понятно
26:59
потом еще спрошу
 */

class KBAnnotatorImpl extends Way2Think {

  val log = LoggerFactory.getLogger(this.getClass)

  /**
   * Way2Think interface.
   * @param inputContext Context of all inbound parameters.
   * @return outputContext
   */
  def apply(inputContext: Context): Context = {
    //we have output context from splitter

    //
    //splitter return annotated sentences array

    var annotatedPhrasesCollection=inputContext.frames.filter(p=> p._1.name.contains(URIHelper.splitterMark()))

    var extractedPhrases= annotatedPhrasesCollection.map(b=> b._2.asInstanceOf[AnnotatedPhrase].phrase)


    //instatiate output context
    var ctxOutput = ContextHelper.apply(annotatedPhrasesCollection.map(t=>t._2).toList,"AnnotatorResults")

    var wordsDetected=0

    //trying to annotate sentences

    val localAnnotator = AnnotatorRegistry.getLocalAnnotator()

    def checkLocalKB(phrase:String):Boolean={

      var localAnnotated=localAnnotator.apply(phrase)

      if (!localAnnotated.isEmpty)
      {
        addAnnotatedPhraseToOutputContext(localAnnotated.get)

        return true

      }

      return false

    }


    def addAnnotatedPhraseToOutputContext(phrase:AnnotatedPhrase)={
      wordsDetected+=1

      //find this phrase and context and append concepts to it
      ctxOutput.frames.find(
        p=>p._2.asInstanceOf[AnnotatedPhrase].phrase==phrase.phrase)
        .head._2.asInstanceOf[AnnotatedPhrase].concepts=phrase.concepts

    }

    extractedPhrases.foreach(ph=> {

      var annotationFound= checkLocalKB(ph)

      if (!annotationFound)
      {

           AnnotatorRegistry.listAnnotators().filter(p=> p.isLocal()!=true).foreach(a=> {
           val synonymous =a.annotate(ph)

           synonymous.foreach(syn=>{
             //check against local KB
             if (checkLocalKB(syn))
              {
                annotationFound=true

                scala.util.control.Breaks.break()
              }
           })

        })

        //if annotation still not found, just append as a empty phrase
        if (!annotationFound)
        {
          addAnnotatedPhraseToOutputContext(AnnotatedPhrase.apply(ph))
          //comment

        }
      }
    })




    ctxOutput
  }

  def start() = false

  def stop() = false
}