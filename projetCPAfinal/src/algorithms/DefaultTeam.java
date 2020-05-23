package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class DefaultTeam {


    public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        int[][] paths = new int[points.size()][points.size()];
        double[][] dis = new double[points.size()][points.size()];
        Floyd.floyd(paths, dis, points, edgeThreshold);

        HashMap<Point, Integer> idents = new HashMap<Point, Integer>();
        for (int i = 0; i < points.size(); i++) {
            idents.put(points.get(i), i);
        }
        System.out.printf("1st kruskal, hitPoint=%d\n", hitPoints.size());
        UFSet ufs = new UFSet(points);
        ArrayList<Edge> spanning = Kruskal.kruskal(hitPoints, ufs, idents, dis, paths);
        System.out.printf("spanning size=%d\n", spanning.size());

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
        ArrayList<Point> allPoint = new ArrayList<Point>(setPoint);

        System.out.printf("2nd kruskal, points=%d\n", allPoint.size());
        ufs = new UFSet(allPoint);
        spanning = Kruskal.kruskal(allPoint, ufs, idents, dis, paths);
        System.out.printf("spanning.size=%d\n", spanning.size());
        return edgesToTree(spanning, spanning.get(0).u);
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
        // REMOVE >>>>>
        Tree2D leafX = new Tree2D(new Point(700, 400), new ArrayList<Tree2D>());
        Tree2D leafY = new Tree2D(new Point(700, 500), new ArrayList<Tree2D>());
        Tree2D leafZ = new Tree2D(new Point(800, 450), new ArrayList<Tree2D>());
        ArrayList<Tree2D> subTrees = new ArrayList<Tree2D>();
        subTrees.add(leafX);
        subTrees.add(leafY);
        subTrees.add(leafZ);
        Tree2D steinerTree = new Tree2D(new Point(750, 450), subTrees);
        System.out.printf("root(%f,%f)", steinerTree.getRoot().getX(), steinerTree.getRoot().getY());
        // <<<<< REMOVE

        return steinerTree;
    }
}
