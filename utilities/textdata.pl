
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
}
