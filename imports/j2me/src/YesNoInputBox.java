
import javax.microedition.lcdui.*;

public class YesNoInputBox extends TextBox {

   Command yes;
   Command no;

   public YesNoInputBox(String textPrompt) {

      super ("Yes/No Input", textPrompt, 100, TextField.ANY);

      yes = new Command("Yes", Command.SCREEN, 0);
      no = new Command("No", Command.SCREEN, 0);

      addCommand (yes);
      addCommand (no);
   }

   public Command getYesCmd() {

      return yes;
   }

   public Command getNoCmd() {

      return no;
   }

   public Displayable getDisplayable() {

      return this;
   }
}
