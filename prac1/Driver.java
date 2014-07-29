import java.util.concurrent.ForkJoinPool;



import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver
{
	
   static int SEQUENTIAL_THRESHOLD = 800;
   static final ForkJoinPool fjPool = new ForkJoinPool();
   public static void main(String[] args)
   {
      Driver driver = new Driver();
      
      driver.testing(args);
   }
   
   void testing(String[] args)
   
   {
      String transmissionFile = "transmit.txt";
      String receivedFile =  "receive.txt";
      
      Scanner transmission = null;
      Scanner received = null;      
      if(args.length == 0)
      {
         System.out.println("Usage: ");
         System.out.println("Please provide the transmission and received ");
         System.out.println("signals filenames as arguments at the command line");
         System.out.println("You can provide a third argument to setup the sequential cut off");
         System.out.println("Example: \"java Correlation testTransmission.input testReceived.input\" ");
         //System.exit(0);//start over
         
      }
      else if(args.length ==2 )
      {
         transmissionFile = args[0];
         receivedFile = args[1];
      }
      else if(args.length == 3)
			SEQUENTIAL_THRESHOLD = Integer.parseInt(args[2]);
	  else if (args.length == 1)
			SEQUENTIAL_THRESHOLD = Integer.parseInt(args[0]);
      
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
            
      
      long t0 = System.nanoTime();
      CrossCorrelation cross = new CrossCorrelation(trans,receiv,crossCor,0,receiv.length);

      fjPool.invoke(cross);
      
      long t1 = System.nanoTime();
      
      //Get the max
      int maxcross = fjPool.invoke(new MaxCorrelation(crossCor,0,crossCor.length));
  
      long t2 = System.nanoTime();
      
      
      System.out.println("Max is : " + maxcross + " value" + crossCor[maxcross] );
      
<<<<<<< HEAD
      //printArr(crossCor);
      System.out.println((t1 -t0)/1000000 + " mili sec for cross correlation and " + (t2 -t1)/1000 + " micro sec for max " +  maxcross );
=======
      System.out.println((t1 -t0)/1000000 + " mili sec for cross correlation and " + (t2 -t1)/1000 + " micro sec for max " +  maxcross  + " with a threshold " + SEQUENTIAL_THRESHOLD);
>>>>>>> origin/master
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
   
   //~ static void printArr(float[] arr){
      //~ for(int i=0; i<arr.length;i++)
      //~ {
         //~ System.out.println("At index " +i + "we have " + arr[i]);
      //~ }
   //~ }
}
