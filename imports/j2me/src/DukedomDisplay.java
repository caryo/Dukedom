
import javax.microedition.lcdui.*;

public class DukedomDisplay {

   public static Display myDisplay;

   public static boolean quitGame;

   public DukedomDisplay(Display d) {

      myDisplay = d;
      quitGame = false;
   }

   public static boolean isQuit() {

      return quitGame;
   }

   public static char getYesNoResponse(String s) {

      YesNoInputBox yn = new YesNoInputBox(s);

      YesNoEvent eh = new YesNoEvent(yn);

      yn.setCommandListener(eh);

      myDisplay.setCurrent(yn);

      boolean exitLoop = false;

      System.out.println("yn.isShown() = " + yn.isShown());

      System.out.println("myDisplay.getCurrent() = " + myDisplay.getCurrent());

      if (yn.isShown())
         myDisplay.setCurrent(yn);

      System.out.println("yn.isShown() = " + yn.isShown());

      System.out.println("myDisplay.getCurrent() = " + myDisplay.getCurrent());

      while (!exitLoop) {

         try {
            synchronized (eh.yesNoResponse) {

               System.out.println("waiting for yes/no input");
               eh.yesNoResponse.wait();

               exitLoop = true;
            }
         }
         catch (InterruptedException ie) {}
      }

      return eh.getAnswer();
   }

   public static int getNumericInput(String p, String s) {

      NumericInputBox getNum = new NumericInputBox(p, s);

      NumericInputEvent numEvent = new NumericInputEvent(getNum);

      getNum.setCommandListener(numEvent);

      myDisplay.setCurrent(getNum);

      boolean exitLoop = false;

      quitGame = false;

      while (!exitLoop) {

         try {

            synchronized (numEvent.numericResponse) {

               numEvent.numericResponse.wait();

               if (numEvent.isQuitCmd()) {

                  quitGame = true;
                  exitLoop = true;
                  continue;
               }

               if (numEvent.isEmpty() || numEvent.isNegative()) {

                  numEvent.reset();
                  getNum.reset();

                  NumericInputAlert numAlert = new NumericInputAlert();

                  myDisplay.setCurrent(numAlert);
                  continue;
               }

               numEvent.getAnswer();

               if (numEvent.isValid()) {

                  exitLoop = true;
                  continue;
               }
            }
         }
         catch (InterruptedException ie) {

            System.out.println("exception: " + ie.getMessage());
            ie.printStackTrace();
         }
      }

      return numEvent.getAnswer();
   }

   public static void showSplashScreen() {

      DukedomSplash splash = new DukedomSplash();

      myDisplay.setCurrent(splash);
   }

   public static char skipDetailedReports() {

      return getYesNoResponse("Skip detailed reports (Y/N)?");
   }

   public static char exitGame() {

      return getYesNoResponse("Exit Game (Y/N)?");
   }

   public static void printTotals(String s) {

      YearlyTotalsBox t = new YearlyTotalsBox(s);

      YearlyTotalsEvent e = new YearlyTotalsEvent(t);

      t.setCommandListener(e);

      myDisplay.setCurrent(t);

      boolean exitLoop = false;

      while (!exitLoop) {

         try {
            synchronized (e.okResponse) {

               System.out.println("waiting for OK input");
               e.okResponse.wait();

               exitLoop = true;
            }
         }
         catch (InterruptedException ie) {}
      }

      return;
   }

   public static int inputGrain() {

      String inputText = new String();

      return getNumericInput("Grain for food?", inputText);
   }
}
