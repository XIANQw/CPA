package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class DefaultTeam {

	public void matrixAdj(ArrayList<Point> points, int edgeThreshold, int[][] paths, double[][] dis) {
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
	}

	public int[][] calculShortestPaths(ArrayList<Point> points, int edgeThreshold) {
		int[][] paths = new int[points.size()][points.size()];
		double[][] dis = new double[points.size()][points.size()];
		matrixAdj(points, edgeThreshold, paths, dis);
		// 1664 1337 OxBADC0DE
		for (int k = 0; k < points.size(); k++) {
			for (int i = 0; i < points.size(); i++) {
				for (int j = 0; j < points.size(); j++) {
					if (dis[i][k] + dis[k][j] < dis[i][j]) {
						dis[i][j] = dis[i][k] + dis[k][j];
						paths[i][j] = paths[i][k];
					}
				}
			}
		}
		return paths;
	}

	class Edge {
		public Point u, v;

		public Edge(Point u, Point v) {
			this.u = u;
			this.v = v;
		}
	}

	class UFset {
		public HashMap<Point, Integer> pointId;
		public int[] root;
		public int count;

		public UFset(ArrayList<Point> points) {
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

	public ArrayList<Edge> kruskal(ArrayList<Point> points, UFset ufs, HashMap<Point, Integer> idents, double[][] dis,
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

	public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
		int[][] paths = new int[points.size()][points.size()];
		double[][] dis = new double[points.size()][points.size()];
		HashMap<Point, Integer> idents = new HashMap<Point, Integer>();
		matrixAdj(points, edgeThreshold, paths, dis);
		// le poids d'arete entre u v est la longueur du plus court chemin
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
		for (int i = 0; i < points.size(); i++) {
			idents.put(points.get(i), i);
		}
		System.out.printf("1st kruskal, hitPoint=%d\n", hitPoints.size());
		UFset ufs = new UFset(points);
		ArrayList<Edge> spanning = kruskal(hitPoints, ufs, idents, dis, paths);
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
		ufs = new UFset(allPoint);
		spanning = kruskal(allPoint, ufs, idents, dis, paths);
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
}
