#include <stdlib.h>
#include <stdio.h>
#include <vector>

#define MAXN 100
#define CLUSN 4
#define P 7

using namespace std;

typedef struct{
    int tab[MAXN];
    int size;    
}cluster;

cluster clusters[CLUSN];

void displayClus(){
    for(int i=0; i<CLUSN; i++){
        for(int j=0; j < MAXN; j++){
            printf("%d\n",clusters[i].tab[j]);
        }
    }
}

void addNode(int id){
    int nclus = rand() % CLUSN;
    int times = 0;
    while (clusters[nclus].size == MAXN){
        nclus = (nclus + 1) % CLUSN;
        times++;
        if(times == CLUSN) return ;
    }
    clusters[nclus].tab[clusters[nclus].size] = id;
    clusters[nclus].size++;    
}

struct Graph{
        long n = 1, m = 0, s;
        vector<int>neig[MAXN*CLUSN];

        Graph(){
        }

        void addEdge(int u, int v){
            neig[u].push_back(v);
            neig[v].push_back(u);
        }

        void display(){
            for(int i = 0; i < MAXN * CLUSN; i++){
                for(int j = 0; j < neig[i].size(); j++){
                    printf("%d %d\n", i, neig[i][j]);
                }
            }

        }
}g;

void generateGraph(){
    for(int i=1; i <= MAXN * CLUSN; i++){
        addNode(i);
    }
    for(int i=0; i<CLUSN; i++){
        for(int j=0; j<MAXN; j++){
            int poss = rand()%10;
            if(poss < P){
                g.addEdge(clusters[i].tab[j], clusters[i].tab[rand()%MAXN]);
            }else{
                int orther = rand()%CLUSN;
                while(orther == i) orther = rand()%CLUSN;
                g.addEdge(clusters[i].tab[j], clusters[orther].tab[rand()%MAXN]);
            }
        }
    }
}



int main(){
    generateGraph();
    displayClus();
    g.display();
}