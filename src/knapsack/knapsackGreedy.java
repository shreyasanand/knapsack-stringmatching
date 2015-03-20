package knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class knapsackGreedy {

	
public static void main(String args[]) {
    
	 	System.out.println("Enter n, the no. of items to be considered");
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); // Reading the no.of items value
		
		System.out.println("Enter the Knapsack's Maximum Weight");
		 sc = new Scanner(System.in);
		int MaxW = sc.nextInt(); // Reading the Knapsack's capacity
		
		
        double start = System.currentTimeMillis();
                
		float[] ratios = new float[N];// Create an array which holds the Pi/Wi ratios
		int l=1, sum=0,c=0;
        float ratio=0;
                
        Map<Integer, Integer> inputMap = new HashMap<Integer, Integer>();
	    Map<Integer, Integer> filtermap = new HashMap<Integer, Integer>();
		Map<Float, Integer> ratioMap = new HashMap<Float, Integer>();
		 
        knapsackGreedy obj = new knapsackGreedy();
                
        inputMap = obj.randominput(inputMap, N, MaxW);
        System.out.println("\nRandomly generated Profit-Weight input pairs");
		 
		for (int key : inputMap.keySet()) {
			System.out.println("Item " +l+++": Profit:"+key+ "  Weight:" +inputMap.get(key));
		}

		for (int k : inputMap.keySet()) {

			int w = inputMap.get(k);
			if (w <= MaxW) {
				filtermap.put(k, w);
			}
		}
                 
		// the ratios of all profits with it's corresponding weights are taken 
        System.out.println("\nThe ratios of Profits to Weights is");
        for(int k : filtermap.keySet()){
        	ratio = (float)k/(float)(filtermap.get(k));
            ratioMap.put(ratio,k);
            ratios[c]= ratio;
            System.out.println(ratios[c]);
            c++;
        }
                
        obj.sort(ratios);
    
        System.out.println("\nThe ratios of Profits to weights after sorting is");
              
        for(int j =0; j<ratios.length; j++){
        	System.out.println(ratios[j]);
        }
            	  
                 
        // we take the highest ratios value and check if it fits in the knapsack and then followed by the next highest
        // the process is repeated until the knapsack is full
        System.out.println("\nThe profits and the corresponding weights of the items in the Sub optimal set are");
        int k=1;
        for(int e=(ratios.length-1);e>=0;e--){
        	int f = ratioMap.get(ratios[e]);
            if(filtermap.get(f)<MaxW){
            	sum+=f;
                MaxW=MaxW-filtermap.get(f);
                System.out.println("Tuple " +k+++": Profit:"+f+ "  Weight:" +filtermap.get(f));
			}
        }
        System.out.println("\nThe total sub optimal profit is "+sum);
        double stop=System.currentTimeMillis();
        System.out.println("\nRunning time of Knapsack(greedy approach) is :" + (stop-start)+ " milliseconds");                               
}

public Map<Integer, Integer> randominput(Map<Integer, Integer> inputMap,
		int N, int M) {
	int s = 0, d = 0, change = 0, r=0;
	while (inputMap.size() != N) {
		int p = (int) ((Math.random() * 1000)) + 1;
		int w = (int) ((Math.random() * 40)) + 1;
		if (!inputMap.containsKey(p)) {
			inputMap.put(p, w);
			s += w;
		}
	}

	if (s <= (2 * M)) {
		d = (2 * M) - s;
		if ((d % N) != 0) {
			r = d % N;
			d += (N - r);
		}
		change = (d / N);
	}

	if (change != 0) {
		for (int k : inputMap.keySet()) {
			int w = inputMap.get(k);
			inputMap.put(k, w + change);
		}
	}

	return inputMap;
}



public void sort(float[] a){
 	  float [] aux = new float[a.length];
 	  sort(a, aux, 0, a.length - 1);
 	 }

   // the ratios are sorted in an order using merge sort  
   private void merge(float[] a, float[] aux, int lo, int mid, int hi){
  	    for (int k = lo; k <= hi; k++)
  	    aux[k] = a[k];
  	    int i = lo, j = mid+1;
  	    for (int k = lo; k <= hi; k++){
  	     if (i > mid) a[k] = aux[j++];
  	     else if (j > hi) a[k] = aux[i++];
  	     else if (aux[j]<aux[i]) a[k] = aux[j++];
  	     else a[k] = aux[i++];
  	    }
  	  }
  	   /**
  	     * This recursive method sorts the two sub arrays.
  	     * @param a[] An array of size m or n holding the integers between 0 and K.
  	     * @param aux[] An auxiliary array required to perform merge operation.
  	     * @param lo This variable points to the first integer in sequence
  	     * @param hi This variable point to the last integer in sequence.
  	     */
  	  private void sort(float [] a, float[] aux, int lo, int hi){
  	  if (hi <= lo) return;
  	  int mid = lo + (hi - lo) / 2; // calculate the middle index
  	  sort (a, aux, lo, mid);
  	  sort (a, aux, mid+1, hi);
  	  merge(a, aux, lo, mid, hi); // call merge method
  	  }

}