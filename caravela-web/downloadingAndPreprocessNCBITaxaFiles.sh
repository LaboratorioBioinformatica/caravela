#!/bin/sh
OUTPUT_PATH=$1
wget ftp://ftp.ncbi.nih.gov/pub/taxonomy/taxdmp.zip -O /tmp/taxdmp.zip
unzip /tmp/taxdmp.zip -d /tmp
grep "scientific name" /tmp/names.dmp | cut -d'|' -f 1,2 > $OUTPUT_PATH/ncbiScientificNames.txt
cut -d'|' -f 1,2,3 /tmp/nodes.dmp > $OUTPUT_PATH/ncbiNodes.txt

