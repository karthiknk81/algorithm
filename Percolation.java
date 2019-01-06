import java.util.ArrayList;
import java.util.Random;

public class Percolation {
	
	private boolean isPercolate;
	private Site [][] grid;
	private int dim;
	private int targetNumOfOpen;
	
	public ArrayList<Site> HEAD; 
	public ArrayList<Site> TAIL;
	
	
	public Percolation(int d) {
		dim = d;
		grid = new Site[dim][dim];
		for(int i = 0; i<dim; i++) {
			for(int j = 0; j<dim; j++) {
				grid[i][j]= new Site(i,j);
			}
		}
		HEAD = new ArrayList<Site>(); 
		TAIL = new ArrayList<Site>();


	}
	
	public boolean isPercolate() {
		boolean retVal = false;
		
		if(TAIL.isEmpty()) return retVal;
		
		for(Site s: TAIL) {
			if(this.isFull(s)) {
				retVal = true;
				break;
			}
		}
		
		return retVal;
	}

	public void setPercolate(boolean isPercolate) {
		this.isPercolate = isPercolate;
	}
	
	public int getTargetNumOfOpen() {
		return targetNumOfOpen;
	}

	public void setTargetNumOfOpen(int targetNumOfOpen) {
		this.targetNumOfOpen = targetNumOfOpen;
	}
	
	/**
	 * Complexity O(1)
	 * @param x
	 * @param y
	 */
	public void open(int row, int col) {
		checkCoordinates(row, col);
		Site site = grid[row][col];
		site.setStatus('O');
		
		
		//if the site being opened is at the top of the grid. 
		if(row == 0) {
			site.setFull(true);
			HEAD.add(site);
		}

	
		// if the site being opened is at the bottom of the grid. 
		if(row == (dim-1)) {
//			site.setParent(TAIL);
			TAIL.add(site);
		}
		
		connectWithNeighbours(site);
				
	}
	
	public boolean isFull(int x, int y) {
		Site root = root(grid[x][y]);
		return root.isFull();
	}

	public boolean isFull(Site s) {
		Site root = root(s);
		return root.isFull();
	}

	
	public boolean percolates(){
		return isPercolate();
	}
	public void connectWithNeighbours(Site s) {
		
		int row = s.getX();
		int col = s.getY();
		
		boolean letfCheckPossible = (col !=0);
		boolean rightCheckPossible = (col!=(dim-1));
		boolean upCheckPossible = (row !=0);
		boolean downCheckPossible = (row != (dim-1));
		
//		Site tmpRef = null;
		
		if(letfCheckPossible && isOpen(row,col-1)) {
				union(s, grid[row][col-1]);
		}
		if(rightCheckPossible && isOpen(row,col+1)) {
//			tmpRef = grid[row][col+1];
			
			union(s, grid[row][col+1]);
		}
		if(upCheckPossible && isOpen(row-1,col)) {
			union(s, grid[row-1][col]);
		}
		if(downCheckPossible && isOpen(row+1,col)) {
			union(s, grid[row+1][col]);
		}
		
		
	} 
	
	public void union(Site p, Site q) {
		
		Site pRt = root(p);
		Site qRt = root(q);
		
		int pRtSz = pRt.getSize();
		int qRtSz = qRt.getSize();
		
		if(pRtSz > qRtSz) {
			qRt.setParent(pRt);
			pRt.setSize(pRtSz + qRtSz); 				// increase the the size of the parent by it's child's size
			pRt.setFull(pRt.isFull() || qRt.isFull());
		}else if(pRtSz < qRtSz) {
			pRt.setParent(qRt);
			qRt.setSize(qRtSz + pRtSz); 				// increase the the size of the parent by it's child's size
			qRt.setFull(qRt.isFull() || pRt.isFull()); 	// Set is Full bit of the site.
		}else {
			pRt.setParent(qRt);
			qRt.setSize(qRtSz+ pRtSz); 					// increase the the size of the parent by it's child's size
			qRt.setFull(qRt.isFull() || pRt.isFull()); 	// Set is Full bit of the site.
		}
	}
	
	public Site root(Site s) {
		Site parent = null;
		// Is parent is a self-reference, then it is a root.
		while(s != s.getParent()) {
			parent = s.getParent();
			s.setParent(parent.getParent());
			s = parent;			
		}
		return s;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 */
	private void checkCoordinates(int row, int col) {
		
		if((row>=dim) ||(col>=dim)) throw new IllegalArgumentException();
	}
	
	/*
	 * O(1)
	 */
	public boolean isOpen(int row, int col) {
		checkCoordinates(row, col);
		return (grid[row][col].getStatus() == 'O');
	}

	public void printGrid(){
		for (int r=0; r<dim;r++) {
			System.out.println();
			Site s=null;
			for(int c=0; c<dim; c++) {
				s = grid[r][c];
				System.out.print(s);
			}
		}
	}
	/**
	 * Monte Carlo Simulation 
	 * @param args
	 * @throws Exception
	 */
	public void populateOpenSite(int n) {
			this.setTargetNumOfOpen(n);
			Random ran = new Random();
			int x = -1;
			int y = -1;
			while(n>0) {
				do {
					x =ran.nextInt(dim);
					y =ran.nextInt(dim);
				}while(isOpen(x,y));
				
				this.open(x,y);
				
				n--;
			}
	}
	
	public void test() {
		open(0, 0);
		open(3, 3);
		open(0, 1);
		open(1, 3);
		open(1, 2);
		open(1, 1);
		open(2, 3);
		open(3, 1);
		
	}
	

	
	public boolean isConnected(Site p, Site q) {
		return (root(p) == root(q));
	}
	
	/*
	 * Main method
	 */
	public static void main(String[] args) {
		Percolation p = new Percolation(10);
		p.populateOpenSite(90);
		System.out.println("Percolates ? "+p.percolates());
	}
}

class Site {
	
	private int x;			// X coordinate in the matrix
	private int y;			// Y coordinate in the matrix
	private char status = 'X';	// false - block; true - Open
	private Site parent;	// In the Union-Join tree, this holds the reference to parents.
	private int size = 1;
	private boolean isFull;
	
	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Site(int xCoordinate, int yCoordinate) {
		setX(xCoordinate);
		setY(yCoordinate);
		setParent(this); //uncomment this to have self-referencing. Otherwise, parents will be set as null.
	}

	public void setStatus (char arg) {
		this.status = arg;
	}
	
	public char getStatus() {
		return this.status;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Site getParent() {
		return parent;
	}
	
	public void setParent(Site parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("(");
		
		sb.append(this.getX());
		sb.append(", ");
		sb.append(this.getY());
		sb.append(", '");
		sb.append(this.getStatus());
		sb.append("', p[");
		
		if(this.parent == null)
			sb.append("null");
		else
			sb.append(this.parent.getX()+", "+this.parent.getY());
		
		sb.append("], sz["+this.getSize()+"], ");
		
		sb.append("isFull="+this.isFull()+")   ");
		
		return sb.toString();
	}

	
}