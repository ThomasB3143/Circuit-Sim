import java.util.ArrayList;
public class Node {
	
	public ArrayList<Component> connected = new ArrayList<Component>();
	public int x = 0;
	public int y = 0;
	public double voltage;
	
	public Node(Component comp1, Component comp2, boolean comp1Port1, boolean comp2Port1) { //Called when two components are connected and neither connected ports have a node, automatically adding both components to the connected list. if the compPort1 is true then it connects to port1, port2 if false
		connected.add(comp1);
		connected.add(comp2);
		if(comp1Port1) {
			comp1.port1 = this;
		}
		else {
			comp1.port2 = this;
		}
		if(comp2Port1) {
			comp2.port1 = this;
		}
		else {
			comp2.port2 = this;
		}
	}
	public double getConSum() { //finds the sum of the conductances of all connected resistors
		double conSum = 0;
		for(int i = 0 ; i < connected.size() ; i ++) {
			if(connected.get(i).isRes() && !connected.get(i).isSwitch && !connected.get(i).isMeter) { //open switches do not add conductance
				conSum += 1.0/connected.get(i).getRes();
			}
		}
		return conSum;
	}
	public double getConBetween(Node n) { //finds the negative conductance between nodes, returns 0 if nodes don't share a common component
		double con = 0;
		for (int i = 0 ; i < connected.size(); i ++) {
			if((connected.get(i).port1 == n || connected.get(i).port2 == n) && connected.get(i).isRes && !connected.get(i).isSwitch && !connected.get(i).isMeter) { //only works if component is connected to both nodes and it is a resistor (open switches do not contribute)
				con -= 1.0/connected.get(i).getRes(); 
			}
		}
		return con;
	}
	public double getTerminal(Cell c) { //returns 1 if the node is connected to the anode of cell, -1 if cathode, 0 if none
		if (this == c.port1) {
			return 1;
		}
		else if (this == c.port2) {
			return -1;
		}
		else {
			return 0;
		}
	}
	public void addComp(Component comp, boolean port1) {
		if (port1) {
			comp.port1 = this;
		}
		else {
			comp.port2 = this;
		}
		connected.add(comp);
	}
	public void merge(Node n) { //transfers all components connected to node n to target, then removes n from nodeList
		if (this != n) {
			do {
				if (n.connected.get(0).port1 == n) {
					n.connected.get(0).port1 = this;
				}
				else {
					n.connected.get(0).port2 = this;
				}
				this.connected.add(n.connected.get(0));
				n.sever(n.connected.get(0),true);
			} while (Main.getNodeList().contains(n));
		}
	}
	public void sever(Component comp, boolean merging) {
		if (comp.port1 == this) {
			comp.port1 = null;
		}
		else {
			comp.port2 = null;
		}
		this.connected.remove(comp);
		if (connected.size() == 1 && merging == false) { //one component connected to node makes node redundant
			this.sever(connected.get(0), false); //severs the final component
			Main.getNodeList().remove(this);
		}
		if (connected.size() == 0 && merging == true) {
			Main.getNodeList().remove(this);
		}
	}
}
