#!/usr/bin/perl

if ($ARGV[0] eq '-i') {
    exec "sedit \"$ARGV[1]\" tex-expand";
}

while (<>) {
    chomp;
    if (/\`(.+?)\b/) {
        my $pre = $`;
        my $post = $';
        my $mode = $1;

        print $pre;
        if ($mode eq '1') {
            print "\\section {$post}";
            $post = '';
        } elsif ($mode eq '2') {
            print "\\subsection {$post}";
            $post = '';
        } elsif ($mode eq '3') {
            print "\\subsubsection {$post}";
            $post = '';
        } elsif ($mode eq 'b') {
            print "\\textbf {$post}";
            $post = '';
        } elsif ($mode eq 'e') {
            print "\\begin{enumerate}";
        } elsif ($mode eq 'ee') {
            print "\\end{enumerate}";
        } elsif ($mode eq 's') {
            print "\\begin{itemize}";
        } elsif ($mode eq 'ss') {
            print "\\end{itemize}";
        } elsif ($mode eq 'z') {
            print "\\begin{enumerate.zh}";
        } elsif ($mode eq 'zz') {
            print "\\end{enumerate.zh}";
        } elsif ($mode eq 'a') {
            print "\\item ";
        } elsif ($mode eq 'c') {
            print "\\begin{center}";
        } elsif ($mode eq 'cc') {
            print "\\end{center}";
        } else {
            die "Bad mode string: $mode";
        }
        print $post . "\n";
    } else {
        print "$_\n";
    }
}
