#!/usr/bin/python
import re

filename = 'computational emotional thinking model.md'
filenameOut = 'computational_emotional_thinking_model_body.tex'
listLevel = 0
lines = [line.strip() for line in open(filename)]
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
			lineOut = lineStripped.replace('[', '\cite{')
			lineOut = lineOut.replace(']', '}')
	# ignore title and pictures
	if (lineStripped.startswith('# ') or lineStripped.startswith('![')):
	    lineOut = ''

	print lineOut
