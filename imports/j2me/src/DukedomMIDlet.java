
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class DukedomMIDlet extends MIDlet implements Runnable {

   MIDlet m;

   DukedomGame g;

   Thread t;

   public DukedomMIDlet () {

   }

   protected void destroyApp(boolean unconditional) {

   }
   
   protected void pauseApp() {

   }

   protected void startApp() {

      m = this;

      DukedomDisplay d = new DukedomDisplay(Display.getDisplay(m));

      g = new DukedomGame(d);

      t = new Thread(g);

      t.start();

      Thread t2 = new Thread(this);

      t2.start();
   }

   public void run() {

      try {

         t.join();
      }
      catch (InterruptedException ie) {}

      destroyApp(false);
      notifyDestroyed();
   }
}
