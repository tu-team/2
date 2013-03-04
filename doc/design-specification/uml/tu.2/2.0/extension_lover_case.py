#! /usr/bin/python

import os

endingUpper = ".PNG"
endingLover = ".png"
files = os.listdir(".")

for f in files:
    print str(f)
    if f.endswith(endingUpper):
        os.rename(f, f.replace(endingUpper, endingLover))

print files

