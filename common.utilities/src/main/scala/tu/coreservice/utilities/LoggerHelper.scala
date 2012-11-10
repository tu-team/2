package tu.coreservice.utilities

import org.slf4j.Logger
import scala.collection.JavaConversions._

/**
 * @author max
 * date 2012-11-11
 * time: 2:16 AM
 */
object LoggerHelper {

  def info(logger: Logger, message: String, objects: List[AnyRef]) {
    logger.info(message, objects.toArray)
  }

}
