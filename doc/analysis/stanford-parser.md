# abstract

it is not helping in real sentences. It not understand context, can't decide what comma separate two parts of sentence,
and of course can not att comma or point if user skip it.

True: 6
False: 11
Precision = 0,35.

# statistic

221: paths in computer like "c:\", "\\machine\folder\file.ext" not recognised, after convert to machine_folder_file_ext - ok
321: bad English, not recognized
421: empty phrase, ok
521: bad root "work" in third sentence
621: ok after convert "?" to "'" it words like isn?t
721: parsing of long sentence is incorrect     btw Flush player is not player
821: incorrect
921: incorrect
1021: incorrect
1121: Ok
1221: ok after replace "\\machine\folder\file.ext" to long_file_name
1321: bad sentence, I not understand myself
1421: Ok
1521: bad, if document portal = portal of documents, SP desided
1621: Ok
1721: bad
1821: 3/4 bad

##Additional
Notation of stanford parser's tree can be found [here](http://bulba.sdsu.edu/jeanette/thesis/PennTags.html).

## Comments
Please compare it with [RelEx](https://github.com/development-team/2/blob/master/doc/informal/relex.md)

Please add statistical analysis: Precision, Recall, F-measure http://en.wikipedia.org/wiki/Recall_(information_retrieval)