
import javax.microedition.lcdui.*;

public class YesNoEvent implements CommandListener {

   Object yesNoResponse;

   YesNoInputBox myYesNoInputBox;

   char ans;

   public YesNoEvent(YesNoInputBox yn) {

      myYesNoInputBox = yn;
      ans = 'N';
      yesNoResponse = new Object();
   }

   public void commandAction(Command c, Displayable d) {

      System.out.println("got a yes/no response");

      if (d != myYesNoInputBox.getDisplayable())
         return;

      if (c == myYesNoInputBox.getYesCmd()) {

         System.out.println ("Got a yes response");
         ans = 'Y';
      }
      else if (c == myYesNoInputBox.getNoCmd()) {

         System.out.println ("Got a no response");
         ans = 'N';
      }

      synchronized (yesNoResponse) {

         yesNoResponse.notify();
      }
   }

   public char getAnswer() {

      return ans;
   }
}
