package tu.coreservice.annotator

/**
 * @author talanov max
 *         date 2012-05-20
 *         time: 10:05 PM
 */


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.slf4j.LoggerFactory
import relex.entity.{EntityMaintainer, EntityTaggerFactory, EntityTagger}
import relex.{ParsedSentence, Sentence, RelationExtractor}
import tu.model.knowledge.domain.ConceptNetwork
import collection.JavaConversions._
import java.util.Properties
import java.io.InputStream
import relex.output.{SimpleView, OpenCogScheme}
import org.scalatest.matchers.MustMatchers
import tu.coreservice.spellcorrector.SpellCorrector
import tu.providers.WordnetAnnotatorProvider
import tu.model.knowledge.communication.ContextHelper
import tu.model.knowledge.{Probability, KnowledgeURI}
import tu.coreservice.utilities.{TestDataGenerator, URIHelper}
import tu.model.knowledge.annotator.{AnnotatedNarrative, AnnotatedSentence, AnnotatedWord, AnnotatedPhrase}
import tu.model.knowledge.narrative.Narrative
import tu.model.knowledge.helper.URIGenerator
import org.scalatest.{Ignore, FunSuite}


@RunWith(classOf[JUnitRunner])
class KBAnnotatorImplTest extends FunSuite with MustMatchers {

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
    for (parse: ParsedSentence <- sent.getParses) {
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


  test("wordnet test is ok") {
    val res=System.getProperty("java.library.path");

    val wordnet = new WordnetAnnotatorProvider
    val result = wordnet.annotate("software")
    assert(result.contains("package"))
  }

  def extractResult( src:String):String = {
    src

  }

  test("line 1 processing is ok") {
    val testString ="User has started using Lotus Notes on his his Vista PC this week and now he can't use Lotus Notes on his XP PC any more. He wants to restore Lotus Notes directory to status of 13/2 so he can use it on his XP PC. Please assist user"
    val corrector = SpellCorrector()
    val corrected = corrector.correctSentence(testString)
    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for (parse: ParsedSentence <- res.getParses) {
      assertResult(this.line1Relations)( SimpleView.printRelations(parse))
    }
  }


  val line1Relations = """_advmod(start, week)
                         |_subj(start, User)
                         |pos(start, verb)
                         |inflection-TAG(start, .v-d)
                         |tense(start, present_perfect)
                         |noun_number(XP, singular)
                         |definite-FLAG(XP, T)
                         |pos(XP, noun)
                         |pos(any, WORD)
                         |pos(more, adj)
                         |pos([, WORD)
                         |inflection-TAG([, .])
                         |noun_number(Lotus, singular)
                         |definite-FLAG(Lotus, T)
                         |pos(Lotus, noun)
                         |noun_number(Notes, singular)
                         |definite-FLAG(Notes, T)
                         |pos(Notes, noun)
                         |_nn(directory, Lotus)
                         |_nn(directory, Notes)
                         |pos(directory, noun)
                         |inflection-TAG(directory, .n)
                         |pos([to], WORD)
                         |_poss(user, him)
                         |_nn(user, XP_PC)
                         |_nn(user, Please)
                         |noun_number(user, singular)
                         |definite-FLAG(user, T)
                         |pos(user, noun)
                         |inflection-TAG(user, .n)
                         |noun_number(XP, singular)
                         |definite-FLAG(XP, T)
                         |pos(XP, noun)
                         |location-FLAG(XP_PC, T)
                         |noun_number(XP_PC, singular)
                         |definite-FLAG(XP_PC, T)
                         |pos(XP_PC, noun)
                         |inflection-TAG(XP_PC, .l)
                         |entity-FLAG(XP_PC, T)
                         |pos([, WORD)
                         |inflection-TAG([, .])
                         |noun_number(Please, singular)
                         |definite-FLAG(Please, T)
                         |pos(Please, noun)
                         |pos([assist], WORD)
                         |noun_number(him, singular)
                         |definite-FLAG(him, T)
                         |gender(him, masculine)
                         |pos(him, adj)
                         |pronoun-FLAG(him, T)
                         |pos(on, prep)
                         |_obj(use, it)
                         |_subj(use, he)
                         |on(use, user)
                         |pos(use, verb)
                         |inflection-TAG(use, .v)
                         |tense(use, present_future)
                         |HYP(use, T)
                         |definite-FLAG(it, T)
                         |gender(it, neuter)
                         |pos(it, noun)
                         |pronoun-FLAG(it, T)
                         |pos(can, verb)
                         |inflection-TAG(can, .v)
                         |noun_number(he, singular)
                         |definite-FLAG(he, T)
                         |gender(he, masculine)
                         |pos(he, WORD)
                         |pronoun-FLAG(he, T)
                         |pos(so, conjunction)
                         |pos(13/2, noun)
                         |pos(of, prep)
                         |of(status, 13/2)
                         |_nn(status, directory)
                         |noun_number(status, uncountable)
                         |pos(status, noun)
                         |inflection-TAG(status, .n-u)
                         |_iobj(restore, XP_PC)
                         |_obj(restore, status)
                         |pos(restore, verb)
                         |inflection-TAG(restore, .v)
                         |tense(restore, infinitive)
                         |HYP(restore, T)
                         |pos(to, prep)
                         |inflection-TAG(to, .r)
                         |noun_number(He, singular)
                         |definite-FLAG(He, T)
                         |gender(He, masculine)
                         |pos(He, noun)
                         |pronoun-FLAG(He, T)
                         |_subj(want, He)
                         |_to-do(want, restore)
                         |pos(want, verb)
                         |inflection-TAG(want, .v)
                         |tense(want, present)
                         |_poss(XP_PC, him)
                         |location-FLAG(XP_PC, T)
                         |noun_number(XP_PC, singular)
                         |definite-FLAG(XP_PC, T)
                         |pos(XP_PC, noun)
                         |inflection-TAG(XP_PC, .l)
                         |entity-FLAG(XP_PC, T)
                         |noun_number(him, singular)
                         |definite-FLAG(him, T)
                         |gender(him, masculine)
                         |pos(him, adj)
                         |pronoun-FLAG(him, T)
                         |pos(on, prep)
                         |_advmod(use, now)
                         |_obj(use, Lotus_Notes)
                         |_subj(use, he)
                         |on(use, XP_PC)
                         |pos(use, verb)
                         |inflection-TAG(use, .v)
                         |NEGATIVE-FLAG(use, T)
                         |tense(use, present_future)
                         |HYP(use, T)
                         |noun_number(he, singular)
                         |definite-FLAG(he, T)
                         |gender(he, masculine)
                         |pos(he, WORD)
                         |pronoun-FLAG(he, T)
                         |pos(can, verb)
                         |pos(now, adv)
                         |inflection-TAG(now, .r)
                         |noun_number(Lotus, singular)
                         |definite-FLAG(Lotus, T)
                         |pos(Lotus, noun)
                         |noun_number(Lotus_Notes, singular)
                         |definite-FLAG(Lotus_Notes, T)
                         |pos(Lotus_Notes, noun)
                         |entity-FLAG(Lotus_Notes, T)
                         |pos(and, conjunction)
                         |inflection-TAG(and, .ij)
                         |_amod(week, this)
                         |pos(week, prep)
                         |inflection-TAG(week, .r)
                         |pos(this, adj)
                         |inflection-TAG(this, .d)
                         |pos(have, verb)
                         |inflection-TAG(have, .v)
                         |noun_number(User, singular)
                         |definite-FLAG(User, T)
                         |pos(User, noun)
                         |noun_number(Vista, singular)
                         |definite-FLAG(Vista, T)
                         |pos(Vista, noun)
                         |_poss(Vista_PC, him)
                         |location-FLAG(Vista_PC, T)
                         |noun_number(Vista_PC, singular)
                         |definite-FLAG(Vista_PC, T)
                         |pos(Vista_PC, noun)
                         |inflection-TAG(Vista_PC, .l)
                         |entity-FLAG(Vista_PC, T)
                         |noun_number(him, singular)
                         |definite-FLAG(him, T)
                         |gender(him, masculine)
                         |pos(him, adj)
                         |pronoun-FLAG(him, T)
                         |pos(on, prep)
                         |_obj(using, Lotus_Notes)
                         |on(using, Vista_PC)
                         |pos(using, noun)
                         |inflection-TAG(using, .g)
                         |noun_number(Lotus, singular)
                         |definite-FLAG(Lotus, T)
                         |pos(Lotus, noun)
                         |noun_number(Lotus_Notes, singular)
                         |definite-FLAG(Lotus_Notes, T)
                         |pos(Lotus_Notes, noun)
                         |entity-FLAG(Lotus_Notes, T)
                         |""".stripMargin

  test("line 2 processing is ok") {
    val testString = "Mapping of shared drive W:\\ fails, user is using \\\\lalala9061105.lalaburg.com\\NetLogon\\secl.bat and has restarted PC, but the problem persists"

    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)

    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for ( parse: ParsedSentence <- res.getParses) {
      //assertResult(this.line2Relations)(SimpleView.printRelations(parse))
    }
  }

