package gui;

//import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.GraphicsConfiguration;
//import java.awt.GraphicsDevice;
//import java.awt.GraphicsEnvironment;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.Transparency;
import java.awt.image.BufferedImage;
//import java.awt.image.ImageObserver;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel {
//    private Image sourceImage;
    private BufferedImage image;
//    private boolean imageLoaded = false;
//    private ImageObserver myImageObserver;
    
//    private int scaledWidth;
//    private int scaledHeight;
    
	public SimulationPanel(){
		initPanel();
	}
	
	private void initPanel(){
		setLayout(null);
		setOpaque(false);				
	}
	
//	public void loadImage(String imageURL){
//    	myImageObserver = new ImageObserver() {
//			public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
//				if ((flags & ALLBITS) != 0) {
//					imageLoaded = true;
//					System.out.println("Image loading finished!");
//					return false;
//				}
//				return true;
//			}
//    	};	
//    	
//    	sourceImage = Toolkit.getDefaultToolkit().getImage(imageURL);
//    	sourceImage.getWidth(myImageObserver);
//    	
//		while (!imageLoaded) {
//			try {
//				repaint();
//				Thread.sleep(100);
//			}
//			catch (InterruptedException e) {}
//		}	
//
//		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
//		GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
//
//		image = graphicsConfiguration.createCompatibleImage(sourceImage.getWidth(null), sourceImage.getHeight(null), Transparency.BITMASK);
//	}
	
//	public BufferedImage resizeImage(BufferedImage image) {
//		scaleImage(image.getWidth(), image.getHeight());
//        final BufferedImage bufferedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
//        final Graphics2D graphics2D = bufferedImage.createGraphics();
//        graphics2D.setComposite(AlphaComposite.Src);
//        graphics2D.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
//        graphics2D.dispose();
// 
//        return bufferedImage;
//    }
	
//	public BufferedImage getImage(){
//		return image;
//	}
	
//	public void scaleImage(int width, int height) {
//	    int bound_width = 450;
//	    int bound_height = 400;
//	    scaledWidth = width;
//	    scaledHeight = height;
//	    
//	    if (width > bound_width) {
//	        scaledWidth = bound_width;
//	        scaledHeight = (scaledWidth * height) / width;
//	    }
//
//	    if (scaledHeight > bound_height) {
//	        scaledHeight = bound_height;
//	        scaledWidth = (scaledHeight * width) / height;
//	    }
//	}
	
	public void drawImage(BufferedImage image){
		this.image = image;
		repaint();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int x = 0;
		int y = 0;

		if(image.getWidth() > image.getHeight()){
			y = (400 - image.getHeight()) / 2;
		}
		else if(image.getHeight() > image.getWidth()){
			x = (450 - image.getWidth()) / 2;
		}
		else{
			x = (450 - image.getWidth()) / 2;
			y = (400 - image.getHeight()) / 2;			
		}
		g2d.drawImage(image, x, y, this);
	}
}
