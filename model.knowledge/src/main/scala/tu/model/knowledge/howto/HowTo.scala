package tu.model.knowledge.howto

import tu.model.knowledge.annotator.AnnotatedPhrase
import util.Random
import tu.exception.UnexpectedException
import scala.Some
import org.slf4j.LoggerFactory
import tu.model.knowledge._
import domain.{ConceptLink, ConceptTag, Concept}
import frame.Frame

/**
 * Stores HowTo and it's parameters.
 * @author max talanov
 *         date 2012-05-08
 *         time: 10:55 PM
 */

case class HowTo(var _parameters: List[Frame],
                 var _tags: List[ConceptTag],
                 _uri: KnowledgeURI,
                 _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_parameters: List[Frame], _tags: List[ConceptTag], _uri: KnowledgeURI) {
    this(_parameters, _tags, _uri, new Probability())
  }

  def parameters = _parameters

  def parameters_=(in: List[Frame]): HowTo = {
    _parameters = in
    this
  }

  def tags = _tags

  def tags_=(in: List[ConceptTag]): HowTo = {
    _tags = in
    this
  }

  def this(map: Map[String, String]) = {
    this(
      List[Frame](),
      List[ConceptTag](),
      new KnowledgeURI(map),
      new Probability(map)
    )
  }


  def save(kb: KB, parent: KBNodeId, key: String, linkType: String): Boolean = {
    var res = kb.saveResource(this, parent, key, linkType)

    for (x: Frame <- _parameters) {

      res &= x.save(kb, this, x.uri.toString)
      for (k:KnowledgeURI <- x.resources.keys) {
        val y = x.resources(k)
        y.save(kb, x, k.toString)

      }

    }

    res
  }
}

object HowTo {

  val log = LoggerFactory.getLogger(this.getClass)

  val howToPostfix = "HowTo"

  /**
   * Creates instance of HowTo based on parent HowTo and parameters
   * @param parent super HowTo
   * @param parameters Resources List
   * @return HowTo instance
   */
  def createInstance(parent: HowTo, parameters: List[Frame]): HowTo = {
    val name = parent.uri.name + "&ID=" + Random.nextInt(Constant.INSTANCE_ID_LENGTH)
    val it = new HowTo(parameters, List[ConceptTag](), KnowledgeURI(name + howToPostfix))
    it
  }

  def crateInstance(parent: HowTo, parameters: List[Concept]): HowTo = {
    val name = parent.uri.name + "&ID=" + Random.nextInt(Constant.INSTANCE_ID_LENGTH)

    val frames: List[Frame] = parameters.map(c => {
      Frame(c)
    })
    val it = new HowTo(frames, List[ConceptTag](), KnowledgeURI(name + howToPostfix))
    it
  }

  def load(kb: KB, parent: KBNodeId, key: String, linkType: String): HowTo = {
    val selfMap = kb.loadChild(parent, key, linkType)
    if (selfMap.isEmpty) {
      log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parent.ID.toString))
      throw new UnexpectedException("LoadError for " + parent.ID.toString)
    }

    val ID = KBNodeId(selfMap)


    val framesID = kb.loadChildrenList(ID, Constant.CONCEPTS_LINK_NAME).map(KBNodeId(_))

    def oneResource (m:Map[String, String]):Resource =
    {
      if (Constant.KB_CLASS_NAME == Concept.getClass.getName)
        new Concept(m)
        // ToDo case HowTo.getClass.getName => HowTo.load(m)
      else
        throw new UnexpectedException("HowTo shouldn't contain " + m(Constant.KB_CLASS_NAME) + " for ID " + parent.toString)

    }

    def oneFrame(node:KBNodeId):Frame = {
      Frame.apply(kb.loadChildrenList(node, Constant.CONCEPTS_LINK_NAME).map(oneResource(_)), KnowledgeURI("frameInHowTo"))
    }

    val parameters = framesID.map(oneFrame(_))


    val tags: List[ConceptTag] = List[ConceptTag]()
      // kb.loadChildrenMap(ID, Constant.TAGS)

    new HowTo(parameters,
      tags,
      new KnowledgeURI(selfMap),
      new Probability(selfMap)
    )
  }
}