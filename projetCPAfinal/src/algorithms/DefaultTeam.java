package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class DefaultTeam {


    public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        /**
         * L'algo floyd remplit le tableau distance et paths, la complexité est O(n3).
         * dis[u][v] est la plus petite distance entre Point u et Point v
         * paths[u][v] est le point de successeur dans le plus court chemin de u à v.
         * idents stocke les identifiants de point. 
         */
        int[][] paths = new int[points.size()][points.size()];
        double[][] dis = new double[points.size()][points.size()];
        Floyd.floyd(paths, dis, points, edgeThreshold);

        HashMap<Point, Integer> idents = new HashMap<Point, Integer>();
        for (int i = 0; i < points.size(); i++) {
            idents.put(points.get(i), i);
        }
        System.out.printf("1st kruskal, hitPoint=%d\n", hitPoints.size());

        /**
         * L'algo kruskal rend l'arbre couvrant minimal de S (hitPoitns).
         */
        UFSet ufs = new UFSet(points);
        ArrayList<Edge> spanning = Kruskal.kruskal(hitPoints, ufs, idents, dis, paths);
        System.out.printf("spanning size=%d\n", spanning.size());

        /** 
         * Dès qu'on a obtenu l'arbre couvrant minimal de S,
         * on remplace les arêtes de l'arbre couvrant de S par le plus court chemin de G.
         * En plus ajouter les points qui sont sur le chemin dans la nouvelle liste de point H.
        */
        HashSet<Point> setPoint = new HashSet<Point>();
        for (Point p : hitPoints) {
            setPoint.add(p);
        }
        for (Edge e : spanning) {
            int u = idents.get(e.u);
            int v = idents.get(e.v);
            // ajouter tous les sommets entre u et v
            while (paths[u][v] != v) {
                int k = paths[u][v];
                if (!setPoint.contains(points.get(k))) {
                    setPoint.add(points.get(k));
                }
                u = k;
            }
        }

        /**
         * La deuxième fois de l'algo kruskal permet de construire l'arbre couvrant T' depuis H.
        */
        ArrayList<Point> allPoint = new ArrayList<Point>(setPoint);
        System.out.printf("2nd kruskal, points=%d\n", allPoint.size());
        ufs = new UFSet(allPoint);
        spanning = Kruskal.kruskal(allPoint, ufs, idents, dis, paths);
        System.out.printf("spanning.size=%d\n", spanning.size());
        Point racine;
        if(spanning.size()==0) racine=hitPoints.get(0);
        else racine= spanning.get(0).u;
        return edgesToTree(spanning, racine);
    }

    private Tree2D edgesToTree(ArrayList<Edge> edges, Point root) {
        ArrayList<Edge> remainder = new ArrayList<Edge>();
        ArrayList<Point> subTreeRoots = new ArrayList<Point>();
        for (Edge current : edges) {
            if (current.u.equals(root)) {
                subTreeRoots.add(current.v);
            } else {
                if (current.v.equals(root)) {
                    subTreeRoots.add(current.u);
                } else {
                    remainder.add(current);
                }
            }
        }

        ArrayList<Tree2D> subTrees = new ArrayList<Tree2D>();
        for (Point subTreeRoot : subTreeRoots) {
            subTrees.add(edgesToTree(new ArrayList<Edge>(remainder), subTreeRoot));
        }
        return new Tree2D(root, subTrees);
    }

    public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        /**
         * L'algo floyd remplit le tableau distance et paths, la complexité est O(n3).
         * dis[u][v] est la plus petite distance entre Point u et Point v
         * paths[u][v] est le point de successeur dans le plus court chemin de u à v.
         * idents stocke les identifiants de point. 
         */
        Point s=hitPoints.get(0);
        System.out.printf("SteinerBudget, s=(%.1f, %.1f)\n", s.getX(), s.getY());
        int[][] paths = new int[points.size()][points.size()];
        double[][] dis = new double[points.size()][points.size()];
        Floyd.floyd(paths, dis, points, edgeThreshold);

        HashMap<Point, Integer> idents = new HashMap<Point, Integer>();
        for (int i = 0; i < points.size(); i++) {
            idents.put(points.get(i), i);
        }
        System.out.printf("1st kruskal, hitPoint=%d\n", hitPoints.size());

        /**
         * L'algo kruskal rend l'arbre couvrant minimal de S (hitPoitns).
         */
        UFSet ufs = new UFSet(points);
        ArrayList<Edge> spanning = Kruskal.kruskal(hitPoints, ufs, idents, dis, paths);
        System.out.printf("spanning size=%d\n", spanning.size());

        /** 
         * Dès qu'on a obtenu l'arbre couvrant minimal de S,
         * on remplace les arêtes de l'arbre couvrant de S par le plus court chemin de G.
         * En plus ajouter les points qui sont sur le chemin dans la nouvelle liste de point H.
        */
        HashSet<Point> setPoint = new HashSet<Point>();
        for (Point p : hitPoints) {
            setPoint.add(p);
        }
        for (Edge e : spanning) {
            int u = idents.get(e.u);
            int v = idents.get(e.v);
            // ajouter tous les sommets entre u et v
            while (paths[u][v] != v) {
                int k = paths[u][v];
                if (!setPoint.contains(points.get(k))) {
                    setPoint.add(points.get(k));
                }
                u = k;
            }
        }

        /**
         * La deuxième fois de l'algo kruskal permet de construire l'arbre couvrant T' depuis H.
        */
        ArrayList<Point> allPoint = new ArrayList<Point>(setPoint);
        System.out.printf("2nd kruskal, points=%d\n", allPoint.size());
        ufs = new UFSet(allPoint);
        spanning = Kruskal.kruskal(allPoint, ufs, idents, dis, paths);
        System.out.printf("spanning.size=%d\n", spanning.size());
        return edgesToTree(spanning, spanning.get(0).u);
    }
}
