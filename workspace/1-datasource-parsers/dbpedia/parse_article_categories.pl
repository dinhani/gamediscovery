# imports
use strict;
use warnings;
require "../_shared/functions.pl";

# main loop
while(<>){
    # split parts
    (my $source, my $type, my $target) = split(" ");

    # parse target
    (my $capture) = $target =~ /.*Category:(.+)>/;
    if (!$capture){
        next
    }
    my $targetId = id($capture);

    # parse source (if source changed)
    ($capture) = $source =~ /.*resource\/(.+)>/;
    my $sourceId = id($capture);

    # output
    print "$sourceId\tcategory\t$targetId\n";
}
