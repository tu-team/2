package tu.host.console

import tu.coreservice.annotator.KBAnnotator
;


/**
 * Hello world!
 *
 */
object AppMain {

  val annotator = new KBAnnotator()

  def main(args: Array[String]): Unit= {

    Console.println("Starting test... ")

       if (args.length<=0)
       {
         Console.println("Format: <sentence> For example, run with 'This is a simple sentence'")
       }
       else
       {
         Console.println("Testing "+ args(0))
         //extract sentence
         var sentence = args(0);
         var res=annotator.parseString(sentence);
         Console.println(res);
       }

  }
}
