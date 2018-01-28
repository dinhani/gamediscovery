# ==============================================================================
# IMPORTS
# ==============================================================================
use strict;
use warnings;
require "../_shared/functions.pl";

# ==============================================================================
# MAIN LOOP
# ==============================================================================
while(<>){
    # split parts
    (my $source, my $type, my $target) = split(" ", $_, 3);

    # ==========================================================================
    # TARGET
    # ==========================================================================
    my $targetId;
    if ($target =~ /.*Category:(.+)>/){
        $targetId = id($1);
    } else {
        next;
    }

    # ==========================================================================
    # SOURCE
    # ==========================================================================
    my $sourceId;
    if($source =~ /.*resource\/(.+)>/){
        $sourceId = id($1);
    } else {
        next;
    }

    # ==========================================================================
    # OUTPUT
    # ==========================================================================
    print "$sourceId\tcategory\t$targetId\n";
}
