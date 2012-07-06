package tu.host.console

import tu.coreservice.annotator.KBAnnotatorImpl


/**
 * Console application of the KBAnnotator.
 *
 */
object AppMain {

  val annotator = new KBAnnotatorImpl()

  def main(args: Array[String]) = {

    Console.println("Starting test... ")

       if (args.length<=0)
       {
         Console.println("Format: <sentence> For example, run with 'This is a simple sentence'")
       }
       else
       {
         Console.println("Testing "+ args(0))
         //extract sentence
         val sentence = args(0);
         //TODO fix this:
         // val res=annotator.parseString(sentence);
         val res = ""
         Console.println(res);
       }

  }
}
