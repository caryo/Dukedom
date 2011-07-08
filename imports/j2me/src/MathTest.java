
import net.jscience.math.MathFP;

public class MathTest {

   public MathTest() {

   }

   public static void doMath() {

      long n = MathFP.toFP(12);
      long m = MathFP.toFP("14.5");
      long a = n + m;
      a = a - MathFP.toFP("1.5");
      a = MathFP.div(a,n);
      a = MathFP.sqrt(a);
      System.out.println(MathFP.toString(a));
   }
}
