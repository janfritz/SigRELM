package preprocess;
 

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
 
/**
 *
 * @author nayef
 */
public class Thinning { 
	private static int[][] imageData;
	
	public static BufferedImage thinEdges(BufferedImage image){
		setImageData(image);
        imageData = doZhangSuenThinning(imageData);
        for (int y = 0; y < imageData.length; y++) {        	 
            for (int x = 0; x < imageData[y].length; x++) { 
                if (imageData[y][x] == 1) {
                    image.setRGB(x, y, Color.BLACK.getRGB()); 
                } else {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                } 
            }
        }
        return image;
	}
	
	private static void setImageData(BufferedImage image){
		imageData = new int[image.getHeight()][image.getWidth()];
//        Color c;
        for (int y = 0; y < imageData.length; y++) {
            for (int x = 0; x < imageData[y].length; x++) {
 
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    imageData[y][x] = 1;
                } else {
                    imageData[y][x] = 0;
 
                }
            }
        }
	}
	
    public static int[][] doZhangSuenThinning(int[][] binaryImage) {
        int a, b;
 
        LinkedList<Point> pointsToChange = new LinkedList<Point>();
        boolean hasChange;
 
        do { 
            hasChange = false;
            for (int y = 1; y + 1 < binaryImage.length; y++) {
                for (int x = 1; x + 1 < binaryImage[y].length; x++) {
                    a = getA(binaryImage, y, x);
                    b = getB(binaryImage, y, x);
                    if ( binaryImage[y][x]==1 && 2 <= b && b <= 6 && a == 1
                            && (binaryImage[y - 1][x] * binaryImage[y][x + 1] * binaryImage[y + 1][x] == 0)
                            && (binaryImage[y][x + 1] * binaryImage[y + 1][x] * binaryImage[y][x - 1] == 0)) {
                        pointsToChange.add(new Point(x, y));
                        //binaryImage[y][x] = 0;
                        hasChange = true;
                    }
                }
            }
            
            for (Point point : pointsToChange) {
                binaryImage[point.getY()][point.getX()] = 0;
            }
 
            pointsToChange.clear();
 
            for (int y = 1; y + 1 < binaryImage.length; y++) {
                for (int x = 1; x + 1 < binaryImage[y].length; x++) {
                    a = getA(binaryImage, y, x);
                    b = getB(binaryImage, y, x);
                    if ( binaryImage[y][x]==1 && 2 <= b && b <= 6 && a == 1
                            && (binaryImage[y - 1][x] * binaryImage[y][x + 1] * binaryImage[y][x - 1] == 0)
                            && (binaryImage[y - 1][x] * binaryImage[y + 1][x] * binaryImage[y][x - 1] == 0)) {
                        pointsToChange.add(new Point(x, y));
 
                        hasChange = true;
                    }
                }
            }
 
            for (Point point : pointsToChange) {
                binaryImage[point.getY()][point.getX()] = 0;
            }
 
            pointsToChange.clear();
 
        } while (hasChange);
 
        return binaryImage;
    }
 
    private static class Point { 
        private int x, y;
 
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
 
        public int getX() {
            return x;
        }
 
        @SuppressWarnings("unused")
		public void setX(int x) {
            this.x = x;
        }
 
        public int getY() {
            return y;
        }
 
        @SuppressWarnings("unused")
		public void setY(int y) {
            this.y = y;
        }
    };
 
    private static int getA(int[][] binaryImage, int y, int x) { 
        int count = 0;
        //p2 p3
        if (binaryImage[y - 1][x] == 0 && binaryImage[y - 1][x + 1] == 1) {
            count++;
        }
        //p3 p4
        if (binaryImage[y - 1][x + 1] == 0 && binaryImage[y][x + 1] == 1) {
            count++;
        }
        //p4 p5
        if (binaryImage[y][x + 1] == 0 && binaryImage[y + 1][x + 1] == 1) {
            count++;
        }
        //p5 p6
        if (binaryImage[y + 1][x + 1] == 0 && binaryImage[y + 1][x] == 1) {
            count++;
        }
        //p6 p7
        if (binaryImage[y + 1][x] == 0 && binaryImage[y + 1][x - 1] == 1) {
            count++;
        }
        //p7 p8
        if (binaryImage[y + 1][x - 1] == 0 && binaryImage[y][x - 1] == 1) {
            count++;
        }
        //p8 p9
        if (binaryImage[y][x - 1] == 0 && binaryImage[y - 1][x - 1] == 1) {
            count++;
        }
        //p9 p2
        if (binaryImage[y - 1][x - 1] == 0 && binaryImage[y - 1][x] == 1) {
            count++;
        }
 
        return count;
    }
 
    private static int getB(int[][] binaryImage, int y, int x) { 
        return binaryImage[y - 1][x] + binaryImage[y - 1][x + 1] + binaryImage[y][x + 1]
                + binaryImage[y + 1][x + 1] + binaryImage[y + 1][x] + binaryImage[y + 1][x - 1]
                + binaryImage[y][x - 1] + binaryImage[y - 1][x - 1];
    }
    
}