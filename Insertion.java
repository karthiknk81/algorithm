import java.util.Arrays;
public class Insertion {
	public static void main(String [] args) throws Exception {
		//int [] set = {4,2,3,5,6,1};

		int [] set = {5,2,4,6,1,3};
		int N = set.length;
		System.out.println(">>> Set = "+ Arrays.toString(set));
		for (int i=0; i<(N-1); i++){
			int key =  set[i+1];
			System.out.println("\t"+Arrays.toString(set));
			if(set[i] > key) {
				set[i+1] = set[i];
				set[i] = key;
				while((i>0) && (set[i]<set[i-1])){
					set[i] = set [i-1];
					set[i-1] = key;
					i = i - 1;
					System.out.println("\t\t"+Arrays.toString(set));
				}
			}
		}
		System.out.println(" >>> Set = "+ Arrays.toString(set));
	}
}

