# URLs check

A set of scripts to make sure that external URLs are still reachable and up-to-date.
The scripts check the HTTP status code of each URL and update the URL if needed.

## Getting started

The scripts only run on Python 3.

``` bash
sudo apt-get install build-essential libcurl4-openssl-dev libssl-dev libffi-dev python3-minimal python3-dev
pip2 install -r requirements.txt
```

## How to use

1. `python3 url_extract.py`
2. `python3 url_validate.py`
3. `python3 url_replace.py`
4. Check the updated URLs into version control.
