import java.util.ArrayList;
//import java.util.random.*;

public class Tree {
	//private int depth;
	private Node root;
	private ArrayList<ArrayList<ArrayList<Node>>> tree = new ArrayList<ArrayList<ArrayList<Node>>>();
	private ArrayList<ArrayList<Node>> level0 = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> level1 = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> level2 = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> level3 = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> level4 = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> level5 = new ArrayList<ArrayList<Node>>();
	private ArrayList<ArrayList<Node>> level6 = new ArrayList<ArrayList<Node>>();




	public Tree(ChessBoard boardObj_i) {
		//this.depth = depth_i;
		long startTime = System.nanoTime();
		this.root = new Node(boardObj_i);
		ArrayList<Node> l0 = new ArrayList<Node>();
		l0.add(root);
		level0.add(l0);
		
		root.generateChildren();
		level1.add(root.getChildren());
		
		
		

		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		//System.out.println("Length1 in milli: " + timeElapsed/1000000);
		
		long startTime2 = System.nanoTime();
		int level1Size = level1.size();
		for(int i = 0; i < level1Size; i++){ //creates level2
			for(int i2 = 0; i2 < level1.get(i).size(); i2++) {
				level1.get(i).get(i2).generateChildren();
				level2.add(level1.get(i).get(i2).getChildren());
				//root.getChildren().get(i2).generateChildren();
				//level2.add(root.getChildren().get(i2).getChildren());
			}
		}
		long endTime2 = System.nanoTime();
		long timeElapsed2 = endTime2 - startTime2;
		//System.out.println("Length2 in milli: " + timeElapsed2/1000000);
//		
//		for(int i = 0; i < level2.size(); i++){ //creates level3
//			for(int i2 = 0; i2 < level2.get(i).size(); i2++) {
//				level2.get(i).get(i2).generateChildren();
//				level3.add(level2.get(i).get(i2).getChildren());
//			}
//		}
//		
//		for(int i = 0; i < level3.size(); i++){ //creates level4
//			for(int i2 = 0; i2 < level3.get(i).size(); i2++) {
//				level3.get(i).get(i2).generateChildren();
//				level4.add(level3.get(i).get(i2).getChildren());
//			}
//		}
		long startTime3 = System.nanoTime();
		tree.add(level0);
		tree.add(level1);
		//tree.add(level2);
		long endTime3 = System.nanoTime();
		long timeElapsed3 = endTime3 - startTime3;
		//System.out.println("Length3 in milli: " + timeElapsed3/1000000);
//		tree.add(level3);
//		tree.add(level4);
		System.out.println("Length1 in milli: " + timeElapsed/1000000);
		System.out.println("Length2 in milli: " + timeElapsed2/1000000);
		System.out.println("Length3 in milli: " + timeElapsed3/1000000);



		

	}
	
	public Move getRanMove(int depth) {
		double ran1 = Math.random(); 
		double ran2 = Math.random();
		int size1 = tree.get(depth).size();
		int size2 = tree.get(depth).get((int)(ran1 * size1)).size();
		int loc1 = (int)(size1 * ran1);
		int loc2 = (int)(size2 * ran2);
		Node target = tree.get(depth).get(loc1).get(loc2);
		return target.getMove();
	}
	
	public int countNodes(int level) {
		int count = 0;
		for(int i = 0; i < tree.get(level).size(); i++) {
			for(int i2 = 0; i2 < tree.get(level).get(i).size(); i2++){
				count++;
			}
		}
		return count;
	}
}
