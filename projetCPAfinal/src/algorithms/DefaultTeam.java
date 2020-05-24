package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class DefaultTeam {

    /**
     * Cette méthode permet de rendre un arbre steiner qui passe tous les points de S(hitPoints),
     * le nombre de points sont fixés et calculer un arbre couvrant dont longueur est minimale.
     * @param points: L'ensemble totale de tous les points G.
     * @param edgeThreshold: Le seuil décidant s'il y a un lien entre deux points. 
     * @param hitPoints: Un sous ensemble de G, l'arbre steiner passe tous les points de cette liste.
     */
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
        for (Edge e : spanning) {
            int u = idents.get(e.u);
            int v = idents.get(e.v);
            if(!setPoint.contains(e.u)) setPoint.add(e.u);
            if(!setPoint.contains(e.v)) setPoint.add(e.v);
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

    /**
     * Cette méthode permet de rendre un arbre steiner dont la longueur est inférieure au budget, et
     * passe plus de points de S possible.
     * @param points: L'ensemble totale de tous les points G.
     * @param edgeThreshold: Le seuil décidant s'il y a un lien entre deux points. 
     * @param hitPoints: Un sous ensemble de G (S), l'arbre steiner passe tous les points de cette liste.
     */
    public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        /**
         * B est le budget de longueur, hitsPoint est S.
         */
        int B=1667;
        Point s=hitPoints.get(0);
        System.out.printf("SteinerBudget, s=(%.1f, %.1f)\n", s.getX(), s.getY());
        
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
        
        /**
         * Chercher les points possibles, si la distance de s au point P est supérieur au budget, 
         * il n'y a pas de possibilité d'arriver ce point.
         */
        ArrayList<Point> possible = new ArrayList<Point>();
        for(Point p:hitPoints){
            if(dis[idents.get(s)][idents.get(p)] < B) possible.add(p);
        }
        
        System.out.printf("1st kruskal, possible=%d\n", possible.size());
        /**
         * L'algo kruskal rend l'arbre couvrant minimal de S (hitPoitns).
         */
        UFSet ufs = new UFSet(points);
        ArrayList<Edge> spanning = Kruskal.kruskal(possible, ufs, idents, dis, paths);
        System.out.printf("spanning size=%d\n", spanning.size());
       
        /**
         * deleteEdge supprimer les arêtes par l'algo glouton.
         * On supprime les arêtes en ordre de longueur décroissant. 
         * C'est pour obtenir plus de points possible et garde une longueur plus petite à la fois.
         */
        spanning = Kruskal.deleteEdge(s, spanning, B, idents, dis);
        System.out.printf("spanning size=%d\n", spanning.size());

        /** 
         * Dès qu'on a obtenu l'arbre couvrant minimal de S après la suppression.
         * on remplace les arêtes de l'arbre couvrant de S par le plus court chemin de G.
         * En plus ajouter les points qui sont sur le chemin dans la nouvelle liste de point H.
        */
        HashSet<Point> setPoint = new HashSet<Point>();
        for (Edge e : spanning) {
            int u = idents.get(e.u);
            int v = idents.get(e.v);
            if(!setPoint.contains(e.u)) setPoint.add(e.u);
            if(!setPoint.contains(e.v)) setPoint.add(e.v);
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
        if(spanning.size() == 0) racine=s;
        else racine = spanning.get(0).u;
        return edgesToTree(spanning, racine);
    }
}
