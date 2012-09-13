package tu.host.console

import tu.coreservice.annotator.KBAnnotatorImpl
import tu.model.knowledge.communication.Request
import tu.model.knowledge.primitive.KnowledgeString
import tu.model.knowledge.KnowledgeURI
import tu.coreservice.thinkinglifecycle.ThinkingLifeCycleMinimal


/**
 * Console application of the KBAnnotator.
 *
 */
object AppMain {

  val annotator = new KBAnnotatorImpl()

  def main(args: Array[String]) = {

    var exitConsole=false



    Console.println("Starting test... ")

    while (!exitConsole)
    {
      Console.println("TU is welcome you. Enter: 'exit' for quit.")
      Console.println("========================================")
      Console.println("You request :>")

      val sentence:String = Console.readLine()

      exitConsole=sentence=="exit" || sentence =="'exit'"

      if (exitConsole) {
        Console.println("Exiting...")
        scala.util.control.Breaks.break()

      }

      Console.println("Testing:" + sentence)
      //extract sentence

      val requestText = sentence
      Console.println("Running thinking lifecycle:" + sentence)
      val r = new Request(KnowledgeString(requestText, "inputtext"), KnowledgeURI("testRequest"))
      val t = new ThinkingLifeCycleMinimal()
      val res = t(r)
      Console.println("End of execution");
    }




  }
}
