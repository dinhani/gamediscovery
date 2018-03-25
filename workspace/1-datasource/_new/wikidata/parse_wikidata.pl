# ==============================================================================
# IMPORTS
# ==============================================================================
use strict;
use warnings;
use Switch;

require "../_shared/functions.pl";

# ==============================================================================
# MAIN LOOP
# ==============================================================================
while(<>){
    # split parts
    (my $source, my $type, my $target) = split(" ", $_, 3);

    # ==========================================================================
    # SOURCE
    # ==========================================================================
    my $sourceId;
    if($source =~ /.*entity\/(.+)>/){
        $sourceId = $1;
    } else {
        next;
    }

    # ==========================================================================
    # TYPE
    # ==========================================================================
    my $typeId;

    # discard
    switch ($type) {
        case "<http://www.w3.org/2000/01/rdf-schema#label>" { last; }
        case "<http://www.w3.org/2004/02/skos/core#prefLabel>" { last; }
        case "<http://www.w3.org/2004/02/skos/core#altLabel>" { last; }
        case "<http://schema.org/name>" { $typeId = "name"; }
        case "<http://schema.org/description>" { $typeId = "description"; }
        else {
            if( $type =~ /.*direct\/(.+)>/){
                $typeId = $1;
            }
        }
    }
    if (!$typeId){
        next;
    }

    # ==========================================================================
    # TARGET
    # ==========================================================================
    my $targetId;

    # name
    switch ($typeId) {
        case "name" {
            if($target =~ /\"(.+)\"\@en \./){
                $targetId = $1;
            }
        }
        case "description" {
            if($target =~ /\"(.+)\"\@en \./){
                $targetId = $1;
            }
        }
        else {
            # entity
            if ($target =~ /.*entity\/(.+)>/){
               $targetId = $1;
            }
        }
    }

    if(!$targetId){
        next;
    }

    # ==========================================================================
    # OUTPUT
    # ==========================================================================
    print "$sourceId\t$typeId\t$targetId\n"
}
