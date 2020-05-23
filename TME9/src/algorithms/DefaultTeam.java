package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class DefaultTeam {

  class Edge {
    protected Point p, q;

    protected Edge(Point p, Point q) {
      this.p = p;
      this.q = q;
    }

    protected double distance() {
      return p.distance(q);
    }

  }

  class UFset{
    public HashMap<Point, Integer> pointId;
    public int[] root;
    public int count; 
    public UFset(ArrayList<Point>points){
      pointId = new HashMap<Point, Integer>();
      root = new int[points.size()];
      count = points.size();
      for(int i=0; i<points.size(); i++){
        pointId.put(points.get(i), i);
        root[i] = i;
      }
    }
    public int find(Point p){
      int id=this.pointId.get(p);
      int rootP = root[id];
      while(rootP != id){
        id = rootP;
        rootP=root[id];
      }
      while(root[id]!=id){
        int father=root[id];
        root[id]=rootP;
        id=father;
      }
      return rootP;
    }

    public void union(Point p, Point q){
      int rootp = find(p), rootq = find(q);
      if (rootp == rootq) return ;
      int idp = pointId.get(p), idq = pointId.get(q);
      this.root[rootp] = rootq;
    }
  }

  public Tree2D calculSteiner(ArrayList<Point> points) {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    for (int i = 0; i < points.size(); i++) {
      for (int j = i + 1; j < points.size(); j++) {
        if (points.get(i).equals(points.get(j)))
          continue;
        edges.add(new Edge(points.get(i), points.get(j)));
      }
    }

    edges.sort(new Comparator<Edge>() {
      @Override
      public int compare(Edge e1, Edge e2) {
        double d1 = e1.distance(), d2 = e2.distance(); 
        if(d1 < d2) return -1;
        else if (d1 > d2) return 1;
        else return 0;
      }
    });

    ArrayList<Edge> kruskal = new ArrayList<Edge>();
    UFset ufs = new UFset(points);
    for(Edge current : edges){
      int tagp = ufs.find(current.p), tagq = ufs.find(current.q);
      if(tagp != tagq) {
        kruskal.add(current);
        ufs.union(current.p, current.q);
      }
    }
    return edgesToTree(kruskal, kruskal.get(0).p);
  }

  private Tree2D edgesToTree(ArrayList<Edge> edges, Point root) {
    ArrayList<Edge> remainder = new ArrayList<Edge>();
    ArrayList<Point> subTreeRoots = new ArrayList<Point>();
    for (Edge current : edges) {
      if (current.p.equals(root)) {
        subTreeRoots.add(current.q);
      } else {
        if (current.q.equals(root)) {
          subTreeRoots.add(current.p);
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