  val line2Relations =
    """_subj(fail, mapping)
      |pos(fail, verb)
      |inflection-TAG(fail, .v)
      |tense(fail, present)
      |_nn(W, drive)
      |noun_number(W, singular)
      |definite-FLAG(W, T)
      |pos(W, noun)
      |inflection-TAG(W, .n)
      |_amod(drive, shared)
      |pos(drive, noun)
      |inflection-TAG(drive, .n)
      |pos(shared, adj)
      |inflection-TAG(shared, .v-d)
      |tense(shared, past)
      |pos(of, prep)
      |pos(,, verb)
      |tense(,, present)
      |of(mapping, W)
      |noun_number(mapping, uncountable)
      |pos(mapping, noun)
      |inflection-TAG(mapping, .g)
      |_obj(use, \\lalala9061105.lalaburg.com\NetLogon\secl.bat)
      |pos(use, verb)
      |inflection-TAG(use, .v)
      |tense(use, present_progressive)
      |noun_number(\\lalala9061105.lalaburg.com\NetLogon\secl.bat, uncountable)
      |pos(\\lalala9061105.lalaburg.com\NetLogon\secl.bat, noun)
      |inflection-TAG(\\lalala9061105.lalaburg.com\NetLogon\secl.bat, .n)
      |conj_and(be, have)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |location-FLAG(PC, T)
      |noun_number(PC, singular)
      |definite-FLAG(PC, T)
      |pos(PC, noun)
      |inflection-TAG(PC, .l)
      |pos(,, punctuation)
      |_subj(persist, problem)
      |pos(persist, verb)
      |inflection-TAG(persist, .v)
      |tense(persist, present)
      |noun_number(problem, singular)
      |definite-FLAG(problem, T)
      |pos(problem, noun)
      |inflection-TAG(problem, .n)
      |pos(but, conjunction)
      |inflection-TAG(but, .ij)
      |pos(the, det)
      |_obj(restart, PC)
      |pos(restart, verb)
      |inflection-TAG(restart, .v-d)
      |tense(restart, present_perfect)
      |pos(have, verb)
      |inflection-TAG(have, .v)
      |pos(and, conjunction)
      |inflection-TAG(and, .j-v)
      |pos([user], WORD)
      |""".stripMargin

