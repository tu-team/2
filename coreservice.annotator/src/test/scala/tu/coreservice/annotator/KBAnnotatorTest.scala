package tu.coreservice.annotator

/**
 * @author talanov max
 *         date 2012-05-20
 *         time: 10:05 PM
 */

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory
import relex.entity.{EntityMaintainer, EntityTaggerFactory, EntityTagger}
import relex.{ParsedSentence, Sentence, RelationExtractor}
import collection.JavaConversions._
import java.util.Properties
import java.io.InputStream
import relex.output.{SimpleView, OpenCogScheme}
import org.scalatest.matchers.MustMatchers
import tu.coreservice.spellcorrector.SpellCorrector

@RunWith(classOf[JUnitRunner])
class KBAnnotatorTest extends FunSuite with MustMatchers {

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

  test("line 1 processing is ok") {
    val testString = "User has started using Lotus Notes on his his Vista PC this week and now he can't use Lotus Notes on his XP PC any more. He wants to restore Lotus Notes directory to status of 13/2 so he can use it on his XP PC. Please assist user"
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line1Relations)(SimpleView.printRelations(parse))
    }
  }


  val line1Relations = """_obj(start, Vista_PC)
on(start, him)
_advmod(start, week)
_subj(start, User)
tense(start, present_perfect)
inflection-TAG(start, .v-d)
pos(start, verb)
inflection-TAG(this, .d)
pos(this, adj)
_amod(week, this)
inflection-TAG(week, .r)
pos(week, prep)
_obj(use, Lotus_Notes)
on(use, XP_PC)
_advmod(use, now)
_subj(use, he)
NEGATIVE-FLAG(use, T)
tense(use, present_future)
HYP(use, T)
inflection-TAG(use, .v)
pos(use, verb)
pos(any, WORD)
pos(more, adj)
inflection-TAG([, .])
pos([, WORD)
pronoun-FLAG(He, T)
gender(He, masculine)
definite-FLAG(He, T)
pos(He, noun)
noun_number(He, singular)
definite-FLAG(Lotus, T)
pos(Lotus, noun)
noun_number(Lotus, singular)
entity-FLAG(Lotus_Notes, T)
definite-FLAG(Lotus_Notes, T)
pos(Lotus_Notes, noun)
noun_number(Lotus_Notes, singular)
_nn(directory, Lotus_Notes)
inflection-TAG(directory, .n)
pos(directory, noun)
pos([to], WORD)
_obj(use, it)
on(use, user)
_subj(use, he)
tense(use, present_future)
HYP(use, T)
inflection-TAG(use, .v)
pos(use, verb)
_nn(user, XP_PC_Please)
_poss(user, him)
definite-FLAG(user, T)
inflection-TAG(user, .n)
pos(user, noun)
noun_number(user, singular)
pos([assist], WORD)
definite-FLAG(XP, T)
pos(XP, noun)
noun_number(XP, singular)
inflection-TAG([, .])
pos([, WORD)
entity-FLAG(XP_PC_Please, T)
definite-FLAG(XP_PC_Please, T)
pos(XP_PC_Please, noun)
noun_number(XP_PC_Please, singular)
pronoun-FLAG(him, T)
gender(him, masculine)
definite-FLAG(him, T)
pos(him, adj)
noun_number(him, singular)
pos(on, prep)
pronoun-FLAG(it, T)
gender(it, neuter)
definite-FLAG(it, T)
pos(it, noun)
inflection-TAG(can, .v)
pos(can, verb)
pronoun-FLAG(he, T)
gender(he, masculine)
definite-FLAG(he, T)
pos(he, WORD)
noun_number(he, singular)
pos(so, conjunction)
pos(13/2, noun)
pos(of, prep)
of(status, 13/2)
_nn(status, directory)
inflection-TAG(status, .n-u)
pos(status, noun)
noun_number(status, uncountable)
_obj(restore, status)
tense(restore, infinitive)
HYP(restore, T)
inflection-TAG(restore, .v)
pos(restore, verb)
_obj(want, XP_PC)
_to-do(want, restore)
_subj(want, He)
tense(want, present)
inflection-TAG(want, .v)
pos(want, verb)
inflection-TAG(to, .r)
pos(to, prep)
definite-FLAG(XP, T)
pos(XP, noun)
noun_number(XP, singular)
_poss(XP_PC, him)
entity-FLAG(XP_PC, T)
definite-FLAG(XP_PC, T)
inflection-TAG(XP_PC, .l)
location-FLAG(XP_PC, T)
pos(XP_PC, noun)
noun_number(XP_PC, singular)
pronoun-FLAG(him, T)
gender(him, masculine)
definite-FLAG(him, T)
pos(him, adj)
noun_number(him, singular)
pos(on, prep)
definite-FLAG(Lotus, T)
pos(Lotus, noun)
noun_number(Lotus, singular)
entity-FLAG(Lotus_Notes, T)
definite-FLAG(Lotus_Notes, T)
pos(Lotus_Notes, noun)
noun_number(Lotus_Notes, singular)
inflection-TAG(now, .r)
pos(now, adv)
pos(can, verb)
pronoun-FLAG(he, T)
gender(he, masculine)
definite-FLAG(he, T)
pos(he, WORD)
noun_number(he, singular)
inflection-TAG(and, .ij)
pos(and, conjunction)
definite-FLAG(Vista, T)
pos(Vista, noun)
noun_number(Vista, singular)
_poss(Vista_PC, him)
entity-FLAG(Vista_PC, T)
definite-FLAG(Vista_PC, T)
inflection-TAG(Vista_PC, .l)
location-FLAG(Vista_PC, T)
pos(Vista_PC, noun)
noun_number(Vista_PC, singular)
pronoun-FLAG(him, T)
gender(him, masculine)
definite-FLAG(him, T)
pos(him, adj)
noun_number(him, singular)
pronoun-FLAG(him, T)
gender(him, masculine)
definite-FLAG(him, T)
pos(him, noun)
noun_number(him, singular)
inflection-TAG(have, .v)
pos(have, verb)
definite-FLAG(User, T)
pos(User, noun)
noun_number(User, singular)
pos(on, prep)
_obj(using, Lotus_Notes)
inflection-TAG(using, .g)
pos(using, noun)
definite-FLAG(Lotus, T)
pos(Lotus, noun)
noun_number(Lotus, singular)
entity-FLAG(Lotus_Notes, T)
definite-FLAG(Lotus_Notes, T)
pos(Lotus_Notes, noun)
noun_number(Lotus_Notes, singular)
"""

  test("line 2 processing is ok") {
    val testString = "Mapping of shared drive W:\\ fails, user is using \\\\lalala9061105.lalaburg.com\\NetLogon\\secl.bat and has restarted PC, but the problem persists"
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line2Relations)(SimpleView.printRelations(parse))
    }
  }

  val line2Relations =
    """tense(,, present)
      |pos(,, verb)
      |_nn(W, drive)
      |definite-FLAG(W, T)
      |inflection-TAG(W, .n)
      |pos(W, noun)
      |noun_number(W, singular)
      |_amod(drive, shared)
      |inflection-TAG(drive, .n)
      |pos(drive, noun)
      |tense(shared, past)
      |inflection-TAG(shared, .v-d)
      |pos(shared, adj)
      |pos(of, prep)
      |of(mapping, W)
      |inflection-TAG(mapping, .g)
      |pos(mapping, noun)
      |noun_number(mapping, uncountable)
      |_subj(fail, mapping)
      |tense(fail, present)
      |inflection-TAG(fail, .v)
      |pos(fail, verb)
      |_obj(use, \\lalala9061105.lalaburg.com\NetLogon\secl.bat)
      |tense(use, present_progressive)
      |inflection-TAG(use, .v)
      |pos(use, verb)
      |inflection-TAG(\\lalala9061105.lalaburg.com\NetLogon\secl.bat, .n)
      |pos(\\lalala9061105.lalaburg.com\NetLogon\secl.bat, noun)
      |noun_number(\\lalala9061105.lalaburg.com\NetLogon\secl.bat, uncountable)
      |conj_and(be, have)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |_obj(restart, PC)
      |tense(restart, present_perfect)
      |inflection-TAG(restart, .v-d)
      |pos(restart, verb)
      |pos(,, punctuation)
      |_subj(persist, problem)
      |tense(persist, present)
      |inflection-TAG(persist, .v)
      |pos(persist, verb)
      |definite-FLAG(problem, T)
      |inflection-TAG(problem, .n)
      |pos(problem, noun)
      |noun_number(problem, singular)
      |inflection-TAG(but, .ij)
      |pos(but, conjunction)
      |pos(the, det)
      |definite-FLAG(PC, T)
      |inflection-TAG(PC, .l)
      |location-FLAG(PC, T)
      |pos(PC, noun)
      |noun_number(PC, singular)
      |inflection-TAG(have, .v)
      |pos(have, verb)
      |inflection-TAG(and, .j-v)
      |pos(and, conjunction)
      |pos([user], WORD)
      |""".stripMargin

  /**
   * Sentence is not formed correctly.
   */
  test("line 3 processing is ok") {
    val testString = "C33NG - User's Catia V5 does not work, after C33NG update."
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line3Relations)(SimpleView.printRelations(parse))
    }
  }

  val line3Relations =
    """after(work, update)
      |_advmod(work, C33NG)
      |_subj(work, Catia_V5)
      |NEGATIVE-FLAG(work, T)
      |tense(work, present_infinitive)
      |HYP(work, T)
      |inflection-TAG(work, .v)
      |pos(work, verb)
      |pos([update], WORD)
      |pos(., punctuation)
      |_obj(update, C33NG)
      |definite-FLAG(C, T)
      |pos(C, noun)
      |noun_number(C, singular)
      |inflection-TAG(-, .r)
      |pos(-, punctuation)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |noun_number(User, singular)
      |inflection-TAG('s, .p)
      |pos('s, adj)
      |definite-FLAG(Catia, T)
      |pos(Catia, noun)
      |noun_number(Catia, singular)
      |definite-FLAG(C33NG, T)
      |pos(C33NG, adv)
      |noun_number(C33NG, singular)
      |inflection-TAG(do, .v)
      |pos(do, verb)
      |inflection-TAG(not, .e)
      |pos(not, adv)
      |_poss(Catia_V5, User)
      |entity-FLAG(Catia_V5, T)
      |definite-FLAG(Catia_V5, T)
      |pos(Catia_V5, noun)
      |noun_number(Catia_V5, singular)
      |pos(after, prep)
      |""".stripMargin


  test("line 4 processing is ok") {
    val testString = "User is also missing IE8."
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line4Relations)(SimpleView.printRelations(parse))
    }
  }

  val line4Relations =
    """_obj(miss, Ie)
      |_advmod(miss, also)
      |_subj(miss, User)
      |tense(miss, present_progressive)
      |inflection-TAG(miss, .v)
      |pos(miss, verb)
      |pos(., punctuation)
      |definite-FLAG(Ie, T)
      |pos(Ie, noun)
      |noun_number(Ie, singular)
      |inflection-TAG(also, .e)
      |pos(also, adv)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |noun_number(User, singular)
      |""".stripMargin

  /**
   * !PLEASE NOTE! makes link parser crash.
   */
  test("line 5 processing is ok") {
    val testString = "User is missing Internet Explorer 8!PLEASE NOTE!, the user reports that it's very important that this is solved BEFORE or AFTER 19th-20th January, since she's doing some imporant work business at the specified date!"
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line5Relations)(SimpleView.printRelations(parse))
    }
  }

  val line5Relations =
    """_obj(miss, Internet_Explorer)
      |at(miss, date)
      |since(miss, do)
      |_subj(miss, User)
      |tense(miss, present_progressive)
      |inflection-TAG(miss, .v)
      |pos(miss, verb)
      |pos(,, punctuation)
      |pos(the, det)
      |inflection-TAG(user, .n)
      |pos(user, noun)
      |that(report, important)
      |_nn(report, user)
      |definite-FLAG(report, T)
      |inflection-TAG(report, .n)
      |pos(report, noun)
      |noun_number(report, plural)
      |pronoun-FLAG(it, T)
      |gender(it, neuter)
      |definite-FLAG(it, T)
      |pos(it, noun)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |inflection-TAG(very, .e)
      |pos(very, adv)
      |_obj(be, th)
      |_subj(be, this)
      |tense(be, present)
      |HYP(be, T)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |pos([January], WORD)
      |pos(,, punctuation)
      |_obj(do, business)
      |at(do, date)
      |_subj(do, she)
      |tense(do, present_progressive)
      |HYP(do, T)
      |inflection-TAG(do, .v)
      |pos(do, verb)
      |pos(!, punctuation)
      |_amod(date, specified)
      |definite-FLAG(date, T)
      |inflection-TAG(date, .n)
      |pos(date, noun)
      |noun_number(date, singular)
      |tense(specified, past)
      |inflection-TAG(specified, .v-d)
      |pos(specified, adj)
      |pos(at, prep)
      |pos(the, det)
      |_quantity(business, some)
      |_nn(business, imporant)
      |_nn(business, work)
      |inflection-TAG(business, .n-u)
      |pos(business, noun)
      |noun_number(business, uncountable)
      |inflection-TAG(imporant, .n)
      |pos(imporant, noun)
      |inflection-TAG(work, .n-u)
      |pos(work, noun)
      |noun_number(work, uncountable)
      |pos(some, adj)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |pronoun-FLAG(she, T)
      |gender(she, feminine)
      |definite-FLAG(she, T)
      |pos(she, WORD)
      |noun_number(she, singular)
      |pos(since, conjunction)
      |_amod(th, BEFORE)
      |_amod(th, AFTER)
      |_nn(th, or)
      |inflection-TAG(th, .n)
      |pos(th, noun)
      |noun_number(th, plural)
      |definite-FLAG(AFTER, T)
      |pos(AFTER, noun)
      |noun_number(AFTER, singular)
      |inflection-TAG(or, .j-n)
      |pos(or, conjunction)
      |conj_or(BEFORE, AFTER)
      |definite-FLAG(BEFORE, T)
      |pos(BEFORE, noun)
      |noun_number(BEFORE, singular)
      |pronoun-FLAG(this, T)
      |inflection-TAG(this, .p)
      |pos(this, noun)
      |noun_number(this, uncountable)
      |tense(solved, past)
      |inflection-TAG(solved, .v-d)
      |pos(solved, adj)
      |inflection-TAG(that, .j-c)
      |pos(that, conjunction)
      |that(important, be)
      |_advmod(important, very)
      |tense(important, present)
      |HYP(important, T)
      |inflection-TAG(important, .a)
      |pos(important, adj)
      |inflection-TAG(that, .j-c)
      |pos(that, conjunction)
      |_amod(NOTE, 8!PLEASE)
      |_appo(NOTE, report)
      |_nn(NOTE, Internet_Explorer)
      |definite-FLAG(NOTE, T)
      |pos(NOTE, noun)
      |noun_number(NOTE, singular)
      |inflection-TAG(8!PLEASE, .a)
      |pos(8!PLEASE, adj)
      |definite-FLAG(Internet, T)
      |pos(Internet, noun)
      |noun_number(Internet, singular)
      |entity-FLAG(Internet_Explorer, T)
      |definite-FLAG(Internet_Explorer, T)
      |pos(Internet_Explorer, noun)
      |noun_number(Internet_Explorer, singular)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |noun_number(User, singular)
      |""".stripMargin

  /**
   * Current test is not running, as there is too many errors: > 5.
   */
  test("line 6 processing is ok") {
    //TODO Auto-correction: aren?t -> aren't
    val testString = "installed, teamcenter and cnext5, seems to work.Seems to CatiaV5 aren?t installed correcly.errormessenge: a program cannot display messge. the program need a promission or information to complete a task.It?s a Vista-PC."
    //process with correction
    val corrector = SpellCorrector();

    val corrected=corrector.correctSentence(testString)

    loadProperties
    val re = setup
    val res = parse(corrected, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line6Relations)(SimpleView.printRelations(parse))
    }
  }

  val line6Relations =
    """_obj(instal, CatiaV5)
      |_advmod(instal, correcly)
      |NEGATIVE-FLAG(instal, T)
      |tense(instal, present_passive)
      |HYP(instal, T)
      |inflection-TAG(instal, .v-d)
      |pos(instal, verb)
      |pos(be, verb)
      |definite-FLAG(CatiaV5, T)
      |pos(CatiaV5, noun)
      |noun_number(CatiaV5, singular)
      |inflection-TAG([, .])
      |pos([, WORD)
      |pos([Errormessenge], WORD)
      |_obj(display, permission)
      |_subj(display, program)
      |NEGATIVE-FLAG(display, T)
      |tense(display, present_future)
      |HYP(display, T)
      |inflection-TAG(display, .v)
      |pos(display, verb)
      |pos(a, det)
      |inflection-TAG(task, .n)
      |pos(task, noun)
      |noun_number(task, singular)
      |inflection-TAG([, .])
      |pos([, WORD)
      |pos([It], WORD)
      |_obj(complete, task)
      |tense(complete, infinitive)
      |HYP(complete, T)
      |inflection-TAG(complete, .v)
      |pos(complete, verb)
      |_obj(be, Vista)
      |_subj(be, information)
      |tense(be, present)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |definite-FLAG(Vista, T)
      |inflection-TAG(Vista, .n)
      |pos(Vista, noun)
      |noun_number(Vista, singular)
      |inflection-TAG(to, .r)
      |pos(to, prep)
      |_to-do(information, complete)
      |inflection-TAG(information, .n-u)
      |pos(information, noun)
      |noun_number(information, uncountable)
      |pos(a, det)
      |inflection-TAG(or, .ij)
      |pos(or, conjunction)
      |_nn(permission, need)
      |_nn(permission, program)
      |inflection-TAG(permission, .n-u)
      |pos(permission, noun)
      |noun_number(permission, uncountable)
      |pos([a], WORD)
      |inflection-TAG(need, .n)
      |pos(need, noun)
      |_amod(program, messge)
      |_nn(program, The)
      |inflection-TAG(program, .n)
      |pos(program, noun)
      |inflection-TAG([, .])
      |pos([, WORD)
      |definite-FLAG(The, T)
      |pos(The, noun)
      |noun_number(The, singular)
      |inflection-TAG(messge, .a)
      |pos(messge, adj)
      |pos(can, verb)
      |inflection-TAG(program, .n)
      |pos(program, noun)
      |noun_number(program, singular)
      |inflection-TAG(:, .j)
      |pos(:, conjunction)
      |pos(a, det)
      |inflection-TAG(correcly, .e)
      |pos(correcly, adv)""".stripMargin


  /**
   * Link parser was unable to process.   */
  test("line 7 processing is ok") {
    val testString = "User reports problems with his current version of flash player. User reports that he has two computers, one with a working (and newer) flash player that he uses to work with and the other computer seems to have a older flash version which does not work properly. Could you take a look at this and see if there is any flash update that can (re)sent to his computer?"
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line7Relations)(SimpleView.printRelations(parse))
    }
  }

  val line7Relations =
    """with(report, player)
      |_obj(report, problem)
      |_subj(report, User)
      |tense(report, present)
      |inflection-TAG(report, .v)
      |pos(report, verb)
      |pos(., punctuation)
      |_nn(player, flash)
      |_poss(player, him)
      |definite-FLAG(player, T)
      |inflection-TAG(player, .n)
      |pos(player, noun)
      |noun_number(player, singular)
      |_nn(flash, version)
      |inflection-TAG(flash, .n)
      |pos(flash, noun)
      |pos([of], WORD)
      |_nn(version, current)
      |inflection-TAG(version, .n)
      |pos(version, noun)
      |inflection-TAG(current, .n)
      |pos(current, noun)
      |pronoun-FLAG(him, T)
      |gender(him, masculine)
      |definite-FLAG(him, T)
      |pos(him, adj)
      |noun_number(him, singular)
      |pos(with, prep)
      |inflection-TAG(problem, .n)
      |pos(problem, noun)
      |noun_number(problem, plural)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |noun_number(User, singular)
    """.stripMargin

  test("line 8 processing is ok") {
    //TODO Auto-correction uanble -> unable
    val testString = "User is unable to start KDP web, please reinstall Java."
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line8Relations)(SimpleView.printRelations(parse))
    }
  }

  val line8Relations =
    """_obj(start, KDP)
      |tense(start, present)
      |inflection-TAG(start, .v)
      |pos(start, verb)
      |pos([web], WORD)
      |definite-FLAG(KDP, T)
      |pos(KDP, noun)
      |noun_number(KDP, singular)
      |_advmod(reinstall, please)
      |tense(reinstall, present)
      |inflection-TAG(reinstall, .v)
      |pos(reinstall, verb)
      |inflection-TAG(please, .e)
      |pos(please, adv)
      |definite-FLAG(Java, T)
      |pos(Java, noun)
      |noun_number(Java, singular)
      |pos(., punctuation)
      |_obj(,, Java)
      |tense(,, infinitive)
      |HYP(,, T)
      |pos(,, verb)
      |_to-do(unable, ,)
      |tense(unable, present)
      |inflection-TAG(unable, .a)
      |pos(unable, adj)
      |_predadj(User, unable)
      |definite-FLAG(User, T)
      |pos(User, noun)
      |noun_number(User, singular)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |inflection-TAG(to, .r)
      |pos(to, prep)
      |""".stripMargin

  /**
   * Direct instruction is not processed correctly.
   */
  test("line 9 processing is ok") {
    val testString = "Hi NNN Admin. Please connect following groups to the shared disk listed below and configure security permissions."
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line9Relations)(SimpleView.printRelations(parse))
    }
  }

  val line9Relations =
    """_subj(configure, Admin)""".stripMargin


  test("line 10 processing is ok") {
    val testString = "PP2C - Cisco IP communicator. Please see if you can fix the problem with the ip phone, it's stuck on configuring ip + sometimes Server error rejected: Security etc."
    loadProperties
    val re = setup
    val res = parse(testString, re)
    logRes(res)
    for (val parse: ParsedSentence <- res.getParses) {
      expect(this.line10Relations)(SimpleView.printRelations(parse))
    }
  }

  val line10Relations =
    """_obj(stick, error)
      |if(stick, fix)
      |_advmod(stick, on)
      |_advmod(stick, PP2C)
      |_advmod(stick, Cisco)
      |_advmod(stick, IP)
      |_advmod(stick, Please)
      |_subj(stick, it)
      |tense(stick, present_perfect)
      |inflection-TAG(stick, .v-d)
      |pos(stick, verb)
      |_obj(reject, error)
      |tense(reject, passive)
      |inflection-TAG(reject, .v-d)
      |pos(reject, verb)
      |inflection-TAG(:, .j)
      |pos(:, prep)
      |inflection-TAG(security, .n-u)
      |pos(security, noun)
      |noun_number(security, uncountable)
      |pos([etc], WORD)
      |pos(., punctuation)
      |_nn(error, Server)
      |inflection-TAG(error, .n-u)
      |pos(error, noun)
      |noun_number(error, uncountable)
      |definite-FLAG(Server, T)
      |pos(Server, noun)
      |noun_number(Server, singular)
      |inflection-TAG(can, .v)
      |pos(can, verb)
      |pronoun-FLAG(you, T)
      |gender(you, person)
      |pos(you, WORD)
      |pos(,, punctuation)
      |_nn(phone, ip)
      |definite-FLAG(phone, T)
      |inflection-TAG(phone, .n)
      |pos(phone, noun)
      |noun_number(phone, singular)
      |inflection-TAG(ip, .n)
      |pos(ip, noun)
      |pos(the, det)
      |pos(with, prep)
      |pos(the, det)
      |definite-FLAG(problem, T)
      |inflection-TAG(problem, .n)
      |pos(problem, noun)
      |noun_number(problem, singular)
      |with(fix, phone)
      |_obj(fix, problem)
      |_subj(fix, you)
      |tense(fix, present_future)
      |HYP(fix, T)
      |inflection-TAG(fix, .v)
      |pos(fix, verb)
      |definite-FLAG(IP, T)
      |inflection-TAG(IP, .l)
      |location-FLAG(IP, T)
      |pos(IP, adv)
      |noun_number(IP, singular)
      |pos([communicator], WORD)
      |inflection-TAG([, .])
      |pos([, WORD)
      |definite-FLAG(Please, T)
      |pos(Please, adv)
      |noun_number(Please, singular)
      |pos([see], WORD)
      |pos(if, conjunction)
      |definite-FLAG(Cisco, T)
      |pos(Cisco, adv)
      |noun_number(Cisco, singular)
      |_obj(configure, ip)
      |_advmod(configure, sometimes)
      |_subj(configure, on)
      |tense(configure, progressive)
      |inflection-TAG(configure, .v)
      |pos(configure, verb)
      |pos([+], WORD)
      |pos(sometimes, adv)
      |inflection-TAG(ip, .n)
      |pos(ip, noun)
      |noun_number(ip, uncountable)
      |pos(on, prep)
      |inflection-TAG(-, .r)
      |pos(-, punctuation)
      |definite-FLAG(PP2C, T)
      |pos(PP2C, adv)
      |noun_number(PP2C, singular)
      |inflection-TAG(be, .v)
      |pos(be, verb)
      |pronoun-FLAG(it, T)
      |gender(it, neuter)
      |definite-FLAG(it, T)
      |pos(it, noun)
      |""".stripMargin
}
