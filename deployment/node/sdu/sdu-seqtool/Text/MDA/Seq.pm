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
our @EXPORT = qw(seq_parse
                 seq_dump
                 seq_dump_viz
                 );

our $NODEL = {
    '@' => { shape => 'doublecircle' },
    '|' => { style => 'dashed' },
    '?' => { '.branch' => 1, shape => 'diamond' },
    '*' => { shape => 'note', style => 'filled', fillcolor => '#ffff88' },
    };
our $EDGEL = {
    ':' => { style => 'dashed' },
    ')' => { style => 'arc' },
    };

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
=head2 seq_parse([LINES])
Parse the source lines and return the lane array.

Each line is in format of:

    Commenct:
        # comment text

    Explicit Link:
        a -> b[attrs]: display text

    Seq Node:
        LEVEL node[attrs](follow[attrs], ...): display text

=cut
sub seq_parse(@) {
    my @lane;
    my $all = {};
    my @prev = ();
    my $meta = {
        # concentrate => 'true',
        labeljust   => 'c',
        labelloc    => 't',
        rankdir     => 'TD',
        splines     => 'polyline',
    };

    for (@_) {
        chomp;
        my $indent = $1 if s/^(\s*)//;
        my $level = int(length($indent) / 4);

        next if $_ eq '';

        if (/^\#/) {
            s/^\#\s*//;
            $meta->{$1} = $2 if /^@(\w+)\s+(.*)$/;
            next;
        }

        _log2 "${indent}[$level] $_";

        # Explicit Link
        my ($src, $dst, $attrs, $text) = /^
                (\S+) \s* -> \s* (\S+?)
                (?:\s* \[ (.*) \])?
                (?:\s* : \s* (.*))? $/x;
        if (defined($src) and defined($dst)) {
            _log2 "Explicit link: $src -> $dst [$attrs]: $text";
            $attrs = parse_attributes($attrs);
            $attrs->{'.text'} = $text;
            $all->{$src}->{'.next'}->{$dst} = $attrs;
            next;
        }

        # Seq Node:
        my $dsts;
        ($src, $attrs, $dsts, $text) = /^
                (\S+?)
                (?:\s* \[ (.*?) \])?
                (?:\s* \( (.*?) \))?
                (?:\s* : \s* (.*) \s*)? $/x;
        die "Illegal seq-node-def: $_" unless defined $src;

        _log2 "Seq Node: $src [$attrs] ($dsts): $text";
        my $node = parse_attributes($attrs);
        $node->{'.text'} = $text;

        $node->{'.level'} = $level;
        $node->{'.lane'} = $prev[0] if $level > 0;

        $src = magic_expand($src, $NODEL, $node);

        if (defined($dsts)) {
            # label=follow[attrs], ...
            my ($text_1, $dst_1, $attrs_1);

            while ($dsts =~ /,?
                    (?: \s* (\S.*?) \s* =)?
                    \s* ([^,]+)
                    (?: \s* \[ (.*?) \])? /gx) {
                ($text_1, $dst_1, $attrs_1) = ($1, $2, $3);
                _log2 "    Dst: $dst_1 [$attrs_1]: $text_1";
                $attrs_1 = parse_attributes($attrs_1);
                $text_1 = magic_expand($text_1, $EDGEL, $attrs_1);
                $attrs_1->{'.text'} = $text_1;
                $node->{'.next'}->{$dst_1} = $attrs_1;
            }
        }

        my $parent = $prev[$level - 1] if $level > 0;
        my $parent_node = $all->{$parent} if defined $parent;
        my $is_branched = (defined $parent_node) && (defined $parent_node->{'.branch'});

        if (!$is_branched and $level > 0) {
            my $prev = undef;
            my $lookback = $level;
            while (not defined $prev) {
                last if $lookback < 0;
                $prev = $prev[$lookback--];
            }
            my $prev_node = $all->{$prev};
               $prev_node->{'.next'}->{$src}->{'.by_seq'} = 1 if defined $prev_node;
        }

        $prev[$level] = $src;
        for (splice(@prev, $level + 1)) {
            $all->{$_}->{'.term'} = 1;
        }
        $all->{$src} = $node;
        push @lane, $src if $level == 0;
    }
    return ($meta, [@lane], $all);
}

sub magic_expand {
    my ($str, $pref, $attrs) = @_;
    my $tab = join('', keys(%$pref));
    while ($str =~ s/(^[$tab])|([$tab]$)//x) {
        my $map = $pref->{$&};
        my ($k, $v);
        $attrs->{$k} = $v while ($k, $v) = each(%$map);
    }
    return $str;
}

