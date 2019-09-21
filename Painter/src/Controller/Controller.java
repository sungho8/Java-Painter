package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import Model.*;

import View.BgFrame;
import View.ColorFrame;
import View.DrawPanel;
import View.FileFrame;
import View.NPolyFrame;
import View.View;

public class Controller{
	static ArrayList<Shape> model;
	View view;
	DrawPanel drawPanel;
	BgFrame bgFrame;
	NPolyFrame npFrame;
	FileFrame fileFrame;
	ColorFrame colorFrame;
	FileController fileController;
	
	static Color strokeColor;
	static Color fillColor;
	
	static Shape current;
	Shape clone;
	Mode mode;
	Mode cloneMode;
	Mode temp;
	double ratio = 1.0;
	static int n=5;
	String file="";
	
	
	public static Point p1;
	Point sp = new Point();
	Point ep = new Point();
	double an = 0;
	static int thick=3;
	
	public Controller(View view,ArrayList<Shape> model) {
		this.view = view;
		this.model = model;
		mode = mode.Pen;
		fileController = new FileController(this.model);
		
		strokeColor = Color.black;
		current = new Line(strokeColor,thick);
			
		drawPanel = view.getDrawPanel();
		
		MouseListener ml = new MouseListener(); 
		drawPanel.addMouseListener(ml);
		drawPanel.addMouseMotionListener(ml);
		
		//Menu
		view.menuItem1.addActionListener(new ActionListener() {	// 1.새로 만들기
			public void actionPerformed(ActionEvent arg0) {
				int s = model.size();
				for(int i = 0; i < s;i++ ) {	//모델에 있는 모든 객체 삭제
					model.remove(0);
				}
			}
		});
		
		view.menuItem2.addActionListener(new ActionListener() {	// 2.저장하기
			public void actionPerformed(ActionEvent arg0) {
				fileFrame = new FileFrame();
 	        	fileFrame.okButton3.addActionListener(new ActionListener() {  
 	    	        public void actionPerformed (ActionEvent ev) {
 	    	        	file = (fileFrame.fileName.getText());
 	    	        	fileFrame.dispose();

 						try {
							fileController.doSerializable(file);	//저장
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 	    	        }
 	        	});
			}
		});
		
		view.menuItem3.addActionListener(new ActionListener() {	//load
			public void actionPerformed(ActionEvent arg0) {
				
				fileFrame = new FileFrame();
 	        	fileFrame.okButton3.addActionListener(new ActionListener() {  
 	    	        public void actionPerformed (ActionEvent ev) {
 	    	        	file= (fileFrame.fileName.getText());
 	    	        	mode = mode.Polygon;
 	    	        	fileFrame.dispose();

 						try {
							fileController.undoSerializable(file);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 	    	        }
 	        	});
				
			}
		});
		
		//Button
		view.bgButton.addActionListener(new ActionListener() {  
 	        public void actionPerformed (ActionEvent ev) {
 	        	bgFrame = new BgFrame();
 	        	bgFrame.okButton.addActionListener(new ActionListener() {  
 	    	        public void actionPerformed (ActionEvent ev) {
 	    	        	drawPanel.setGridSize(Integer.parseInt(bgFrame.sizeText.getText()));
 	    	        	bgFrame.dispose();
 	   	        }
 	   	    });
	        }
	    });		
		
		view.penButton.addActionListener(new ActionListener() {  
 	        public void actionPerformed (ActionEvent ev) {
	        	mode = mode.Pen;
	        }
	    });
		
		view.lineButton.addActionListener(new ActionListener() {
 	        public void actionPerformed (ActionEvent ev) {
	        	mode = mode.Line;
	        }
	    });
		
		view.triangleButton.addActionListener(new ActionListener() {  
 	        public void actionPerformed (ActionEvent ev) {
 	        	mode = mode.Triangle;
	        }
	    });
		
		view.rectangleButton.addActionListener(new ActionListener() {  
 	        public void actionPerformed (ActionEvent ev) {
 	        	mode = mode.Rectangle;
	        }
	    });
		
		view.circleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				mode = mode.Circle;
			}
		});
		
