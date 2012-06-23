
package relex;

import java.text.BreakIterator;
import java.util.Locale;

class Break
{
 public static void printEachForward(BreakIterator boundary, String source) {
     int start = boundary.first();
     for (int end = boundary.next();
          end != BreakIterator.DONE;
          start = end, end = boundary.next()) {
          System.out.println(source.substring(start,end));
     }
 }


     public static void main(String args[])
 {
              // String stringToExamine = "I live on Enfield Dr. in Austin. My doctor is Dr. Enfield, he lives on Mark Ave. in Austin.";
              String stringToExamine = "I live on Enfield Dr. My doctor is Dr. Enfield. This is more text.";
              //print each word in order
              // BreakIterator boundary = BreakIterator.getWordInstance();
              // boundary.setText(stringToExamine);
              // printEachForward(boundary, stringToExamine);
              //print each sentence
              BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.US);
              boundary.setText(stringToExamine);
              printEachForward(boundary, stringToExamine);
     }
     
}
