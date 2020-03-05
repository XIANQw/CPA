
#include <cstdio>
#include <fstream>
#include <cstring>
#include <vector>
#include <iostream>
#include <queue>

using namespace std;
const int MAXN = 1000000;
const int MAXM = 2000000;
const int DUMMY = -231;
int dis[MAXN];
int maxDis = 0, q = 0, p = 0;

struct Edge{
    long to;
    int value;
    Edge(){
        to = DUMMY;
        value = DUMMY;
    }
    Edge(long to, int value):to(to), value(value){}

};

struct Graph{
    long n = 1, m = 0, s;
    vector<Edge> edges;
    vector<long>nodes[MAXN];

    Graph(){
    }

    void addEdge(long u, long v){
        Edge e(v, 1);
        edges.push_back(e);
        nodes[u].push_back(m);
        m++;
    }

    long bfs(long start){
        long res = 0;
        queue<long>q;
        q.push(start);
        bool vis[MAXN];
        memset(dis, -1, MAXN);
        memset(vis, false, MAXN);
        dis[start] = 0;
        vis[start] = true;
        n = 1;
        while(!q.empty()){
            long cur = q.front(); q.pop();
            for(long i=0; i<nodes[cur].size(); i++){
                if(vis[edges[nodes[cur][i]].to]) continue;
                vis[edges[nodes[cur][i]].to] = true;
                int to = edges[nodes[cur][i]].to;
                q.push(to);
                dis[edges[nodes[cur][i]].to] = dis[cur] + 1;                
                n++;
                if(dis[edges[nodes[cur][i]].to] > maxDis){
                    res = edges[nodes[cur][i]].to;
                    maxDis = dis[edges[nodes[cur][i]].to];
                }
                // printf("%ld dis: %d -> %ld dis: %d\n", cur, dis[cur], edges[nodes[cur][i]].to, dis[edges[nodes[cur][i]].to]);
            }
        }
        return res;
    }

    void diameter(){
        q = bfs(edges[0].to);
        printf("maxDis : %d\n", maxDis);
        p = bfs(q);
    }

}g;


int main(){
    long u, v;
    std::ifstream in("data/data-amazon1.txt");
    while(in >> u >> v){
        g.addEdge(u, v);
        g.addEdge(v, u);
        // printf("%ld %ld\n", u, v);
    }
    // g.bfs(0);
    g.diameter();
    printf("n: %ld; m: %ld\n", g.n, g.m);
    printf("maxDis : %d\n", maxDis);
}   




