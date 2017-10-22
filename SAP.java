

public class SAP {
	
	private Digraph G;
	
	public SAP(Digraph G) {
		this.G = G;
	}
	
	
	public int length(int v, int w) {
		if (ancestor(v,w) != -1) {
			int ant = ancestor(v, w);
			BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
			BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
			int distV = bfsV.distTo(ant);
			int distW = bfsW.distTo(ant);
			return distV + distW;
		}
		return -1;
	}
	
	public int ancestor(int v, int w) {
		int ant = -1;
		int len = Integer.MAX_VALUE;
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

		for(int i = 0; i < G.V(); i++) {
			if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
				int distV = bfsV.distTo(i);
				int distW = bfsW.distTo(i);
				int sum = distV + distW;
				if(sum < len) {
					len = sum;
					ant = i;
				}
			}
		}
		return ant;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP S = new SAP(G);
		In in2 = new In(args[1]);
		while(!in2.isEmpty()){
			int v = in2.readInt();
			int w = in2.readInt();
			int ant = S.ancestor(v, w);
			StdOut.print("sap = " + S.length(v, w) + ", ancestor = " + ant + "\n");
		}
	}
}
