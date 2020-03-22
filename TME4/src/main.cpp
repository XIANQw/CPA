#include "graph.h"
#include "ex1.h"

char target[] = "./data/email-Eu-core.txt";

int main(){
    clock_t start, end;
    start = clock();
    ifstream in(target);
    Graph g(in);
    printf("g.n=%ld, g.m=%ld\n", g.n_nodes, g.n_edges);
    end = clock();
    printf("graph created, use %f ms\n", float(end - start) / CLOCKS_PER_SEC * 1000);
    start = clock();
    core_decomposition(g);
    end = clock();
    printf("core calcul finish, use %f ms\n", float(end - start) / CLOCKS_PER_SEC * 1000);
}