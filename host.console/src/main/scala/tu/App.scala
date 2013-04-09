package tu.host.console

import tu.coreservice.annotator.KBAnnotatorImpl
import tu.model.knowledge.communication.{TrainingRequest, Request}
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{Constant, KnowledgeURI}
import tu.coreservice.thinkinglifecycle.ThinkingLifeCycleMinimal
import tu.dataservice.knowledgebaseserver.KBAdapter
import org.slf4j.LoggerFactory


/**
 * Console application of the TU.
 *
 */
object AppMain {

  val annotator = new KBAnnotatorImpl()
  val log = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]) {

    var exitConsole = false

    if (args.size > 0 && args(0) == "trainfile") {
      val file = args(1)
      log.info("Loading train data from file " + file)
      val lines = scala.io.Source.fromFile(file).mkString
      train(lines)
    }

    log.info("Starting... ")
    if (args.length <= 0) {
      while (!exitConsole) {
        log.info("Please type: 'exit' to quit, 'request' to enter ")
        log.info("request mode, 'train' to enter training mode")
        log.info("==================================================================")
        log.info(" :>")

        val command: String = Console.readLine()

        exitConsole = command == "exit" || command == "'exit'"

        if (exitConsole) {
          log.info("Exiting...")
          exitConsole = true

        }
        else if (command == "train") {

          var exitTraining = false
          while (!exitTraining) {
            log.info("Training mode: please type train phrase or exit to terminate->")
            val cmd = Console.readLine()
            if (cmd == "exit") {
              exitTraining = true
            }
            else {
              train(cmd)
            }
          }

        }
        else if (command == "clean") {
          KBAdapter.cleanDatabase()
        }
        else if (command == "request") {
          //extract sentence
          var exitRequest = false
          while (!exitRequest) {
            log.info("Request mode: please type request phrase or exit to terminate ->")
            val cmd = Console.readLine()
            if (cmd == "exit") {
              exitRequest = true
            }
            else {
              val requestText = cmd
              log.debug("Running thinking lifecycle:" + command)
              val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.defaultDomainName))
              val t = new ThinkingLifeCycleMinimal()
              val res = t(r)
              log.info("End")
            }
          }

        }
      }
    }

    def train(st: String) {
      val command = "train"
      val requestText = st
      log.debug("Running thinking lifecycle:" + command)
      val r = new TrainingRequest(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.defaultDomainName))
      val t = new ThinkingLifeCycleMinimal()
      val res = t(r)
      log.info("End")
    }
  }
}
