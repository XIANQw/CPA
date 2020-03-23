#include "ex4.h"

void ex4(Graph & g){
    FILE * fichier = fopen("node_degree.txt","w");
    if(fichier!=NULL){
        printf("=========ex4 generate file node degree================\n");
        fprintf(fichier,"#node    degree\n");
        for(int i=0;i<g.degree.size();i++){
                fprintf(fichier, "%ld         %d\n",g.origin[i], g.degree[i]);
        }
    }
}
