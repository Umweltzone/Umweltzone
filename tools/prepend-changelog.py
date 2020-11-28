#!/usr/bin/python3


# Prepends a range of formatted commits to the CHANGELOG.md to ease composing a CHANGELOG.
# Usage: Execute from the root folder of the repository.


import git, os
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("commit_hash")
args = parser.parse_args()

if args.commit_hash == "":
    print("Please enter a commit hash.")
    exit(1)


from_commit = args.commit_hash
repo_path = os.getcwd()
file_path = f"{repo_path}/CHANGELOG.md"

repo = git.Repo(repo_path)
commits = list(repo.iter_commits(rev=f"{from_commit}..HEAD"))

print(f"Found {len(commits)} commits from {from_commit} till HEAD.\n")


with open(file_path, "r") as original:
    data = original.read()

with open(file_path, "w") as modified:

    for c in commits:
        if c.summary.startswith("Merge pull request") or c.summary.startswith("Merge branch"):
            continue
        line = f"* {c.summary}, [{c.hexsha[0:5]}](https://bitbucket.org/tbsprs/umweltzone/commits/{c.hexsha})"
        #print(line)
        modified.write(f"{line}\n")

    modified.write("\n\n" + data)

print(f"Written to {file_path}.")
