package tu.dataservice.knowledgebaseserver


import tu.coreservice.utilities.TestDataGenerator

/**
 * @author alex
 *         Time stamp: 9/18/12 6:29 PM
 */
object InitialData {

  def fill() {

  }

  def fillSimulationModelConceptNetwork() {
    var dt = TestDataGenerator.generateDomainModelConceptNetwork
    N4JKB.saveResource(dt,"simulationConceptNetwork")

  }

  def fillReflectiveCrititcs(){
         // List[CriticModel](CriticModel("tu.coreservice.action.critic.manager.DoNotUnderstandManager")


  }


}
