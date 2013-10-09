package sift;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Vector;

import mpi.cbg.fly.Feature;
import mpi.cbg.fly.SIFT;


public class FeatureExtraction{
	public static LinkedList<SIFTLines> lines = new LinkedList<SIFTLines>();
	
	public static void processSIFT(BufferedImage image) {
		lines.clear();
		try {
			int pixels[] = toPixelsTab(image);			
			Vector<Feature> features = SIFT.getFeatures(image.getWidth(), image.getHeight(), pixels);

			for (Feature f : features)
				lines.add(new SIFTLines(f.location[0], f.location[1], f.scale, f.orientation));

		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
		}
	}
	
	private static int[] toPixelsTab (BufferedImage picture) {
		return picture.getRGB(0, 0, picture.getWidth(), picture.getHeight(), null, 0, picture.getWidth());
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
//		g2.drawImage(image, 3, 26, this);

		for (SIFTLines l : lines){
			g2.setStroke(new BasicStroke(2f));
			g2.setColor(Color.GREEN);
			g2.drawLine(l.x1, l.y1, l.x2, l.y2);

			g2.setStroke(new BasicStroke(4f));
			g2.setColor(Color.YELLOW);
			g2.drawLine(l.x1, l.y1, l.x1, l.y1);	
		}
	}
}
