import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;
public class Builder extends JFrame {
	private Container con;
	private Canvas canvas;
	public Builder() {
		this.setTitle("Circuit Yard");
		this.setResizable(false);
		this.setSize(1616,1039);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Canvas.backgroundBlue);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		con = this.getContentPane();
		this.setVisible(true);
		this.add(canvas = new Canvas());
	}
}
class Editor extends JFrame implements ActionListener {
	private Container con = this.getContentPane();
	private SpringLayout layout = fixLayout();
	private JTextField nameField = inputField();
	private JButton cancelButton = button("Cancel");
	private JLabel nameLabel = label("Name");
	private JTextField valField = inputField();
	private JButton saveButton = button("Save");
	private JLabel valLabel = label("");
	private JLabel expoLabel = new JLabel("x10", 0);
	private Component comp;
	private JTextField expoField;
	private JTextArea errorLabel;
	private int labelCount = 0;
	public SpringLayout fixLayout() { //returns a SpringLayout object added to the Editor object
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		return layout;
	}
	public JLabel label(String text) {
		JLabel label = new JLabel(text, 0);
		label.setFont(new Font("Arial" , 1 , 17));
		label.setForeground(Canvas.black);
		label.setBackground(Canvas.white);
		label.setBorder(BorderFactory.createLineBorder(Canvas.black, 2));
		label.setOpaque(true);
		con.add(label);
		layout.putConstraint(SpringLayout.NORTH, label, labelCount * 40 + 10, SpringLayout.NORTH, con);
		layout.putConstraint(SpringLayout.EAST, label, 150, SpringLayout.WEST, con);
		layout.putConstraint(SpringLayout.WEST, label, 50, SpringLayout.WEST, con);
		labelCount ++;
		return label;
	}
	public JLabel powerLabel() {
		JLabel label = new JLabel("x10", 0);
		label.setFont(new Font("Arial" , 1 , 17));
		label.setForeground(Canvas.black);
		label.setBackground(Canvas.white);
		label.setBorder(BorderFactory.createLineBorder(Canvas.black, 2));
		label.setOpaque(true);
		con.add(label);
		layout.putConstraint(SpringLayout.NORTH, label, labelCount * 40 + 10, SpringLayout.NORTH, con);
		layout.putConstraint(SpringLayout.EAST, label, 400, SpringLayout.WEST, con);
		layout.putConstraint(SpringLayout.WEST, label, 360, SpringLayout.WEST, con);
		return label;
	}
	public JTextField inputField() {
		JTextField field = new JTextField();
		field.setFont(new Font("Arial" , 1 , 17));
		field.setForeground(Canvas.black);
		field.setBackground(Canvas.white);
		field.setBorder(BorderFactory.createLineBorder(Canvas.black, 2));
		field.setOpaque(true);
		con.add(field);
		layout.putConstraint(SpringLayout.NORTH, field, labelCount * 40 + 10, SpringLayout.NORTH, con);
		layout.putConstraint(SpringLayout.EAST, field, 350, SpringLayout.WEST, con);
		layout.putConstraint(SpringLayout.WEST, field, 170, SpringLayout.WEST, con);
		return field;
	}
	public JButton button(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial" , 1 , 17));
		button.setForeground(Canvas.black);
		button.setBackground(Canvas.white);
		button.setBorder(BorderFactory.createLineBorder(Canvas.black, 2));
		button.setOpaque(true);
		button.setFocusPainted(false);
		con.add(button);
		layout.putConstraint(SpringLayout.NORTH, button, 100, SpringLayout.NORTH, con);
		layout.putConstraint(SpringLayout.EAST, button, 130 + labelCount * 270, SpringLayout.WEST, con);
		layout.putConstraint(SpringLayout.WEST, button, 50 + labelCount * 290, SpringLayout.WEST, con);
		return button;
	}
	public Editor() {
		this.setTitle("Editor");
		this.setResizable(false);
		this.setSize(500,200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setBackground(Canvas.backgroundBlue);
		expoLabel.setFont(new Font("Arial" , 1 , 17));
		expoLabel.setForeground(Canvas.black);
		expoLabel.setBackground(Canvas.white);
		expoLabel.setBorder(BorderFactory.createLineBorder(Canvas.black, 2));
		expoLabel.setOpaque(true);
		con.add(expoLabel);
		layout.putConstraint(SpringLayout.NORTH, expoLabel, 50, SpringLayout.NORTH, con);
		layout.putConstraint(SpringLayout.EAST, expoLabel, 400, SpringLayout.WEST, con);
		layout.putConstraint(SpringLayout.WEST, expoLabel, 360, SpringLayout.WEST, con);
		
		errorLabel = new JTextArea();
		errorLabel.setFont(new Font("Arial" , 1 , 17));
		errorLabel.setForeground(Canvas.white);
		errorLabel.setBackground(new Color(100,0,0));
		errorLabel.setBorder(BorderFactory.createLineBorder(Canvas.white, 2));
		errorLabel.setAlignmentY(CENTER_ALIGNMENT);
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		errorLabel.setOpaque(true);
		con.add(errorLabel);
		layout.putConstraint(SpringLayout.NORTH, errorLabel, 130, SpringLayout.NORTH, con);
		layout.putConstraint(SpringLayout.EAST, errorLabel, -10, SpringLayout.EAST, con);
		layout.putConstraint(SpringLayout.WEST, errorLabel, 10, SpringLayout.WEST, con);
		
		expoField = new JTextField();
		expoField.setFont(new Font("Arial" , 1 , 17));
		expoField.setForeground(Canvas.black);
		expoField.setBackground(Canvas.white);
		expoField.setBorder(BorderFactory.createLineBorder(Canvas.black, 2));
		expoField.setOpaque(true);
		
		con.add(expoField);
		layout.putConstraint(SpringLayout.NORTH, expoField, 40, SpringLayout.NORTH, con);
		layout.putConstraint(SpringLayout.EAST, expoField, 430, SpringLayout.WEST, con);
		layout.putConstraint(SpringLayout.WEST, expoField, 405, SpringLayout.WEST, con);
		saveButton.addActionListener(this);
		saveButton.setActionCommand("s");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("c");
		System.out.println(cancelButton.getActionCommand());
		this.setVisible(false);
	}
	public void setComponent(Component comp) { //called when pressing edit button
		this.comp = comp;
		nameField.setText(comp.name);
		errorLabel.setVisible(false);
		if (comp.isMeter || comp.isSwitch) {
			valField.setVisible(false);
			valLabel.setVisible(false);
			expoLabel.setVisible(false);
			expoField.setVisible(false);
		}
		else if (comp.isRes) {
			valField.setText(comp.res / Math.pow(10, Math.floor(Math.log10(comp.res))) + "");
			expoField.setText((int)Math.floor(Math.log10(comp.res)) + "");
			valLabel.setText("Resistance");
			valField.setVisible(true);
			valLabel.setVisible(true);
			expoLabel.setVisible(true);
			expoField.setVisible(true);
		}
		else {
			valField.setText(comp.pd / Math.pow(10,Math.floor(Math.log10(comp.pd))) + "");
			expoField.setText((int)Math.floor(Math.log10(comp.pd)) + "");
			valLabel.setText("Voltage");
			valField.setVisible(true);
			valLabel.setVisible(true);
			expoLabel.setVisible(true);
			expoField.setVisible(true);
		}
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("s") == true) { //save button pressed
			try {
				String name = nameField.getText();
				double mantissa = Double.parseDouble(valField.getText());
				double expo = Double.parseDouble(expoField.getText());
				if (name.length() > 15) {
					errorLabel.setVisible(true);
					errorLabel.setText("        NAME CANNOT EXCEED 15 CHARACTERS");
				}
				else if (mantissa <= 0) { //checks if mantissa is positive
					errorLabel.setVisible(true);
					if (comp.isRes) {
						errorLabel.setText("           RESISTANCE CANNOT BE NEGATIVE OR ZERO");
					}
					else {
						errorLabel.setText("             VOLTAGE CANNOT BE NEGATIVE OR ZERO");
					}
				}
				else if ((int)expo != expo) { //checks if expo is an integer
					errorLabel.setVisible(true);
					errorLabel.setText("                   EXPONENT MUST BE AN INTEGER");
				}
				else if (valField.getText().length() >= 7) { //checks if mantissa is not too long
					errorLabel.setVisible(true);
					errorLabel.setText("                  TOO MANY SIGNIFICANT FIGURES");
				}
				else if (expo >= 15 || expo <= -15) { //checks if exponent is small enough in magnitude
					errorLabel.setVisible(true);
					errorLabel.setText("           EXPONENT MUST BE BETWEEN 15 AND -15");
				}
				else { //inputs allowed
					if (comp.isRes) {
						comp.res = mantissa * Math.pow(10, expo);
					}
					else {
						comp.pd = mantissa * Math.pow(10, expo);
					}
					comp.name = name;
					this.setVisible(false);
				}
			} catch (Exception error) { //mantissa or expo were not doubles
				errorLabel.setVisible(true);
				errorLabel.setText(" ALL VARIABLES BESIDES NAME MUST BE A NUMBER");
			}
		}
		else { //cancel button pressed
			this.setVisible(false);
		}
	}
}
class Canvas extends JPanel implements MouseMotionListener, MouseInputListener {
	public static final Color black = new Color(0, 0, 0);
	public static final Color white = new Color(255 , 255, 255);
	public static final Color backgroundBlue = new Color(104, 122, 189);
	public static final Color lightGray = new Color(200, 200, 200);
	private ArrayList<Node> nodeList = Main.getNodeList();
	private ArrayList<Cell> cellList = Main.getCellList();
	private ArrayList<Resistor> resList = Main.getResList();
	private Editor editor;
	//variables for use with recursive node function
	int maxX = 40;
	int maxY = 40;
	int minX = 0;
	int minY = 0;
	int avgX = 0;
	int avgY = 0;
	int vertCount = 0;
	// variables altered through mouse events
	int drag = 0; //0 is no drag, 1 is a dragged cell, 2 is a dragged resistor, 3 is a dragged wire
	int mouseX = -1;
	int mouseY = -1;
	boolean placeable; //shows if dragged component can be placed
	boolean cutting;
	Component selected; //The component being dragged or having wires attached to
	Component highlighted; //The component appearing in the menu to change its properties.
	boolean port1; //the port of the component selected when connecting it to another component
	Graphics2D g;
	public Canvas() {
		this.setSize(1616, 1039);
		this.setBackground(backgroundBlue);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		editor = new Editor();
	}
	public void placeNode(Node current, int i) { //recursive node placement and wire drawing function
		if(current.connected.size() > i) { //if components remain (general case)
			g.setStroke(new BasicStroke(3));
			if(current.connected.get(i).port1 == current && current.connected.get(i).angle == 1 || current.connected.get(i).port1 != current && current.connected.get(i).angle == 3) { //Connects to Bottom
				if (current.connected.get(i).y + 2 > minY) {
					minY = current.connected.get(i).y + 2;
				}
				avgX += current.connected.get(i).x;
				vertCount++;
				placeNode(current, i + 1);
				g.drawLine(440 + current.x*40, 40 + current.y*40, 440 + 40*current.connected.get(i).x, 40 + current.y*40); //goes left or right
				g.drawLine(440 + 40*current.connected.get(i).x, 40 + current.y*40, 440 + 40*current.connected.get(i).x, 40*current.connected.get(i).y + 80); //goes up
			}
			else if (current.connected.get(i).port1 == current && current.connected.get(i).angle == 3 || current.connected.get(i).port1 != current && current.connected.get(i).angle == 1){ //Connects to Top
				if (current.connected.get(i).y - 2 < maxY) {
					maxY = current.connected.get(i).y - 2;
				}
				avgX += current.connected.get(i).x;
				vertCount ++;
				placeNode(current, i + 1);
				g.drawLine(440 + current.x*40, 40 + current.y*40, 440 + 40*current.connected.get(i).x, 40 + current.y*40); //goes left or right
				g.drawLine(440 + 40*current.connected.get(i).x, 40 + current.y*40, 440 + 40*current.connected.get(i).x, 40*current.connected.get(i).y); //goes down or up
			}
			else if (current.connected.get(i).port1 == current && current.connected.get(i).angle == 0 || current.connected.get(i).port1 != current && current.connected.get(i).angle == 2) { //Connects to Right
				if (current.connected.get(i).x + 2 > minX) {
					minX = current.connected.get(i).x + 2;
				}
				avgY += current.connected.get(i).y;
				placeNode(current, i + 1);
				g.drawLine(440 + current.x*40, 40 + current.y*40, 440 + 40*current.x, 40 + current.connected.get(i).y *40); //goes up or down
				g.drawLine(440 + 40*current.x, 40 + current.connected.get(i).y *40, 480 + 40*current.connected.get(i).x, 40*current.connected.get(i).y + 40); //goes left
			}
			else { //Connects to Left
				if (current.connected.get(i).x - 2  < maxX) {
					maxX = current.connected.get(i).x - 2;
				}
				avgY += current.connected.get(i).y;
				placeNode(current, i + 1);
				g.drawLine(440 + current.x*40, 40 + current.y*40, 440 + 40*current.x, 40 + current.connected.get(i).y *40); //goes up or down
				g.drawLine(440 + 40*current.x, 40 + current.connected.get(i).y *40, 400 + 40*current.connected.get(i).x, 40*current.connected.get(i).y + 40); //goes left
			}
		}
		else { //Base case
			if (minX == 0 || maxX == 40) {
				if(maxX != 40) {
					current.x = maxX;
				}
				else if(minX != 0) {
					current.x = minX;
				}
				else {
					current.x = avgX/vertCount;
				}
			}
			else {
				current.x = (minX + maxX) / 2;
			}
			if (minY == 0 || maxY == 40) {
				if(maxY != 40) {
					current.y = maxY;
				}
				else if(minY != 0) {
					current.y = minY;
				}
				else {
					current.y = avgY/(current.connected.size() - vertCount);
				}
			}
			else {
				current.y = (minY + maxY) / 2;
			}
			g.setColor(black);
		}
	}
	public void drawSelected(int x, int y, int angle, Color color) {
		if (selected.isRes) {
			if (selected.isSwitch) { //open switch
				drawSwitch(x, y, angle, color, true);
			}
			else if (selected.isMeter) { //voltmeter
				drawVoltmeter(x, y, angle, color, selected.pd );
			}
			else {
				drawRes(x, y, angle, color);

			}
		}
		else {
			if (selected.isSwitch) {
				drawSwitch(x, y, angle, color, false);
			}
			else if (selected.isMeter) {
				drawAmmeter(x, y, angle, color, selected.current);
			}
			else {
				drawCell(x, y, angle, color);
			}
		}
	}
	public void drawHighlighted(int x, int y, int angle, Color color) {
		if (highlighted.isRes) {
			if (highlighted.isSwitch) { //open switch
				drawSwitch(x, y, angle, color, true);
			}
			else if (highlighted.isMeter) { //voltmeter
				drawVoltmeter(x, y, angle, color, highlighted.pd );
			}
			else {
				drawRes(x, y, angle, color);

			}
		}
		else {
			if (highlighted.isSwitch) {
				drawSwitch(x, y, angle, color, false);
			}
			else if (highlighted.isMeter) {
				drawAmmeter(x, y, angle, color, highlighted.current);
			}
			else {
				drawCell(x, y, angle, color);
			}
		}
	}
	public void drawCell(int x, int y, int angle, Color color) {
		Color prev = g.getColor();
		g.setColor(color);
		if(angle == 0) { //anode on right (big line)
			g.fillRect(50 + x, 10 + y, 3, 60);
			g.fillRect(30 + x, 30 + y, 2, 20);
			g.fillRect(x, 39 + y, 30, 3);
			g.fillRect(50 + x, 39 + y, 30, 3);
		}
		else if(angle == 1) { //anode on bottom (big line)
			g.fillRect(10 + x, 50 + y, 60, 3);
			g.fillRect(30 + x, 30 + y, 20, 2);
			g.fillRect(39 + x, y, 3, 30);
			g.fillRect(39 + x, 50 + y, 3, 30);
		}
		else if(angle == 2) { //anode on left (big line)
			g.fillRect(30 + x, 10 + y, 3, 60);
			g.fillRect(50 + x, 30 + y, 2, 20);
			g.fillRect(x, 39 + y, 30, 3);
			g.fillRect(50 + x, 39 + y, 30, 3);
		}
		else if(angle == 3) { //anode on top (big line)
			g.fillRect(30 + x, 50 + y, 20, 2);
			g.fillRect(10 + x, 30 + y, 60, 3);
			g.fillRect(39 + x, y, 3, 30);
			g.fillRect(39 + x, 50 + y, 3, 30);
		}
		g.setColor(prev);
	}
	public void drawAmmeter(int x, int y, int angle, Color color,double current) {
		Color prevColour = g.getColor();
		Font prevFont = g.getFont();
		g.setColor(color);
		g.setFont(new Font("Arial" , 1 , 20));
		g.setStroke(new BasicStroke(3));
		g.drawRoundRect(x + 3, y + 3, 74, 74, 15, 15);
		g.drawLine(x + 3, y + 25, x + 77, y + 25);
		g.setStroke(new BasicStroke(1));
		g.drawString("A", x + 34, y + 22);
		DecimalFormat dF = new DecimalFormat("#.000");
		if (current != 0) {
			g.drawString(dF.format(Math.abs(current) / Math.pow(10, Math.floor(Math.log10(Math.abs(current))))) + " A", x + 5, y + 47);
			g.drawString("x10", x + 14 , y + 72);
			g.setFont(new Font("Arial" , 1 , 16));
			g.drawString("" + (int)Math.floor(Math.log10(current)), x + 47 , y + 61 );
		}
		else {
			g.drawString("0.000 A", x + 5, y + 47);
			g.drawString("x10", x + 14 , y + 72);
			g.setFont(new Font("Arial" , 1 , 16));
			g.drawString("0", x + 47 , y + 61 );
		}
		if (angle % 2 == 0 ) { //horizontal ports
			g.fillRect(x, y + 35, 4, 11);
			g.fillRect(x + 77, y + 35, 4, 11);
		}
		else {// vertical ports
			g.fillRect(x + 35, y , 11 , 4);
			g.fillRect(x + 35, y + 77, 11, 4);
		}
		g.setFont(prevFont);
		g.setColor(prevColour);
	}
	public void drawVoltmeter(int x, int y, int angle, Color color, double voltage) {
		Color prevColour = g.getColor();
		Font prevFont = g.getFont();
		g.setColor(color);
		g.setFont(new Font("Arial" , 1 , 20));
		g.setStroke(new BasicStroke(3));
		g.drawRoundRect(x + 3, y + 3, 74, 74, 15, 15);
		g.drawLine(x + 3, y + 25, x + 77, y + 25);
		g.setStroke(new BasicStroke(1));
		g.drawString("V", x + 34, y + 22);
		double num = voltage;
		DecimalFormat dF = new DecimalFormat("#.000");
		if (voltage != 0) {
			g.drawString(dF.format(Math.abs(voltage) / Math.pow(10, Math.floor(Math.log10(Math.abs(voltage))))) + " V", x + 5, y + 47);
			g.drawString("x10", x + 14 , y + 72);
			g.setFont(new Font("Arial" , 1 , 16));
			g.drawString("" + (int)Math.floor(Math.log10(voltage)), x + 47 , y + 61 );
		}
		else {
			g.drawString("0.000 V", x + 5, y + 47);
			g.drawString("x10", x + 14 , y + 72);
			g.setFont(new Font("Arial" , 1 , 16));
			g.drawString("0", x + 47 , y + 61 );
		}
		if (angle % 2 == 0 ) { //horizontal ports
			g.fillRect(x, y + 35, 4, 11);
			g.fillRect(x + 77, y + 35, 4, 11);
		}
		else {// vertical ports
			g.fillRect(x + 35, y , 11 , 4);
			g.fillRect(x + 35, y + 77, 11, 4);
		}
		g.setFont(prevFont);
		g.setColor(prevColour);
	}
	public void drawSwitch(int x, int y, int angle, Color color, boolean open) {
		Color prev = g.getColor();
		g.setColor(color);
		if(angle%2 == 0) { //horizontal
			if (open) {
				g.setStroke(new BasicStroke(3));
				g.drawLine(30 + x, 23 + y, 60 + x, 40 + y);
			}
			else {
				g.fillRect(20 + x, y + 39, 40, 3);
			}
			g.setStroke(new BasicStroke(1));
			g.fillOval(16 + x, 36 + y, 8, 8);
			g.fillOval(56 + x, 36 + y, 8, 8);
			g.fillRect(x, 39 + y, 20, 3);
			g.fillRect(60 + x, 39 + y, 20, 3);
		}
		else { //pivot on bottom
			if (open) {
				g.setStroke(new BasicStroke(3));
				g.drawLine(57 + x, 30 + y, 40 + x, 60 + y);
			}
			else {
				g.fillRect(39 + x, y + 20, 3, 40);
			}
			g.setStroke(new BasicStroke(1));
			g.fillOval(36 + x, 16 + y, 8, 8);
			g.fillOval(36 + x, 56 + y, 8, 8);
			g.fillRect(x + 39, y, 3, 20);
			g.fillRect(x + 39, 60 + y, 3, 20);
		}
		g.setColor(prev);
	}
	public void drawRes(int x, int y, int angle, Color color) {
		Color prev = g.getColor();
		g.setColor(color);
		if(angle % 2 == 0) { //horizontal resistor
			g.drawRect(15 + x, 27 + y, 50, 26);
			g.drawRect(16 + x, 28 + y, 48, 24);
			g.fillRect(x, 39 + y, 15, 3);
			g.fillRect(65 + x, 39 + y, 15, 3);
		}
		else {//vertical resistor
			g.drawRect(27 + x, 15 + y, 26, 50);
			g.drawRect(28 + x, 16 + y, 24, 48);
			g.fillRect(39 + x, y, 3, 15);
			g.fillRect(39 + x, 65 + y, 3, 15);
		}
		g.setColor(prev);
	}
	public void paint(Graphics g1) {
		super.paint(g1);
		g = (Graphics2D)g1;
		g.setFont(new Font("Arial" , 1 , 25));
		g.setColor(black); //drag and drop blacks and "simulate" black
		g.fillRoundRect(107, 7, 186, 47, 25, 25);
		g.fillRoundRect(62, 97, 106, 107, 25, 25);
		g.fillRoundRect(62, 217, 106, 107, 25, 25);
		g.fillRoundRect(62, 337, 106, 107, 25, 25);
		g.fillRoundRect(232, 97, 106, 107, 25, 25);
		g.fillRoundRect(232, 217, 106, 107, 25, 25);
		g.fillRoundRect(232, 337, 106, 107, 25, 25);
		g.fillRoundRect(122, 847, 156, 47, 17, 17);
		g.setColor(white); //drag and drop fill and "simulate" fill
		g.fillRoundRect(110, 10, 180, 40, 20, 20);
		g.fillRoundRect(65, 100, 100, 100, 20, 20);
		g.fillRoundRect(65, 220, 100, 100, 20, 20);
		g.fillRoundRect(65, 340, 100, 100, 20, 20);
		g.fillRoundRect(235, 100, 100, 100, 20, 20);
		g.fillRoundRect(235, 220, 100, 100, 20, 20);
		g.fillRoundRect(235, 340, 100, 100, 20, 20);
		g.fillRoundRect(125, 850, 150, 40, 15, 15);
		g.setColor(black);
		g.drawString("Drag and Drop", 115 , 40);
		g.drawString("Simulate", 150, 880);
		
		g.setFont(new Font("Arial" , 1 , 18));
		g.drawString("Toggle", 256, 370);
		g.drawString("Wire", 267, 395);
		g.drawString("Cutter", 257, 420);
		g.setFont(new Font("Arial" , 1 , 25));		

		drawCell(75, 110, 0, black);
		drawRes(245, 110, 0, black);
		drawAmmeter(75, 230, 0, black, 0);
		drawVoltmeter(245, 230, 0, black, 0);
		drawSwitch(75, 350, 0, black, true);
		if (highlighted != null) { //a component is highlighted
			DecimalFormat dF = new DecimalFormat("#.000");
			g.setColor(black);
			g.fillRoundRect(173, 497, 180, 37, 17, 17); //boxes for selected component
			g.fillRoundRect(47, 497, 106, 107, 25, 25);
			g.fillRoundRect(47, 622, 306, 37, 17, 17); //1st row
			//g.fillRoundRect(47, 667, 306, 37, 17, 17); 2nd row
			//g.fillRoundRect(47, 712, 306, 37, 17, 17); 3rd row
			g.fillRoundRect(267, 567, 86, 37, 17, 17);
			g.fillRoundRect(167, 567, 86, 37, 17, 17);

			g.setColor(white);
			g.fillRoundRect(176, 500, 174, 30, 15, 15);
			g.fillRoundRect(50, 500, 100, 100, 20, 20);
			g.fillRoundRect(50, 625, 300, 30, 15, 15); //1st row
			//g.fillRoundRect(50, 670, 300, 30, 15, 15); 2nd row
			//g.fillRoundRect(50, 715, 300, 30, 15, 15); 3rd row
			g.fillRoundRect(270, 570, 80, 30, 15, 15);
			g.fillRoundRect(170, 570, 80, 30, 15, 15);

			g.setColor(black);
			g.setFont(new Font("Arial" , 1 , 17));
			g.drawString(highlighted.name, 181, 523);
			g.drawString("Edit", 295, 593);
			g.drawString("Delete", 187, 593);
			drawHighlighted(60,510,highlighted.angle, black);
			if (highlighted.isSwitch) {
				g.fillRoundRect(112, 712, 176, 37, 17, 17);
				g.setColor(white);
				g.fillRoundRect(115, 715, 170, 30, 15, 15);
				g.setColor(black);
				g.drawString("Toggle Open/Closed", 120, 738);
			}
			if (highlighted.isRes && !highlighted.isSwitch && !highlighted.isMeter) { //for resistors
				g.setColor(black);
				g.fillRoundRect(47, 667, 306, 37, 17, 17);
				g.fillRoundRect(47, 712, 306, 37, 17, 17);
				g.setColor(white);
				g.fillRoundRect(50, 670, 300, 30, 15, 15);
				g.fillRoundRect(50, 715, 300, 30, 15, 15);
				g.setColor(black);
				g.drawString("Resistance: " + dF.format(Math.abs(highlighted.res) / Math.pow(10, Math.floor(Math.log10(Math.abs(highlighted.res))))) + " x10     Ω", 55, 648);
				g.setFont(new Font("Arial" , 1 , 15));
				g.drawString("" + (int)Math.floor(Math.log10(highlighted.res)), 224 ,640 );
				g.setFont(new Font("Arial" , 1 , 17));
				if (highlighted.current != 0) {
					g.drawString("Current: " + dF.format(Math.abs(highlighted.current) / Math.pow(10, Math.floor(Math.log10(Math.abs(highlighted.current))))) + " x10     A", 55, 693);
					g.setFont(new Font("Arial" , 1 , 15));
					g.drawString("" + (int)Math.floor(Math.log10(highlighted.current)), 199 ,685 );
					g.setFont(new Font("Arial" , 1 , 17));
				}
				else {
					g.drawString("Current: 0.000 A", 55, 693);
				}
				if (highlighted.pd != 0) {
					g.drawString("Voltage: " + dF.format(Math.abs(highlighted.pd) / Math.pow(10, Math.floor(Math.log10(Math.abs(highlighted.pd))))) + " x10     V", 55, 738);
					g.setFont(new Font("Arial" , 1 , 15));
					g.drawString("" + (int)Math.floor(Math.log10(Math.abs(highlighted.pd))), 197 ,730 );
					g.setFont(new Font("Arial" , 1 , 17));
				}
				else {
					g.drawString("Voltage: 0.000 V", 55, 738);
				}
			}
			else if (highlighted.isRes){ // for open switches and voltmeters
				g.setColor(black);
				if (highlighted.pd != 0) {
					g.drawString("Voltage: " + dF.format(Math.abs(highlighted.pd) / Math.pow(10, Math.floor(Math.log10(Math.abs(highlighted.pd))))) + " x10     V", 55, 648);
					g.setFont(new Font("Arial" , 1 , 15));
					g.drawString("" + (int)Math.floor(Math.log10(Math.abs(highlighted.pd))), 197 ,640 );
					g.setFont(new Font("Arial" , 1 , 17));
				}
				else {
					g.drawString("Voltage: 0.000 V", 55, 648);
				}
			}
			else if (!highlighted.isSwitch && !highlighted.isMeter){ // for cells
				g.setColor(black);
				g.fillRoundRect(47, 667, 306, 37, 17, 17);
				g.setColor(white);
				g.fillRoundRect(50, 670, 300, 30, 15, 15);
				g.setColor(black);
				g.drawString("Voltage: " + dF.format(Math.abs(highlighted.pd) / Math.pow(10, Math.floor(Math.log10(Math.abs(highlighted.pd))))) + " x10     V", 55, 648);
				g.setFont(new Font("Arial" , 1 , 15));
				g.drawString("" + (int)Math.floor(Math.log10(highlighted.pd)), 197 ,640 );
				g.setFont(new Font("Arial" , 1 , 17));
				if (highlighted.current != 0) {
					g.drawString("Current: " + dF.format(Math.abs(highlighted.current) / Math.pow(10, Math.floor(Math.log10(Math.abs(highlighted.current))))) + " x10     A", 55, 693);
					g.setFont(new Font("Arial" , 1 , 15));
					
					g.drawString("" + (int)Math.floor(Math.log10(Math.abs(highlighted.current))), 199 ,685 );
					g.setFont(new Font("Arial" , 1 , 17));
				}
				else {
					g.drawString("Current: 0.000 A", 55, 693);
				}
			}
			else { // for closed switches and ammeters
				g.setColor(black);
				if (highlighted.current != 0) {
					g.drawString("Current: " + dF.format(Math.abs(highlighted.current) / Math.pow(10, Math.floor(Math.log10(Math.abs(highlighted.current))))) + " x10     A", 55, 648);
					g.setFont(new Font("Arial" , 1 , 15));
					g.drawString("" + (int)Math.floor(Math.log10(Math.abs(highlighted.current))), 199 ,640 );
					g.setFont(new Font("Arial" , 1 , 17));
				}
				else {
					g.drawString("Current: 0.000 A", 55, 648);
				}
			}
		}
		else {
			g.setColor(black);
			g.fillRoundRect(62, 497, 276, 300, 25, 25);
			g.setColor(white);
			g.fillRoundRect(65, 500, 270, 294, 20, 20);
			g.setColor(black);
			g.drawString("Select a component", 80, 600);
		}
		
		g.setFont(new Font("Arial" , 1 , 12));
		g.setColor(white);
		g.fillRect(400,0,1200,1000); //30x25 grid
		g.setColor(lightGray);
		for (int i = 0 ; i <= 1640 ; i += 40) {
			g.drawLine(i + 400, 0, i + 400, 1000);
		}
		for (int i = 0 ; i <= 1040 ; i += 40) {
			g.drawLine(400, i, 1600, i);
		}
		g.setColor(black);
	
		for (int i = 0 ; i < cellList.size() ; i ++) { //draws cells
			Cell current = cellList.get(i);
			
			if (cellList.get(i).isSwitch) {
				//g.setColor(green);
				//g.drawString(Math.abs(Math.round(current.current*10)/10.0) + "A", 400 + current.x*40, 79 + current.y*40);
				drawSwitch(current.x*40 + 400, current.y*40, current.angle, black, false);//closed switch
				//g.setColor(black);
			}
			else if (cellList.get(i).isMeter) {
				drawAmmeter(current.x*40 + 400, current.y*40, current.angle, black, cellList.get(i).current);//closed switch
			}
			else {
				//g.drawString(current.pd + "V", 400 + current.x*40, 12 + current.y*40);
				//g.setColor(green);
				//g.drawString(Math.abs(Math.round(current.current*10)/10.0) + "A", 400 + current.x*40, 79 + current.y*40);
				drawCell(current.x*40 + 400, current.y*40, current.angle, black);
				//g.setColor(black);
			}
		}
		g.setColor(black);
		for (int i = 0 ; i < resList.size() ; i ++) { //draws resistors
			Resistor current = resList.get(i);
			if (resList.get(i).isSwitch) {
				//g.setColor(green);
				//g.drawString(Math.abs(Math.round(current.current*10)/10.0) + "A", 400 + current.x*40, 79 + current.y*40);
				drawSwitch(current.x*40 + 400, current.y*40, current.angle, black, true);//open switch
				//g.setColor(black);
			}
			else if (resList.get(i).isMeter) {
				drawVoltmeter(current.x*40 + 400, current.y*40, current.angle, black, resList.get(i).pd);
			}
			else {
				//g.drawString(current.res + "Ω", 400 + current.x*40, 12 + current.y*40);
				//g.setColor(green);
				//g.drawString(Math.abs(Math.round(current.current*10)/10.0) + "A", 400 + current.x*40, 79 + current.y*40);
				//g.setColor(red);
				//g.drawString(Math.abs(Math.round(current.pd*100)/100.0) + "V", 448 + current.x*40, 79 + current.y*40);
				drawRes(current.x*40 + 400, current.y*40, current.angle, black);
				//g.setColor(black);
			}
		}
		g.setColor(black);
		if (drag != 0) { //if something is being dragged
			if (drag == 1) { //Component being dragged
				if (mouseX < 420 || mouseY < 20 || mouseX > 1580 || mouseY > 980) { //Cell on toolbox
					drawSelected(mouseX - 40, mouseY - 40, selected.angle, Color.RED);
					placeable = false;
				}
				else {
					boolean collision = false;
					for(int i = 0 ; i < cellList.size() && collision == false ; i ++) { //Checks for collisions with cells
						if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 60 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 60 && cellList.get(i) != selected ) {
							collision = true;
						}
					}
					for(int i = 0 ; i < resList.size() && collision == false ; i ++) { //Checks for collisions with resistors
						if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 60 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 60 && resList.get(i) != selected) {
							collision = true;
						}
					}
					if (collision) {
						drawSelected((int)(Math.round(mouseX/40.0))*40-40,(int)(Math.round(mouseY/40.0))*40-40, selected.angle, Color.red);
						placeable = false;
					}
					else {
						drawSelected((int)(Math.round(mouseX/40.0))*40-40,(int)(Math.round(mouseY/40.0))*40-40, selected.angle, Color.green);
						placeable = true;
					}
				}
			}
			else if (drag == 3) { //connecting a component
				g.setStroke(new BasicStroke(3));
				if(selected.angle == 1 && port1 || selected.angle == 3 && port1 == false) { //Connects to Bottom
					g.drawLine((int)(Math.round(mouseX/40.0))*40,(int)(Math.round(mouseY/40.0))*40, 440 + selected.x*40, 80 + selected.y*40);
					g.fillOval(435+ selected.x * 40, 75 + selected.y * 40, 10, 10);
					
				}
				else if (selected.angle == 3 && port1 || selected.angle == 1 && port1 == false){ //Connects to Top
					g.drawLine((int)(Math.round(mouseX/40.0))*40,(int)(Math.round(mouseY/40.0))*40, 440 + selected.x*40, selected.y*40);
					g.fillOval(435+ selected.x * 40, selected.y * 40 - 5, 10, 10);
				}
				else if (selected.angle == 0 && port1 || selected.angle == 2 && port1 == false) { //Connects to Right
					g.drawLine((int)(Math.round(mouseX/40.0))*40,(int)(Math.round(mouseY/40.0))*40, 480 + selected.x*40, 40 + selected.y*40);
					g.fillOval(475+ selected.x * 40, 35 + selected.y * 40, 10, 10);
				}
				else { //Connects to Left
					g.drawLine((int)(Math.round(mouseX/40.0))*40,(int)(Math.round(mouseY/40.0))*40, 400 + selected.x*40, 40 + selected.y*40);
					g.fillOval(395+ selected.x * 40, 35 + selected.y * 40, 10, 10);
				}
				g.setStroke(new BasicStroke(1));
				g.fillOval((int)(Math.round(mouseX/40.0))*40 - 5,(int)(Math.round(mouseY/40.0))*40 - 5, 10, 10);
			}
		}
		for(int i = 0 ; i < cellList.size() ; i ++) { //Checks for cells to highlight ports
			if (((Math.abs(mouseX - (cellList.get(i).x*40 + 400)) < 30 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 30) || (Math.abs(mouseX - (cellList.get(i).x*40 + 480)) < 30 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 30)) && cellList.get(i).angle % 2 == 0) { //horizontal cell
				g.fillOval(395 + cellList.get(i).x * 40, 35 + cellList.get(i).y * 40, 10, 10);
				g.fillOval(475 + cellList.get(i).x * 40, 35 + cellList.get(i).y * 40, 10, 10);
			}
			else if (((Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 30 && Math.abs(mouseY - (cellList.get(i).y*40)) < 30) || (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 30 && Math.abs(mouseY - (cellList.get(i).y*40 + 80)) < 30)) && cellList.get(i).angle % 2 == 1) {//vertical cell
				g.fillOval(435 + cellList.get(i).x * 40, cellList.get(i).y * 40 - 5, 10, 10);
				g.fillOval(435 + cellList.get(i).x * 40, 75 + cellList.get(i).y * 40, 10, 10);
			}
		}
		for(int i = 0 ; i < resList.size() ; i ++) { //Checks for resistors to highlight ports
			if (((Math.abs(mouseX - (resList.get(i).x*40 + 400)) < 30 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 30) || (Math.abs(mouseX - (resList.get(i).x*40 + 480)) < 30 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 30)) && resList.get(i).angle % 2 == 0) { //horizontal cell
				g.fillOval(395 + resList.get(i).x * 40, 35 + resList.get(i).y * 40, 10, 10);
				g.fillOval(475 + resList.get(i).x * 40, 35 + resList.get(i).y * 40, 10, 10);
			}
			else if (((Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 30 && Math.abs(mouseY - (resList.get(i).y*40)) < 30) || (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 30 && Math.abs(mouseY - (resList.get(i).y*40 + 80)) < 30)) && resList.get(i).angle % 2 == 1) {//vertical cell
				g.fillOval(435 + resList.get(i).x * 40, resList.get(i).y * 40 - 5, 10, 10);
				g.fillOval(435 + resList.get(i).x * 40, 75 + resList.get(i).y * 40, 10, 10);
			}
		}
		g.setStroke(new BasicStroke(3));
		for (int i = 0 ; i < nodeList.size() ; i ++) { //draws nodes
			placeNode(nodeList.get(i), 0);
			maxX = 40;
			maxY = 40;
			minX = 0;
			minY = 0;
			avgX = 0;
			avgY = 0;
			vertCount = 0;
		}
		if (cutting) { //draws wire cutter mouse icon
			g.setColor(Color.red);
			g.setStroke(new BasicStroke(4));
			g.drawOval(mouseX - 20, mouseY - 20, 40, 40);
			g.drawLine(mouseX - 13, mouseY - 13, mouseX + 13, mouseY + 13);
			g.setStroke(new BasicStroke(1));
		}
	}
	public void mouseClicked(MouseEvent e) {
		if (mouseX > 400 && !cutting) { //interactions with the board when not cutting
			for(int i = 0 ; i < cellList.size() ; i ++) { //checks for clicking on cell bodies
				if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 32 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 32) {
					if (e.getButton() == MouseEvent.BUTTON3) { //right click
						cellList.get(i).angle = (cellList.get(i).angle + 1) % 4;//rotates object clockwise (mod prevents angle being > 3)
					}
					else { //left click
						highlighted = cellList.get(i);
					}
				}
			}
			for(int i = 0 ; i < resList.size() ; i ++) { //checks for clicking on resistor bodies
				if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 32 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 32) {
					if (e.getButton() == MouseEvent.BUTTON3) { //right click
						resList.get(i).angle = (resList.get(i).angle + 1) % 4;
					}
					else { //left click
						highlighted = resList.get(i);
					}
				}
			}
		}
		else if (mouseX > 400) {//cutting is true
			for(int i = 0 ; i < cellList.size() ; i ++) { //checks for clicking on cell ports
				if (Math.abs(mouseX - (cellList.get(i).x*40 + 400)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 20 && cellList.get(i).angle % 2 == 0) {
					if (cellList.get(i).angle == 2 && cellList.get(i).port1 != null ) {
						cellList.get(i).port1.sever(cellList.get(i), false);
					}
					else if (cellList.get(i).angle == 0 && cellList.get(i).port2 != null){
						cellList.get(i).port2.sever(cellList.get(i), false);
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 480)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 20 && cellList.get(i).angle % 2 == 0) {
					if (cellList.get(i).angle == 2 && cellList.get(i).port2 != null) {
						cellList.get(i).port2.sever(cellList.get(i), false);
					}
					else if (cellList.get(i).angle == 0 && cellList.get(i).port1 != null){
						cellList.get(i).port1.sever(cellList.get(i), false);
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40 + 80)) < 20 && cellList.get(i).angle % 2 == 1) {
					if (cellList.get(i).angle == 3 && cellList.get(i).port2 != null) {
						cellList.get(i).port2.sever(cellList.get(i), false);
					}
					else if (cellList.get(i).angle == 1 && cellList.get(i).port1 != null){
						cellList.get(i).port1.sever(cellList.get(i), false);
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40)) < 20 && cellList.get(i).angle % 2 == 1) {
					if (cellList.get(i).angle == 3 && cellList.get(i).port1 != null) {
						cellList.get(i).port1.sever(cellList.get(i), false);
					}
					else if (cellList.get(i).angle == 1 && cellList.get(i).port2 != null){
						cellList.get(i).port2.sever(cellList.get(i), false);
					}
				}
			}
			for(int i = 0 ; i < resList.size() ; i ++) { //checks for clicking on resistor ports
				if (Math.abs(mouseX - (resList.get(i).x*40 + 400)) < 20 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 20 && resList.get(i).angle % 2 == 0) {
					if (resList.get(i).angle == 2 && resList.get(i).port1 != null) {
						resList.get(i).port1.sever(resList.get(i), false);
					}
					else if (resList.get(i).angle == 0 && resList.get(i).port2 != null){
						resList.get(i).port2.sever(resList.get(i), false);
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 480)) < 20 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 20 && resList.get(i).angle % 2 == 0) {
					if (resList.get(i).angle == 2 && resList.get(i).port2 != null) {
						resList.get(i).port2.sever(resList.get(i), false);
					}
					else if (resList.get(i).angle == 0 && resList.get(i).port1 != null){
						resList.get(i).port1.sever(resList.get(i), false);
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (resList.get(i).y*40 + 80)) < 20 && resList.get(i).angle % 2 == 1) {
					if (resList.get(i).angle == 3 && resList.get(i).port2 != null) {
						resList.get(i).port2.sever(resList.get(i), false);
					}
					else if (resList.get(i).angle == 1 && resList.get(i).port1 != null){
						resList.get(i).port1.sever(resList.get(i), false);
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (resList.get(i).y*40)) < 20 && resList.get(i).angle % 2 == 1) {
					if (resList.get(i).angle == 3 && resList.get(i).port1 != null) {
						resList.get(i).port1.sever(resList.get(i), false);
					}
					else if (resList.get(i).angle == 1 && resList.get(i).port2 != null){
						resList.get(i).port2.sever(resList.get(i), false);
					}
				}
			}
		}
		else if ( 270 < mouseX && mouseX < 350 && 570 < mouseY && mouseY < 600 && highlighted != null) { //edit button pressed
			editor.setComponent(highlighted);
		}
		else if ( 170 < mouseX && mouseX < 250 && 570 < mouseY && mouseY < 600 && highlighted != null) { //delete button pressed
			highlighted.delete();
			highlighted = null;
		}
		//(115, 715, 170, 30, 15, 15);
		else if ( 115 < mouseX && mouseX < 285 && 715 < mouseY && mouseY < 745 && highlighted != null) { //toggle button pressed for a switch
			highlighted = highlighted.toggleSwitch();
		}
		else if ( 235 < mouseX && mouseX < 335 && 340 < mouseY && mouseY < 440) { //wire cutter button pressed
			cutting = !cutting; //toggles wire cutting variable
		}
		else if ( 125 < mouseX && mouseX < 275 && 850 < mouseY && mouseY < 890 ) { //Simulate button pressed
			try {
				Main.MNA();
			} catch (Exception error) {
				JOptionPane.showMessageDialog(this, "Invalid Circuit!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			repaint();
		}
	}
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		placeable = false;
		if (cutting) {
			return;
		}
		if(mouseX < 400) { //For interactions with the toolbar
			if ( 65 < mouseX && mouseX < 165 && 100 < mouseY && mouseY < 200) { //pressing on cell button
				drag = 1;
				selected = new Cell(0,-2);
			}
			else if ( 235 < mouseX && mouseX < 335 && 100 < mouseY && mouseY < 200) { //pressing on resistor button
				drag = 1;
				selected = new Resistor(0,-2);
			}
			else if ( 65 < mouseX && mouseX < 165 && 220 < mouseY && mouseY < 320) { //pressing on ammeter button
				drag = 1;
				selected = new Cell(0,-2, false);
			}
			else if ( 235 < mouseX && mouseX < 335 && 220 < mouseY && mouseY < 320) { //pressing on voltmeter button
				drag = 1;
				selected = new Resistor(0,-2, false);
			}
			else if ( 65 < mouseX && mouseX < 165 && 340 < mouseY && mouseY < 440) { //pressing on switch button
				drag = 1;
				selected = new Resistor(0,-2, true);
			}
		}
		else { //for interactions with the board
			for(int i = 0 ; i < cellList.size() ; i ++) { //checks for clicking on cell ports or bodies
				if (Math.abs(mouseX - (cellList.get(i).x*40 + 400)) < 12 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 12 && cellList.get(i).angle % 2 == 0) {
					drag = 3;
					selected = cellList.get(i);
					if (cellList.get(i).angle == 2) {
						port1 = true;
					}
					else {
						port1 = false;
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 480)) < 12 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 12 && cellList.get(i).angle % 2 == 0) {
					drag = 3;
					selected = cellList.get(i);
					if (cellList.get(i).angle == 2) {
						port1 = false;
					}
					else {
						port1 = true;
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 12 && Math.abs(mouseY - (cellList.get(i).y*40)) < 12 && cellList.get(i).angle % 2 == 1) {
					drag = 3;
					selected = cellList.get(i);
					if (cellList.get(i).angle == 3) {
						port1 = true;
					}
					else {
						port1 = false;
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 12 && Math.abs(mouseY - (cellList.get(i).y*40 + 80)) < 12 && cellList.get(i).angle % 2 == 1) {
					drag = 3;
					selected = cellList.get(i);
					if (cellList.get(i).angle == 3) {
						port1 = false;
					}
					else {
						port1 = true;
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 32 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 32) {
					drag = 1;
					selected = cellList.get(i);
				}
			}
			for(int i = 0 ; i < resList.size() ; i ++) { //checks for clicking on resistor ports
				if (Math.abs(mouseX - (resList.get(i).x*40 + 400)) < 12 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 12 && resList.get(i).angle % 2 == 0) {
					drag = 3;
					selected = resList.get(i);
					if (resList.get(i).angle == 2) {
						port1 = true;
					}
					else {
						port1 = false;
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 480)) < 12 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 12 && resList.get(i).angle % 2 == 0) {
					drag = 3;
					selected = resList.get(i);
					if (resList.get(i).angle == 2) {
						port1 = false;
					}
					else {
						port1 = true;
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 12 && Math.abs(mouseY - (resList.get(i).y*40)) < 12 && resList.get(i).angle % 2 == 1) {
					drag = 3;
					selected = resList.get(i);
					if (resList.get(i).angle == 3) {
						port1 = true;
					}
					else {
						port1 = false;
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 12 && Math.abs(mouseY - (resList.get(i).y*40 + 80)) < 12 && resList.get(i).angle % 2 == 1) {
					drag = 3;
					selected = resList.get(i);
					if (resList.get(i).angle == 3) {
						port1 = false;
					}
					else {
						port1 = true;
					}
				}
				if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 32 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 32) {
					drag = 1;
					selected = resList.get(i);
				}
			}
		}
		repaint();
	}
	public void mouseReleased(MouseEvent e) {
		if(drag == 1) { //placing a cell or resistor
			if (placeable) {
				selected.x = (int)(Math.round((mouseX-400)/40.0))-1;
				selected.y = (int)(Math.round(mouseY/40.0))-1;
			}
			else if(selected.y < 0) { //if a new component dragged but cancelled
				cellList.remove(selected);
				resList.remove(selected);
			}
		}
		else if(drag == 3) { //connecting two components
			for(int i = 0 ; i < cellList.size() ; i ++) { //checks for releasing on cell ports
				if (Math.abs(mouseX - (cellList.get(i).x*40 + 400)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 20 && cellList.get(i).angle % 2 == 0) {
					if (cellList.get(i).angle == 2) {
						selected.connect(cellList.get(i), port1, true);
					}
					else {
						selected.connect(cellList.get(i), port1, false);
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 480)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40 + 40)) < 20 && cellList.get(i).angle % 2 == 0) {
					if (cellList.get(i).angle == 2) {
						selected.connect(cellList.get(i), port1, false);
					}
					else {
						selected.connect(cellList.get(i), port1, true);
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40 + 80)) < 20 && cellList.get(i).angle % 2 == 1) {
					if (cellList.get(i).angle == 3) {
						selected.connect(cellList.get(i), port1, false);
					}
					else {
						selected.connect(cellList.get(i), port1, true);
					}
				}
				else if (Math.abs(mouseX - (cellList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (cellList.get(i).y*40)) < 20 && cellList.get(i).angle % 2 == 1) {
					if (cellList.get(i).angle == 3) {
						selected.connect(cellList.get(i), port1, true);
					}
					else {
						selected.connect(cellList.get(i), port1, false);
					}
				}
			}
			for(int i = 0 ; i < resList.size() ; i ++) { //checks for releasing on resistor ports
				if (Math.abs(mouseX - (resList.get(i).x*40 + 400)) < 20 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 20 && resList.get(i).angle % 2 == 0) {
					if (resList.get(i).angle == 2) {
						selected.connect(resList.get(i), port1, true);
					}
					else {
						selected.connect(resList.get(i), port1, false);
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 480)) < 20 && Math.abs(mouseY - (resList.get(i).y*40 + 40)) < 20 && resList.get(i).angle % 2 == 0) {
					if (resList.get(i).angle == 2) {
						selected.connect(resList.get(i), port1, false);
					}
					else {
						selected.connect(resList.get(i), port1, true);
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (resList.get(i).y*40 + 80)) < 20 && resList.get(i).angle % 2 == 1) {
					if (resList.get(i).angle == 3) {
						selected.connect(resList.get(i), port1, false);
					}
					else {
						selected.connect(resList.get(i), port1, true);
					}
				}
				else if (Math.abs(mouseX - (resList.get(i).x*40 + 440)) < 20 && Math.abs(mouseY - (resList.get(i).y*40)) < 20 && resList.get(i).angle % 2 == 1) {
					if (resList.get(i).angle == 3) {
						selected.connect(resList.get(i), port1, true);
					}
					else {
						selected.connect(resList.get(i), port1, false);
					}
				}
			}
		}
		drag = 0;
		selected = null;
		repaint();
	}
	public void mouseEntered(MouseEvent e) {	
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
}
