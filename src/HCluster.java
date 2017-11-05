import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;



public class HCluster {
	public static void main(String[] args) throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner scanFile = new Scanner(new File("/home/nawrin/B.txt"));
		ArrayList<Point> points = new ArrayList<Point>();
		
		while(scanFile.hasNext()) {
			String coord = scanFile.nextLine();
			String[] x_y = coord.split(" ");
			Point point = new Point(Double.parseDouble(x_y[0]), Double.parseDouble(x_y[1]));
			points.add(point);
		}
		System.out.println("points" + points.size());
		
		ArrayList<Cluster> currentCluster = new ArrayList<Cluster>();
		for(Point p : points) {
			ArrayList<Point> elem = new ArrayList<Point>();
			elem.add(p);
			Cluster c = new Cluster(elem, -1, -1);
			currentCluster.add(c);
		}
		
		System.out.println("current cluster" + currentCluster.size());
		
		//Add two points one at a time
		//create a new cluster with nearest two point
		//Add new cluster to current cluster
		//remove those two point from current cluster
		while (currentCluster.size() > 2) {
			Cluster newCluster = findNearestCluster(currentCluster);
			Cluster a = currentCluster.get(newCluster.getIndex1());
			Cluster b = currentCluster.get(newCluster.getIndex2());
			currentCluster.remove(a);
			currentCluster.remove(b);
			currentCluster.add(newCluster);
			
			System.out.println("current cluster" + currentCluster.size());
		}
		System.out.println(currentCluster.get(0).getCluster().size());
		System.out.println(currentCluster.get(1).getCluster().size());

		
		GraphPanel mainPanel = new GraphPanel(currentCluster.get(0).getCluster(), currentCluster.get(1).getCluster());
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Hierarchical Clustering");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	private static Cluster findNearestCluster(ArrayList<Cluster> currentCluster) {
		ArrayList<Point> pointInCluster = new ArrayList<Point>();
		int c1 = 0, c2 = 0;
		Cluster cluster1 = null;
		Cluster cluster2 = null;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < currentCluster.size(); i++) {
			for (int j = i + 1; j < currentCluster.size(); j++) {
				cluster1 = currentCluster.get(i);
				cluster2 = currentCluster.get(j);
				double distance = getDistance(cluster1, cluster2);
				if (min > distance) {
					min = distance;
					c1 = i;
					c2 = j;
				}
			}
		}
		cluster1 = currentCluster.get(c1);
		cluster2 = currentCluster.get(c2);
		for (Point p : cluster1.getCluster()) {
			pointInCluster.add(p);
		}
		for (Point p : cluster2.getCluster()) {
			pointInCluster.add(p);
		}
		return new Cluster(pointInCluster, c1, c2);
	}
	
	private static double getDistance(Cluster c1, Cluster c2) {
		ArrayList<Point> c1Cluster = c1.getCluster();	
		ArrayList<Point> c2Cluster = c2.getCluster();
		double min = Double.MAX_VALUE;
		for (int i = 0; i < c1Cluster.size(); i++) {
			for (int j = 0; j < c2Cluster.size(); j++) {
				double dis = distance(c1Cluster.get(i), c2Cluster.get(j));
				if (min > dis) {
					min = dis;
				}
			}
		}
		return min;
	}
	
	private static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow((p2.getY() - p1.getY()), 2) + Math.pow((p2.getX() - p1.getX()), 2));
	}
}
