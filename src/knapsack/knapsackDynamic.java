
package knapsack;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class knapsackDynamic {
	
	public static int dynamic(int N,int MaxW,int [] Profit,int[] Weight ,int known[][])
	{
		
		for(int i=0;i<=N;i++)
		{
			for(int j=0;j<=MaxW;j++)
			{
				if(i==0||j==0)
				{
					known[i][j]=0;
				}
				else  if (Weight[i-1] <= j)
				{
					
	                 known[i][j] = Math.max(Profit[i-1] + known[i-1][j-Weight[i-1]],  known[i-1][j]);
				}
				else
	           {
	                 known[i][j] = known[i-1][j];	
	           }
			 }
		}
		return known[N][MaxW];
	}
	
	public static void main(String[] args) throws IOException
	{
		
		System.out.println("Enter n, the no. of items to be considered");
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); // Reading the no.of items value

		System.out.println("Enter the Knapsack's Maximum Weight");
		sc = new Scanner(System.in);
		int MaxW = sc.nextInt(); // Reading the Knapsack's capacity
		
	
		double start = System.currentTimeMillis();
		int s=0, change=0, d=0, r=0  ;         
		int known[][];
		int[] Weight= new int[N+1];
		int[] Profit= new int[N+1];
		 
		 
		 for(int i=1;i<N+1;i++)
			{
				Profit[i]=(int)(Math.random() * 1000)+ 1;
			}
		  for(int j=1;j<N+1;j++)
			{
				Weight[j]= (int)(Math.random() * 40)+ 1 ;
	            s+= Weight[j];

			}
		  if (s <= (2 * MaxW)) {
				d = (2 * MaxW) - s;
				if ((d % N) != 0) {
					r = d % N;
					d += (N - r);
				}
			 change = (d/ N)+1;
		  }
		  for(int c=1;c<N+1;c++)
			{
				Weight[c] = Weight[c]+ change;
				
			}
		  System.out.println("\nRandomly generated Profit-Weight input pairs");
		  for (int z=1;z<N+1;z++)
		  {
			  System.out.println("Item " +z+": Profit:"+Profit[z]+ "  Weight:" +Weight[z]);
		  }
        
              
		//we initialize a table in 2-D to store values so that it can be reused again
		known= new int[N+1][MaxW+1];
		
		int oprofit=dynamic(N,MaxW,Profit,Weight,known);
		System.out.println("\nThe profits and the corresponding weights of the items in the Optimal set are");
		for(int x=N,y=MaxW;x>0 && y>0;x--)

		{
			if (known[x][y] != known[x-1][y])
			{
				y -= Weight[x];
				System.out.println("Item " +x+": Profit:"+Profit[x]+ "  Weight:" +Weight[x]);
				
			}
		}
		
		System.out.println("\nThe total optimal Profit  is  "+oprofit);
		double stop = System.currentTimeMillis();
		
		System.out.println("\nThe running time is " + (stop-start)+ " ms");
         
	}
	
  }
