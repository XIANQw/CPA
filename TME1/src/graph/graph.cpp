#include "graph.h"
#include <vector>

using namespace std;
/*====== ex2 start ======*/
ulong get_max_node(ifstream & in){
    ulong u, v, max_node = 0;
    while(in >> u >> v){
        max_node = max(max_node, u);
        max_node = max(max_node, v);
    }
    in.clear(); in.seekg(0, ios::beg);
    return max_node;
}

ulong get_nb_nodes(ifstream & in, ulong max_node){
    vector<ulong> nodes(max_node + 1);
    ulong n = 0, u, v;
    while(in >> u >> v){
        nodes[u] = 1;
        nodes[v] = 1;
    }
    for(ulong i=0; i<max_node+1; i++){
        if(nodes[i] == 1) n++;
    }
    in.clear(); in.seekg(0, ios::beg);
    return n;
}

ulong get_nb_liens(ifstream & in){
    ulong u,  v, m = 0;
    while(in >> u >> v){
        m++;
    }
    in.clear(); in.seekg(0, ios::beg);
    return m;
}
/*====== ex2 end ======*/


vector<ulong> get_tab_newname(ifstream & in, ulong max_node){
    vector<ulong> tab(max_node+1);
    ulong u, v;
    while(in >> u >> v){
        tab[u] = 1;
        tab[v] = 1;
    }
    
    vector<ulong> new_name(tab.size());
    ulong newname = 0;
    for(int i=0; i<tab.size();i++){
        if(tab[i] == 1){
            new_name[i] = newname;
            newname++;
        }
    }
    in.clear(); in.seekg(0, ios::beg);
    return new_name;
}

vector<ulong> get_tab_origin(ifstream & in, ulong max_node, ulong nb_nodes){
    vector<ulong> tab(max_node+1);
    vector<ulong> origin_name(nb_nodes);
    ulong u,v;
    while(in >> u >> v){
        tab[u] = 1;
        tab[v] = 1;
    }

    ulong newname = 0;
    for(int i=0; i<max_node+1; i++){
        if(tab[i] == 1){
            origin_name[newname] = i;
            newname++;
        }
    }
    in.clear(); in.seekg(0, ios::beg);
    return origin_name;
}


void Graph::add_edge(ulong u, ulong v){
    if(u == v) return;
    edges.push_back(Edge(u, v));
    adjarray[u].push_back(v);
    adjarray[v].push_back(u);
    degree[u]++;
    degree[v]++;
}


Graph::Graph(ifstream & in){
    ulong max_node = get_max_node(in);
    n_edges = get_nb_liens(in);
    n_nodes = get_nb_nodes(in, max_node);
    new_name = get_tab_newname(in, max_node);
    origin = get_tab_origin(in, max_node, n_nodes);

    edges.reserve(n_edges);
    adjarray.resize(n_nodes);
    degree.resize(n_nodes);
    ulong u , v;
    while(in >> u >> v){
        add_edge(new_name[u], new_name[v]);
    }
    in.clear(); in.seekg(0, ios::beg);
}

void Graph::printEdges(){
    for(ulong u=0; u<adjarray.capacity();u++){
        for(ulong v=0; v<adjarray[u].size(); v++){
            printf("%ld(%ld) -> %ld(%ld)\n",u, origin[u], adjarray[u][v], origin[adjarray[u][v]]);
        }
    }
}

void Graph::printDegree(){
    for(int i = 0; i<degree.size(); i++){
        printf("degree[%d] = %d\n", i, degree[i]);
    }
}