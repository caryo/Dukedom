
import javax.microedition.lcdui.*;

public class NumericInputEvent implements CommandListener {

   Object numericResponse;

   NumericInputBox myNumericInputBox;

   int ans;

   public NumericInputEvent(NumericInputBox num) {

      myNumericInputBox = num;
      ans = -1;
      numericResponse = new Object();
   }

   public void commandAction(Command c, Displayable d) {

      if (d != myNumericInputBox.getDisplayable())
         return;

      if (c == myNumericInputBox.getOkCmd()) {

         System.out.println ("Got an integer response");
         ans = -3;
      }
      else if (c == myNumericInputBox.getQuitCmd()) {

         System.out.println ("Got a quit response");
         ans = -2;
      }

      synchronized (numericResponse) {

         numericResponse.notify();
      }
   }

   public int getAnswer() {

      if (ans == -3) {

         if (!isEmpty()) {

            String inputStr = myNumericInputBox.getString();
            ans = Integer.parseInt(inputStr);
         }
      }

      return ans;
   }

   public void reset() {

      ans = -1;
   }

   public boolean isValid() {

      if (ans >= 0)
         return true;

      return false;
   }

   public boolean isEmpty() {

      String inputStr;

      inputStr = myNumericInputBox.getString();

      if ((inputStr == null) || (inputStr.length() == 0))
         return true;

      return false;
   }

   public boolean isNegative() {

      String inputStr;

      inputStr = myNumericInputBox.getString();

      if ((inputStr == null) || (inputStr.length() == 0))
         return false;

      ans = Integer.parseInt(inputStr);

      if (ans < 0)
         return true;

      return false;
   }

   public boolean isQuitCmd() {

      if (ans == -2)
         return true;

      return false;
   }
}
