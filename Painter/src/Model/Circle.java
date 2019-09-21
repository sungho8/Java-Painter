package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Circle extends Shape{
    public Circle(Color currentColor,int thick) {
        super(currentColor,thick);
    }
    
	public void draw(Graphics g) {
		setPoint();
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform transform = g2.getTransform();
		
		if(this.isRotated()) {
			g2.rotate(angle,(startP.x+endP.x)/2, (startP.y+endP.y)/2);	//rotate
		}
		
		if(this.isFill()) {
			g.setColor(fillColor);
			g.fillOval(minX, minY, width, height);						//fill
		}
		
		if(endP!=null) {
			g2.setStroke(new BasicStroke(thick));
			g.setColor(strokeColor);
			g.drawOval(minX, minY, width, height);
		}
		g2.setTransform(transform);
		handleBox(g);
	}

}
