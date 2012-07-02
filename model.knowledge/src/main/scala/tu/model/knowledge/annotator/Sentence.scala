package tu.model.knowledge.annotator

import tu.model.knowledge.domain.Concept
import tu.model.knowledge.{Resource, Probability, KnowledgeURI}

/**
 * @author alex
 *         Time stamp: 6/29/12 11:25 AM
 */

case class Sentence(var _phrases: List[Phrase], _uri: KnowledgeURI, _probability: Probability = new Probability())
  extends Resource(_uri, _probability) {

  def this(_phrases: List[Phrase], _uri: KnowledgeURI) = {
    this(_phrases,  _uri, new Probability())
  }

  def phrases = _phrases

  def concepts_=(in: List[Phrase]): Sentence = {
    _phrases = in
    this
  }



}

object Sentence {
  def apply(phrases: List[Phrase]): Sentence = {
    new Sentence(phrases, KnowledgeURI( "Sentence"))
  }

}

