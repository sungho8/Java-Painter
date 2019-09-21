package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.Shape;
import View.View;

public class FileController{
	ArrayList<Shape> model;
	int number = 0;
	
	public FileController(ArrayList<Shape> model){
		this.model = model;
	}
	
    public void doSerializable(String file) throws IOException {
    	    	
        FileOutputStream fos = new FileOutputStream(file+".ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for(int i = 0; i < model.size(); i++) {
        	oos.writeObject(model.get(i));
        }
        oos.close();
    }
    
    public void undoSerializable(String file) throws IOException, ClassNotFoundException {
    	int s = model.size();
		for(int i = 0; i < s;i++ ) {
			model.remove(0);
		}
		
        FileInputStream fis = new FileInputStream(file+".ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
	        while(ois.read() == -1){
	    		Shape shape = (Shape) ois.readObject();
	    		model.add(shape);
	        	
	        }
        }catch(Exception e) {
        	
        }
        ois.close();
         
    }

}
