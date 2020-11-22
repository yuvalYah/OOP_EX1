package ex1.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import ex1.src.WGraph_Algo;
import ex1.src.weighted_graph_algorithms;


public class WGraph_Algo implements weighted_graph_algorithms , Serializable {

	private weighted_graph wga;

	public WGraph_Algo() {
		wga=new WGraph_DS();
	}

	@Override
	public void init(weighted_graph g) {
		wga=g;
	}

	/**
	 * @return the graph
	 */

	@Override
	public weighted_graph getGraph() {
		return wga;
	}

	/**
	 * define temp graph and copy all the elements from this graph to temp
	 * and start to copy all the nodes , neighbors ,weight and edge to this graph.
	 * @return weighted_graph 
	 */

	@Override
	public weighted_graph copy() {
		weighted_graph temp = new WGraph_DS();
		java.util.Iterator<node_info> it=  wga.getV().iterator();
		while(temp.getV().size()<wga.getV().size()) {
			node_info n= it.next();
			if(n != null) {
				temp.addNode(n.getKey());//add the elements to temp
			}
			else {
				temp.addNode(n.getKey()); // if don't have node next so we add and remove
				temp.removeNode(n.getKey());
			}
		}
		//connect edge to temp
		it=  wga.getV().iterator();
		for (int i = 0; i < wga.getV().size(); i++) {
			for (int j = 0; j < wga.getV().size(); j++) {
				if(i!=j && wga.hasEdge(i, j)) {
					temp.connect(i, j, wga.getEdge(i, j));
				}

			}
		}
		//init this graph
		wga=new WGraph_DS();
		java.util.Iterator<node_info> it2=  temp.getV().iterator();
		while(temp.getV().size()>wga.getV().size()) {
			node_info n= it2.next();
			if(n != null) {
				wga.addNode(n.getKey());
			}
			else {
				wga.addNode(n.getKey());
				wga.removeNode(n.getKey());
			}
		}
		it =  temp.getV().iterator();
		for (int i = 0; i < temp.getV().size(); i++) {
			for (int j = 0; j < temp.getV().size(); j++) {
				if(i!=j && temp.hasEdge(i, j)) {
					wga.connect(i, j, temp.getEdge(i, j));
				}
			}
		}
		return wga;
	}

	/**
	 * check if this graph is connecting, if there is path between all the nodes
	 * add to the queue the first node and add all his neighbors 
	 * when we poll node from the queue , we add the key to linkedlist
	 * in the end, if the size of linked list equals to node size --> the graph is connecting
	 * @return boolean
	 */
	@Override
	public boolean isConnected() {

		if(wga.nodeSize()==0 ||wga.nodeSize()==1) return true; //if we don't have ver or we have 1 ver -->the graph is connected
		else if(wga.nodeSize()-1>wga.edgeSize() )return false; /// if we have more of ver then edge --> the graph isn't connected
		else {
			// we pass with iterator on the graph 
			Iterator<node_info> it = wga.getV().iterator();
			//define arr in size of the nodes
			node_info arr[]=new node_info[wga.nodeSize()];
			for (int i = 0; i < arr.length ; i++) {///with for we passing all the nodes and put in arr 
				node_info n=  it.next();
				if(n != null) {
					n.setTag(0);//set tag to 0 
					arr[i]=n;
				}
				else i--;
			}
			// define ll to contain the keys of the graph
			LinkedList<Integer> ll=new LinkedList<>(); 
			BlockingQueue<node_info> q = new LinkedBlockingDeque<node_info>(); // queue to put in the node and use to bfs  
			q.add(arr[0]);

			while(!q.isEmpty()) {
				node_info n= q.poll(); // n == to the head of the queue
				if(n.getTag()==0) {
					n.setTag(1); /// if n tag is 0 we change to 1 , to know that we been there
					ll.add(n.getKey()); // add to ll the key
					node_info a= wga.getNode(n.getKey());
					int len=a.getInfo().length();
					for (int i = 2; i < len; i+=3) {
						//////////////// in the info ,save all the neighbors nodes
						int temp=0;
						int num=a.getInfo().charAt(i)-'0';
						boolean flag=false;
						while(i+1<len&&a.getInfo().charAt(i+1) !=' ') {//when the num >9 
							num=a.getInfo().charAt(i)-'0';
							temp=temp+num;
							if(i+1<len && a.getInfo().charAt(i+1) !=' ') temp=temp*10;
							i++;
							if(i+1<len && a.getInfo().charAt(i+1)==' ')temp=temp+a.getInfo().charAt(i)-'0';
							if(i+1==len) flag=true;
						}
						if(flag==true) temp= temp+a.getInfo().charAt(i)-'0';
						if(temp!=0) num = temp;
						//////////////////////
						q.add(wga.getNode(num));

					}
				}
			}
			if( ll.size()!= wga.nodeSize()) return false; // in the and , if in ll there is all key in the graph so the graph is connected
			else return true;
		}

	}