  /**
   * AnnotatedSentence is not formed correctly.
   */
  test("line 3 processing is ok") {
    val testString = "C33NG - User's Catia V5 does not work, after C33NG update."

    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)


    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for ( parse: ParsedSentence <- res.getParses) {
      //assertResult(this.line3Relations)(SimpleView.printRelations(parse))
    }
  }

  val line3Relations =
    """pos(not, adv)
      |inflection-TAG(not, .e)
      |_advmod(work, CNN)
      |_subj(work, Caria)
      |pos(work, verb)
      |inflection-TAG(work, .v)
      |NEGATIVE-FLAG(work, T)
      |tense(work, present_infinitive)
      |HYP(work, T)
      |pos(-, punctuation)
      |inflection-TAG(-, .r)
      |noun_number(User, singular)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |pos('s, adj)
      |inflection-TAG('s, .p)
      |_poss(Caria, User)
      |_quantity(Caria, V)
      |noun_number(Caria, singular)
      |definite-FLAG(Caria, T)
      |pos(Caria, noun)
      |pos(do, verb)
      |inflection-TAG(do, .v)
      |noun_number(V, singular)
      |definite-FLAG(V, T)
      |pos(V, noun)
      |inflection-TAG(V, .id)
      |noun_number(CNN, singular)
      |definite-FLAG(CNN, T)
      |pos(CNN, adv)
      |pos(,, punctuation)
      |pos(after, prep)
      |pos([update], WORD)
      |pos(., punctuation)
      |noun_number(CNN, singular)
      |definite-FLAG(CNN, T)
      |pos(CNN, noun)
      |""".stripMargin


  test("line 4 processing is ok") {
    val testString = "User is also missing IE8."

    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)

    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for (parse: ParsedSentence <- res.getParses) {
      //assertResult(this.line4Relations)(SimpleView.printRelations(parse))
    }
  }

  val line4Relations =
    """_advmod(miss, also)
      |_obj(miss, Ie)
      |_subj(miss, User)
      |pos(miss, verb)
      |inflection-TAG(miss, .v)
      |tense(miss, present_progressive)
      |pos(also, adv)
      |inflection-TAG(also, .e)
      |pos(., punctuation)
      |noun_number(Ie, singular)
      |definite-FLAG(Ie, T)
      |pos(Ie, noun)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |noun_number(User, singular)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |""".stripMargin

  /**
   * !PLEASE NOTE! makes link parser crash.
   */

  test("line 5 processing is ok") {
    val testString = "User is missing Internet Explorer 8!PLEASE NOTE!, the user reports that it's very important that this is solved BEFORE or AFTER 19th-20th January, since she's doing some imporant work business at the specified date!"
    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)


    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for (parse: ParsedSentence <- res.getParses) {
      //assertResult(this.line5Relations)(SimpleView.printRelations(parse))
    }
  }

  val line5Relations =
    """pos(!, punctuation)
      |_amod(date, specified)
      |noun_number(date, singular)
      |definite-FLAG(date, T)
      |pos(date, noun)
      |inflection-TAG(date, .n)
      |pos(specified, adj)
      |inflection-TAG(specified, .v-d)
      |tense(specified, past)
      |pos(the, det)
      |pos(at, prep)
      |pos(some, adj)
      |pos(imporant, adj)
      |inflection-TAG(imporant, .a)
      |_amod(work, imporant)
      |noun_number(work, uncountable)
      |pos(work, noun)
      |inflection-TAG(work, .n-u)
      |_quantity(business, some)
      |_nn(business, work)
      |noun_number(business, uncountable)
      |pos(business, noun)
      |inflection-TAG(business, .n-u)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |noun_number(she, singular)
      |definite-FLAG(she, T)
      |gender(she, feminine)
      |pos(she, WORD)
      |pronoun-FLAG(she, T)
      |_obj(do, business)
      |_subj(do, she)
      |pos(do, verb)
      |inflection-TAG(do, .v)
      |tense(do, present_progressive)
      |HYP(do, T)
      |at(miss, date)
      |_obj(miss, NOTE)
      |_subj(miss, User)
      |since(miss, do)
      |pos(miss, verb)
      |inflection-TAG(miss, .v)
      |tense(miss, present_progressive)
      |pos(,, punctuation)
      |pos(the, det)
      |noun_number(user, singular)
      |definite-FLAG(user, T)
      |pos(user, noun)
      |inflection-TAG(user, .n)
      |conj_or(BEFORE, AFTER_January)
      |noun_number(BEFORE, singular)
      |definite-FLAG(BEFORE, T)
      |pos(BEFORE, noun)
      |noun_number(AFTER, singular)
      |definite-FLAG(AFTER, T)
      |pos(AFTER, noun)
      |pos([19th-20th], WORD)
      |pos(,, punctuation)
      |pos(since, conjunction)
      |noun_number(AFTER_January, singular)
      |definite-FLAG(AFTER_January, T)
      |gender(AFTER_January, feminine)
      |pos(AFTER_January, noun)
      |inflection-TAG(AFTER_January, .f)
      |person-FLAG(AFTER_January, T)
      |entity-FLAG(AFTER_January, T)
      |noun_number(or, uncountable)
      |pos(or, conjunction)
      |inflection-TAG(or, .j-n)
      |noun_number(report, plural)
      |pos(report, noun)
      |inflection-TAG(report, .n)
      |_iobj(solve, user)
      |_obj(solve, or)
      |_subj(solve, report)
      |pos(solve, verb)
      |inflection-TAG(solve, .v-d)
      |tense(solve, past)
      |noun_number(this, uncountable)
      |pos(this, noun)
      |inflection-TAG(this, .p)
      |pronoun-FLAG(this, T)
      |_obj(be, report)
      |_subj(be, this)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |tense(be, present)
      |HYP(be, T)
      |_advmod(important, very)
      |that(important, be)
      |pos(important, adj)
      |inflection-TAG(important, .a)
      |tense(important, present)
      |pos(very, adv)
      |inflection-TAG(very, .e)
      |pos(that, conjunction)
      |inflection-TAG(that, .j-c)
      |definite-FLAG(it, T)
      |gender(it, neuter)
      |pos(it, noun)
      |pronoun-FLAG(it, T)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |pos(that, conjunction)
      |inflection-TAG(that, .j-r)
      |_appo(NOTE, user)
      |_amod(NOTE, 8!PLEASE)
      |_nn(NOTE, Internet)
      |_nn(NOTE, Explorer)
      |noun_number(NOTE, singular)
      |definite-FLAG(NOTE, T)
      |pos(NOTE, noun)
      |pos(8!PLEASE, adj)
      |inflection-TAG(8!PLEASE, .a)
      |noun_number(Explorer, singular)
      |definite-FLAG(Explorer, T)
      |pos(Explorer, noun)
      |noun_number(Internet, singular)
      |definite-FLAG(Internet, T)
      |pos(Internet, noun)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |noun_number(User, singular)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |""".stripMargin

  /**
   * Current test is not running, as there is too many errors: > 5.
   */
  test("line 6 processing is ok") {
    //TODO Auto-correction: aren?t -> aren't
    val testString = "installed, teamcenter and cnext5, seems to work.Seems to CatiaV5 aren?t installed correcly.errormessenge: a program cannot display messge. the program need a promission or information to complete a task.It?s a Vista-PC."
    //process with correction
    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)

    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)

    for (parse: ParsedSentence <- res.getParses) {
      //assertResult(this.line6Relations)(SimpleView.printRelations(parse))
    }
  }

  val line6Relations =
    """pos(a, det)
      |pos(task.Its, adj)
      |inflection-TAG(task.Its, .a)
      |pos([a], WORD)
      |_amod(Vista, task.Its)
      |noun_number(Vista, singular)
      |definite-FLAG(Vista, T)
      |pos(Vista, noun)
      |pos(., punctuation)
      |_obj(complete, Vista)
      |pos(complete, verb)
      |inflection-TAG(complete, .v)
      |tense(complete, infinitive)
      |HYP(complete, T)
      |_obj(display, information)
      |_subj(display, program)
      |to(display, complete)
      |pos(display, verb)
      |inflection-TAG(display, .v)
      |NEGATIVE-FLAG(display, T)
      |tense(display, present_future)
      |HYP(display, T)
      |pos(to, prep)
      |inflection-TAG(to, .r)
      |_nn(information, provision)
      |_nn(information, need)
      |noun_number(information, uncountable)
      |pos(information, noun)
      |inflection-TAG(information, .n-u)
      |pos([or], WORD)
      |pos(provision, noun)
      |inflection-TAG(provision, .n)
      |pos([a], WORD)
      |_nn(need, program)
      |pos(need, noun)
      |inflection-TAG(need, .n)
      |_nn(program, message)
      |pos(program, noun)
      |inflection-TAG(program, .n)
      |pos([, WORD)
      |inflection-TAG([, .])
      |pos([the], WORD)
      |pos(message, noun)
      |inflection-TAG(message, .n)
      |pos(can, verb)
      |noun_number(program, singular)
      |pos(program, noun)
      |inflection-TAG(program, .n)
      |pos(:, conjunction)
      |inflection-TAG(:, .j)
      |pos(a, det)
      |pos(correcly.errormessenge, adj)
      |inflection-TAG(correcly.errormessenge, .a)
      |HYP(correcly.errormessenge, T)
      |_obj(instal, arent)
      |pos(instal, verb)
      |inflection-TAG(instal, .v-d)
      |tense(instal, passive)
      |_nn(arent, CatiaV5)
      |pos(arent, noun)
      |inflection-TAG(arent, .n)
      |noun_number(CatiaV5, singular)
      |definite-FLAG(CatiaV5, T)
      |pos(CatiaV5, noun)
      |pos(to, prep)
      |inflection-TAG(to, .r)
      |noun_number(work, plural)
      |pos(work, noun)
      |inflection-TAG(work, .n)
      |pos(to, prep)
      |inflection-TAG(to, .r)
      |pos([,], WORD)
      |_nn(cnext5, teamster)
      |noun_number(cnext5, uncountable)
      |pos(cnext5, noun)
      |inflection-TAG(cnext5, .n)
      |pos([and], WORD)
      |pos(teamster, noun)
      |inflection-TAG(teamster, .n)
      |_to-be(seem, correcly.errormessenge)
      |to2(seem, arent)
      |_subj(seem, cnext5)
      |to(seem, work)
      |pos(seem, verb)
      |inflection-TAG(seem, .v)
      |tense(seem, present)
      |pos(instal, verb)
      |inflection-TAG(instal, .v-d)
      |tense(instal, past)
      |pos(,, punctuation)
      |""".stripMargin


  /**
   * Link parser was unable to process.   */
  test("line 7 processing is ok") {
    val testString = "User reports problems with his current version of flash player. User reports that he has two computers, one with a working (and newer) flash player that he uses to work with and the other computer seems to have a older flash version which does not work properly. Could you take a look at this and see if there is any flash update that can (re)sent to his computer?"

    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)

    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for ( parse: ParsedSentence <- res.getParses) {
      assertResult(this.line7Relations)(SimpleView.printRelations(parse))
    }
  }

  val line7Relations =
    """_obj(start, KBP)
      |pos(start, verb)
      |inflection-TAG(start, .v)
      |tense(start, present)
      |pos([web], WORD)
      |noun_number(KBP, singular)
      |definite-FLAG(KBP, T)
      |pos(KBP, noun)
      |_advmod(reinstall, please)
      |pos(reinstall, verb)
      |inflection-TAG(reinstall, .v)
      |tense(reinstall, present)
      |pos(please, adv)
      |inflection-TAG(please, .e)
      |noun_number(Java, singular)
      |definite-FLAG(Java, T)
      |pos(Java, noun)
      |pos(., punctuation)
      |_obj(,, Java)
      |pos(,, verb)
      |tense(,, infinitive)
      |HYP(,, T)
      |_to-do(unable, ,)
      |pos(unable, adj)
      |inflection-TAG(unable, .a)
      |tense(unable, present)
      |pos(to, prep)
      |inflection-TAG(to, .r)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |_predadj(User, unable)
      |noun_number(User, singular)
      |definite-FLAG(User, T)
      |pos(User, noun)
    """.stripMargin

  test("line 8 processing is ok") {
    //TODO Auto-correction uanble -> unable
    val testString = "User is unable to start KDP web, please reinstall Java."
    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)

    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for ( parse: ParsedSentence <- res.getParses) {
      assertResult(this.line8Relations)(SimpleView.printRelations(parse))
    }
  }

  val line8Relations =
    """_obj(start, KBP)
      |pos(start, verb)
      |inflection-TAG(start, .v)
      |tense(start, present)
      |pos([web], WORD)
      |noun_number(KBP, singular)
      |definite-FLAG(KBP, T)
      |pos(KBP, noun)
      |_advmod(reinstall, please)
      |pos(reinstall, verb)
      |inflection-TAG(reinstall, .v)
      |tense(reinstall, present)
      |pos(please, adv)
      |inflection-TAG(please, .e)
      |noun_number(Java, singular)
      |definite-FLAG(Java, T)
      |pos(Java, noun)
      |pos(., punctuation)
      |_obj(,, Java)
      |pos(,, verb)
      |tense(,, infinitive)
      |HYP(,, T)
      |_to-do(unable, ,)
      |pos(unable, adj)
      |inflection-TAG(unable, .a)
      |tense(unable, present)
      |pos(to, prep)
      |inflection-TAG(to, .r)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |_predadj(User, unable)
      |noun_number(User, singular)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |""".stripMargin

  /**
   * Direct instruction is not processed correctly.
   */
  test("line 9 processing is ok") {
    val testString = "Hi NNN Admin. Please connect following groups to the shared disk listed below and configure security permissions."
    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)


    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for ( parse: ParsedSentence <- res.getParses) {
      //assertResult(this.line9Relations)(SimpleView.printRelations(parse))
    }
  }

  val line9Relations =
    """pos(., punctuation)
      |_nn(permission, security)
      |noun_number(permission, plural)
      |pos(permission, noun)
      |inflection-TAG(permission, .n)
      |noun_number(security, uncountable)
      |pos(security, noun)
      |inflection-TAG(security, .n-u)
      |_subj(list, group)
      |conj_and(list, configure)
      |pos(list, verb)
      |inflection-TAG(list, .v-d)
      |tense(list, past)
      |_subj(configure, group)
      |pos(configure, verb)
      |inflection-TAG(configure, .v)
      |tense(configure, present)
      |pos([below], WORD)
      |pos(and, conjunction)
      |inflection-TAG(and, .j-v)
      |_amod(disk, shared)
      |noun_number(disk, singular)
      |definite-FLAG(disk, T)
      |pos(disk, noun)
      |inflection-TAG(disk, .n)
      |pos(shared, adj)
      |inflection-TAG(shared, .v-d)
      |tense(shared, past)
      |pos(the, det)
      |pos(to, prep)
      |inflection-TAG(to, .r)
      |to(group, disk)
      |_nn(group, Hi_NNN_Admin_Please)
      |_nn(group, following)
      |noun_number(group, plural)
      |pos(group, noun)
      |inflection-TAG(group, .n)
      |noun_number(Hi, singular)
      |definite-FLAG(Hi, T)
      |pos(Hi, noun)
      |pos([, WORD)
      |inflection-TAG([, .])
      |pos([connect], WORD)
      |pos(following, noun)
      |inflection-TAG(following, .n)
      |noun_number(Hi_NNN_Admin_Please, singular)
      |definite-FLAG(Hi_NNN_Admin_Please, T)
      |pos(Hi_NNN_Admin_Please, noun)
      |entity-FLAG(Hi_NNN_Admin_Please, T)
      |""".stripMargin


  test("line 10 processing is ok") {
    val testString = "PP2C - Cisco IP communicator. Please see if you can fix the problem with the ip phone, it's stuck on configuring ip + sometimes Server error rejected: Security etc."
    val corrector = SpellCorrector();

    val corrected = corrector.correctSentence(testString)


    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for ( parse: ParsedSentence <- res.getParses) {
     // assertResult(this.line10Relations)(SimpleView.printRelations(parse))
    }
  }

  val line10Relations =
    """pos(on, prep)
      |_advmod(configure, sometimes)
      |_obj(configure, error)
      |_subj(configure, on)
      |pos(configure, verb)
      |inflection-TAG(configure, .v)
      |tense(configure, progressive)
      |noun_number(Server, singular)
      |definite-FLAG(Server, T)
      |pos(Server, noun)
      |_nn(error, Server)
      |noun_number(error, uncountable)
      |pos(error, noun)
      |inflection-TAG(error, .n-u)
      |_obj(reject, error)
      |pos(reject, verb)
      |inflection-TAG(reject, .v-d)
      |tense(reject, passive)
      |pos(:, prep)
      |inflection-TAG(:, .j)
      |pos([etc], WORD)
      |pos(., punctuation)
      |noun_number(security, uncountable)
      |pos(security, noun)
      |inflection-TAG(security, .n-u)
      |pos(sometimes, adv)
      |pos([in], WORD)
      |pos([+], WORD)
      |pos(be, verb)
      |inflection-TAG(be, .v)
      |definite-FLAG(it, T)
      |gender(it, neuter)
      |pos(it, noun)
      |pronoun-FLAG(it, T)
      |_advmod(stick, on)
      |_advmod(stick, PPC)
      |_advmod(stick, Cisco)
      |_advmod(stick, IP)
      |_advmod(stick, communicator)
      |_subj(stick, it)
      |pos(stick, verb)
      |inflection-TAG(stick, .v-d)
      |tense(stick, present_perfect)
      |pos([, WORD)
      |inflection-TAG([, .])
      |pos([Please], WORD)
      |pos([see], WORD)
      |pos(,, punctuation)
      |noun_number(phone, singular)
      |definite-FLAG(phone, T)
      |pos(phone, noun)
      |inflection-TAG(phone, .n)
      |pos(the, det)
      |pos([in], WORD)
      |pos(with, prep)
      |with(fix, phone)
      |_obj(fix, problem)
      |_subj(fix, you)
      |pos(fix, verb)
      |inflection-TAG(fix, .v)
      |tense(fix, present_future)
      |HYP(fix, T)
      |noun_number(problem, singular)
      |definite-FLAG(problem, T)
      |pos(problem, noun)
      |inflection-TAG(problem, .n)
      |pos(can, verb)
      |inflection-TAG(can, .v)
      |gender(you, person)
      |pos(you, noun)
      |pronoun-FLAG(you, T)
      |pos(the, det)
      |pos(if, conjunction)
      |if(communicator, fix)
      |pos(communicator, adv)
      |inflection-TAG(communicator, .a)
      |location-FLAG(IP, T)
      |noun_number(IP, singular)
      |definite-FLAG(IP, T)
      |pos(IP, adv)
      |inflection-TAG(IP, .l)
      |noun_number(Cisco, singular)
      |definite-FLAG(Cisco, T)
      |pos(Cisco, adv)
      |pos(-, punctuation)
      |inflection-TAG(-, .r)
      |noun_number(PPC, singular)
      |definite-FLAG(PPC, T)
      |pos(PPC, adv)
      | """.stripMargin

  /*
   test for KB Annotator Impl

   */
  test("Annotator test") {
    //prepare inpout context
    var annotator = new KBAnnotatorImpl

    //create proper container -> List[Sentence]-> Phrases - > Words

    val sentence = new AnnotatedSentence(List(
      new AnnotatedPhrase(
        List(
          AnnotatedPhrase("get"),
          AnnotatedPhrase("rid"),
          AnnotatedPhrase("off")
        ), URIGenerator.generateURI("AnnotatedSentence")
      ),
      //
      new AnnotatedPhrase(
        List(
          AnnotatedPhrase("please")
        ), URIGenerator.generateURI("AnnotatedSentence")
      )
    ), new KnowledgeURI(URIHelper.annotatorNamespace(), URIHelper.sentenceMark() + "1", URIHelper.version()))


    val inputCtx = ContextHelper.createContext(Map(
      new KnowledgeURI(URIHelper.annotatorNamespace(), URIHelper.splitterMark() + "1", URIHelper.version()) ->
        AnnotatedNarrative.apply(List(sentence), new KnowledgeURI(URIHelper.annotatorNamespace(), URIHelper.splitterMark() + "1", URIHelper.version()), new Probability(1, 1))
    ), null, "ctx"
    )
    inputCtx.domainModel=TestDataGenerator.domainModel
    val output = annotator.apply(inputCtx)

    //check if please has annotation
    assertResult(true)(output.lastResult.get.asInstanceOf[AnnotatedNarrative].sentences.head._phrases.count(p => p.phrase == "please" && p.concepts.length > 0) > 0)

  }


}
