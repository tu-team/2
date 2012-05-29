# Natural language processing analysis page.

## Original analysis
 1. [OpenNLP](opennlp.md)
 1. [RelEx](relex.md)
 1. [StanfordParser](stanford-parser.md)

## OpenNLP
According to [OpenNLP site](http://incubator.apache.org/opennlp/documentation/1.5.2-incubating/manual/opennlp.html#tools.parser.training) the
The OpenNLP offers two different parser implementations, the chunking parser and the treeinsert parser. The later one is still experimental and not recommended for production use.
Dependencies parsing is not available for OpenNLP.

_Unusable_, at the moment.

## RelEx
Has dependency parsing.
Precision = 0.39.

## Stanford parser
Has dependency parsing
Precision = 0,35.

## Conclusion

RelEx is the best option at the moment, at it has highest precision and dependency parsing.