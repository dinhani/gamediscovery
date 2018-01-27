# imports
use strict;
use warnings;

sub id{
    # lowercase
    my $id = lc(shift);

    # transform special chars to nothing
    $id = $id =~ s/['!?.:\/\\]//gr;

    # transform space chars to dashes
    $id = $id =~ s/[,_ ]/-/gr;

    return $id;
}

1;
