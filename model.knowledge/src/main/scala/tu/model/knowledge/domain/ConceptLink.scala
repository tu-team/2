package tu.model.knowledge.domain

import tu.model.knowledge.annotator.AnnotatedPhrase
import tu.model.knowledge.semanticnetwork.SemanticNetworkLink
import util.Random
import tu.model.knowledge.{Constant, Probability, KnowledgeURI, TypedKLine}

/**
 * Class stores the SemanticNetworkLink of the Concepts.
 * @author talanov max
 *         date 2012-06-09
 *         time: 10:47 PM
 */

case class ConceptLink(var _generalisations: TypedKLine[ConceptLink],
                       var _specialisations: TypedKLine[ConceptLink],
                       var _phrases: TypedKLine[AnnotatedPhrase],
                       _source: Concept,
                       _destination: Concept,
                       _uri: KnowledgeURI, _probability: Probability)
  extends SemanticNetworkLink(_source, _destination, _uri, _probability) {

  def this(_generalisations: TypedKLine[ConceptLink],
           _specialisations: TypedKLine[ConceptLink],
           _phrases: TypedKLine[AnnotatedPhrase],
           _source: Concept,
           _destination: Concept,
           _uri: KnowledgeURI) = {
    this(_generalisations, _specialisations, _phrases, _source, _destination, _uri: KnowledgeURI, new Probability())
  }

  def phrases = _phrases

  def phrases_=(in: TypedKLine[AnnotatedPhrase]): ConceptLink = {
    _phrases = in
    this
  }

  def generalisations = _generalisations

  def generalisations_=(in: TypedKLine[ConceptLink]): ConceptLink = {
    _generalisations = in
    this
  }

  def specialisations = _specialisations

  def specialisations_=(in: TypedKLine[ConceptLink]): ConceptLink = {
    _specialisations = in
    this
  }
}

object ConceptLink {
  def apply(source: Concept, destination: Concept, name: String): ConceptLink = {
    new ConceptLink(TypedKLine("generalisations"), TypedKLine("specialisations"), TypedKLine("phrases"), source, destination, KnowledgeURI(name))
  }

  def createSubConceptLink(parent: ConceptLink, source: Concept, destination: Concept, name: String): ConceptLink = {
    val it = new ConceptLink(TypedKLine("generalisations", parent), TypedKLine("specialisations"), TypedKLine("phrases"), source, destination, KnowledgeURI(name))
    parent.specialisations = parent.specialisations + (it.uri -> it)
    it
  }

  def createInstanceConceptLink(parent: ConceptLink, source: Concept, destination: Concept): ConceptLink = {
    val it = new ConceptLink(TypedKLine("generalisations", parent), TypedKLine("specialisations"), TypedKLine("phrases"),
      source, destination, KnowledgeURI(parent.uri.name + Random.nextString(Constant.INSTANCE_ID_LENGTH) + "ConceptLink"))
    parent.specialisations = parent.specialisations + (it.uri -> it)
    it
  }
}
