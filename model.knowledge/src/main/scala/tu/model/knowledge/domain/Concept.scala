package tu.model.knowledge.domain

import tu.model.knowledge.semanticnetwork.SemanticNetworkNode
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.annotator.AnnotatedPhrase
import util.Random
import tu.model.knowledge._
import org.slf4j.LoggerFactory
import tu.model.knowledge.KBMap._
import collection.mutable.ListBuffer
import tu.model.knowledge.helper.ModelHelper

/**
 * @author max talanov
 *         date 2012-06-01
 *         time: 8:50 AM
 */

case class Concept(var _generalisations: TypedKLine[Concept],
                   var _specialisations: TypedKLine[Concept],
                   var _phrases: TypedKLine[AnnotatedPhrase],
                   @deprecated __content: Resource,
                   var _conceptLinks: List[ConceptLink],
                   override val _uri: KnowledgeURI,
                   override val _probability: Probability = new Probability()
                    )
  extends SemanticNetworkNode[Resource](__content, _conceptLinks, _uri, _probability) {

  def this(_generalisations: TypedKLine[Concept],
           _specialisations: TypedKLine[Concept],
           _phrases: TypedKLine[AnnotatedPhrase],
           _content: Resource,
           _links: List[ConceptLink],
           _uri: KnowledgeURI) {
    this(_generalisations, _specialisations, _phrases, _content, _links, _uri, new Probability())
  }

  def this(map: Map[String, String]) = {
    this(
      TypedKLine[Concept]("generalisation"),
      TypedKLine[Concept]("specialisation"),
      TypedKLine[AnnotatedPhrase]("phrases"),
      map.get("content") match {
        case Some(x) => KnowledgeString(x, x)
        case None => KnowledgeString(Constant.NO_NAME, Constant.NO_NAME)
      },
      List[ConceptLink](),
      new KnowledgeURI(map),
      new Probability(map)
    )
  }


  def phrases: TypedKLine[AnnotatedPhrase] = _phrases

  def phrases_=(in: TypedKLine[AnnotatedPhrase]): Concept = {
    _phrases = in
    this
  }

  def phrasesAdd(in: AnnotatedPhrase): Concept = {
    _phrases = _phrases + in
    this
  }

  def generalisations: TypedKLine[Concept] = _generalisations

  def generalisationsList: List[Concept] = {
    _generalisations.frames.values.toList
  }


  /**
   * Assigns generalisation list to specified.
   * @param conceptKline List of concepts to assign
   * @return this
   */
  def generalisations_=(conceptKline: TypedKLine[Concept]): Concept = {
    _generalisations = conceptKline
    this
  }

  /**
   * Assigns generalisation list to specified.
   * @param concepts List of concepts to assign
   * @return this
   */
  def generalisations_=(concepts: List[Concept]): Concept = {
    _generalisations.frames = concepts.map {
      c: Concept => {
        (c.uri -> c)
      }
    }.toMap
    this
  }

  /**
   * Adds generalisation concept to TypedKline[Concept]
   * @param concept a Concept to add
   * @return this
   */
  def generalisationsAdd(concept: Concept): Concept = {
    _generalisations.frames = _generalisations.frames + (concept.uri -> concept)
    this
  }

  def specialisations = _specialisations

  def specialisations_=(in: TypedKLine[Concept]): Concept = {
    _specialisations = in
    this
  }

  override def links: List[ConceptLink] = _conceptLinks

  def links_=(in: List[ConceptLink]): Concept = {
    _conceptLinks = in
    this
  }

  def setLinks(in: List[ConceptLink]): Concept = {
    _conceptLinks = in
    this
  }

  def linksAdd(link: ConceptLink): Concept = {
    _conceptLinks = _conceptLinks ::: List(link)
    // generalisations are unidirectional
    if (link.isGeneralisationLink && link.destination != null) {
      generalisationsAdd(link.destination)
    }
    this
  }

  def linkedConcepts: Set[Concept] = {
    val res: List[Concept] = for (l <- _conceptLinks) yield {
      if (l._destination == this) {
        l.source
      } else {
        l.destination
      }
    }
    res.toSet
  }

  /**
   * Returns true if current Concept has at least one same parent(generalisation) with specified.
   * @param that Concept to compare
   * @return Boolean true if that has at least one common parent(generalisation).
   */
  def hasSameGeneralisation(that: Concept): Boolean = {
    val commonGeneralisations = that.generalisations.frames.filter {
      uriConcept: Pair[KnowledgeURI, Concept] => this.generalisations.frames.contains(uriConcept._1)
    }
    commonGeneralisations.size > 0
  }

  /**
   * Returns true if current Concept has at least one same parent with specified.
   * @param parent Concept to compare with PHRASES
   * @return Boolean true if that has same parent.
   */
  def hasGeneralisation(parent: Concept): Boolean = {
    this.generalisations.frames.contains(parent.uri)
  }

  override def toString: String = {
    if (_generalisations ==null)
      log debug  "null generalizations"
    this.uri.name +
      "[generalisations=" + _generalisations.toString +
      ";specialisations=" + _specialisations.toString +
      ";links=" + _conceptLinks.map(l => l.toString).mkString(",") +
      ";phrase=" + _phrases +
      "]"
  }

  def getGeneralisationsRec: List[Concept] = {
    val res: List[Concept] = this.generalisations.frames.values.map {
      g: Concept => g.getGeneralisationsRec
    }.toList.flatten
    res ::: this.generalisations.frames.values.toList
  }

  /**
   * Checks if current concept could be generalised to specified parent Concept, according to: name, namespace, revision.
   * @param parent Concept to check generalisation.
   * @return true if current Concept could be generalised to specified.
   */
  def hasGeneralisationRec(parent: Concept): Boolean = {
    val res: List[Concept] = getGeneralisationsRec.filter {
      c: Concept => {
        c.uri.equals(parent.uri)
      }
    }
    res.size > 0
  }

  /**
   * Checks if current concept could be generalised exactly to specified parent Concept, according to: name, namespace, revision, uid.
   * @param parent Concept to check generalisation.
   * @return true if current Concept could be generalised to specified.
   */
  def hasExactGeneralisationRec(parent: Concept): Boolean = {
    val res: List[Concept] = getGeneralisationsRec.filter {
      c: Concept => {
        c.uri.exactEquals(parent.uri)
      }
    }
    res.size > 0
  }

  override def save(kb: KB, parent: KBNodeId, key: String, linkType: String, saved: ListBuffer[String] = new ListBuffer[String]()): Boolean = {
    //if (saved.contains(key)) return true

    if (ModelHelper.checkIfSaved(kb, parent, key, linkType, saved, this, this.uri)) return true

    var res = kb.saveResource(this, parent, key, linkType)

    ModelHelper.appendToSave(this.uri,saved)

    for (x: Resource <- generalisations.frames.values.iterator) {
      //if (!savedLocal.contains(x.uri.toString))
      //{
      res &= x.save(kb, this, x.uri.toString, Constant.GENERALISATION_LINK_NAME, saved)
      //  savedLocal::=x.uri.toString
      //}
    }

    for (y: Resource <- specialisations.frames.values.iterator) {
      res &= y.save(kb, this, y.uri.toString, Constant.SPECIALISATION_LINK_NAME, saved)
    }

    for (z: Resource <- phrases.frames.values.iterator) {
      res &= z.save(kb, this, z.uri.toString, Constant.PHRASES_LINK_NAME, saved)
    }

    for (t: ConceptLink <- _conceptLinks) {
      res &= t.source.save(kb, this, t.uri.name, Constant.CONCEPT_LINK_SOURCE_NAME, saved)
      res &= t.destination.save(kb, this, t.uri.name, Constant.CONCEPT_LINK_DESTINATION_NAME, saved)
    }
    res
  }

  override def export: Map[String, String] = {
    super.export + Pair("content", this._content.uri.name)
  }
}


