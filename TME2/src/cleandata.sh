#!/bin/bash
#variable du fichier
for f in ./data/*.txt 
do
	echo "cleaning $f"
	FILE=$(echo $f| cut -d'/' -f 3)
	awk '{
			if ($1 > $2) {
				printf("%d ",$2); 
				printf("%d\n", $1);
			}
			if ($1 < $2)  {
				printf("%d ",$1); 
				printf("%d\n", $2);
			}
		}'   < "$f"  |sort -n| uniq > "./cleaned/$FILE"
done    
exit 0



