
public class Move {
	private int r1;
	private int c1;
	private int r2;
	private int c2;
	
	public Move(int r_i, int c_i, int r2_i, int c2_i) {
		this.r1 = r_i;
		this.c1 = c_i;
		this.r2 = r2_i;
		this.c2 = c2_i;
	}
	
	public int getR1() {
		return r1;
	}
	public int getC1() {
		return c1;
	}
	public int getR2() {
		return r2;
	}
	public int getC2() {
		return c2;
	}
	
}
