# ==============================================================================
# IMPORTS
# ==============================================================================
use strict;
use warnings;

use XML::XPath;
use XML::XPath::XMLParser;
use String::Util 'trim';

# ==============================================================================
# PARSE
# ==============================================================================
# get filename from input
my $filename = <STDIN>;
$filename = trim($filename);
print "$filename\n";

# parse xml
my $xpath = XML::XPath->new(filename => $filename);
my $games = $xpath->find("/mediawiki/page/");

# iterate games
foreach my $game ($games->get_nodelist) {
    # parse title
    my $title = $game->findvalue('title');

    # parse text
    my $text = $game->find('revision/text');
    print "$title\n";
    print "$text\n";
}
