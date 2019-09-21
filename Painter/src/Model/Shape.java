package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Vector;

import Controller.Controller;
import View.View;

public abstract class Shape implements Serializable {
	Controller controller;
	View view;

	Point startP;  
	Point endP;    
	int minX;
	int minY;
	int maxX;
	int maxY; 
	int width;
    int height;
    
    Color strokeColor;
    Color fillColor;
    
    Point[] handle;
    Point[] rHandle;
    int handleNum = 0;
    int area = 15;
    int thick;
    
    public double angle;
    
    Point center;
	
    public boolean isRotate = false;
	boolean isSelect = false;
	boolean isScale = false;
	boolean isFill = false;
		
	public Shape(Color currentColor,int thick){
		strokeColor = currentColor;
		this.thick = thick ;
		handle = new Point[4];
		rHandle = new Point[4];
		for(int i =0;i<4;i++) {
			rHandle[i] = new Point(0,0);
		}
		angle = 0;
	}
	
	public void setPoint(){
		if(endP != null) {
			minX = Math.min(startP.x, endP.x);
			minY = Math.min(startP.y, endP.y);
			maxX = Math.max(startP.x, endP.x);
			maxY = Math.max(startP.y, endP.y);
			width = Math.abs(minX-maxX);
			height = Math.abs(minY-maxY);

			center = new Point((startP.x+endP.x)/2, (startP.y+endP.y)/2);
			
			handle[0] = new Point(minX, minY);
			handle[1] = new Point(maxX, minY);
			handle[2] = new Point(maxX, maxY);
			handle[3] = new Point(minX, maxY);
			
			area = (width+height)/25;
			
			rotateHandle();
		}
		
	}
	
	public Point getStartP() {
		return startP;
	}

	public void setStartP(Point startP) {
		this.startP = startP;
	}

	public Point getEndP() {
		return endP;
	}

	public void setEndP(Point endP) {
		this.endP = endP;
	}
	
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	
	public void setStroke(Color color) {
		strokeColor = color;
	}
	
	public boolean getSelect() {
		return this.isSelect;
	}
	
	public boolean isFill() {
		return isFill;
	}

	public void setFill(boolean isFill) {
		this.isFill = isFill;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public void setThick(int thick) {
		this.thick = thick;
	}

	public void computeAngle(Point p1, Point p2) {
		double angle1 = Math.atan2(center.x - p1.x, center.y - p1.y);
		double angle2 = Math.atan2(center.x - p2.x, center.y - p2.y);
		this.angle = angle1-angle2;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void rotateHandle() {
		for(int i = 0; i < 4; i++){
			rHandle[i].x = (int)((handle[i].x - center.x) * Math.cos(angle) - (handle[i].y - center.y) * Math.sin(angle) + center.x);
			rHandle[i].y = (int)((handle[i].x - center.x) * Math.sin(angle) + (handle[i].y - center.y) * Math.cos(angle) + center.y);
		}
	}
	
	public boolean isRotated () {
        if (this.angle == 0) {
            return false;
        }
        return true;
    }
	
	public void handleBox(Graphics g){
		int size = 10;
		g.setColor(Color.BLACK);
		if(isSelect) {
			for(int i = 0; i < 4; i++){
				g.fillOval(rHandle[i].x-size/2, rHandle[i].y-size/2, size, size);
			}
		}
	}
	
	public boolean select(Point p){  
		if(p.x >= minX + area & p.x <= maxX - area & p.y >= minY + area & p.y <= maxY - area){
				isSelect = true;
				return true;
		}
		return false;
	}
	
	public void move(Point p1,Point p2){
		int moveX = p2.x-p1.x;
		int moveY = p2.y-p1.y;
		
		startP.x += moveX;
		startP.y += moveY;
		endP.x += moveX;
		endP.y += moveY;
	}
	
	public boolean scaleSelect(Point p){
		for(int i = 0; i < 4; i++){
			if(p.x > rHandle[i].x-area && p.x < rHandle[i].x + area && p.y > rHandle[i].y-area && p.y < rHandle[i].y+area){
				isScale = true;
				handleNum = i;
				return true;
			}
		}
		return false;
	}
	
	public void scale(Point p1,Point p2){
		int scaleX = p2.x-p1.x;
		int scaleY = p2.y-p1.y;
		
		switch(handleNum){
			case 0:
				startP.x += scaleX;
				startP.y += scaleY;
				break;
			case 1:
				endP.x += scaleX;
				startP.y += scaleY;
				break;
			case 2:
				endP.x += scaleX;
				endP.y += scaleY;
				break;
			case 3:
				startP.x += scaleX;
				endP.y += scaleY;
				break;
		}
	}
	
	public abstract void draw(Graphics g);
}
