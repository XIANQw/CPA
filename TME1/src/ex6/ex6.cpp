#include "ex6.h"
#include <algorithm>

using namespace std;

int get_max_degree(ifstream &  in){
    int max_degree = 0;
    int node, degree;
    int line = 1;
    string tmp1, tmp2; in >> tmp1 >> tmp2;
    while(in >> node >> degree){
        max_degree = max(degree, max_degree);
        line++;
    }
    printf("1. line=%d\n", line);
    in.clear();in.seekg(0, ios::beg);
    return max_degree;
}


void ex6(){
    printf("==========ex6 computing degree distribution==========\n");
    ifstream in("./node_degree");
    int max_degree = get_max_degree(in);
    vector<int> degree_nodes(max_degree+1);
    printf("max_degree=%d\n", max_degree);
    int node,  degree;
    string tmp1, tmp2;
    in >> tmp1 >> tmp2;
    int line = 1;
    while(in >> node >> degree){
        line++;
        degree_nodes[degree]++;
    }
    printf("2. line=%d\n", line);

    FILE * f = fopen("degree_nNodes.txt", "w");
    fprintf(f, "degree    nNodes\n");
    for(int i=1; i<=max_degree;i++){
        if(degree_nodes[i]==0) continue;
        fprintf(f, "%d      %d\n", i, degree_nodes[i]);
    }
    fclose(f);
}