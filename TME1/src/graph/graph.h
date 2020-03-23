#ifndef H_GRAPH
#define H_GRAPH


#include <cstdio>
#include <fstream>
#include <cstring>
#include <vector>
#include <iostream>
#include <queue>

using namespace std;

typedef unsigned int uint;
typedef unsigned long ulong;

struct Edge{
    ulong u;
    ulong v;
    Edge(ulong u, ulong v):u(u), v(v){}
};

struct Graph{
    ulong n_nodes, n_edges;
    vector<Edge> edges;
    vector<vector<ulong>> adjarray;
    vector<int> degree;
    vector<ulong> new_name;
    vector<ulong> origin;

    void add_edge(ulong u, ulong v);

    Graph(ifstream & in);

    void printEdges();

    void printDegree();

};

ulong get_max_node(ifstream & in);
ulong get_nb_nodes(ifstream & in, ulong max_node);
ulong get_nb_liens(ifstream & in);
vector<ulong> get_tab_newname(ifstream & in, ulong max_node);
vector<ulong> get_tab_origin(ifstream & in, ulong max_node, ulong nb_nodes);



#endif



