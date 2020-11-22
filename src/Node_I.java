package ex1.src;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
public class Node_I implements node_info ,Serializable {
    private static final long serialVersionUID = 6529685098267757690L;


	private int key;
	private double tag;
	private String info;
	private ArrayList<node_info> ni;

	public Node_I(int key) {
		this.ni=new ArrayList<>();
		this.tag=0;
		this.info="";
		this.key= key;

	}
	@Override
	public int getKey() {
		return this.key;
	}
	
	@Override
	public String getInfo() {
		return this.info;
	}
	
	@Override
	public void setInfo(String s) {
		this.info=s;
	}
	@Override
	public double getTag() {

		return this.tag;
	}

	@Override
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



	public void addNi(node_info t) {

		if(!hasNi(t.getKey())) {
			int k= t.getKey();
			if(k>ni.size()) {
				int size=ni.size();
				while(k>size) {
					ni.add(null);
					size++;
				}
				ni.add(t);
				this.setInfo(getInfo()+" ,"+t.getKey());
			}
			else if(k<ni.size()) {
				ni.add(k, t);
				this.setInfo(getInfo()+" ,"+t.getKey());

			}
			else {
				ni.add(t);
				this.setInfo(getInfo()+" ,"+t.getKey());
			}
		}

	}
	public void removeNode(node_info node) {

		if(node.getKey()<ni.size() && ni.get(node.getKey()) !=null) {
			ni.set(node.getKey(), null);
			this.setInfo(getInfo().replaceFirst(" ,"+node.getKey(), ""));

		}
	}
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof node_info)) { 
			return false; 
		}
		node_info newOther= (node_info)other;
		if(!this.getInfo().equalsIgnoreCase(newOther.getInfo())) return false;

		return true;
	}

}
