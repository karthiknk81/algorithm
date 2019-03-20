import java.util.Arrays;

public class MergeSort {
  public static void main(String [] args) {
    int [] inputArray = {9,8,7,6,5,4,3,2,1,0};//{31,41,5,9,26,41,58,6};
    int n = inputArray.length;
    //try {
    new MergeSort().mergeSort(inputArray, 0,n);
  //} catch (Exception e) {
    //System.out.println("exception ...");
  //}
  //  System.out.println("Array = "+Arrays.toString(inputArray));
  }

  public void mergeSort(int [] array, int p, int r){
//    System.out.println("p ="+p+"; r ="+r);
    if (p<r){
      int q = (p+r)/2;
      mergeSort(array,p, q);
      //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      mergeSort(array,q+1,r);
      merge(array,p,q,r);
    }
  }
  public void merge(int [] array, int p, int q, int r){
    int len_L = q - p;
    int len_R = r - q;
    int[] L = new int[len_L];
    int[] R = new int[len_R];

//    System.out.println("Array = "+Arrays.toString(array));
//    System.out.println("p value = "+ p);
//    System.out.println("q value = "+ q);
//    System.out.println("r value = "+ r);
    System.out.println("length of L = "+ len_L);
    System.out.println("length of R = "+ len_R);


    //Copy p to q values into Left (L) array.
    for(int i=0, j=p; i<len_L; i++, j++){
      L[i] = array [j];
    }
//    System.out.println("L Array = "+Arrays.toString(L));

    //Copy q+1 to r values into Left (L) array.
    for(int i=0, j=q; i<len_R; i++, j++){
      R[i] = array [j];
    }

    //System.out.println("R Array = "+Arrays.toString(R));
    //Now permutate the number in p to q range in sorted order
    int i =0 , j = 0;
    for (int k=p; k<r;k++){
        if(L[i] < R[j]) {
          array[k] = L[i];
          i++;
        }else {
          array[k] = R[j];
          j++;
        }
      }


    }

}
