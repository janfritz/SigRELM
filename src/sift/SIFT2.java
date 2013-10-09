package sift;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import de.lmu.ifi.dbs.jfeaturelib.features.Sift;

public class SIFT2 {
	public static List<double[]> result;
    
    public static void extractSIFTFeatures(BufferedImage img){    
    	try{
		    ImageProcessor ip = new ColorProcessor(img);
		    ip = ip.convertToByte(false);
		    Sift sift = new Sift(new File("binaries/siftWin32.exe"));
		    sift.run(ip);
		    
		    //note that result.get(i)[0..3] represent y/x/scale/rotation, respectively.
		    result = sift.getFeatures();
		    for(int ctr = 0; ctr < result.size(); ctr++){
//		  	  System.out.println("Feature: (y)" + result.get(ctr)[0] + ", (x)" + result.get(ctr)[1] + ", (s)" + result.get(ctr)[2] + ", (r)" + result.get(ctr)[3]);
		    }
//		    System.out.println(result.size() + " features extracted...");
    	}catch(Exception ex){}
    }
}
