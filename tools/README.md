# Prepend changelog

A script to first read a range of commit messages and then put them to the beginning of the changelog. There the commit can be sorted into groups manually.

## Getting started

The script only runs on Python 3.

``` bash
sudo apt-get install python3-git
pip3 install argparse
```

## How to use

``` bash
python3 tools/prepend-changelog.py 1a2b3c
```
