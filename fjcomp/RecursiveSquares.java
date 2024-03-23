/*************************************************************************
¨   FJCOMP Previous version 0.0  - Date: Fevrier 2013*
*   FJCOMP Version 1.0  - updated : Avril 2023     *
*   Ce code est genere et mis en forme par le compilateur FJComp         *
* Auteur du Compilateur: Abdourahmane Senghor  -- boya2senghor@yahoo.fr  *
**************************************************************************/


package fjcomp ;
import static java . lang . Math . log ;
import java . util . concurrent . ForkJoinPool ;
import java . util . concurrent . RecursiveAction ;
import static java . lang . Math . pow ;
import java . util . ArrayList ;
import java . util . List ;
import java . util . concurrent . atomic . AtomicInteger ;
public class RecursiveSquares {
   public static void drawSquare ( double x , double y , double size ) {
      StdDraw . setPenColor ( StdDraw . LIGHT_GRAY ) ;
      StdDraw . filledSquare ( x , y , size / 2 ) ;
      StdDraw . setPenColor ( StdDraw . BLACK ) ;
      StdDraw . square ( x , y , size / 2 ) ;
   }
   public static void draw ( int n , double x , double y , double size ) {
      String nbthreadsStr = System . getProperty ( "fjcomp.threads" ) ;
      int numthreads = Runtime . getRuntime ( ) . availableProcessors ( ) ;
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
      drawImpl adrawImpl = new drawImpl ( 0 , n , x , y , size ) ;
      pool . invoke ( adrawImpl ) ;
   }
   private static class drawImpl extends RecursiveAction {
      private int maxdepth ;
      private int n ;
      private double x ;
      private double y ;
      private double size ;
      private drawImpl ( int maxdepth , int n , double x , double y , double size ) {
         this . maxdepth = maxdepth ;
         this . n = n ;
         this . x = x ;
         this . y = y ;
         this . size = size ;
      }
      protected void compute ( ) {
         int MAX_DEPTH ;
         String maxdepthStr = System . getProperty ( "fjcomp.maxdepth" ) ;
         MAX_DEPTH = 4 ;
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
         if ( maxdepth >= MAX_DEPTH ) {
            draw ( n , x , y , size ) ;
         }
         else {
            if ( n == 0 ) return ;
            drawSquare ( x , y , size ) ;
            double ratio = 2.2 ;
            drawImpl task1 = null ;
            drawImpl task2 = null ;
            drawImpl task3 = null ;
            drawImpl task4 = null ;
            task1 = new drawImpl ( maxdepth + 1 , n - 1 , x - size / 2 , y - size / 2 , size / ratio ) ;
            task2 = new drawImpl ( maxdepth + 1 , n - 1 , x - size / 2 , y + size / 2 , size / ratio ) ;
            task3 = new drawImpl ( maxdepth + 1 , n - 1 , x + size / 2 , y - size / 2 , size / ratio ) ;
            task4 = new drawImpl ( maxdepth + 1 , n - 1 , x + size / 2 , y + size / 2 , size / ratio ) ;
            invokeAll ( task1 , task2 , task3 , task4 ) ;
         }
      }
      private void draw ( int n , double x , double y , double size ) {
         if ( n == 0 ) return ;
         drawSquare ( x , y , size ) ;
         double ratio = 2.2 ;
         draw ( n - 1 , x - size / 2 , y - size / 2 , size / ratio ) ;
         draw ( n - 1 , x - size / 2 , y + size / 2 , size / ratio ) ;
         draw ( n - 1 , x + size / 2 , y - size / 2 , size / ratio ) ;
         draw ( n - 1 , x + size / 2 , y + size / 2 , size / ratio ) ;
      }
   }
   public static void main ( String [ ] args ) {
      int n = 10 ;
      double x = 0.5 , y = 0.5 ;
      double size = 0.5 ;
      draw ( n , x , y , size ) ;
   }
}
 