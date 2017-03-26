package tu.coreservice.action.critic.spike

import tu.coreservice.action.critic.{Critic, CriticLink}
import tu.coreservice.action.way2think.spike.SpikeGeneratorWay2Think
import tu.model.knowledge.communication.ShortTermMemory
import tu.model.knowledge.{KnowledgeURI, Probability}
import tu.model.knowledge.Constant.SPIKE_FILES_STORAGE_DIRECTORY

abstract class FamilyCritic(_excluded: List[CriticLink], _included: List[CriticLink], _uri: KnowledgeURI, _probability: Probability)
  extends Critic(_excluded, _included, _uri, _probability) {

  private var _storageDirectory: String = SPIKE_FILES_STORAGE_DIRECTORY

  def storageDirectory = _storageDirectory
  def storageDirectory_= (newDir: Option[String]) = {
    newDir match {
      case None => _storageDirectory = SPIKE_FILES_STORAGE_DIRECTORY
      case Some(dir) => _storageDirectory = dir
    }
  }

  override def start(): Boolean = false
  override def stop(): Boolean = false

  protected def apply(inputContext: ShortTermMemory, family: String): ShortTermMemory = {
    SpikeGeneratorWay2Think(_storageDirectory,family).apply(inputContext)
  }
}
