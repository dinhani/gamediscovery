# split parts
($source, $type, $target) = split(" ");

# match target
($capture) = $target =~ /.*Category:(.+)>/;
if (!$capture){
    next
}

# set target
$target = lc($capture);

# match source
($capture) = $source =~ /.*Category:(.+)>/;
$source = lc($capture);

# output
print "$source\tcategory\t$target\n";
