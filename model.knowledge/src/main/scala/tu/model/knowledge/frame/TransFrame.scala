package tu.model.knowledge.frame

import tu.model.knowledge.{Probability, KnowledgeURI, Resource}


/**
 * @author talanovm
 *         date 2012-05-06
 *         time: 7:14 PM
 */

case class TransFrame[Type<:Resource](_initialState: TypedFrame[Type], _finalState: TypedFrame[Type], _uri: KnowledgeURI, _probability: Probability)
  extends Resource(_uri, _probability) {

  def this(_initialState: TypedFrame[Type], _finalState: TypedFrame[Type], _uri: KnowledgeURI) {
    this(_initialState, _finalState, _uri, new Probability())
  }

  def initialState = _initialState

  def finalState = _finalState

  def delta: Map[KnowledgeURI, Type] = {
    val res = _initialState.resources.filterKeys(uri => {
      _finalState.resources.keySet.contains(uri)
    })
    res
  }

  def deltaDeep: Map[KnowledgeURI, Type] = {
    val res = _initialState.resources.filterKeys(uri => {
      _finalState.resources.keySet.contains(uri) && _finalState.resources.get(uri).get.eq(initialState.resources.get(uri).get)
    })
    res
  }

  override def equals(that: Any) = {
    that.isInstanceOf[TransFrame[Resource]] &&
      that.asInstanceOf[TransFrame[Resource]].initialState.equals(_initialState) &&
      that.asInstanceOf[TransFrame[Resource]].finalState.equals(_finalState)
  }
}
