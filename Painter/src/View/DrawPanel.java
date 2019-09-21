package View;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import Model.Shape;

public class DrawPanel extends JPanel{
	ArrayList<Shape> model;
	
	int gridSize = 0; 
	
	public DrawPanel(ArrayList<Shape> model){
		this.model = model;
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		changeBackground(g);
		if(model.size() != 0){
			for(int i = 0; i < model.size(); i++){
				model.get(i).draw(g);
			}
		}
		repaint();
	}
	
	public void changeBackground(Graphics g){
		g.setColor(new Color(180,180,180));
		for(int i = 0 ; i < 500;i++){
			g.drawLine(0, i*gridSize, 2000, i*gridSize);
			g.drawLine(i*gridSize, 0, i*gridSize, 2000);
		}
	}
	
	public void setGridSize(int gridSize){
		this.gridSize = gridSize;
	}
	
}
