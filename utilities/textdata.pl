
my $fname = $ARGV[1];
$fname = '../common.utilities/src/main/scala/tu/coreservice/utilities/TestDataGenerator.scala' if ($fname eq '');

#print $fname;

my $start = 0;

open( FILE, "<",  $fname) or die "file $fname not found";


while(my $line = <FILE>)
{
 if($line =~ /for Perl - below/)
 {
    $start = 1;
 }

 next if (! $start);

 if($line =~ /Concept\("(.*)"/)
 {
    print "$1 is kind of concept.\n";
 }
 if($line =~ /Concept.createSubConcept\((\w*)Concept, "(.*)"\)/)
 {
    print "$2 is kind of $1.\n";
 }

 if($line =~ /ConceptLink\((\w*)Concept, (\w*)Concept, "(.*)"/)
 {
    print "word \"$3\" is a link from $1 to $2.\n";
 }

 if($line =~ /createSubConceptLink\((\w*), (\w*)Concept, (\w*)Concept, "(.*)"/)
 {
    print "word \"$4\" is a kind of link \"$1\" from $2 to $3.\n";
 }

}
