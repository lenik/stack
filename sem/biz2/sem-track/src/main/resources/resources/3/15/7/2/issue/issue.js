function switchType() {
    var current = $("#issueType");
    var next = 'I';

    switch (current.value()) {
    case 'I':
        next = 'R';
        break;
    case 'R':
        next = 'T';
        break;
    case 'T':
        next = 'I';
        break;
    }

    current.value(next);
}
