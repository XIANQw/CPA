package algorithms;

import java.util.ArrayList;
import java.awt.Point;

public class Floyd {

    /**
     * L'algo floyd remplit le tableau distance et paths, la complexité est O(n3).
     * dis[u][v] est la plus petite distance entre Point u et Point v
     * paths[u][v] est le point de successeur dans le plus court chemin de u à v.
     * idents stocke les identifiants de point. 
     */
    public static void floyd(int[][] paths, double[][] dis, ArrayList<Point> points, int edgeThreshold) {
        for (int i = 0; i < paths.length; i++)
            for (int j = 0; j < paths.length; j++)
                paths[i][j] = j;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double d = points.get(i).distance(points.get(j));
                if (d < edgeThreshold) {
                    dis[i][j] = d;
                    dis[j][i] = d;
                } else {
                    dis[i][j] = 99999999;
                    dis[j][i] = 99999999;
                }
            }
        }
     
        /**
         * le poids d'arete entre u v est la longueur du plus court chemin
         * La complexité est O(n3).
         */ 
        for (int k = 0; k < points.size(); k++) {
            for (int u = 0; u < points.size(); u++) {
                for (int v = 0; v < points.size(); v++) {
                    if (dis[u][k] + dis[k][v] < dis[u][v]) {
                        dis[u][v] = dis[u][k] + dis[k][v];
                        paths[u][v] = paths[u][k];
                    }
                }
            }
        }
    }
}
