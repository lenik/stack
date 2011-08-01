#include <stdio.h>
#include <string.h>

#define _XOPEN_SOURCE
#include <unistd.h>
char *crypt(const char *key, const char *salt);

const char *DEFAULT_SALT = "hi";

int main(int argc, char **argv) {

    char *key;
    char *salt = strdup(DEFAULT_SALT);

    if (argc <= 1) {
        fprintf(stderr, "You didn't specify the password. ");
        return 1;
    }

    key = strdup(argv[1]);

    char *dat = crypt(key, salt);
    printf("%s\n", dat);

    return 0;
}
