
my $fname = $ARGV[1];
$fname = '../common.utilities/src/main/scala/tu/coreservice/utilities/TestDataGenerator.scala' if ($fname eq '');

#print $fname;

open( FILE, "<",  $fname) or die "file $fname not found";


while(my $line = <FILE>)
{
 if($line =~ /Concept\("(.*)"/)
 {
    print "$1 is kind of concept.\n";
 }
 if($line =~ /Concept.createSubConcept\((.*)Concept, "(.*)"\)/)
 {
    print "$2 is kind of $1.\n";
 }

 if($line =~ /ConceptLink\((.*)Concept, (.*)Concept, "(.*)"/)
 {
    print "$3 is a link from $1 to $2.\n";
 }

 if($line =~ /createSubConceptLink\((.*), (.*)Concept, (.*)Concept, "(.*)"/)
 {
    print "$4 is a link $1 from $2 to $3.\n";
 }
}