sub parse_attributes($) {
    my $text = shift;
    my $attrs = {};
    if (defined($text)) {
        for (split(',', $text)) {
            s/^\s+//;
            s/\s+$//;
            my ($key, $val) = /^\s*(\S+)(?:\s*=\s*(.*))?\s*$/;

            $attrs->{$key} = $val;
        }
    }
    return $attrs;
}

sub format_attrs($) {
    my ($attrs, $ltext) = @_;
    my $s = '';
    my @keys = grep { $_ !~ /^\./ } keys(%$attrs);
    if (scalar(@keys)) {
        $s .= '[';
        my $first = 1;
        for (@keys) {
            $s .= ', ' unless $first;
            $s .= $_;
            my $val = $attrs->{$_};
            $s .= "=$val" if $val ne '';
            $first = 0;
        }
        $s .= ']';
    }
    return $s;
}

sub seq_dump($$;$) {
    my ($all, $id, $indent, $skip) = @_;
    my $node = $all->{$id};

    # node[attrs](dsts): text
    print "$indent$id";
    print format_attrs($node);

    my $next = $node->{'.next'};
    my @next_ids;
    @next_ids = sort keys %$next if defined $next;
    my $next1 = @next_ids;
    my $next1_attrs = $next->{$next1};

    my @dsts = @next_ids;
#    shift @dsts if scalar(keys %$next1_attrs) == 0;

    if (scalar(@dsts)) {
        print '(';
        my $first = 1;
        for (@dsts) {
            print ', ' unless $first;
            my $linkattrs = $next->{$_};
            my $text = $linkattrs->{'.text'};
            print "$text=" if defined $text;
            print $_;
            print format_attrs($linkattrs);
            $first = 0;
        }
        print ')';
    }

    my $text = $node->{'.text'};
    print ": $text" if defined $text;
    print "\n";
    $skip->{$id} = 1;

    for (@next_ids) {
        next if defined $skip->{$_};
        seq_dump($all, $_, $indent.' ', $skip);
    }
}

sub seq_dump_viz($$$;$) {
    my ($meta, $all, $lanes) = @_;
    $meta = { %$meta };

    my $doctitle = $meta->{'doctitle'} || "Activity Sequence";
    delete $meta->{'doctitle'};
    $meta->{'label'} = $doctitle;

    print "digraph seq2dot {\n";
    print format_attrs_viz($meta, '    ', "; \n    ", "; \n");

    print "    node [ shape = box ]; \n";

    print "\n    /* Nodes */\n\n";

    for my $lane (@$lanes) {
        my $lane_meta = $all->{$lane};
        my $lane_text = $lane_meta->{'.text'} || $lane;

        my @lane_nodes = grep { $all->{$_}->{'.lane'} eq $lane } keys(%$all);
        print "    subgraph cluster_$lane {\n";
        print "        label = \"$lane_text\"\n";
        for my $node_name (sort @lane_nodes) {
            my $node = $all->{$node_name};
            my $level = $node->{'.level'};
            next if $level == 0;
            print "        $node_name";
            print format_attrs_viz($node);
            print "; \n";
        }
        print "    }\n\n";
    }

    print "\n    /* Edges */\n\n";

    for my $src (sort keys %$all) {
        my $src_node = $all->{$src};
        next if $src_node->{'.level'} == 0;

        my $next = $src_node->{'.next'};
        for my $dst (sort keys %$next) {
            my $edge = $next->{$dst};
            my $text = $edge->{'.text'};
            print "    $src -> $dst";
            print format_attrs_viz($edge);
            print "; \n";
        }
    }

    print "\n";
    print "}\n";
}

sub format_attrs_viz($;$$$) {
    my $attrs = shift @_;
    my ($left, $sep, $right) = ('[', ', ', ']');
       ($left, $sep, $right) = @_ if (@_);

    my $s = '';
    while (my ($k, $v) = each(%$attrs)) {
        if ($k =~ /^\./) {
            if ($k eq '.text') {
                next if $v eq '';
                $k = 'label';
            } else {
                next;
            }
        }

        if ($s eq '') {
            $s = $left;
        } else {
            $s .= $sep;
        }

        $s .= $k;
        if ($v ne '') {
            # $v =~ s/([\\"])/\\\1/g if ($v !~ /^\w+$/);
            $v = '"'.$v.'"';
            $s .= "=$v";
        }
    }
    $s .= $right if $s ne '';
    return $s;
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
