#!/bin/bash

logFile="C:/Users/Software/Desktop/caseStudy/bash/disk.log"
threshold=70
d=$(date +"%Y/%m/%d %H-%M-%S")
usedSpace=$(df -h | awk 'NR==2 {print $6}' | sed 's/%//')

if [ "$usedSpace" -ge "$threshold" ]; then
    echo "Warning!! The Disk Space Exceeds $threshold% | Date: ${d}" >> "${logFile}"
else 
    echo "The Disk Space Is Safe | Date: ${d}" >> "${logFile}"
fi