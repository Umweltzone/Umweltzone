#!/usr/bin/python

import json


INPUT_FILE = "../Umweltzone/src/main/res/raw/zones_de.json"
URL_KEY = "urlUmweltPlaketteDe"
LINE_TERMINATOR = "\n"
OUTPUT_FILE = "urls.txt"


def extract_urls(current_urls, file_name, url_key):
    print("Extracting URLs from %s ..." % file_name)
    with open(file_name) as f:
        zones = json.loads(f.read())
        for zone in zones:
            url = zone[url_key]
            current_urls.append(url)


def write_file(file_name, iterable_content):
    print("Writing URLs to %s ..." % file_name)
    with open(file_name, "w") as f:
        for line in iterable_content:
            f.write("%s%s" % (line, LINE_TERMINATOR))


current_urls = []
extract_urls(current_urls, INPUT_FILE, URL_KEY)
print("Found %d URLs." % len(current_urls))

write_file(OUTPUT_FILE, current_urls)
