package algorithms;

import java.util.ArrayList;
import java.awt.Point;
import java.util.HashMap;
import java.util.Comparator;

public class Kruskal {
    public static ArrayList<Edge> kruskal(ArrayList<Point> points, UFSet ufs, HashMap<Point, Integer> idents, double[][] dis,
            int[][] paths) {
        /**
         *  Connecter les points, implémenter la liste des arêtes
         * Complexité O(n²) 
         */
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Point p : points) {
            for (Point q : points) {
                if (!p.equals(q)) {
                    Edge a = new Edge(p, q);
                    edges.add(a);
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
}