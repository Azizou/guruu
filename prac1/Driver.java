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