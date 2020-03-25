#include "ex2/ex2.h"
#include "ex4/ex4.h"
#include "ex5/ex5.h"
#include "ex6/ex6.h"

int main(int argv, char ** args){
    if(argv == 1){
        printf("input num of ex [2,4,5,6]\n");
        exit(0);
    }
    char target[] = "./cleaned/com-lj.ungraph.txt";    
    // char target[] = "./cleaned/com-amazon.txt";
    ifstream in(target);
    clock_t start;
    start = clock();
    Graph g(in);
    int num = atoi(args[1]);
    switch (num){
    case 2:
        ex2(g); 
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);
        break;
    case 4:
        ex4(g); 
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);
        break;
    case 5:
        ex5(g); 
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);
        break;
    case 6:
        ex6(in);
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);
        break;
    default:
        printf("num error\n");
        break;
    }
    in.close();
}