object Concept {

  val log = LoggerFactory.getLogger(this.getClass)

  def apply(phrases: TypedKLine[AnnotatedPhrase], name: String): Concept = {
    new Concept(TypedKLine[Concept]("generalisation"), TypedKLine[Concept]("specialisation"),
      phrases, KnowledgeString(name, name), List[ConceptLink](), KnowledgeURI(name + Constant.conceptSuffix))
  }

  def apply(word: String, name: String): Concept = {
    new Concept(TypedKLine[Concept]("generalisation"),
      TypedKLine[Concept]("specialisation"),
      TypedKLine[AnnotatedPhrase]("sentences", AnnotatedPhrase(word)),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name + Constant.conceptSuffix))
  }

  def apply(name: String): Concept = {
    new Concept(TypedKLine[Concept]("generalisation"),
      TypedKLine[Concept]("specialisation"),
      TypedKLine[AnnotatedPhrase]("sentences"),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name + Constant.conceptSuffix))
  }

  /**
   * Translates name to camelCase.
   * @param name to process.
   * @return camelCase name.
   */
  def prepareName(name: String): String = {
    val space = " "
    if (name.contains(space)) {
      val nameSplit = name.split(space)
      val nameUpdated = nameSplit.reverse.reduceLeft(
        (element: String, accumulator: String) => {
          if (element.size > 1) {
            accumulator + element.substring(0, 1).capitalize + element.substring(1)
          } else if (element.size == 1) {
            accumulator + element.substring(0, 1).capitalize
          } else {
            accumulator
          }
        }
      )
      nameUpdated
    } else {
      name
    }
  }

  def apply(name: String, probability: Probability): Concept = {
    new Concept(TypedKLine[Concept]("generalisation"),
      TypedKLine[Concept]("specialisation"),
      TypedKLine[AnnotatedPhrase]("sentences"),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name + Constant.conceptSuffix),
      probability)
  }

  def createSubConcept(parent: Concept, name: String): Concept = {
    val it = new Concept(TypedKLine[Concept]("generalisations", parent), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("sentences"),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name))
    parent.specialisations = parent.specialisations + (it.uri -> it)
    it
  }

  /**
   * Creates instance of the concept based on specified Concept.
   * @param parent base Concept.
   * @return created Concept instance.
   */
  def createInstanceConcept(parent: Concept): Concept = {
    val name = parent.uri.name
    val uID = Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED)
    val it = new Concept(TypedKLine[Concept]("generalisations", parent), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("sentences"),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name, uID.toString))
    parent.specialisations = parent.specialisations + (it.uri -> it)
    it
  }

  /**
   * Creates instance of the concept based on specified Concept and name.
   * @param parent base Concept.
   * @param content of the instance Concept.
   * @return created Concept instance.
   */
  def createInstanceConcept(parent: Concept, content: String): Concept = {
    val name = parent.uri.name
    val uID = Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED)
    val it = new Concept(TypedKLine[Concept]("generalisations", parent), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("sentences"),
      KnowledgeString(content, content),
      List[ConceptLink](),
      KnowledgeURI(name, uID.toString))
    parent.specialisations = parent.specialisations + (it.uri -> it)
    it
  }

  /**
   * Creates instance of the concept based on specified name.
   * @param instanceName of the instance Concept.
   * @return created Concept instance.
   */
  def createInstanceConcept(instanceName: String): Concept = {
    val name = instanceName
    val uID = Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED)
    val it = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("sentences"),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name, uID.toString))
    it
  }

  /**
   * Creates instance of the concept based on specified name and phrase.
   * @param instanceName of the instance Concept.
   * @param phrase the phrase to be used in Concept.
   * @return created Concept instance.
   */
  def createInstanceConcept(instanceName: String, phrase: AnnotatedPhrase): Concept = {
    val name = instanceName
    val uID = Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED)
    val it = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("phrases", phrase),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name, uID.toString))
    it
  }

  /**
   * Creates instance of the concept based on specified phrase.
   * @param phrase the phrase to be used in Concept.
   * @return created Concept instance.
   */
  def createInstanceConcept(phrase: AnnotatedPhrase): Concept = {
    val name = Concept.prepareName(phrase.text)
    val uID = Random.nextInt(Constant.INSTANCE_ID_RANDOM_SEED)
    val it = new Concept(TypedKLine[Concept]("generalisations"), TypedKLine[Concept]("specialisations"),
      TypedKLine[AnnotatedPhrase]("phrases", phrase),
      KnowledgeString(name, name),
      List[ConceptLink](),
      KnowledgeURI(name, uID.toString))
    it
  }

  def load(kb: KB, parentId: KBNodeId, key: String, linkType: String): Concept = {
    val selfMap = kb.loadChild(parentId, key, linkType)
    if (selfMap.isEmpty) {
      log.error("Concept not loaded for link {}/{} for {}", List(key, linkType, parentId.toString))
      //throw new UnexpectedException("Concept not loaded for link " + key + "/" + linkType + " for " + parentId.toString)
      return null
    }
    //try to load from cache
    val cached = KBMap.loadFromCache(new KnowledgeURI(selfMap))
    if (!cached.isEmpty  ) return cached.get.asInstanceOf[Concept]
    val ID = new KBNodeId(selfMap)
    val name = selfMap.get("content") match {
      case Some(x) => x
      case None => {
        log.error("Concept without content")
        ""
      }
    }

    val res = new Concept(null,
      null,
      null,
      KnowledgeString(name, name),
      null,
      new KnowledgeURI(selfMap),
      new Probability(selfMap)
    )

    //register primary object
    KBMap.register(res, ID.ID)


    //load children list
    def oneList(items: Map[String, Map[String, String]], linkName: String): Map[KnowledgeURI, Concept] = {
      items.keys.foldLeft(Map[KnowledgeURI, Concept]()) {
        (acc, uri) => acc + Pair(KnowledgeURI(uri, true), Concept.load(kb, ID, uri, linkName))
      }
    }

    def oneListPhrases(items: Map[String, Map[String, String]]): Map[KnowledgeURI, AnnotatedPhrase] = {
      items.keys.foldLeft(Map[KnowledgeURI, AnnotatedPhrase]()) {
        (acc, uri) => acc + Pair(KnowledgeURI(uri, true), AnnotatedPhrase.load(kb, ID, uri, Constant.PHRASES_LINK_NAME))
      }
    }

    val generalisation =
      TypedKLine[Concept](
        "generalisation",
        oneList(kb.loadChildrenMap(ID, Constant.GENERALISATION_LINK_NAME), Constant.GENERALISATION_LINK_NAME)
      )

    val specialisation =
      TypedKLine[Concept](
        "specialisation",
        oneList(kb.loadChildrenMap(ID, Constant.SPECIALISATION_LINK_NAME), Constant.SPECIALISATION_LINK_NAME)
      )

    val phrases =
      TypedKLine[AnnotatedPhrase](
        "specialisation",
        oneListPhrases(kb.loadChildrenMap(ID, Constant.PHRASES_LINK_NAME))
      )


    val linksSourceMap = kb.loadChildrenMap(ID, Constant.CONCEPT_LINK_SOURCE_NAME)
    val linksDestinationMap = kb.loadChildrenMap(ID, Constant.CONCEPT_LINK_SOURCE_NAME)
    val conceptLinkList: List[ConceptLink] =
      linksSourceMap.keys.foldLeft(List[ConceptLink]()) {
        (acc, uri) => ConceptLink(new Concept(linksSourceMap(uri)), new Concept(linksDestinationMap(uri)), uri) :: acc
      }
    /* assign loaded children*/
    res.generalisations = generalisation
    res.specialisations = specialisation
    res.phrases = phrases
    res.links = conceptLinkList

    if (res.generalisations   ==null)
      log debug "NULL GEN WHEN LOAD"


    /*val res = new Concept(generalisation,
      specialisation,
      sentences,
      KnowledgeString(name, name),
      conceptLinkList,
      new KnowledgeURI(selfMap),
      new Probability(selfMap)
    ) */

    //KBMap.register(res, ID.ID)

    res
  }

  /**
   * Copies generalisations, specialisations, phrases, links from source concept to destination concept.
   * @param source to from copy generalisations, specialisations, phrases, links.
   * @param destination to copy to generalisations, specialisations, phrases, links.
   * @return updated destination Concept.
   */
  def copy(source: Concept, destination: Concept): Concept = {
    /*var _generalisations: TypedKLine[Concept],
    var _specialisations: TypedKLine[Concept],
    var _phrases: TypedKLine[AnnotatedPhrase],
    __content: Resource,
    var _conceptLinks: List[ConceptLink],*/
    destination.generalisations = source.generalisations
    destination.specialisations = source.specialisations
    destination.phrases = source.phrases
    destination.links = source.links
    destination
  }

  /**
   * Searches for missing needles Concepts in heap List of Concepts, missing is triggered when needle concept could not be generalised to heap Concept.
   * @param needles List[Concept] to find generalisations in heap
   * @param heap List[Concept] to look for generalisations.
   * @return List[Concept] missing.
   */
  def findMissing(needles: List[Concept], heap: List[Concept]): List[Concept] = {
    val missing = needles.filter {
      iC: Concept => {
        heap.filter {
          mC: Concept => {
            iC.hasExactGeneralisationRec(mC)
          }
        }.size > 0
      }
    }
    missing
  }
}
