
import javax.microedition.lcdui.*;

public class YearlyTotalsEvent implements CommandListener {

   Object okResponse;

   YearlyTotalsBox myYearlyTotalsBox;

   public YearlyTotalsEvent(YearlyTotalsBox yt) {

      myYearlyTotalsBox = yt;
      okResponse = new Object();
   }

   public void commandAction(Command c, Displayable d) {

      System.out.println("got a response");

      if (d != myYearlyTotalsBox.getDisplayable())
         return;

      if (c == myYearlyTotalsBox.getOkCmd()) {

         System.out.println ("Got an ok response");
      }

      synchronized (okResponse) {

         okResponse.notify();
      }
   }
}
