
import javax.microedition.lcdui.*;

public class NumericInputAlert extends Alert {

   public NumericInputAlert() {

      super ("Numeric Input", "Please enter a non-negative integer", null,
         AlertType.INFO);

      setTimeout(3000);
   }
}