package vip.lovek.interview.面试Java;


/**
 * author： yuzhirui@douban.com
 * date: 2022-03-26 20:14
 */
public class OuterClass {

   public void onCreate() {
      new Thread() {
         @Override
         public void run() {

         }
      }.start();

     Thread t = new Thread() {
         @Override
         public void run() {

         }
      };
      t.start();
   }

}