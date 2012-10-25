package tu.host.console

import tu.coreservice.annotator.KBAnnotatorImpl
import tu.model.knowledge.communication.Request
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.{Constant, KnowledgeURI}
import tu.coreservice.thinkinglifecycle.ThinkingLifeCycleMinimal


/**
 * Console application of the KBAnnotator.
 *
 */
object AppMain {

  val annotator = new KBAnnotatorImpl()

  def main(args: Array[String]) = {

    var exitConsole=false

    if (args(0)=="trainfile")
    {
        val file = args(1)
        Console.println("Trying to load train data from file " + file)
        val lines = scala.io.Source.fromFile(file).mkString
        train(lines)
    }

    Console.println("Starting test... ")
    if (args.length<=0)
    {
      while (!exitConsole)
      {
        Console.println("TU is welcome you. Enter: 'exit' for quit, 'request' to enter ")
        Console.println("request mode, 'train' to enter training mode")
        Console.println("==================================================================")
        Console.println(" :>")

        val command:String = Console.readLine()

        exitConsole=command=="exit" || command =="'exit'"

        if (exitConsole) {
          Console.println("Exiting...")
          exitConsole=true

        }
        else if (command =="train")
        {
          Console.println("Entering training mode, please enter train phrase ->")

          train(Console.readLine())
        }
        else if (command =="request")
        {
          Console.println("Entering request mode, please enter request phrase ->")
          //extract sentence

          val requestText = Console.readLine()
          Console.println("Running thinking lifecycle:" + command)
          val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"), KnowledgeURI(Constant.defaultDomainName))
          val t = new ThinkingLifeCycleMinimal()
          val res = t(r)
          Console.println("End of execution");
        }



      }
    }

    def train(st:String)
    {
       //TODO:  //add training here
    }


  }
}
