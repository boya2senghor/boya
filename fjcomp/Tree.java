/*************************************************************************
�   FJCOMP Previous version 0.0  - Date: Fevrier 2013*
*   FJCOMP Version 1.0  - updated : Avril 2023     *
*   Ce code est genere et mis en forme par le compilateur FJComp         *
* Auteur du Compilateur: Abdourahmane Senghor  -- boya2senghor@yahoo.fr  *
**************************************************************************/


package fjcomp ;
import java . util . concurrent . ForkJoinPool ;
import java . util . concurrent . RecursiveAction ;
import java . util . ArrayList ;
import java . util . List ;
public class Tree {
    public static int num_coeurs;
   public void tree ( int n , double x , double y , double a , double branchRadius ) {
      String nbthreadsStr = System . getProperty ( "fjcomp.threads" ) ;
      int numthreads = Runtime . getRuntime ( ) . availableProcessors ( ) ;
      num_coeurs=numthreads;
      try {
         numthreads = Integer . parseInt ( nbthreadsStr ) ;
         if ( numthreads == 0 ) {
            System . out . println ( "La valeur de fjcomp.threads doit etre differente de zero" ) ;
            System . exit ( 1 ) ;
         }
      }
      catch ( Exception ex ) {
         if ( nbthreadsStr == null ) ;
         else {
            System . out . println ( "La valeur fr fjcomp.threads doit etre un entier" ) ;
            System . exit ( 1 ) ;
         }
      }
      ForkJoinPool pool = new ForkJoinPool ( numthreads ) ;
      treeImpl atreeImpl = new treeImpl ( 0 , n , x , y , a , branchRadius ) ;
      pool . invoke ( atreeImpl ) ;
   }
   private class treeImpl extends RecursiveAction {
      private int maxdepth ;
      private int n ;
      private double x ;
      private double y ;
      private double a ;
      private double branchRadius ;
      private treeImpl ( int maxdepth , int n , double x , double y , double a , double branchRadius ) {
         this . maxdepth = maxdepth ;
         this . n = n ;
         this . x = x ;
         this . y = y ;
         this . a = a ;
         this . branchRadius = branchRadius ;
      }
      protected void compute ( ) {
         int MAX_DEPTH ;
         String maxdepthStr = System . getProperty ( "fjcomp.maxdepth" ) ;
         try {
            MAX_DEPTH = Integer . parseInt ( maxdepthStr ) ;
            if ( MAX_DEPTH == 0 ) {
               System . out . println ( "La valeur de fjcomp.maxdepth doit etre differente de zero" ) ;
               System . exit ( 1 ) ;
            }
         }
         catch ( Exception ex ) {
            if ( maxdepthStr == null ) ;
            else {
               System . out . println ( "La valeur  fjcomp.maxdepth doit etre un entier" ) ;
               System . exit ( 1 ) ;
            }
         }
         if ( maxdepth >= 2 ) {
            tree ( n , x , y , a , branchRadius ) ;
         }
         else {
            double bendAngle = Math . toRadians ( 15 ) ;
            double branchAngle = Math . toRadians ( 37 ) ;
            double branchRatio = 0.65 ;
            double cx = x + Math . cos ( a ) * branchRadius ;
            double cy = y + Math . sin ( a ) * branchRadius ;
            StdDraw . setPenRadius ( 0.001 * Math . pow ( n , 1.2 ) ) ;
            StdDraw . line ( x , y , cx , cy ) ;
            if ( n == 0 ) {
               return ;
            }
            treeImpl task1 = null ;
            treeImpl task2 = null ;
            treeImpl task3 = null ;
            task1 = new treeImpl ( maxdepth + 1 , n - 1 , cx , cy , a + bendAngle - branchAngle , branchRadius * branchRatio ) ;
            task2 = new treeImpl ( maxdepth + 1 , n - 1 , cx , cy , a + bendAngle + branchAngle , branchRadius * branchRatio ) ;
            task3 = new treeImpl ( maxdepth + 1 , n - 1 , cx , cy , a + bendAngle , branchRadius * ( 1 - branchRatio ) ) ;
            invokeAll ( task1 , task2 , task3 ) ;
         }
      }
      private void tree ( int n , double x , double y , double a , double branchRadius ) {
         double bendAngle = Math . toRadians ( 15 ) ;
         double branchAngle = Math . toRadians ( 37 ) ;
         double branchRatio = 0.65 ;
         double cx = x + Math . cos ( a ) * branchRadius ;
         double cy = y + Math . sin ( a ) * branchRadius ;
         StdDraw . setPenRadius ( 0.001 * Math . pow ( n , 1.2 ) ) ;
         StdDraw . line ( x , y , cx , cy ) ;
         if ( n == 0 ) {
            return ;
         }
         tree ( n - 1 , cx , cy , a + bendAngle - branchAngle , branchRadius * branchRatio ) ;
         tree ( n - 1 , cx , cy , a + bendAngle + branchAngle , branchRadius * branchRatio ) ;
         tree ( n - 1 , cx , cy , a + bendAngle , branchRadius * ( 1 - branchRatio ) ) ;
      }
   }
   public static void main ( String [ ] args ) {
       System.out.println("Le nombre de coeurs: "+Runtime . getRuntime ( ) . availableProcessors ( ));
      int n = 10 ;
      StdDraw . disableDoubleBuffering ( ) ;
      StopWatch stopWatch = new StopWatch ( ) ;
      System . out . println ( "Parallel Execution start: 0 " ) ;
      Tree tree = new Tree ( ) ;
      tree . tree ( n , 0.5 , 0 , Math . PI / 2 , 0.3 ) ;
      stopWatch . stop ( ) ;
      System . out . println ( "Parallel Elapsed Time: " + stopWatch . getElapsedTime ( ) ) ;
      StdDraw . show ( ) ;
   }
}
 