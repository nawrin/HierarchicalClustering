import java.util.ArrayList;


public class Cluster {
	private ArrayList<Point> cluster;
	private int index1;
	private int index2;
	
	//ArrayList<Point> cluster2;
	
	public Cluster(ArrayList<Point> c1, int i, int j) {
		this.cluster = c1;
		this.index1 = i;
		this.index2 = j;
	}
	
	public ArrayList<Point> getCluster() {
		return cluster;
	}
	
	public int getIndex1() {
		return index1;
	}
	
	public int getIndex2() {
		return index2;
	}
}
