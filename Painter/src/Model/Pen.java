package Model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Pen extends Shape{
	ArrayList<Line> freeLine = new ArrayList<Line>();
	Point sp;
	
	public Pen(Color currentColor,int thick) {
		super(currentColor,thick);
	}

	@Override
	public void draw(Graphics g) {
		setPoint();
		Graphics2D g2 = (Graphics2D)g;
		if(endP!=null) {
			Line l = new Line(strokeColor,thick);
			l.setStartP(sp);	l.setEndP(endP);
			freeLine.add(l);
			for(Line i : freeLine) {
				g2.setStroke(new BasicStroke(thick));
				g.setColor(strokeColor);
				g.drawLine(i.startP.x, i.startP.y, i.endP.x, i.endP.y);
			}
			sp = endP;
		}
	}
	
	public void setStartP(Point startP) {
		if(this.startP == null)
			this.startP = startP;
		sp = startP;
	}
}
