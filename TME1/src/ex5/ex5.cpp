#include "ex5.h"

void ex5(Graph & g){
    ulong quantity = 0;
    printf("===========ex5 compute quantity special==========\n");
    for(Edge & e : g.edges){
        quantity += g.degree[e.u] * g.degree[e.v];
    }
    printf("Quantity=%ld\n", quantity);
}