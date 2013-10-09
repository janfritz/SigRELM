package preprocess;


import java.awt.Color;
import java.awt.image.BufferedImage;


public class Grayscale {
	
    public BufferedImage image;
	
	// The luminance method
    public static BufferedImage toGray(BufferedImage original) { 
        int alpha, red, green, blue;
        int newPixel;
        
        BufferedImage lum = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
 
        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) { 
                // Get pixels by R, G, B
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();
 
                red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
                // Return back to original format
                newPixel = RGB.colorToRGB(alpha, red, red, red);
 
                // Write pixels into image
                lum.setRGB(i, j, newPixel); 
            }
        } 
        return lum; 
    }

}
