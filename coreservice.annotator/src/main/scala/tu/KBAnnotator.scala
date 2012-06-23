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
