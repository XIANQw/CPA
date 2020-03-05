#include <cstdio>
#include <fstream>
#include <cstring>
#include <vector>
#include <iostream>
#include <queue>

using namespace std;

typedef unsigned int uint;
typedef unsigned long ulong;

const int DUMMY = -231;
uint q = 0, p = 0;

struct Edge{
    ulong u;
    ulong v;
    float t;
    Edge(ulong u, ulong v, float t):u(u), v(v), t(t){}
};

struct Graph{
    ulong n_nodes, n_edges;
    vector<Edge> transition_matrix;
    vector<int> outdegree;

    Graph(ifstream & in):n_nodes(0),n_edges(0){
        ulong u, v;
        while(in >> u >> v){
            n_edges++;
            n_nodes = max(n_nodes, u);
            n_nodes = max(n_nodes, v);
            
        }
        transition_matrix.reserve(n_edges);
        outdegree.reserve(n_nodes);
        in.clear(); in.seekg(0, ios::beg);
        while(in >> u >> v) outdegree[u-1]++;
        in.clear(); in.seekg(0, ios::beg);
        while(in >> u >> v) addEdge(u, v, (float)1/outdegree[u-1]);
    }

    void addEdge(ulong u, ulong v, float t){
        Edge e(u, v, t);
        transition_matrix.push_back(e);
    }

    void printEdges(){
        for (Edge & e:transition_matrix){
            printf("%ld -> %ld\n", e.u, e.v);
        }
    }
    void printOutdegree(){
        for(ulong i = 0; i < n_nodes; i++){
            if(outdegree[i]) printf("%ld:%d ", i + 1, outdegree[i]);
        }
        printf("\n");
    }

};







