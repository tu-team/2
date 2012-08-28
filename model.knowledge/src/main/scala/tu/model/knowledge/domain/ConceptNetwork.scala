package tu.model.knowledge.domain

import tu.model.knowledge._
import semanticnetwork.SemanticNetwork

/**
 * @author max
 *         date 2012-06-17
 *         time: 10:41 PM
 */

case class ConceptNetwork(_nodes: List[Concept] = List[Concept](),
                          _links: List[ConceptLink] = List[ConceptLink](),
                          override val _uri: KnowledgeURI,
                          override val _probability: Probability = new Probability())
  extends SemanticNetwork(_nodes, _uri, _probability) {

  def this(map: Map[String, String]) = {
    this(
      List[Concept](),
      List[ConceptLink](),
      new KnowledgeURI(map),
      new Probability(map)
    )
  }

  def loadLinksNodes(kb: KB): List[Concept] = {
    val list = kb.loadChildrenList(this, Constant.NODES_LINK_NAME)
    list.map {
      x: Map[String, String] => {
        new Concept(x)
      }
    }
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

}

object ConceptNetwork {

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