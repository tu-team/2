package tu.coreservice.annotator


import org.slf4j.LoggerFactory
import relex.entity.{EntityMaintainer, EntityTaggerFactory, EntityTagger}
import relex.{ParsedSentence, Sentence, RelationExtractor}
import collection.JavaConversions._
import java.util.Properties
import java.io.InputStream
import relex.output.{SimpleView, OpenCogScheme}
import tu.coreservice.spellcorrector.SpellCorrector

/**
 *
 * @author toscheva
 * Date: 15.06.12
 * Time: 18:26
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

class KBAnnotator {

  val log = LoggerFactory.getLogger(this.getClass)
    val systemProperties = "system.properties"

    def loadProperties: Properties = {
      val p = new Properties(System.getProperties)
      val propFile: InputStream = getClass.getClassLoader.getResourceAsStream(systemProperties)
      p.load(propFile)
      // set the system properties
      System.setProperties(p)
      System.getProperties
    }

    def setup: RelationExtractor = {
      // relex.RelationExtractor -n 4 -l -t -f -r -a
      val re = new RelationExtractor(false)
      // -n 4
      re.setMaxParses(1)
      // -l -f -a
      val opencog: OpenCogScheme = new OpenCogScheme()
      opencog.setShowLinkage(true)
      opencog.setShowFrames(true)
      re.do_anaphora_resolution = true
      opencog.setShowAnaphora(true)
      // -t
      re.do_tree_markup = true
      re.do_pre_entity_tagging = true
      re.do_post_entity_tagging = true
      re
    }

    def logRes(sent: Sentence) {
      for (val parse: ParsedSentence <- sent.getParses) {
        // log.info("\n====\n")
        // log.info("Dependency graph:\n")
        // log.info(RawView.printZHeads(parse.getLeft))
        // log.info("\n======\n")
        // log.info("Parse string:\n")
        // log.info("\n" + parse.getPhraseString)
        log.info("\n======\n")
        log.info("Link string:\n")
        log.info("\n" + parse.getLinkString)
        log.info("\n======\n")
        log.info("Dependency relations:\n")
        log.info(SimpleView.printRelations(parse))
        log.info("\n======\n")
      }
    }

    def parse(sentence: String, re: RelationExtractor): Sentence = {


      log.info("; SENTENCE: [" + sentence + "]")
      var em: EntityMaintainer = new EntityMaintainer
      //TODO add sentence split
      if (re.do_pre_entity_tagging) {
        val gent: EntityTagger = EntityTaggerFactory.get()
        gent.tagEntities("") // force initialization to measure initialization time
        gent.reset()
        re.tagger = gent
        em.set(gent)
      }
      val sntc: Sentence = re.processSentence(sentence, em)
      sntc
    }

  /**
   *
   * @param input sentence to be parsed
   * @return  relex output
   */
  def parseString(input: String): String = {
        val corrector = SpellCorrector();
        val corrected = corrector.correctSentence(input);

        Console.println("Source Sentence: "+input);
        Console.println("Corrected Sentence: "+corrected);
        var resString=""
        loadProperties
        val re = setup
        val res = parse(corrected, re)
        logRes(res)
        for (val parse: ParsedSentence <- res.getParses) {
          resString+= (SimpleView.printRelations(parse))
        }

    return resString
  }
}
