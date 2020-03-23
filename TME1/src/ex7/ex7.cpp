#include "ex7.h"

void ex7(Graph & g){
    printf("List edges:\n");
    printf("size=%ld\n",g.edges.size());
    printf("adjarray:\n");
    printf("size=%ld\n", g.adjarray.size());
    
    printf("matrix:\n");
    ulong matrix[g.n_nodes][g.n_nodes];
    for(int i=0; i<g.adjarray.size(); i++){
        for(int j=0; j<g.adjarray[i].size(); j++){
            matrix[i][j] = 1;
            matrix[j][i] = 1;
        }
    }
}