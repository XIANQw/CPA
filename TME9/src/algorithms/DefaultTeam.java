package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class DefaultTeam {

  class Edge {
    protected Point p, q;

    protected Edge(Point p, Point q){
      this.p = p;
      this.q = q;
    }

    protected double distance() {
      return p.distance(q);
    }

  }

  class NameTag {
    private ArrayList<Point> points;
    private int[] tag;

    protected NameTag(ArrayList<Point> points) {
      this.points = new ArrayList<Point>(points);
      tag = new int[points.size()];
      for (int i = 0; i < points.size(); i++)
        tag[i] = i;
    }

    protected void reTag(int j, int k) {
      for (int i = 0; i < tag.length; i++)
        if (tag[i] == j)
          tag[i] = k;
    }

    protected int tag(Point p) {
      for (int i = 0; i < points.size(); i++)
        if (p.equals(points.get(i)))
          return tag[i];
      return 0xBADC0DE;
    }
  }

  public Tree2D calculSteiner(ArrayList<Point> points) {
    // KRUSKAL ALGORITHM, NOT OPTIMAL FOR STEINER!
    ArrayList<Edge> edges = new ArrayList<Edge>();
    for (int i=0; i<points.size(); i++) {
      for (int j=i+1; j<points.size(); j++) {
        if (points.get(i).equals(points.get(j))) continue;
        edges.add(new Edge(points.get(i), points.get(j)));
      }
    }

    edges.sort(new Comparator<Edge>(){
      @Override
      public int compare(Edge e1, Edge e2){
        return (int)(e1.distance()-e2.distance());
      }
    });

    ArrayList<Edge> kruskal = new ArrayList<Edge>();
    NameTag forest = new NameTag(points);
    for (Edge current : edges) {
      if (forest.tag(current.p) != forest.tag(current.q)) {
        kruskal.add(current);
        forest.reTag(forest.tag(current.p), forest.tag(current.q));
      }
    }
    return edgesToTree(kruskal, kruskal.get(0).p);
  }

  
  private Tree2D edgesToTree(ArrayList<Edge> edges, Point root) {
    ArrayList<Edge> remainder = new ArrayList<Edge>();
    ArrayList<Point> subTreeRoots = new ArrayList<Point>();
    for (Edge current: edges) {
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
