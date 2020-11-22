package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NodeInfo implements Serializable {
	private static final long serialVersionUID = 6529685098267757690L;

	private int key;
	private double tag;
	private String info;
	private ArrayList<node_info> ni;
	private List<Double> weight;

	public NodeInfo(int key) {
		this.ni=new ArrayList<>();
		this.weight=new ArrayList<>();
		this.tag=0;
		this.info="";
		this.key= key;

	}

	public int getKey() {
		return this.key;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String s) {
		this.info=s;
	}

	public double getTag() {

		return this.tag;
	}

	public void setTag(double t) {
		this.tag=t;
	}


	public Collection<node_info> getNi() {
		return ni;
	}


	public boolean hasNi(int key) {

		if(key < ni.size() && ni.get(key)!=null) {
			return true;
		}
		return false;
	}



	public void addNi(node_info t , double w) {

		if(!hasNi(t.getKey())) {
			int k= t.getKey();
			if(k>ni.size()) {
				int size=ni.size();
				while(k>size) {
					ni.add(null);
					weight.add(-1.0);
					size++;
				}
				ni.add(t);
				weight.add(w);
				this.setInfo(getInfo()+" ,"+t.getKey());
			}
			else if(k<ni.size()) {
				ni.set(k, t);
				weight.set(k,w);
				this.setInfo(getInfo()+" ,"+t.getKey());

			}
			else {
				ni.add(t);
				weight.add(w);
				this.setInfo(getInfo()+" ,"+t.getKey());
			}
		}

	}

	public double getWeight(int key) {
		if(ni.size()>key) return weight.get(key);
		return -1.0;
	}
	public void setWeight(int ind , double w) {
		weight.set(ind, w);
	}

	public void removeNode(node_info node) {

		if(node.getKey()<ni.size() && ni.get(node.getKey()) !=null) {
			weight.set(node.getKey(), -1.0);
			ni.set(node.getKey(), null);
			this.setInfo(getInfo().replaceFirst(" ,"+node.getKey(), ""));

		}
	}

}
