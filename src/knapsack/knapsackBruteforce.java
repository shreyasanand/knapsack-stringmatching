package knapsack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class knapsackBruteforce {


	public static void main(String args[]) {
		
        System.out.println("Enter n, the no. of items to be considered");
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); // Reading the no.of items value

		System.out.println("Enter the Knapsack's Maximum Weight");
		sc = new Scanner(System.in);
		int MaxW = sc.nextInt(); // Reading the Max weight of Knapsack
									
		
       double start = System.currentTimeMillis(); //Recording the start time of the algorithm
                
		int l = 1,p, s;
		knapsackBruteforce obj = new knapsackBruteforce(); //creating object for the class

		Map<Integer, Integer> inputMap = new HashMap<Integer, Integer>();
	    Map<Integer, Integer> filtermap = new HashMap<Integer, Integer>();
	    Map<Integer, List<Integer>> sets = new HashMap<Integer, List<Integer>>();

		List<Integer> pList = new ArrayList<Integer>();

		// populating random profit and weight
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

	
		for (int nk : filtermap.keySet()) {
			Map<Integer, List<Integer>> subSet = new HashMap<Integer, List<Integer>>();

			for (int csk : sets.keySet()) {
				List<Integer> oan = new ArrayList<Integer>();
				oan.addAll(sets.get(csk));
				oan.add(nk);
				p = 0;
				s = 0;
				
				for (int k : oan) {
					s += filtermap.get(k);
					p += k;
				}
				if (s <= MaxW) {
					subSet.put(p, oan);
					pList.add(p);
				}
			}

			List<Integer> setValue = new ArrayList<Integer>();

			if (filtermap.get(nk) <= MaxW) {
				setValue.add(nk);
				subSet.put(nk, setValue);
				pList.add(nk);
			}

			sets.putAll(subSet);
		}
		
			Collections.sort(pList);
			double stop = System.currentTimeMillis();

			System.out.println("\nThe Optimal Subset is\n");
			
			int i=1;
			int Maxp = pList.get(pList.size() - 1);
			for (int k : sets.get(Maxp)) {
				
				 System.out.println("Tuple " +i+++": Profit:"+k+ "  Weight:" +filtermap.get(k));
		    }
			
			System.out.print("\nThe total optimal Subset profit is " + Maxp);
			System.out.println("\nTotal number of feasible sets is "+ sets.size());

		System.out.println("\nTime Taken to run this algorithm:  "+ (stop - start)+" ms");
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
			change = (d / N)+1;
		}

		if (change != 0) {
			for (int k : inputMap.keySet()) {
				int w = inputMap.get(k);
				inputMap.put(k, w + change);
			}
		}

		return inputMap;
	}

}
