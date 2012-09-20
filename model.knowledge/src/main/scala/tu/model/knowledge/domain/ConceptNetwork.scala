package tu.model.knowledge.domain

import tu.model.knowledge._
import tu.model.knowledge.semanticnetwork.SemanticNetwork
import org.slf4j.LoggerFactory
import tu.exception.UnexpectedException

/**
 * @author max talanov
 *         date 2012-06-17
 *         time: 10:41 PM
 */

case class ConceptNetwork(_nodes: List[Concept] = List[Concept](),
                          _links: List[ConceptLink] = List[ConceptLink](),
                          override val _uri: KnowledgeURI,
                          override val _probability: Probability = new Probability())
  extends SemanticNetwork(_nodes, _uri, _probability) {

  val log = LoggerFactory.getLogger(this.getClass)

  def this(map: Map[String, String]) = {
    this(
      List[Concept](),
      List[ConceptLink](),
      new KnowledgeURI(map),
      new Probability(map)
    )
  }


  def this(uri: KnowledgeURI) = {
    this(List[Concept](), List[ConceptLink](), uri)
  }

  def nodes = _nodes

  def links = _links

  override def rootNodes = _nodes

  /**
   * Returns nodes with specified name.
   * @param name String parameter of filter
   * @return List[Concept] that has uri-s with specified name.
   */
  def getNodeByName(name: String): List[Concept] = {
    _nodes.filter {
      concept: Concept => {
        concept.uri.name == name
      }
    }
  }

  /**
   * Returns links with specified name.
   * @param name String parameter of filter
   * @return List[ConceptLink] that has uri-s with specified name.
   */
  def getLinkByName(name: String): List[ConceptLink] = {
    _links.filter {
      link: ConceptLink => {
        link.uri.name == name
      }
    }
  }

  /**
   * Returns nodes that has generalisations with specified name.
   * @param name String parameter of filter
   * @return List[Concepts] that has generalisations uri-s with specified name.
   */
  def getNodeByGeneralisationName(name: String): List[Concept] = {
    _nodes.filter {
      concept: Concept => {
        val gens: Map[KnowledgeURI, Concept] = concept.generalisations.frames.filter {
          uriConcept: Pair[KnowledgeURI, Concept] => {
            uriConcept._1 == name
          }
        }
        gens.size > 0
      }
    }
  }

  override def toString = {
    this.getClass.getName + " [" + nodes.toString + "][" + links + "]@" + uri.toString
  }

  def save(kb: KB, parent: KBNodeId, key: String, linkType: String): Boolean = {
    var res = kb.saveResource(this, parent, key, linkType)

    for (x: Resource <- _rootNodes) {
      res &= x.save(kb, KBNodeId(this), x.uri.toString, Constant.NODES_LINK_NAME)
    }

    for (y: Resource <- links) {
      res &= y.save(kb, KBNodeId(this), y.uri.toString, Constant.LINKS_LINK_NAME)
    }
    res
  }
}

object ConceptNetwork {

  val log = LoggerFactory.getLogger(this.getClass)

  def load(kb: KB, parent: KBNodeId, key: String, linkType: String): ConceptNetwork = {
    val selfMap = kb.loadChild(parent, key, linkType)
    if (selfMap.isEmpty) {
      log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parent.ID.toString))
      throw new UnexpectedException("Concept not loaded for link " + key + "/" + linkType + " for " + parent.ID.toString)
    }


    val ID = new KBNodeId(selfMap)

    def oneList(items: Map[String, Map[String, String]]): Map[KnowledgeURI, Concept] = {
      items.keys.foldLeft(Map[KnowledgeURI, Concept]()) {
        (acc, uri) => acc + Pair(KnowledgeURI(uri), new Concept(items(uri)))
      }
    }

    val concepts: List[Concept] = oneList(kb.loadChildrenMap(ID, Constant.NODES_LINK_NAME)).map {
      pair: Pair[KnowledgeURI, Resource] => pair._2.asInstanceOf[Concept]
    }.toList

    val linksSourceMap = kb.loadChildrenMap(ID, Constant.CONCEPT_LINK_SOURCE_NAME)
    val linksDestinationMap = kb.loadChildrenMap(ID, Constant.CONCEPT_LINK_SOURCE_NAME)
    val conceptLinkList: List[ConceptLink] =
      linksSourceMap.keys.foldLeft(List[ConceptLink]()) {
        (acc, uri) => ConceptLink(new Concept(linksSourceMap(uri)), new Concept(linksDestinationMap(uri)), uri) :: acc
      }

    new ConceptNetwork(concepts,
      conceptLinkList,
      new KnowledgeURI(selfMap),
      new Probability(selfMap)
    )
  }


  def apply(nodes: List[Concept], links: List[ConceptLink], name: String) = {
    val uri = KnowledgeURI(name)
    new ConceptNetwork(nodes, links, uri)
  }

  def apply(nodes: List[Concept], name: String) = {
    val uri = KnowledgeURI(name)
    val links: List[ConceptLink] = nodes.map {
      node: Concept => {
        node.links
      }
    }.flatten
    new ConceptNetwork(nodes, links, uri)
  }

  /**
   * Returns nodes that has generalisations with specified name.
   * @param nodes List[Concept] to filter.
   * @param name String parameter of filter.
   * @return List[Concepts] that has generalisations uri-s with specified name.
   */
  def getNodeByGeneralisationName(nodes: List[Concept], name: String): List[Concept] = {
    nodes.filter {
      concept: Concept => {
        val gens: Map[KnowledgeURI, Concept] = concept.generalisations.frames.filter {
          uriConcept: Pair[KnowledgeURI, Concept] => {
            uriConcept._1.name == name
          }
        }
        gens.size > 0
      }
    }
  }

  /**
   * Runs recursively, returns nodes that has generalisations with specified name.
   * @param nodes List[Concept] to filter.
   * @param name String parameter of filter.
   * @return List[Concepts] that has generalisations uri-s with specified name.
   */
  def getNodeByGeneralisationNameRec(nodes: List[Concept], name: String): List[Concept] = {
    nodes.filter {
      concept: Concept => {
        val gens: Map[KnowledgeURI, Concept] = filterGeneralisations(concept.generalisations, name)
        gens.size > 0
      }
    }
  }

  private def filterGeneralisations(generalisations: TypedKLine[Concept], name: String): Map[KnowledgeURI, Concept] = {
    generalisations.frames.filter {
      uriConcept: Pair[KnowledgeURI, Concept] => {
        if (uriConcept._1.name == name) {
          true
        } else {
          filterGeneralisations(uriConcept._2.generalisations, name).size > 0
        }
      }
    }
  }
}