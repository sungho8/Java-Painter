package View;

import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ColorFrame extends JFrame {
   Container contentPane;
   
   public JColorChooser colorChooser;
   
   public ColorFrame(){
      colorChooser = new JColorChooser();      
      contentPane = getContentPane();
   }
}