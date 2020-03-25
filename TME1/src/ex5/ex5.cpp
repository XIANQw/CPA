#include "ex5.h"
#include <algorithm>

using namespace std;

pair<ulong, int> bfs(Graph & g, ulong start){
    int max_dis = 0; ulong node_far;
    queue<ulong>q;
    q.push(start);
    bool vis[g.n_nodes];
    int dis[g.n_nodes];
    memset(dis, -1, g.n_nodes);
    memset(vis, false, g.n_nodes);
    dis[start] = 0;
    vis[start] = true;
    while(!q.empty()){
        ulong cur = q.front(); q.pop();
        for(ulong i=0; i<g.adjarray[cur].size(); i++){
            ulong next = g.adjarray[cur][i];
            if(vis[next]) continue;
            dis[next] = dis[cur] + 1;
            vis[next] = true;
            q.push(next);
            if(dis[next] > max_dis){
                max_dis = dis[next];
                node_far = next;
            }
        }
    }
    return pair<ulong, int>(node_far, max_dis);
}

int diameter(Graph & g){
    ulong node_far = bfs(g, 0).first;
    return bfs(g, node_far).second;
}

void ex5(Graph & g){
    printf("=========ex5 computing diameter via bfs===========\n");
    int res = diameter(g);
    printf("diameter=%d\n", res);
}