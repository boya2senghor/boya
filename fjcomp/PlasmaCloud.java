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
import java . awt . Color ;
public class PlasmaCloud {
   public static void plasma ( double x , double y , double size , double stddev , double c1 , double c2 , double c3 , double c4 ) {
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
      plasmaImpl aplasmaImpl = new plasmaImpl ( 0 , x , y , size , stddev , c1 , c2 , c3 , c4 ) ;
      pool . invoke ( aplasmaImpl ) ;
   }
   private static class plasmaImpl extends RecursiveAction {
      private int maxdepth ;
      private double x ;
      private double y ;
      private double size ;
      private double stddev ;
      private double c1 ;
      private double c2 ;
      private double c3 ;
      private double c4 ;
      private plasmaImpl ( int maxdepth , double x , double y , double size , double stddev , double c1 , double c2 , double c3 , double c4 ) {
         this . maxdepth = maxdepth ;
         this . x = x ;
         this . y = y ;
         this . size = size ;
         this . stddev = stddev ;
         this . c1 = c1 ;
         this . c2 = c2 ;
         this . c3 = c3 ;
         this . c4 = c4 ;
      }
      protected void compute ( ) {
         int MAX_DEPTH ;
         String maxdepthStr = System . getProperty ( "fjcomp.maxdepth" ) ;
         MAX_DEPTH = 2 ;
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
            plasma ( x , y , size , stddev , c1 , c2 , c3 , c4 ) ;
         }
         else {
            if ( size <= 0.001 ) return ;
            double displacement = StdRandom . gaussian ( 0 , stddev ) ;
            double cM = ( c1 + c2 + c3 + c4 ) / 4.0 + displacement ;
            Color color = Color . getHSBColor ( ( float ) cM , 0.8f , 0.8f ) ;
            StdDraw . setPenColor ( color ) ;
            StdDraw . filledSquare ( x , y , size ) ;
            double cT = ( c1 + c2 ) / 2.0 ;
            double cB = ( c3 + c4 ) / 2.0 ;
            double cL = ( c1 + c3 ) / 2.0 ;
            double cR = ( c2 + c4 ) / 2.0 ;
            plasmaImpl task1 = null ;
            plasmaImpl task2 = null ;
            plasmaImpl task3 = null ;
            plasmaImpl task4 = null ;
            task1 = new plasmaImpl ( maxdepth + 1 , x - size / 2 , y - size / 2 , size / 2 , stddev / 2 , cL , cM , c3 , cB ) ;
            task2 = new plasmaImpl ( maxdepth + 1 , x + size / 2 , y - size / 2 , size / 2 , stddev / 2 , cM , cR , cB , c4 ) ;
            task3 = new plasmaImpl ( maxdepth + 1 , x - size / 2 , y + size / 2 , size / 2 , stddev / 2 , c1 , cT , cL , cM ) ;
            task4 = new plasmaImpl ( maxdepth + 1 , x + size / 2 , y + size / 2 , size / 2 , stddev / 2 , cT , c2 , cM , cR ) ;
            invokeAll ( task1 , task2 , task3 , task4 ) ;
         }
      }
      private void plasma ( double x , double y , double size , double stddev , double c1 , double c2 , double c3 , double c4 ) {
         if ( size <= 0.001 ) return ;
         double displacement = StdRandom . gaussian ( 0 , stddev ) ;
         double cM = ( c1 + c2 + c3 + c4 ) / 4.0 + displacement ;
         Color color = Color . getHSBColor ( ( float ) cM , 0.8f , 0.8f ) ;
         StdDraw . setPenColor ( color ) ;
         StdDraw . filledSquare ( x , y , size ) ;
         double cT = ( c1 + c2 ) / 2.0 ;
         double cB = ( c3 + c4 ) / 2.0 ;
         double cL = ( c1 + c3 ) / 2.0 ;
         double cR = ( c2 + c4 ) / 2.0 ;
         plasma ( x - size / 2 , y - size / 2 , size / 2 , stddev / 2 , cL , cM , c3 , cB ) ;
         plasma ( x + size / 2 , y - size / 2 , size / 2 , stddev / 2 , cM , cR , cB , c4 ) ;
         plasma ( x - size / 2 , y + size / 2 , size / 2 , stddev / 2 , c1 , cT , cL , cM ) ;
         plasma ( x + size / 2 , y + size / 2 , size / 2 , stddev / 2 , cT , c2 , cM , cR ) ;
      }
   }
   public static void main ( String [ ] args ) {
      double c1 = StdRandom . uniform ( ) ;
      double c2 = StdRandom . uniform ( ) ;
      double c3 = StdRandom . uniform ( ) ;
      double c4 = StdRandom . uniform ( ) ;
      double stddev = 1.0 ;
      plasma ( 0.5 , 0.5 , 0.5 , stddev , c1 , c2 , c3 , c4 ) ;
   }
}
 