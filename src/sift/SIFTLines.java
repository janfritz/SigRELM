package sift;

public class SIFTLines {
	public int x1;
	public int y1;
	public int x2;
	public int y2;

	public SIFTLines(float x, float y, double scale, double orientation){
		scale *= 6.;
		double sin = Math.sin(orientation);
		double cos = Math.cos(orientation);

		x1 = (int) x;
		y1 = (int) y;
		x2 = (int)(x - (sin - cos) * scale);
		y2 = (int)(y - (sin - cos) * scale); 
	}
	
	public int getX1(){
		return x1;
	}
	
	public int getX2(){
		return x2;
	}
	
	public int getY1(){
		return y1;
	}
	
	public int getY2(){
		return y2;
	}
}
