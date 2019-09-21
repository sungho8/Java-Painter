package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Line extends Shape{
	public Line(Color currentColor,int thick) {
		super(currentColor,thick);
	}
	
	public void draw(Graphics g) {
		setPoint();
		g.setColor(strokeColor);
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform transform = g2.getTransform();
		
		if(this.isRotated()) {
			g2.rotate(angle,(startP.x+endP.x)/2, (startP.y+endP.y)/2);
		}
		
		if(endP!=null) {
			g2.setStroke(new BasicStroke(thick));
			g.drawLine(startP.x, startP.y, endP.x, endP.y);
		}
		g2.setStroke(new BasicStroke(3));
		g2.setTransform(transform);
		handleBox(g);
	}
}