#!/usr/bin/python


INPUT_FILE = "valid_urls.csv"
CSV_DELIMITER = ","
LINE_TERMINATOR = "\n"
FILE_TO_MODIFY = "../Umweltzone/src/main/res/raw/zones_de.json"


def read_urls(url_triples, file_name):
    print("Reading URLs from %s ..." % file_name)
    with open(file_name, "r") as f:
        lines = f.readlines()
        for line in lines:
            line = line.rstrip(LINE_TERMINATOR)
            url_triples.append(line.split(CSV_DELIMITER))


def replace(file_name, url_triples):
    print("Replacing URLs in %s ..." % file_name)
    with open(file_name, 'r+') as f:
        content = f.read()
        print(type(content))
        for triple in url_triples:
            old_str = triple[1]
            new_str = triple[2]
            print("REPLACE: %s %s" % (old_str, new_str))
            f.seek(0)
            content = content.replace(old_str, new_str)
        f.write(content)


url_triples = []
read_urls(url_triples, INPUT_FILE)
print("URL triples count = %d" % len(url_triples))
# print("First triple = %s, %s, %s" % (url_triples[0][0], url_triples[0][1], url_triples[0][2]))

if len(url_triples) > 0:
    replace(FILE_TO_MODIFY, url_triples)
else:
    print("No URLs to replace.")
