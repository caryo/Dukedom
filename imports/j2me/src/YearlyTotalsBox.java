
import javax.microedition.lcdui.*;

public class YearlyTotalsBox extends TextBox {

   Command ok;

   public YearlyTotalsBox(String textString) {

      super ("Yearly Totals", textString, 100, TextField.ANY);

      ok = new Command("ok", Command.SCREEN, 0);

      addCommand (ok);
   }

   public Command getOkCmd() {

      return ok;
   }

   public Displayable getDisplayable() {

      return this;
   }
}
