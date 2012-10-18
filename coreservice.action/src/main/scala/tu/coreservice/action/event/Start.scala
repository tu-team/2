package tu.coreservice.action.event

import tu.coreservice.action.Action
import tu.model.knowledge.communication.ShortTermMemory

/**
 * Start event width action and inputContext parameters.
 * @author max talanov
 *         date 2012-07-09
 *         time: 7:05 PM
 */

case class Start(action: Action, inputContext: ShortTermMemory) {

}
