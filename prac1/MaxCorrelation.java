import java.util.concurrent.RecursiveTask;

class MaxCorrelation extends RecursiveTask<Integer>
{
   static int SEQUENTIAL_THRESHOLD = 800;
   
   int start=0;
   int stop=0;
   float[] crossCorrelation;
   
   public MaxCorrelation(float[] crossCorrelation,int start, int stop)
   {
      this.start = start;
      this.stop = stop;
      this.crossCorrelation = crossCorrelation;
   }
   
   public Integer compute()
   {
      if(stop - start <= SEQUENTIAL_THRESHOLD)
      {
         int max = start;
         for(int i= start + 1; i<stop;i++)
         {
            if (crossCorrelation[i] > crossCorrelation[max])
            {
               max = i;
            }
         }
         System.out.println("I is" + max);
         return max;
      }
      else 
      {
         MaxCorrelation first = new MaxCorrelation(crossCorrelation,start,(start + stop)/2);
         MaxCorrelation second = new MaxCorrelation(crossCorrelation,(start + stop)/2,stop);
         
         second.fork();
         int firstMax = first.compute();
         int secondMax = second.join();
         
         return (firstMax < secondMax) ? firstMax : secondMax;

      }
   }
   
   
}
