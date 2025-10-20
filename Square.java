import java.awt.Color;

public class Square {
	private int r;
	private int c;
	private Color color;
	
	public Square(int r_i, int c_i) {
		this.r = r_i;
		this.c = c_i;
	}
	
	public Square(int r_i, int c_i, Color color_i) {
		this.r = r_i;
		this.c = c_i;
		this.color = color_i;
	}
	
	public int getR() {
		return r;
	}
	public int getC() {
		return c;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color c) {
		this.color = c;
	}

}
