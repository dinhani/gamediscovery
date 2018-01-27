# imports
use strict;
use warnings;

# log
if ($. % 200000 == 0){
    print STDERR "$.\n"
}

# split parts
(my $source, my $type, my $target) = split(" ");

# parse target
(my $capture) = $target =~ /.*Category:(.+)>/;
if (!$capture){
    next
}
my $targetId = lc($capture);

# parse source (if source changed)
($capture) = $source =~ /.*resource\/(.+)>/;
my $sourceId = lc($capture);

# output
print "$sourceId\tcategory\t$targetId\n";
