# URLs check

A set of scripts to make sure that external URLs are still reachable and up-to-date.
The scripts check the HTTP status code of each URL and update the URL if needed.

## Getting started

The scripts only run on Python 2.

``` bash
sudo apt-get install build-essential libcurl4-openssl-dev libssl-dev libffi-dev python2-minimal python2-dev
pip2 install -r requirements.txt
```

## How to use

1. `python url_extract.py`
2. `python url_validate.py`
3. `python url_replace.py`
4. Check the updated URLs into version control.
