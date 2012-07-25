package tu.model.knowledge

import domain.ConceptNetwork
import howto.{Solution, HowTo}
import semanticnetwork.SemanticNetwork

/**
 * Created by IntelliJ IDEA.
 * User: adel
 * Date: 10.07.12
 * Time: 7:37
 * To change this template use File | Settings | File Templates.
 */

class SolvedIssue (val issue:ConceptNetwork, val solution: Solution,   _uri: KnowledgeURI, _probability: Probability)
                                                                              extends Resource(_uri, _probability) {


}