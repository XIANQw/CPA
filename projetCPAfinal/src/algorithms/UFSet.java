package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;


/**
 *  La structure de données Union-Find Set. Elle est utilisée pour 
 * vérifier si ces deux points sont dans la même partie.
 */

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
    /**
     * La méthode permet de trouver à quelle partie le Point p appartient.
     * En cherchant la racine de point, le chemin est compressé, cela permet de améliorer
     * la complexité est O(logn) au pire, O(1) en moyenne.
     * @param p: Le point dont on veut chercher la racine. 
     * 
     */

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

    /**
     * Union permet de fusioner deux partie de ces deux points 
     * la complexité est O(logn) au pire, O(1) en moyenne.
     * 
     * @param pq: Deux points qu'on veut fusioner
     */
    public void union(Point p, Point q) {
        int rootp = find(p), rootq = find(q);
        if (rootp == rootq)
            return;
        this.root[rootp] = rootq;
    }

}