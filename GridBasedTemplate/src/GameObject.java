
import java.awt.*;

import javax.swing.*;


public class GameObject extends JFrame {
//	BufferedImage img= ImageIO.read(new File("file:///Users/rupalt/Downloads/GridBasedGameTemplate/src/black.jpg"));

	private ImageIcon green;

public GameObject() {
	super();
	this.green = new ImageIcon(getClass().getResource("green.jpg"));
}

public ImageIcon getGreen() {
	return green;
}

public void setGreen(ImageIcon green) {
	this.green = green;
}




	
	
}
