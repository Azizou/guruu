

public class EightQueen
{
   int[] rows;
   int numQueens;
   
   public EightQueen(int numQ)
   {
      numQueens = numQ;
      rows = new int[numQ + 1];
   }
   
   private boolean place(int row, int col)
   {
      for(int j=1;j<numQueens;j++)
      {
         if ((rows[j]==col)  || (Math.abs(rows[j] - col)==Math.abs(rows[j]-row)))
         {
            return false;
         }
         
      }
      return true;
   }
   
   void NQueens(int row)
   {
      for(int i=1; i<numQueens +1; i++)
      {
         if(place(row,i))
         {
            rows[row] = i;
            if(row==numQueens)
            {
               print(rows);
            }
            else
            {
               NQueens(row +1);
            }
         }
      }
   }
   void print(int[] arr)
   {
      for(int i=0; i<arr.length;i++)
      {
         if(arr[i]==0)
         System.out.print(" ");
         else
         System.out.print("Q ");
      }
      System.out.println();
   }
   
   public static void main(String[] args)
   {
      EightQueen eq = new EightQueen(4);
      eq.NQueens(1);
      
   }
}