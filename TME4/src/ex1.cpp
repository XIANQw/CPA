#include "graph.h"
#include <algorithm>

int get_min_node(const vector<int> & degree){
    int min_node = 0;
    int min_degree = degree[0];
    for(int i=0; i<degree.size(); i++){
        if(degree[i] < min_degree){
            min_degree = degree[i];
            min_node = i;
        }
    }
    return min_node;
}

bool is_connect(const Graph & g, ulong u, ulong v){
    for(int i=0; i<g.adjarray[u].size(); i++){
        if(g.adjarray[u][i] == v){
            return true;
        }
    }
    return false;
}


int get_size_densest_prefix(const Graph & g, const vector<int> &order){
    vector<double> densities(g.n_nodes);
    vector<double> edges_density(g.n_nodes);
    // sub-graph has just 1 node (first node)
    int nbnode = 1, nblien = 0;
    densities[0] = (double)nblien/nbnode;
    edges_density[0] = 0;
    printf("densest_prefix, n_node=%ld\n", g.n_nodes);
    for(nbnode=2; nbnode <= g.n_nodes; nbnode++){
        int node1 = order[nbnode-1];
        for(int i=0; i< nbnode-1; i++){
            int node2 = order[i];
            if(is_connect(g, node1, node2)) nblien++;
        }
        double density = (double)nblien/nbnode;
        densities[nbnode-1] = density;
    }
    printf("tab density finish\n");
    
    double max_density = 0;
    int max_size = 1;
    for(int i=0; i<g.n_nodes; i++){
        if(densities[i] > max_density){
            max_density = densities[i];
            max_size = i+1;
        }
    }
    printf("max_density=%f\n", max_density);
    return max_size;
}

// #define DEBUG
void core_decomposition(Graph & g){
    int n_node = g.n_nodes, c = 0;
    vector<int> degree_copy(n_node);
    for(int i=0; i<n_node; i++){
        degree_copy[i] = g.degree[i];
    }
    vector<int> core(n_node);
    vector<int> order(n_node);
    while(n_node > 0){
        // find min node v
        int index_v = get_min_node(degree_copy);
        
        if(degree_copy[index_v] > c){
            c = degree_copy[index_v];
            #ifdef DEBUG
                printf("n_node=%d c=%d degree_copy[%d]=%d\n", n_node, c, index_v, degree_copy[index_v]);
            #endif
        }
        core[index_v] = c;

        // Delete node v from le graph, every its neigb's degree--
        for(int i=0; i<g.adjarray[index_v].size();i++){
            int index_u = g.adjarray[index_v][i];
            degree_copy[index_u]--;
        }
        degree_copy[index_v] = INT32_MAX;
        // Record the order of delete, the last one is the highest core node 
        order[n_node - 1] = index_v;
        n_node--;   
    }
    printf("max_core=%d\n", c);
    int size_densest_prefix = get_size_densest_prefix(g, order);
    printf("size_densest_prefix=%d\n", size_densest_prefix);
}




