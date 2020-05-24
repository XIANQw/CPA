package algorithms;

import java.util.ArrayList;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;

public class Kruskal {
    /**
     * Cette méthode produit un arbre couvrant minimal de points.
     * @param points: La liste des points composant une arbre couvrant minimal
     * @param ufs: Union-Find-Set, pour vérifier si deux points sont dans la même partie.
     * @param idents: Un hashmap gardant l'identifiant de point
     * @param dis: Un tableau à 2 dimensions qui stocke la longueur de plus court chemin entre deux points
     * @param paths: Un tableau à 2 dimensions qui garde le point de successeur dans le plus court chemin entre deux points.
     */
    public static ArrayList<Edge> kruskal(ArrayList<Point> points, UFSet ufs, HashMap<Point, Integer> idents, double[][] dis,
            int[][] paths) {
        /**
         *  Connecter les points, implémenter la liste des arêtes
         * Complexité O(n²) 
         */
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Point p : points) {
            for (Point q : points) {
                if (!p.equals(q) && (int)(dis[idents.get(p)][idents.get(q)]) != 99999999 ) {
                    edges.add(new Edge(p, q));
                }
            }
        }
        /**
         *  Trier la liste des arêtes en ordre de la distance croissante. 
         *  La complexité est O(nlogn)
         */
        edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return (int) (dis[idents.get(e1.u)][idents.get(e1.v)] - dis[idents.get(e2.u)][idents.get(e2.v)]);
            }
        });

        /**
         * Ajouter les arrêtes dans l'arbre couvrant, si l'arête ne produit pas de cycle.
         * Ici, j'utilise union-find-set pour vérifier si l'arête produit la cycle.
         * On sait que si deux points sont dans la même partie, alors le lien qui est entre ces deux points
         * va produire un cycle.
         * La complexité O(n²), parce qu'il y a n² arêtes, la méthode find est O(1) en moyenne.
         */
        ArrayList<Edge> span = new ArrayList<Edge>();
        for (Edge current : edges) {
            int tagp = ufs.find(current.u), tagq = ufs.find(current.v);
            if (tagp != tagq) {
                span.add(current);
                ufs.union(current.u, current.v);
            }
        }
        return span;
    }
    /**
     * deleteEdge supprimer les arêtes par l'algo glouton.
     * On supprime les arêtes en ordre de longueur décroissant. 
     * C'est pour obtenir plus de points possible et garde une longueur plus petite à la fois.
     * 
     * @param s: Le point de maison-mère
     * @param B: Le budget de longeur
     * @param spanningTree: La liste des arêtes de l'arbre couvrant
     * @param idents: L'identifiant de Point
     * @param dis: dis[u][v] est la plus petite distance entre Point u et Point v
     * 
     */
    public static ArrayList<Edge> deleteEdge(Point s, ArrayList<Edge> spanningTree, int B, HashMap<Point, Integer> idents, double[][] dis){
        double length=0;
        HashMap<Point, Integer> count = new HashMap<Point, Integer>();
        /**
         * count est un compteur qui stocke le degré d'un point. 
         * Si le degré de p == 1, cela veut dire que le point est l'extrêmité d'une seul arête.
         * Si > 1, le point est l'extrếmité de plusieurs arêtes.
         * Si 0, le point n'existe plus dans l'arbre.
         */
        for(Edge e:spanningTree){
            length += dis[idents.get(e.u)][idents.get(e.v)];
            if(count.containsKey(e.u)) count.put(e.u, count.get(e.u) + 1);
            else count.put(e.u, 1);
            if(count.containsKey(e.v)) count.put(e.v, count.get(e.v) + 1);
            else count.put(e.v, 1);
        }
        ArrayList<Edge> res = new ArrayList<Edge>();
        HashSet<Edge> deleted = new HashSet<Edge>();

        /**
         * On cherche la plus longue arête d'extrếmité de l'arbre et la supprime depuis l'arbre.
         * Cela permet de diminuer la longueur total de l'arbre mais ne perd pas trop de points.
         */
        while(length > B){
            Edge toDel = null; double lenToDel=0;
            for(Edge e: spanningTree){
                // Si point s est l'extrêmité de l'arbre, on ne peut pas supprimer cette arête.
                if((e.u.equals(s) || e.v.equals(s)) && count.get(s) == 1){
                    continue;
                }
                
                // Si l'arête a déjà été supprimer, on continue.
                if(deleted.contains(e)) continue;

                // Si l'arête est dans l'intérieux de l'arbre, on ne peut pas la supprimer non plus.
                if(count.get(e.u) > 1 && count.get(e.v) > 1){
                    continue;
                }

                // On supprimer la plus longue arête d'extrêmité de l'arbre.
                double lenEdge = dis[idents.get(e.u)][idents.get(e.v)];
                if(toDel == null || lenEdge > lenToDel){
                    lenToDel = lenEdge;
                    toDel = e;
                }
            }
            if(toDel == null) break;
            length -= lenToDel;
            deleted.add(toDel);
            count.put(toDel.u, count.get(toDel.u) - 1);
            count.put(toDel.v, count.get(toDel.v) - 1);
            if(count.get(toDel.u) == 0) count.remove(toDel.u);
            if(count.get(toDel.v) == 0) count.remove(toDel.v);
        }
        System.out.printf("length=%.1f\n", length);
        for(Edge e:spanningTree){
            if(deleted.contains(e)) continue;
            res.add(e);
        }

        return res;
    }
}
