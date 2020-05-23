package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class UFSet {

    public HashMap<Point, Integer> pointId;
    public int[] root;
    public int count;

    public UFSet(ArrayList<Point> points) {
        pointId = new HashMap<Point, Integer>();
        root = new int[points.size()];
        count = points.size();
        for (int i = 0; i < points.size(); i++) {
            pointId.put(points.get(i), i);
            root[i] = i;
        }
    }

    public int find(Point p) {
        int id = this.pointId.get(p);
        int rootP = root[id];
        while (rootP != id) {
            id = rootP;
            rootP = root[id];
        }
        while (root[id] != id) {
            int father = root[id];
            root[id] = rootP;
            id = father;
        }
        return rootP;
    }

    public void union(Point p, Point q) {
        int rootp = find(p), rootq = find(q);
        if (rootp == rootq)
            return;
        this.root[rootp] = rootq;
    }

}