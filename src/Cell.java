public class Cell extends Component {
	public int ammeterCount = 0;
	public Cell(int X, int Y) {
		super(X, Y);
		isSwitch = false;
		isRes = false;
		res = 0;
		pd = 1;
		Main.getCellList().add(this);
		name = "V" + Main.getCellList().size();
	}
	public Cell(int X, int Y, boolean isSwitch) {// isSwitch is false for a meter
		super(X, Y);
		this.isSwitch = isSwitch;
		this.isMeter = !isSwitch; //cell meters are ammeters
		isRes = false;
		res = 0;
		pd = 0;
		Main.getCellList().add(this);
		if (isSwitch) {
			name = "Switch";
		}
		else {
			name = "Ammeter";
		}
	}
	public Cell(Component comp) { //converts open switch to closed switch
		super(comp.x, comp.y);
		angle = comp.angle;
		if (comp.port1 != null) {
			comp.port1.addComp(this, true);
		}
		if (comp.port2 != null) {
			comp.port2.addComp(this, false);
		}
		isRes = false;
		isSwitch = true;
		isMeter = false;
		pd = 0;
		res = 0;
		Main.getCellList().add(this);
		name = comp.name;
		comp.delete();
	}
}
