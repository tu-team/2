#!/usr/bin/python
import re
import sys, getopt

def main(argv):
   inputfile = ''
   try:
      opts, args = getopt.getopt(argv,"hi:",["ifile="])
   except getopt.GetoptError:
      print 'md2latex.py -i <inputfile> > <outputfile>'
      sys.exit(2)
   for opt, arg in opts:
      if opt == '-h':
         print 'md2latex.py -i <inputfile> > <outputfile>'
         sys.exit()
      elif opt in ("-i", "--ifile"):
         inputfile = arg
   print 'Input file is ', inputfile
   if (len(inputfile.strip()) == 0):
      print 'md2latex.py -i <inputfile> > <outputfile>'
      sys.exit(2)
   listLevel = 0
   try: 
      lines = [line.strip() for line in open(inputfile)]
      for line in lines:
         # print line
         # parsing
         # sections
         lineStripped = line.strip()
         lineOut = lineStripped
         # sections
         if lineStripped.startswith('##'):
                 sectionName = lineStripped.strip('##').strip()
                 lineOut = '\section{' + sectionName + '}'	
         if lineStripped.startswith('###'):
                 sectionName = lineStripped.strip('###').strip()
                 lineOut = '\subsection{' + sectionName + '}'
         if lineStripped.startswith('####'):
                 sectionName = lineStripped.strip('####').strip()
                 lineOut = '\subsubsection{' + sectionName + '}'
         # lists
         if (lineStripped.startswith('1.') or lineStripped.startswith('2.') or lineStripped.startswith('3.')):
                 currentListLevel = lineStripped[0]
                 lineOut = ''
                 # print str(listLevel) + ' ' + str(currentListLevel)		
                 if currentListLevel > listLevel:
                         lineOut = '\\begin{enumerate}\n'
                 elif currentListLevel < listLevel:
                         lineOut = '\end{enumerate}\n'		
                 listLevel = currentListLevel
                 item = lineStripped.strip('1.').strip('2.').strip('3.')
                 lineOut = lineOut + '\item ' + item
         else:
                 if listLevel > 0 :
                         #print listLevel
                         for i in range(int(listLevel)): 
                                 lineOut = lineOut + '\end{enumerate}\n'
                 listLevel = 0 
         # references
         if (not (lineStripped.startswith('![')) and not(lineStripped.startswith('Figure'))):
                 if(lineStripped.find('[') > 0 and lineStripped.find(']') > 0):
                    #reference link case
                    if (lineStripped.find('][') > 0):
                       lineOut = lineOut.replace('[', '', 1)
                       lineOut = lineOut.replace(']', '', 1)
                       lineOut = lineOut.replace('[', '\cite{')
                       lineOut = lineOut.replace(']', '}')
                    else:
                       #simple case
                       lineOut = lineStripped.replace('[', '\cite{')
                       lineOut = lineOut.replace(']', '}')
         # ignore title and pictures
         if (lineStripped.startswith('# ') or lineStripped.startswith('![')):
             lineOut = ''

         print lineOut
   except IOError:
      print 'There is no file: ', inputfile 
   
if __name__ == "__main__":
   main(sys.argv[1:])


