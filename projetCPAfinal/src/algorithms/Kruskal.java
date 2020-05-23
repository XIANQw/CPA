package algorithms;

import java.util.ArrayList;
import java.awt.Point;
import java.util.HashMap;
import java.util.Comparator;

public class Kruskal {
    public static ArrayList<Edge> kruskal(ArrayList<Point> points, UFSet ufs, HashMap<Point, Integer> idents, double[][] dis,
            int[][] paths) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (Point p : points) {
            for (Point q : points) {
                if (!p.equals(q)) {
                    Edge a = new Edge(p, q);
                    edges.add(a);
                }
            }
        }

        edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return (int) (dis[idents.get(e1.u)][idents.get(e1.v)] - dis[idents.get(e2.u)][idents.get(e2.v)]);
            }
        });

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