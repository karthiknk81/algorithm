
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Random;

/**
 * Social Network Model
 * @author Karthikeyan K
 *
 */
class SocialNetwork {
	
	private int largestTree;
	private int totMem;
	private int [] people;
	private int [] weight;
	private int [] network;
	
	public SocialNetwork(int totMembers) {
		// Initialize the people and network tag. In original stage, 
		// each members belongs to their own network
		totMem = totMembers;
		people = new int[totMembers];
		weight = new int[totMembers];
		network = new int[totMembers];
		
		for(int i=0; i<totMembers; i++) {
			network[i] = i;
			weight[i] = 1;
			people[i] = i;
		}
		
	}
	
	public boolean isAllConnected() {
		return (this.largestTree == totMem);
	}
	
	public void setLargestTree(int newTreeWeight) {
		if(largestTree < newTreeWeight) {
			this.largestTree = newTreeWeight;
			if(isAllConnected()) {
				System.out.println("*************All connected**********\n"+this);
				
			}
		}				
	}

	public void connect(int p, int q, boolean logSwitch) {
		
		System.out.println(new Timestamp(System.currentTimeMillis())+" >> connecting ("+p+", "+q+")");
		
		if (logSwitch) System.out.println(this);
		
		int rootOfP = root(p);
		int rootOfQ = root(q);
		int wtP = weight[rootOfP];
		int wtQ = weight[rootOfQ];
		
		if(rootOfP == rootOfQ) return;
		
		if(wtP > wtQ) {
			network[rootOfQ] = rootOfP;
			weight[rootOfP] += weight[rootOfQ];
			setLargestTree(weight[rootOfP]);
		} else if (wtP < wtQ) {
			network[rootOfP] = rootOfQ;
			weight[rootOfQ] += weight[rootOfP];
			setLargestTree(weight[rootOfQ]);
		} else {
			network[rootOfP] = rootOfQ;
			weight[rootOfQ] += weight[rootOfP];
			setLargestTree(weight[rootOfQ]);
		}
		
		
		if (logSwitch) System.out.println(this);
		
	}
	
	private int root(int element) {
		
		while(element != network[element]) {
			
			network [element] = network[network[element]];	// Single path compression
			element = network[element];						// Move the poster step towards root 
			
		}
	
		return element;
	}
	
	@Override
	public String toString() {
		StringBuilder strBldr = new StringBuilder();
		
		strBldr.append("\n----------------------------------------------------");
		strBldr.append("\n|People  >>"+Arrays.toString(this.people));
		strBldr.append("\n|Network >>"+Arrays.toString(this.network));
		strBldr.append("\n|Weight  >>"+Arrays.toString(this.weight));
		strBldr.append("\n----------------------------------------------------");
		
		return strBldr.toString();
	}
	
	
	
	public static void main(String [] args) throws Exception {
		
		SocialNetwork snc = new SocialNetwork(10);
		Random random = new Random();

		for(byte i= 0;!(snc.isAllConnected());i++) {
			int p = random.nextInt(10);
			int q = random.nextInt(10);
			System.out.print(i+ " ===> ");
			snc.connect(p, q,false);
		}

	}
}
