package tu.coreservice.action.way2think.simulation

import tu.model.knowledge.domain.ConceptNetwork
import tu.model.knowledge.annotator.{AnnotatedPhrase, AnnotatedNarrative}

/**
 * Simulation Way2Think implementation.
 * @author max talanov
 *         date 2012-06-25
 *         time: 12:45 PM
 */

class Simulation {

  def apply(in: AnnotatedNarrative, simulationModel: ConceptNetwork): ConceptNetwork = {

    in.phrases.map {
      phrase: AnnotatedPhrase => {

      }
    }
    null
  }

}
