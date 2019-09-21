package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class N_Polygon extends Shape{
	int n = 5;
	int[] xPoints;
	int[] yPoints;
	
	double degree;
	double cos_d;
	double sin_d;
	int dx;
    int dy;
	Point center;	

    public N_Polygon(Color currentColor,int thick,int n) {
        super(currentColor,thick);
        this.n = n;
        xPoints = new int[this.n];
        yPoints = new int[this.n];
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
			g.fillPolygon(xPoints, yPoints, xPoints.length);
		}
		
		if(endP!=null) {
			xPoints[0] = (startP.x + endP.x)/2; 
		    yPoints[0] = startP.y;
			
	        xPoints[0] = (startP.x + endP.x)/2; 
	        yPoints[0] = startP.y;      
	        center = new Point((int)((startP.x + endP.x)/2),(int)((startP.y + endP.y)/2));
	        degree = 360 / n;
	  	
	        degree = Math.toRadians(degree);
		    cos_d = Math.cos(degree);
	        sin_d = Math.sin(degree);
	      
	        for(int i = 0; i < xPoints.length-1; i++ ){  
	      	    dx = xPoints[i] - center.x;
	            dy = yPoints[i] - center.y; 
	          
	            xPoints[i+1] = (int)(dx * cos_d - dy * sin_d) + (int)center.x; 
	            yPoints[i+1] = (int)(dx * sin_d + dy * cos_d) + (int)center.y;
	        }
			
			g2.setStroke(new BasicStroke(thick));
			g.setColor(strokeColor);
			g.drawPolygon(xPoints, yPoints, xPoints.length);
		}
		g2.setTransform(transform);
		handleBox(g);
	}
}

