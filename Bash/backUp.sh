#!/bin/bash

owner=UNIVERSITY
pW=123
db=XE
dir="C:\Users\Software\Desktop\caseStudy\bash"
d=$(date +"%Y%m%d_%H%M%S")
fileName="backup_${d}.dmp"

expdp ${owner}/${pW}@${db} DUMPFILE=${fileName}  DIRECTORY=back_up;

if [ $? -eq 0 ]; then
	echo "Backup is made Successfully " >> "${dir}/backup.log"
else 
	echo " ERROR : The Backup Failed " >> "${dir}/backup.log"
fi