import acm.graphics.*;
import acm.program.*;
import java.util.*;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class BoxDiagram extends GraphicsProgram {

	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;

	public void init() {
		windowContents = new HashMap<String, GCompound>();
		addControlBar();
		addMouseListeners();
		addActionListeners();
	}

	// Adds the control bar to the bottom of the window
	private void addControlBar() {
		add(new JLabel("Name"), SOUTH);

		textBox = new JTextField(30);
		add(textBox, SOUTH);
		textBox.addActionListener(this);

		addButton = new JButton("Add");
		add(addButton, SOUTH);

		removeButton = new JButton("Remove");
		add(removeButton, SOUTH);

		clearButton = new JButton("Clear");
		add(clearButton, SOUTH);
	}

	// Adds or removes button, or clears all if they are pressed
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (e.getSource() == addButton || e.getSource() == textBox) {
			addBox(textBox.getText());
		} else if (source == removeButton) {
			removeBox(textBox.getText());
		} else if (source == clearButton) {
			clearAll();
		}
	}

	// Clears all boxes from the window
	private void clearAll() {
		for (String key : windowContents.keySet()) {
			remove(windowContents.get(key));
		}
		windowContents.clear();
	}

	// Removes a box if the same name is in the text box
	private void removeBox(String key) {
		if (windowContents.get(key) != null) {
			remove(windowContents.get(key));
			windowContents.remove(key);
		}
	}

	// Adds the box to the window with the text in text box
	private void addBox(String name) {

		// Creates a single compound for the box and text to move together
		GCompound box = new GCompound();

		GRect rect = new GRect(BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(name);
		box.add(rect, -BOX_WIDTH / 2, -BOX_HEIGHT / 2);
		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		add(box, getWidth() / 2, getHeight() / 2);
		windowContents.put(name, box);
	}

	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		currentBox = getElementAt(last);
	}

	public void mouseDragged(MouseEvent e) {
		if (currentBox != null) {
			currentBox.move(e.getX() - last.getX(), e.getY() - last.getY());
			last = new GPoint(e.getPoint());
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (currentBox != null)
			currentBox.sendToFront();
	}

	private JTextField textBox;
	private JButton addButton;
	private JButton removeButton;
	private JButton clearButton;
	private HashMap<String, GCompound> windowContents;
	private GObject currentBox;
	private GPoint last;

}
