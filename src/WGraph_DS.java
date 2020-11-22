package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import ex1.src.weighted_graph;

public class WGraph_DS implements weighted_graph , Serializable{

	private ArrayList<NodeInfo> ver;
	private ArrayList<node_info> nodes;
	private int sum_ver;
	private int sum_edge;
	private int MC;

	public WGraph_DS() {
		this.ver=new ArrayList<>();
		this.nodes=new ArrayList<>();
		this.sum_ver = 0;
		this.sum_edge = 0;
		this.MC = 0;

	}

	/**
	 * return node whith the given key
	 * o(1)
	 * if key > size of arr of nodes -->dont have the given key
	 * @param key
	 * @return node_info
	 */

	@Override
	public node_info getNode(int key) {

		if(key >= nodes.size()) return null;
		else return nodes.get(key);

	}

	/**
	 * need to check if there is edge between node1 to node2
	 * Each node in the graph has weight 
	 * if node1 or node2 > size of array --> ofCourse that don't has an edge if node doesn't exist
	 * if the node with the given key is null --> there no such edge between in the graph
	 * @param node1 , node2
	 * @return boolean 
	 * 
	 */
	@Override
	public boolean hasEdge(int node1, int node2) {
		int k= ver.size();
		boolean ans=false;
		if(node1 <k && node2 <k && ver.get(node1) != null && ver .get(node2) != null ) {
			boolean n1= ver.get(node1).hasNi(node2);
			boolean n2= ver.get(node2).hasNi(node1);
			if(n1==true && n2 == true ) ans=true;
		}

		return ans;

	}

	/**
	 * in this given graph we return the weight of the edge
	 * if we dont have an edge between node1 to node2-->return -1
	 * @param node1 node2
	 * @return double edge 
	 */

	@Override
	public double getEdge(int node1, int node2) {
		if(!hasEdge(node1 , node2)) return -1;
		else {
			NodeInfo n1=ver.get(node1);
			return n1.getWeight(node2);
		}

	}

	/**
	 * adding the specified element to his place in this list.
	 * his place == to the value of his key
	 * @param key element to be adding to this list.
	 */

	@Override
	public void addNode(int key) {
		if(key<ver.size()) { // if the place in the list ==null --> change to this node
			if(getNode(key)==null) {
				ver.set(key, new NodeInfo(key));
				nodes.set(key, new Node_I(key));
				sum_ver++;
				MC++;
			}
		}
		else if(key>ver.size()) {// add null and in the end add the new node
			int size=ver.size();
			while(key>size) {
				ver.add(null);
				nodes.add(null);
				size++;
			}
			ver.add(new NodeInfo(key));
			nodes.add(new Node_I(key));
			sum_ver++;
			MC++;

		}
		else {
			ver.add(new NodeInfo(key));
			node_info a2= new Node_I(key);
			nodes.add(a2);
			sum_ver++;
			MC++;
		}
	}

	/**
	 * this method connect between node1 to node2
	 * if we has an edge so we need to change the weight 
	 * else we add node2 to the neighbors list of node1 ,
	 *  and add node1 to to the neighbors list of node2 .
	 *  and adding w weight to the list in the place of the node
	 *  @param node1 , node2 , w
	 */
	@Override
	public void connect(int node1, int node2, double w) {
		if(w >= 0) {
			if(hasEdge(node1,node2)) {
				ver.get(node1).setWeight(node2, w);
				ver.get(node2).setWeight(node1, w);
				MC++;
			}
			else {

				if(ver.get(node1)!=null && ver.get(node2)!=null && node1 != node2) {
					int q =nodes.get(node1).getKey();
					String str =nodes.get(q).getInfo();
					nodes.get(q).setInfo(str+" ,"+node2);
					q =nodes.get(node2).getKey();
					str =nodes.get(q).getInfo();
					nodes.get(q).setInfo(str+" ,"+node1);
					ver.get(node1).addNi(nodes.get(node2), w);
					ver.get(node2).addNi(nodes.get(node1), w);
					sum_edge++;
					MC++;
				}

			}
		}

	}

	/**
	 * @return the list of the nodes
	 */
	@Override
	public Collection<node_info> getV() {
		return nodes;
	}

	/**
	 * @return list of the neighbors of this node
	 */

	@Override
	public Collection<node_info> getV(int node_id) {
		NodeInfo v=ver.get(node_id);
		return v.getNi();
	}

	/**
	 * remove node from the graph in place of key
	 * and remove this node from the list of neighbor of his neighbors
	 * @param key
	 * @return node_info
	 */

	@Override
	public node_info removeNode(int key) {
		if(key<ver.size() && getNode(key) != null) {
			NodeInfo n= ver.get(key);
			node_info s=nodes.get(key);
			java.util.Iterator<node_info> it=  n.getNi().iterator();
			int k= n.getNi().size();
			int i =0 ;
			while(k>i) {
				node_info a= it.next();
				if(a != null) {
					ver.get(a.getKey()).removeNode(s);
					int q =a.getKey();
					String str =nodes.get(q).getInfo();
					nodes.get(q).setInfo(str.replace(" ,"+key, ""));
					sum_edge--;
					MC++;
				}
				i++;
			}
			nodes.set(key, null);
			ver.set(key, null);
			sum_ver--;
			MC++;
			return s;
		}
		return null;
	}

	/**
	 * remove the edge in his place in the weight list .
	 * remove the node from the neighbors list
	 * remove from the nodes tag's
	 * @param node1 node2.
	 */
	@Override
	public void removeEdge(int node1, int node2) {
		if(node1 != node2 && node1<ver.size() && node2< ver.size()) {
			if(ver.get(node1)!= null && ver.get(node2)!= null && hasEdge(node1 , node2)) {
				ver.get(node1).removeNode(nodes.get(node2));
				ver.get(node2).removeNode(nodes.get(node1));
				int q =nodes.get(node1).getKey();
				String str =nodes.get(q).getInfo();
				nodes.get(q).setInfo(str.replaceFirst(" ,"+node2, ""));
				q =nodes.get(node2).getKey();
				str =nodes.get(q).getInfo();
				nodes.get(q).setInfo(str.replaceFirst(" ,"+node1, ""));

				sum_edge--;
				MC++;

			}
		}

	}

	/**
	 * count the node size in the graph
	 * @return node size
	 */

	@Override
	public int nodeSize() {
		return sum_ver;
	}

	/**
	 * count the edge size in the graph
	 * @return edge size
	 */

	@Override
	public int edgeSize() {
		return sum_edge;
	}

	/**
	 * MC is all the changes in the graph
	 * @return MC 
	 */

	@Override
	public int getMC() {
		return MC;
	}

	/**
	 *  Compares the specified object with this list for equality.
	 *  Returns true if and only if the specified graph is equals to other graph
	 *  both graph have the same size,
	 *  and all corresponding pairs of elements in the two lists are equal.
	 *  @return true of false
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof weighted_graph)) { 
			return false; 
		}
		weighted_graph newOther =(weighted_graph)other;
		if(this.nodeSize() != newOther.nodeSize() ) return false;
		else if (this.edgeSize() != newOther.edgeSize()) return false;
		else {
			java.util.Iterator<node_info> it=  newOther.getV().iterator();//// max to do get v of nodeinfo
			for (int i = 0; i < ver.size() && it.hasNext() ; i++) {
				node_info n1=nodes.get(i);
				node_info k1=it.next();
				if(n1 != null && k1 != null) {
					if(!n1.equals(k1)) return false;
				}
				else if(n1 != null && k1 == null) return false;
				else if(n1 == null && k1 != null) return false;

			}
		}
		return true;
	}

}