	/**
	 * Count the smallest distance from stc to dest
	 * arr help us to save the distance in each index.
	 * @return double distance
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		ArrayBlockingQueue<node_info> q= new ArrayBlockingQueue<>(wga.getV().size());
		java.util.Iterator<node_info> it=  wga.getV().iterator();//// max to do get v of nodeinfo
		double arr[]=new double[wga.getV().size()];
		while(it.hasNext()) {
			node_info n =it.next();
			if(n !=null ) {			
				n.setTag(0);
				if(n.getKey()==src) {
					try {
						q.put(n);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}
		boolean ans=false;
		while(!q.isEmpty()) {
			node_info n = q.poll();
			n.setTag(1);
			int len=n.getInfo().length();
			for (int i = 2; i < n.getInfo().length(); i+=3) {
				////////////////////
				int temp=0;
				int num=n.getInfo().charAt(i)-'0';
				boolean flag=false;
				while(i+1<len&&n.getInfo().charAt(i+1) !=' ') {
					num=n.getInfo().charAt(i)-'0';
					temp=temp+num;
					if(i+1<len && n.getInfo().charAt(i+1) !=' ') temp=temp*10;
					i++;
					if(i+1<len && n.getInfo().charAt(i+1)==' ')temp=temp+n.getInfo().charAt(i)-'0';
					if(i+1==len) flag=true;
				}
				if(flag==true) temp= temp+n.getInfo().charAt(i)-'0';
				if(temp!=0) num = temp;
				////////////////////
				node_info nib=wga.getNode(num);
				if(wga.getNode(num).getTag()!=1) {
					double kt=arr[nib.getKey()] + wga.getEdge(n.getKey(), nib.getKey()); ///
					if(arr[num]==0) {
						double a1=arr[n.getKey()];
						double a2=wga.getEdge(n.getKey(), nib.getKey());
						double sum=a1+a2;
						arr[num]=sum;
						try {
							q.put(nib);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else {

						if(arr[n.getKey()]+wga.getEdge(n.getKey(), nib.getKey())<arr[num]) {
							arr[num]=arr[n.getKey()]+wga.getEdge(n.getKey(), nib.getKey());

						}
						else if(kt < arr[n.getKey()]) arr[n.getKey()]=kt;
					}
				}

			}
			if(n.getKey()==dest) {
				ans=true;
				break;
			}
		}
		if(ans==true) return arr[dest];
		else return -1;

	}

	/**
	 * make list to show the path of the smallest dist.
	 * arr help us to save the distance from src to each index ,
	 * paths help us to save string wits the path from src to each index
	 * @return ArrayList 
	 */
	@Override
	public List<node_info> shortestPath(int src, int dest) {
		ArrayList<node_info> shortestPath = new ArrayList<>();
		java.util.Iterator<node_info> it=  wga.getV().iterator();
		ArrayBlockingQueue<node_info> q= new ArrayBlockingQueue<>(wga.getV().size());
		double arr[]=new double[wga.getV().size()];
		String pathS[]=new String[wga.getV().size()];
		while(it.hasNext()) {//init the node tag
			node_info n =it.next();
			if(n !=null ) {			
				n.setTag(0);
				if(n.getKey()==src) {
					try {
						q.put(n); //add scr to the queue
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		boolean ans=false;
		if(!q.isEmpty())pathS[q.peek().getKey()]=" ,"+q.peek().getKey();
		while(!q.isEmpty()) {
			node_info n = q.poll();
			n.setTag(1);
			int len=n.getInfo().length();
			for (int i = 2; i < n.getInfo().length(); i+=3) { // add the neighbors
				////////////////////
				int temp=0;
				int num=n.getInfo().charAt(i)-'0';
				boolean flag=false;
				while(i+1 < len && n.getInfo().charAt(i+1) !=' ') {
					num=n.getInfo().charAt(i)-'0';
					temp=temp+num;
					if(i+1 < len && n.getInfo().charAt(i+1) !=' ') temp=temp*10;
					i++;
					if(i+1 < len && n.getInfo().charAt(i+1)==' ')temp=temp+n.getInfo().charAt(i)-'0';
					if(i+1 == len) flag=true;
				}
				if(flag == true) temp = temp+n.getInfo().charAt(i)-'0';
				if(temp!= 0) num = temp;
				////////////////////
				node_info nib=wga.getNode(num);
				if(wga.getNode(num).getTag()!=1) {
					double kt=arr[nib.getKey()] + wga.getEdge(n.getKey(), nib.getKey()); 
					if(arr[num]==0) {
						double sum =arr[n.getKey()] + wga.getEdge(n.getKey(), nib.getKey());
						arr[num]=sum;
						pathS[num]=pathS[n.getKey()]+" ,"+nib.getKey();

						try {
							q.put(nib);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else {
						if(arr[n.getKey()] + wga.getEdge(n.getKey(), nib.getKey()) < arr[num]) {
							arr[num]=arr[n.getKey()]+wga.getEdge(n.getKey(), nib.getKey());
							pathS[num]=pathS[n.getKey()]+" ,"+nib.getKey();
						}
						else if(kt < arr[n.getKey()]) {
							arr[n.getKey()]=kt;
							pathS[n.getKey()]=pathS[nib.getKey()]+" ,"+n.getKey();
						}

					}
				}
			}
			if(n.getKey()==dest) {
				ans=true;
				break;
			}
		}
		if(ans==true) {//after we found the shortest path and we have string with the path keys :
			int len=pathS[dest].length();
			String path=pathS[dest];
			for (int i = 2; i < len; i+=3) {//make them to real numbers and add to the ArrayList
				int temp=0;
				int num=pathS[dest].charAt(i)-'0';
				boolean flag=false;
				while(i+1<len&&path.charAt(i+1) !=' ') {
					num=path.charAt(i)-'0';
					temp=temp+num;
					if(i+1<len && path.charAt(i+1) !=' ') temp=temp*10;
					i++;
					if(i+1<len && path.charAt(i+1)==' ')temp=temp+path.charAt(i)-'0';
					if(i+1==len) flag=true;
				}
				if(flag==true) temp= temp+path.charAt(i)-'0';
				if(temp!=0) num = temp;
				////////////////////
				node_info node=wga.getNode(num);
				shortestPath.add(node);

			}
			shortestPath.get(0).setTag(0);
			double count=0;
			for (int i = 1; i < shortestPath.size(); i++) {
				count = count+wga.getEdge(shortestPath.get(i).getKey(), shortestPath.get(i-1).getKey());
				shortestPath.get(i).setTag(count);
			}
		}

		return shortestPath;
	}

	/**
	 * save the graph on text.
	 * @param file
	 * @return boolean, true  if we save on text file
	 */
	@Override
	public boolean save(String file) {
		boolean ans = false;
		ObjectOutputStream objout;
		try {
			FileOutputStream fileout = new FileOutputStream(file, true);
			objout = new ObjectOutputStream(fileout);
			objout.writeObject(this);
			objout.close();
			ans= true;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace(); }
		catch (IOException e) {e.printStackTrace();}
		return ans;
	}

	/**
	 * load the graph from the text.
	 * @param file
	 * @return boolean 
	 */

	@Override
	public boolean load(String file) {	
		try {

			ObjectInputStream inp =new ObjectInputStream(new FileInputStream(file));
			WGraph_Algo newgraph= (WGraph_Algo) inp.readObject();
			inp.close();
			return true;

		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;

	}
}
