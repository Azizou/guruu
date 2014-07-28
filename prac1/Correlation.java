
import java.util.Random;
import java.io.PrintWriter;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Correlation
{
   private static float[] arrayY;
   public static void main(String[] args)
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
            
      long t0 = System.nanoTime();

      float[] cross_correlation = crossCorelation(receiv,trans);
      
      long t1 = System.nanoTime();
      
      float[] max = findMax(cross_correlation,0,cross_correlation.length);
      
      long t2 = System.nanoTime();
      //for(int i=0;   i<cross_correlation.length;i++)
        // System.out.println(cross_correlation[i] + " at position " + i);
      System.out.println("\nMax is : " + max[0]);
      
      int[] a = {1,2,3,4,5,6,7,8,9,10};
      
      
     // test(a);
     printArr(cross_correlation);
      System.out.println((t1 -t0)/1000000 + " mili sec for cross correlation and " + (t2 -t1)/1000 + " micros sec for max " +max[0] );
   }
   
   static float[] crossCorelation(float[] receivedSignal, float[] transmittedSignal)
   {  
      arrayY = new float[receivedSignal.length];
      for(int i=0; i<receivedSignal.length; i++)
      {
         float sum=0.0f;
         for(int j=0; j<transmittedSignal.length; j++)
         {
            if(i+j < receivedSignal.length)
            {
               sum += receivedSignal[i+j]*transmittedSignal[j];
            }
         }
         arrayY[i]=sum;
         //System.out.println(sum + " at position " + i);
      }
      return arrayY;
   }
   
   /**
    * A helper method to load the content of the file into an array
    * @scanner -the scanner or stream 
    *
    *
    **/
   
   static float[] loadFile(Scanner scanner)
   {
      /*
       * The first value in the file specify the number of float in the file
       * Each value after that is then added to the array of floats 
       */
      int size = scanner.nextInt();
      float[] temp = new float[size];
      for(int i=0; i<size;++i)
      {
         temp[i]=scanner.nextFloat();
      }
      
      return temp;
   }
   
   static float[] findMax(final float[] cross_correlation,int low, int high)
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
   
  static void test(int[] arr){
      
       for(int i=0; i<arr.length;i++){
         System.out.println(arr[i] + "  " + i);
      }
      
      for(int i=0; i<arr.length;i++){
         arr[i] = arr[i] + 5;
      }
      System.out.println("\ndONE\n");
      for(int i=0; i<arr.length;i++){
         System.out.println(arr[i] + "  " + i);
      }
   }
   
   static void writeArray(float[][] cross, String outfile)throws IOException
   {
      int size = cross.length;
      
      
      PrintWriter pr = new PrintWriter(new FileWriter(outfile));
      pr.println(size);
      for(int i=1; i <= size;i++)
      {
         if(i%10>0)
            pr.print(cross[i] + "\t");
         else
            pr.println();
            
      }
   }
   static void printArr(float[] arr){
      for(int i=0; i<arr.length;i++)
      {
         System.out.println("At index " +i + "we have " + arr[i]);
      }
   }
}
