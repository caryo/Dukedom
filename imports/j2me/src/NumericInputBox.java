
import javax.microedition.lcdui.*;

public class NumericInputBox extends TextBox {

   Command ok;
   Command quit;

   public NumericInputBox(String promptText, String integerText) {

      super (promptText, integerText, 8, TextField.NUMERIC);

      ok = new Command ("Ok", Command.SCREEN, 0);
      quit = new Command ("Quit", Command.SCREEN, 0);

      addCommand(ok);
      addCommand(quit);
   }

   public Command getOkCmd() {

      return ok;
   }

   public Command getQuitCmd() {

      return quit;
   }

   public Displayable getDisplayable() {

      return this;
   }

   public void reset() {

      setString(null);
   }
}
