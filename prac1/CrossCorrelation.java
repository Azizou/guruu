import java.util.concurrent.RecursiveAction;

class CrossCorrelation extends RecursiveAction
{
   static int SEQUENTIAL_THRESHOLD =1500;
   
   int start=0;
   int stop=0;
   float[] crossCorrelation;
   float[] transmition;
   float[] received;
   
   public CrossCorrelation(float[] transmition, float[] received, float[] crossCorrelation, int start, int stop)
   {
	  SEQUENTIAL_THRESHOLD = Driver.SEQUENTIAL_THRESHOLD;
      this.start = start;
      this.stop = stop;
      this.crossCorrelation = crossCorrelation;
      this.transmition = transmition;
      this.received = received;
   }
   
   public void compute()
   {
      if(stop - start <= SEQUENTIAL_THRESHOLD)
      {
         for(int i=start; i<stop;i++)
         {
            float sum=0.0f;
           // System.out.println(start + " and stop  at " + stop);
            for(int j=0; j<transmition.length; ++j)
            {
               if(i+j < received.length)
               {
                  sum += received[i+j]*transmition[j];
               }
            }
            crossCorrelation[i]=sum;
         }
      }
      else 
      {
         CrossCorrelation first = new CrossCorrelation(transmition,received, crossCorrelation,start,(start + stop)/2);
         CrossCorrelation second = new CrossCorrelation(transmition,received,crossCorrelation,(start + stop)/2,stop);
         
         second.fork();
         first.compute();
         second.join();
         
      }
   }
   
}