		view.diamondButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				mode = mode.Diamond;
			}
		});
		
		view.polygonButton.addActionListener(new ActionListener() {  
 	        public void actionPerformed (ActionEvent ev) {
 	        	npFrame = new NPolyFrame();
 	        	npFrame.okButton2.addActionListener(new ActionListener() {  
 	    	        public void actionPerformed (ActionEvent ev) {
 	    	        	n = Integer.parseInt(npFrame.sizeText.getText());
 	    	        	mode = mode.Polygon;
 	    	        	npFrame.dispose();
 	    	        }
 	        	});
	        }
	    });
		
		view.eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent ev) {
				mode = mode.Eraser;
			}
		});
		
		view.clickButton.addActionListener(new ActionListener() {  
 	        public void actionPerformed (ActionEvent ev) {
 	        	mode = mode.Control;
	        }
	    });
		
		view.rotateButton.addActionListener(new ActionListener() {  
 	        public void actionPerformed (ActionEvent ev) {
 	        	mode = mode.Rotate;
	        }
	    });
		// 선 색깔
		view.strokeButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
              colorFrame = new ColorFrame();
               Color selectedColor = JColorChooser.showDialog(null, "Color", Color.blue); 
               if (selectedColor != null){
                   strokeColor = selectedColor;
               }    
        	   current.setStroke(strokeColor);
               view.strokeButton.setBackground(strokeColor);
           }
       });
		// 면 색깔
		view.fillButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
              colorFrame = new ColorFrame();
               Color selectedColor = JColorChooser.showDialog(null, "Color", Color.blue); 
               if (selectedColor != null){
                   fillColor = selectedColor;
               }  
               current.setFill(true);
               current.setFillColor(fillColor);
               view.fillButton.setBackground(fillColor);
           }
       });
		// 굵기버튼
		view.thicknessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				thick = Integer.parseInt(view.thicknessText.getText()) ;
				current.setThick(thick);
			}
		});
		
		// Key 리스너
		view.frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				temp = mode;	//임시로 사용중이던 모드를 저장
				
				// 각 키에 맞는 모드로 변경
				if(e.getKeyCode() == KeyEvent.VK_SPACE)
					mode = mode.CameraMove;
				
				if(e.getKeyCode() == KeyEvent.VK_CONTROL)
					mode = mode.Expand;
				
				if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					// current의 값들을 모두 저장
					sp.x = current.getStartP().x + 30;	ep.x = current.getEndP().x + 30;
					sp.y = current.getStartP().y + 30;  ep.y = current.getEndP().y + 30;
					an = current.getAngle();
					// 모드..
					if(mode.getShape() != null && mode != null)
						cloneMode = mode;
				}
				if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					System.out.println(cloneMode);
					clone = cloneMode.getShape();
					clone.setStartP(new Point(sp.x,sp.y));
					clone.setEndP(new Point(ep.x,ep.y));
					clone.setAngle(an);
					model.add(clone);
					current = model.get(model.size()-1);
				}
			}
			public void keyReleased(KeyEvent e) {
				// 이전에 사용하던 모드로 다시 변경
				mode = temp;
			}
		});
		
		// Wheel 리스너
		view.frame.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(mode == mode.Expand) {
					if(e.getWheelRotation() < 0)		// 위로 올릴때 
						magnifier(1.05,e.getPoint());	// 확대
					else								// 아래로 내릴때
						magnifier(0.95,e.getPoint());	// 축소
				}
			}
			
		});
	}
	
	// 마우스 리스너
	class MouseListener extends MouseAdapter implements MouseMotionListener{
		public void mousePressed(MouseEvent e){		
			view.frame.requestFocus();	//focus를 frame 으로
			
			// Control Mode
			if(mode == mode.Control){				
				setControl();
				for(int i = model.size() - 1; i >= 0 ; i--){
					if(model.get(i).select(e.getPoint())){
						current = model.get(i);
						break;
					}
				}
			}
			// Draw Mode
			if(mode.getShape() != null){
				current = mode.getShape();			// mode에 맞는 객체를 반환해서 current로 설정
				setControl();						// 다른 도형 선택을 품
				current.setSelect(true);			// 현재 그린거 선택
				current.setStartP(e.getPoint());	
				model.add(current);					
			}
			p1 = e.getPoint();
		}

		public void mouseDragged(MouseEvent e){
			// Draw Mode
			if(mode.getShape() != null) {
				current.setEndP(e.getPoint());
			}
			// Control Mode
			else {
				mode.doAction(p1,e.getPoint());
				
				// rotate는 p1값을 바꾸면 안됨
				if(mode != Mode.Rotate)
					p1=e.getPoint();
				
				if(mode == Mode.Eraser){
					for(int i = model.size() - 1; i >= 0 ; i--){
						if(model.get(i).select(e.getPoint())){ 
							model.remove(i);
							break;
						}
					}
				}
			}
		}
		
		public void mouseMoved(MouseEvent e){
			if(mode == mode.Control){	
				if(current.getSelect() && current.select(e.getPoint())){
					drawPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR)); 			
				}else if(current.scaleSelect(e.getPoint())){
					drawPanel.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
				}else{
					drawPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
				
//			if(mode == Mode.CameraMove) {
//				drawPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//			}
//			

		}
	}
	
	public void setControl() {
		for(int i = model.size() - 1; i >= 0 ; i--){
			model.get(i).setSelect(false);
		}
	}
	
	// ratio > 1 : 확대
	// ratio < 1 : 축소
	// focus     : 현재 마우스 위치
	public void magnifier(double ratio, Point focus) {
		for(int i = 0; i < model.size(); i++) {
			Point sp = model.get(i).getStartP();
			Point ep = model.get(i).getEndP();
			
			sp.x = (int) (focus.x + (ratio * (sp.x - focus.x)));
			sp.y = (int) (focus.y + (ratio * (sp.y - focus.y)));
			ep.x = (int) (focus.x + (ratio * (ep.x - focus.x)));
			ep.y = (int) (focus.y + (ratio * (ep.y - focus.y)));
			
			model.get(i).setStartP(sp);
			model.get(i).setEndP(ep);
		}
	}
	
	enum Mode{
		Pen(){
			public Shape getShape() {return new Pen(strokeColor,thick);}
			public void doAction(Point p1, Point p2) {}
		},
		Line(){
			public Shape getShape() {return new Line(strokeColor,thick);}
			public void doAction(Point p1, Point p2) {}
		},
		Triangle(){
			public Shape getShape() {return new Triangle(strokeColor,thick);}
			public void doAction(Point p1, Point p2) {}
		},
		Rectangle(){
			public Shape getShape() {return new Rectangle(strokeColor,thick);}
			public void doAction(Point p1, Point p2) {}
		},
		Circle(){
			public Shape getShape() {return new Circle(strokeColor,thick);}
			public void doAction(Point p1, Point p2) {}
		},
		Diamond(){
			public Shape getShape() {return new Diamond(strokeColor,thick);}
			public void doAction(Point p1, Point p2) {}
		},
		Polygon(){
			public Shape getShape() {return new N_Polygon(strokeColor,thick,n);}
			public void doAction(Point p1, Point p2) {}
		},
		Eraser(){
			public Shape getShape() { return null;}
			public void doAction(Point p1, Point p2) {}
		},
		Control(){
			public Shape getShape() {return null;}
			public void doAction(Point p1, Point p2) {
				if(current.scaleSelect(p1)){
					current.scale(p1,p2);
				}
				else if(current.select(p1)){
					current.move(p1,p2); 
				}
			}
		},
		Rotate(){
			public Shape getShape() {return null;}
			public void doAction(Point p1, Point p2) {
				current.computeAngle(p1,p2);
			}
		},
		Expand(){
			public Shape getShape() {return null;}
			public void doAction(Point p1, Point p2) {}
		},
		CameraMove(){
			public Shape getShape() {return null;}
			public void doAction(Point p1, Point p2) {
				for(int i = 0; i < model.size(); i++) {
					model.get(i).move(p1, p2);
				}
			}
		};
		abstract public Shape getShape();
		abstract public void doAction(Point p1, Point p2);
	}
}
