package Text::MDA::Seq;

=head1 NAME

Seq - Tools for analyze & convert .seq file format

=cut
use strict;
use vars qw($LOGNAME $LOGLEVEL);
use cmt::log(2);
    our $LOGNAME    = __PACKAGE__;
    our $LOGLEVEL   = 1;
use cmt::util();
use cmt::vcs('parse_id');
    my %RCSID   = parse_id('$Id: perllib.pm 2141 2010-12-13 06:15:26Z lenik $');
    our $VER    = "0.$RCSID{rev}";
use Exporter;

our @ISA    = qw(Exporter);
our @EXPORT = qw(parse
                 );

# INITIALIZORS

=head1 SYNOPSIS

    use Seq;
    mysub(arguments...)

=head1 DESCRIPTION

Seq model after analyzed:

    node(key): {
        (attrs...)
        next: { target: { attributes }, ... }
        lane: lane-REF
    }

    lane(name): [ node-LIST ]

=head1 FUNCTIONS

=cut
=head2 parse([LINES])
Parse the source lines and return the lane array.

Each line is in format of:

    Commenct:
        # comment text

    Explicit Link:
        a -> b[attrs]: display text

    Seq Node:
        LEVEL node[attrs](follow[attrs], ...): display text

=cut
sub parse(@) {
    my @lane;
    my $all = {};
    my @prev = ();

    for (@_) {
        chomp;
        my ($indent) = s/^(\s*)//;
        my $level = length($indent) / 4;

        next if /^#/;
        next if $_ eq '';

        my ($src, $dst, $attrs, $text) = /^(\S+) \s* -> \s* (\S+?) (?:\[ (.*) \])? (?:\:(.*))?$/x;
        if (defined($src) and defined($dst)) {
            $attrs = parse_attributes($attrs);
            $attrs->{.text} = $text;
            $all->{$src}->{.next}->{$dst} = $attrs;
            next;
        }

        my $dsts;
        ($src, $attrs, $dsts, $text) = /^(\S+) \s* (?:\[ (.*) \])? \s* (?:\( (.*?) \))? (?:\s* : \s* (.*) \s*)$/x;
        if (defined($src)) {
            $node = parse_attributes($attrs);
            $node->{.text} = $text;

            if (defined($dsts)) {
                # follow[attrs], ...
                my ($dst_1, $attrs_1);
                while (($dst_1, $attrs_1) = $dsts =~ /(\S+?) \s* (?:\[ (.*?) \])/g) {
                    $attrs_1 = parse_attributes($attrs_1);
                    $node->{.next}->{$dst_1} = $attrs_1;
                }
            }

            my $prev = $prev[$level];
            $prev->{.next}->{$src} = $node;
            $prev[$level] = $node;

            $all->{$src} = $node;
            push @lane, $node if $LEVEL == 0;
        }
    }
    return [@lane];
}

sub parse_attributes($) {
    my $text = shift;
    my $attrs = {};
    if (defined($text)) {
        for (split(',', $text)) {
            s/^\s+//;
            s/\s+$//;
            my ($key, $val) = /^\s*(\S+)\s*=\s*(.*)\s*$/;
            $attrs->{$key} = $val;
        }
    }
    return $attrs;
}

=head1 DIAGNOSTICS

(No Information)

=cut
# (HELPER FUNCTIONS)

=head1 HISTORY

=over

=item 0.x

The initial version.

=back

=head1 SEE ALSO

The L<cmt/"Perl_simple_module_template">

=head1 AUTHOR

Lenik (谢继雷) <>

=cut
1
