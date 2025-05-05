
public class Component {
	
	public Node port1; //For the cell subclass, port1 is the anode and port2 is the cathode
	public Node port2;
	public String name; //No parameter for name in any constructors since it can be modified through accessors
	public int x; //Top left corner co-ords
	public int y;
	public boolean isRes;
	public double res; 
	public double current;
	public double pd;
	public boolean isSwitch;
	public boolean isMeter;
	public int angle; //0,1,2,3 represent anode to the right,bottom,left and top
	public boolean drawn;
	public Component(int X, int Y) {
		x = X;
		y = Y;
		angle = 0;
	}
	public boolean isRes() {
		return isRes;
	}
	public void setRes(double r) {
		res = r;
	}
	public double getRes() {
		return res;
	}
	public void connect(Component comp, boolean comp1Port1, boolean comp2Port1) { //creates a node if neither ports have a node. If both ports have a node, then one node is removed and all connected components are transferred. If one port has a node then that node is used.
		if (comp != this) {// ensures user can't connect the two ends of a component
			Node comp1Node; //delcaring and assigning values to a node object for each component eliminates need for 16 if statements
			Node comp2Node;
			if (comp1Port1) {
				comp1Node = this.port1;
			}
			else {
				comp1Node = this.port2;
			}
			if (comp2Port1) {
				comp2Node = comp.port1;
			}
			else {
				comp2Node = comp.port2;
			}
			if (comp1Node == null && comp2Node == null) { //neither components have a node
				Main.getNodeList().add(new Node(this , comp , comp1Port1 , comp2Port1));
			}
			else if (comp1Node == null && comp2Node != null) { //only comp2 has a node, adds comp1 to comp2 node
				comp2Node.addComp(this, comp1Port1);
			}
			else if (comp1Node != null && comp2Node == null) { //only comp1 has a node, adds comp2 to comp1 node
				comp1Node.addComp(comp, comp2Port1);
			}
			else { //Both components have a node, all components from the node at comp2 are transferred to the node of comp1 and the empty node is removed from nodeList
				comp1Node.merge(comp2Node);
			}
		}
	}
	public void delete() {
		if (port1 != null) {
			port1.sever(this,false);
		}
		if (port2 != null) {
			port2.sever(this,false);
		}
		if (isRes) {
			Main.getResList().remove(this);
		}
		else {
			Main.getCellList().remove(this);
		}
	}
	public double getResCurrent() {
		if (isRes() && !isSwitch) { //switches will have a current of 0
			return (port1.voltage - port2.voltage)/res;
		}
		else {
			return 0;
		}
	}
	public Component toggleSwitch() {
		if (isSwitch && isRes) {//closing an open switch
			return new Cell(this);
		}
		else if (isSwitch && !isRes) {
			return new Resistor(this);
		}
		return this;
	}
}
