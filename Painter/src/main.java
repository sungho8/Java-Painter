

import java.util.ArrayList;

import Model.Shape;
import Controller.Controller;
import View.View;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Shape> model = new ArrayList<Shape>();
		View view = new View(model);
		Controller controller = new Controller(view,model);
	}
}
