#!/usr/bin/python
import re
import sys, getopt


def main(argv):
    inputfile = ''
    figureNumber = 1
    try:
        opts, args = getopt.getopt(argv, "hi:", ["ifile="])
    except getopt.GetoptError:
        print 'md2latex.py -i <inputfile> > <outputfile>'
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print 'md2latex.py -i <inputfile> > <outputfile>'
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        #print 'Input file is ', inputfile
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
            # print 'lists'
            if (lineStripped.startswith('1.') or lineStripped.startswith('2.') or lineStripped.startswith('3.')):
                currentListLevel = lineStripped[0]
                lineOut = ''
                spaces = ''
                # print str(listLevel) + ' ' + str(currentListLevel) + ' ' + str(currentListLevel > listLevel)
                if currentListLevel > listLevel:
                    # print 'begin enumerate'
                    for i in range(int(listLevel)):
                        spaces += ' '
                    lineOut = spaces + '\\begin{enumerate}\n'
                    # print "lineout 0 " + lineOut
                elif currentListLevel < listLevel:
                    for i in range(int(currentListLevel)):
                        spaces += ' '
                    lineOut = spaces + '\\end{enumerate}\n'
                listLevel = currentListLevel
                item = lineStripped.strip('1.').strip('2.').strip('3.')
                spaces = ''
                for i in range(int(currentListLevel)):
                    spaces += ' '
                lineOut = lineOut + spaces + '\item ' + item
            else:
                if listLevel > 0:
                    #print str(listLevel)
                    for i in range(int(listLevel)):
                        #print str(int(listLevel) - i - 1)
                        spaces = ''
                        for j in range(int(listLevel) - i - 1):
                            spaces += ' '
                        lineOut = lineOut + spaces + '\end{enumerate}\n'
                listLevel = 0
                # references
            #print "lineout 1 " + lineOut
            if (not (lineOut.startswith('![')) and not (lineOut.startswith('Figure'))):
                #print 'we are in !fugure'
                if (lineOut.find('[') > -1 and lineOut.find(']') > 0):
                    #print 'we are in link'
                    #reference link case
                    if (lineOut.find('][') > -1):
                        lineOut = lineOut.replace('[', '', 1)
                        lineOut = lineOut.replace(']', '', 1)
                        lineOut = lineOut.replace('[', '\cite{')
                        lineOut = lineOut.replace(']', '}')
                        #replace links
                    if (lineOut.find(']:') > -1):
                        #print 'we are here', lineOut
                        lineOut = ''
                    else:
                        #simple case
                        lineOut = lineOut.replace('[', '\cite{')
                        lineOut = lineOut.replace(']', '}')
                # emphasize
            while (lineOut.find('**') > -1):
                lineOut = lineOut.replace('**', '\emph{', 1)
                lineOut = lineOut.replace('**', '}', 1)
            while (lineOut.find('*') > -1):
                lineOut = lineOut.replace('*', '\emph{', 1)
                lineOut = lineOut.replace('*', '}', 1)
            # title and pictures
            if (lineOut.startswith('![')):
                caption = '...'
                label = '...'
                pic_filename = '...'
                pic_filename = lineOut[lineOut.find('(') + 1: lineOut.rfind('.')]
                caption = lineOut[lineOut.find('[') + 1: lineOut.rfind(']')]
                label = pic_filename
                lineOut = '\\begin{figure}\n\\begin{center}\n \\includegraphics[height=10cm]{' + pic_filename + '}\n\\end{center}\n\\caption{' + caption + '}\n\\label{' + label + '}\n\\end{figure}'
                figureNumber = figureNumber + 1
            if (lineOut.startswith('#')):
                lineOut = '\\title{' + lineStripped.replace('#', '') + '}'

            print lineOut
    except IOError:
        print 'There is no file: ', inputfile


if __name__ == "__main__":
    main(sys.argv[1:])


