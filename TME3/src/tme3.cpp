#include "graph.h"

struct Pagerank{

    float a;
    int boucle;
    vector<float> pr;
    Graph * g;

    Pagerank(float a, int boucle, Graph * g):a(a), boucle(boucle), g(g){
        pr.reserve(g->n_nodes);
        for(Edge &e: g->transition_matrix){
            pr[e.u - 1] = (float)1/g->n_nodes; 
            pr[e.v - 1] = (float)1/g->n_nodes;
        }
    }
    // P = (1 - a) * T * P + a*I
    void prodMatrixVec(){
        vector<float> newPr(g->n_nodes);
        for(Edge & e: g->transition_matrix){
            newPr[e.v - 1] += (1-a) * e.t * pr[e.u - 1];
        }
        for(float & t: newPr) t += a / g->n_nodes; 
        pr = newPr;
    }

    void normalize(){
        float s = 0;
        for(float t : pr) s += t; 
        s = (1 - s)/g->n_nodes;
        for(float & t : pr) t+= s;
    }

    void printPr(){
        for(ulong i=0; i<pr.capacity();i++) printf("Pr(%ld): %f\n", i+1, pr[i]);
        printf("\n");
    }

    void pagerank(){
        pair<ulong, float> res;
        for(int i=0; i<boucle; i++){
            prodMatrixVec();
            normalize();
            res = maxPage();
            printf("%d, maxpage: pr[%ld] = %f\n", i+1, res.first, res.second);
        }
    }

    pair<ulong, float> maxPage(){
        float max = 0;
        ulong index = 0;
        for (int i = 0; i < g->n_nodes; i++) {
			if (pr[i] > max) {
				max = pr[i];
				index = i + 1;
			}
		}
        return pair<ulong, float>(index, max);
    }

};


int main(){
    char target [] = "./data/alr21--dirLinks--enwiki-20071018.txt";
    char test []  = "./data/test";
    clock_t start, end;
    start = clock();
    float a = 0.15;
    int boucle = 20;
    ifstream in(target);
    Graph graph(in);
    Pagerank p(a, boucle, &graph);
    p.pagerank();
    end = clock();
    printf("Spent time: %f ms\n", float(end - start) / CLOCKS_PER_SEC * 1000);
}   