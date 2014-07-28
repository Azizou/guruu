<<<<<<< HEAD
import java.util.concurrent.ForkJoinPool;



import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver
{
   static final ForkJoinPool fjPool = new ForkJoinPool();
   public static void main(String[] args)
   {
      Driver driver = new Driver();
      driver.testing(args);
   }
   
   void testing(String[] args)
   
   {
      String transmissionFile = "testTransmission.input";
      String receivedFile =  "testReceived.input";
      
      Scanner transmission = null;
      Scanner received = null;      
      if(args.length != 2)
      {
         System.out.println("Usage: ");
         System.out.println("Please provide the transmission and received ");
         System.out.println("signals filenames as argument at the command line");
         System.out.println("Example: \"java Correlation testTransmission.input testReceived.input\" ");
        // System.exit(0);//start over
         
      }
      else
      {
         transmissionFile = args[0];
         receivedFile = args[1];
      }
      
      try
      {
         transmission = new Scanner(new File(transmissionFile));
         received = new Scanner(new File(receivedFile));
      }
      
      catch(FileNotFoundException ex)
      {
         System.out.println("Some of your file could not be read");
         ex.printStackTrace();
      }
      
      //long transmissionSize = transmission.nextInt();
      //long receivedSize = received.nextInt();
      
      //System.out.println("In Trans: " + transmissionSize + " and in Received " + receivedSize);
      //Using an array of float instead of float in case values are bigger than float
      float[] trans = loadFile(transmission);
      float[] receiv = loadFile(received);
      float[] crossCor = new float[receiv.length];
            
      CrossCorrelation cross = new CrossCorrelation(trans,receiv,crossCor,0,receiv.length);
      
      long t0 = System.nanoTime();

      fjPool.invoke(cross);
      
      long t1 = System.nanoTime();
      
      
      MaxCorrelation max = new MaxCorrelation(crossCor,0,crossCor.length);
      int maxcross = fjPool.invoke(max);
      
      long t2 = System.nanoTime();
      System.out.println("Max is : " + maxcross + " value" + crossCor[maxcross] );
      
      printArr(crossCor);
      System.out.println((t1 -t0)/1000000 + " mili sec for cross correlation and " + (t2 -t1)/1000 + " micro sec for max " +  maxcross );
   }
   
   
   static float[] loadFile(Scanner file)
   {
      int size = file.nextInt();
      float[] temp = new float[size];
      for(int i=0; i<size;++i)
      {
         temp[i]=file.nextFloat();
      }
      
      return temp;
   }
   
   static float[] findMax(float[] cross_correlation,int low, int high)
   {
      int max = low;
      int i = 0;
      float[] temp = {0,0};
      for (i = low +1; i<high; ++i){
         if(cross_correlation[i]>cross_correlation[max])
         {
            max = i;
         }
      }
      temp[0] = max;
      temp[1] = (float)i;
      return temp;
   }
   
   static void printArr(float[] arr){
      for(int i=0; i<arr.length;i++)
      {
         System.out.println("At index " +i + "we have " + arr[i]);
      }
   }
}
=======
class SumThread extends java.lang.Thread {
int lo; // fields for communicating inputs
int hi;
int[] arr;
int ans = 0; // for communicating result
SumThread(int[] a, int l, int h) { arr=a; lo=l; hi=h; }
public void run() {
try{
if(hi - lo == 1) {
ans = arr[lo];
} else {
SumThread left = new SumThread(arr,lo,(hi+lo)/2);
SumThread right = new SumThread(arr,(hi+lo)/2,hi);
left.start();
right.start();
left.join();
right.join();
ans = left.ans + right.ans;
}
}
catch(Exception e){}
}
}
class Driver{
static int sum(int[] arr) throws InterruptedException{
SumThread t = new SumThread(arr,0,arr.length);
t.run();
return t.ans;
}
public static void main(String[] args)throws InterruptedException{
   int[] arr= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
  System.out.println( sum(arr));
}
}
>>>>>>> 8b2da5d5dac89af46e7bdd911e85e7d6f839d98c
