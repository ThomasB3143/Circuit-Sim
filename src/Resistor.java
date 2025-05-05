public class Resistor extends Component {
	public Resistor(int X, int Y) {
		super(X, Y);
		isSwitch = false;
		isRes = true;
		pd = 0;
		res = 1;
		Main.getResList().add(this);
		name = "R" + Main.getResList().size();
	}
	public Resistor(int X, int Y, boolean isSwitch) { //isSwitch is false for a meter
		super(X, Y);
		this.isSwitch = isSwitch;
		this.isMeter = !isSwitch; //Resistor meters are voltmeters
		isRes = true;
		pd = 0;
		res = -1; //-1 is used to denote infinite resistance, hence 0 conductance
		Main.getResList().add(this);
		if (isSwitch) {
			name = "Switch";
		}
		else {
			name = "Voltmeter";
		}
	}
	public Resistor(Component comp) { //converts closed switch to open switch
		super(comp.x, comp.y);
		angle = comp.angle;
		if (comp.port1 != null) {
			comp.port1.addComp(this, true);
		}
		if (comp.port2 != null) {
			comp.port2.addComp(this, false);
		}
		isRes = true;
		isSwitch = true;
		isMeter = false;
		pd = 0;
		res = -1; //-1 is used to denote infinite resistance, hence 0 conductance
		Main.getResList().add(this);
		name = comp.name;
		comp.delete();
	}
}
