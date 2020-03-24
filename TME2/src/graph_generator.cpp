#include <stdlib.h>
#include <stdio.h>

#define MAXN 100
#define CLUSN 4
#define P 9

#define CLUS(i) i/MAXN
void generateGraph(){
    FILE * f = fopen("./data/graph.txt", "w");
    if(!f){
        printf("error open file\n");
        exit(0);
    }
    for(int i=0; i<CLUSN*MAXN; i++){
        for(int j=0; j<CLUSN*MAXN; j++){
            int poss = rand()%10;
            if((CLUS(i) == CLUS(j)) && poss < P){
                fprintf(f,"%d %d\n", i, j);
            }
            else if((CLUS(i) != CLUS(j)) && (poss >= P)){
                fprintf(f,"%d %d\n", i, j);
            }        
        }
    }
    fclose(f);
}


int main(){
    generateGraph();
}