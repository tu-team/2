package tu.model.knowledge.howto

import tu.model.knowledge._
import frame.TypedFrame
import primitive.KnowledgeString
import tu.model.knowledge.annotator.AnnotatedPhrase
import domain.{ConceptLink, ConceptTag, Concept}
import util.Random
import tu.exception.UnexpectedException
import scala.Some
import org.slf4j.LoggerFactory

/**
 * Stores HowTo and it's parameters.
 * @author max talanov
 *         date 2012-05-08
 *         time: 10:55 PM
 */

case class HowTo(var _parameters: List[TypedFrame[Resource]],
                 var _tags: List[ConceptTag],
                 _uri: KnowledgeURI,
                 _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_parameters: List[TypedFrame[Resource]], _tags: List[ConceptTag], _uri: KnowledgeURI) {
    this(_parameters, _tags, _uri, new Probability())
  }

  def parameters = _parameters

  def parameters_=(in: List[TypedFrame[Resource]]): HowTo = {
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
      List[TypedFrame[Resource]](),
      List[ConceptTag](),
      new KnowledgeURI(map),
      new Probability(map)
    )
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
  def createInstance(parent: HowTo, parameters: List[TypedFrame[Resource]]): HowTo = {
    val name = parent.uri.name + "&ID=" + Random.nextInt(Constant.INSTANCE_ID_LENGTH)
    val it = new HowTo(parameters, List[ConceptTag](), KnowledgeURI(name + howToPostfix))
    it
  }

  def crateInstance(parent: HowTo, parameters: List[Concept]): HowTo = {
    val name = parent.uri.name + "&ID=" + Random.nextInt(Constant.INSTANCE_ID_LENGTH)

    val frames: List[TypedFrame[Resource]] = parameters.map(c => {
      TypedFrame(c)
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

    def oneList(items: Map[String, Map[String, String]]): Map[KnowledgeURI, Concept] = {
      items.keys.foldLeft(Map[KnowledgeURI, Concept]()) {
        (acc, uri) => acc + Pair(KnowledgeURI(uri), new Concept(items(uri)))
      }
    }

    def oneListPhrases(items: Map[String, Map[String, String]]): Map[KnowledgeURI, AnnotatedPhrase] = {
      items.keys.foldLeft(Map[KnowledgeURI, AnnotatedPhrase]()) {
        (acc, uri) => acc + Pair(KnowledgeURI(uri), AnnotatedPhrase.load(kb, ID, uri, Constant.SENTENCES_LINK_NAME))
      }
    }


    val framesID = kb.loadChildrenList(ID, Constant.CONCEPTS_LINK_NAME).map(KBNodeId(_))

    //TODO if howto will stored not only Concepts, then we should store relations with different linkTypes
    def oneFrame(node:KBNodeId):TypedFrame[Resource] = {
      TypedFrame.apply(kb.loadChildrenList(node, Constant.CONCEPTS_LINK_NAME).map(new Concept(_)), KnowledgeURI("frameInHowTo"))
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