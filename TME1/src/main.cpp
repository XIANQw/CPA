#include "ex2/ex2.h"
#include "ex4/ex4.h"
#include "ex5/ex5.h"
#include "ex6/ex6.h"
#include "ex7/ex7.h"
#include "ex8/ex8.h"
#include "ex9/ex9.h"

int main(int argv, char ** args){
    if(argv == 1){
        printf("input num of ex [2,4,5,6,7,8,9]\n");
        exit(0);
    }
    char target[] = "./cleaned/email-Eu-core.txt";
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
        ex6();
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);
        break;
    case 7:
        ex7(g);
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);
        break;    
    case 8:
        ex8(g); 
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);        
        break;  
    case 9:
        ex9(in); 
        printf("execute time=%f\n", float(clock() - start)/CLOCKS_PER_SEC * 1000);        
        break;    
    default:
        printf("num error\n");
        break;
    }
    in.close();
}