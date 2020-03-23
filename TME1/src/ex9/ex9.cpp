#include "ex9.h"


void multiply(int size, int ** A, int ** B, int ** C){
    for (int i = 0; i < size; ++i){
		for (int j = 0; j < size; ++j){
			C[i][j]=0;
			for (int k = 0; k < size; ++k){
				C[i][j]+=A[i][k]*B[k][j];
			}
		}
	}
}



void ex9(ifstream & in){
    printf("========ex9 computing triangles quantity========\n");
    int n = get_max_node(in)+1;
    printf("n=%d\ninit\n", n);
    int ** res = (int **)malloc(n* sizeof(int *)); 
    int ** ans = (int **)malloc(n*sizeof(int *));
    int ** matrix = (int **)malloc(n*sizeof(int *));
    
    for(int i=0; i<n;i++){
        matrix[i] = (int *)malloc(n*sizeof(int));
        res[i] = (int *)malloc(n*sizeof(int));
        ans[i] = (int *)malloc(n*sizeof(int));
        memset(matrix[i], 0, n);
        memset(res[i], 0, n);
        memset(ans[i], 0, n);
    }
    int u, v;
    while(in >> u >> v){
        matrix[u][v] = 1;
        matrix[v][u] = 1;
    }
    printf("init finish\n");
    printf("multiply 1\n");
    multiply(n, matrix, matrix, res);
    printf("multiply 2\n");
    multiply(n, matrix, res, ans);
	int sum = 0;
	for (int i = 0; i < n; ++i){
		sum+=ans[i][i];
	}
	int triangles = sum/6;
    printf("triangles=%d\n", triangles);
}


