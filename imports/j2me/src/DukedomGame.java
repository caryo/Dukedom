
import java.util.Random;

import javax.microedition.lcdui.*;
import net.jscience.math.MathFP;

public class DukedomGame implements Runnable {

   static DukedomDisplay myDisplay;

   static Boolean stopThread;

   static boolean skipDetailedReports;   // R$

   static int totalPeasants;  // P
   static int totalLand;      // L
   static int totalGrain;     // G

   static int currentYear;    // Y%

   static long cropYield;   // double C1

   static int D;  // D

   static final int MAX_PEASANTS = 8;
   static final int MAX_LAND = 3;
   static final int MAX_GRAIN = 10;
   static final int MAX_FERTILITY = 6;

   static int[] peasants;        // P(8)
   static int[] land;            // L(3)
   static int[] grain;           // G(10)
   static int[] landFertility;   // S(6)

   static int unrest1;  // U1
   static int unrest2;  // U2

   static String[] peasantsText = {   // P$(8)

      "Peasants at start ",
      "Starvations       ",
      "King's levy       ",
      "War casualties    ",
      "Looting victims   ",
      "Disease victims   ",
      "Natural deaths    ",
      "Births            "
   };

   static String[] landText = {   // L$(8)

      "Land at start     ",
      "Bought/sold       ",
      "Fruits of war     "
   };

   static String[] grainText = {  // G$(10)

      "Grain at start    ",
      "Used for food     ",
      "Land deals        ",
      "Seeding           ",
      "Rat losses        ",
      "Mercenary hire    ",
      "Fruits of war     ",
      "Crop yield        ",
      "Castle expense    ",
      "Royal tax         "
   };

   static final int RANDOM_RANGE = 2;  // F3%

   static final long INITIAL_CROP_YIELD = MathFP.toFP("3.95");  // double C1

   static final long WAR_FACTOR = MathFP.toFP("1.95"); // double M

   static final int MAX_RANDOM_VALUES = 8;

   static int[] randomValue;    // R(8)

   static int warWithKingStatus;   // K%

   public DukedomGame(DukedomDisplay d) {

      myDisplay = d;
      stopThread = new Boolean(false);
   }

   public void stop() {

      stopThread = new Boolean(true);
   }

   public boolean exitGame() {

      char ans = myDisplay.exitGame();
      System.out.println("exit game = " + ans);

      if (ans == 'Y')
         return true;

      return false;
   }

   public static int getRandomNumber(int p1, int p2) {

      Random r = new Random();

      int nRandomOne = Math.abs(r.nextInt() % 1000000);  // 0 to 999999
//      System.out.println("nRandomOne = " + nRandomOne);

      long xRandomOne = MathFP.toFP(nRandomOne);
//      System.out.println("xRandomOne = " + MathFP.toString(xRandomOne));

      long xMillion = MathFP.toFP("1000000.0");
//      System.out.println("xMillion = " + MathFP.toString(xMillion));

      xRandomOne = MathFP.div(xRandomOne, xMillion);
//      System.out.println("xRandomOne/1000000.0 = " +
//         MathFP.toString(xRandomOne));

      long xRandomPart = xRandomOne;
//      System.out.println("xRandomPart = " + MathFP.toString(xRandomPart));

      int rangePart = 1 + p2 - p1;

//      System.out.println("rangePart = " + rangePart);

      long xProductPart = MathFP.mul(xRandomPart, MathFP.toFP(rangePart));
//      System.out.println("xProductPart = " + MathFP.toString(xProductPart));

      String sProductPart = MathFP.toString(xProductPart);
//      System.out.println("sProductPart = " + sProductPart);

      int iProductPart = sProductPart.indexOf('.');
//      System.out.println("iProductPart = " + iProductPart);

      sProductPart = sProductPart.substring(0,iProductPart);
//      System.out.println("sProductPart = " + sProductPart);

      int nProductPart = Integer.parseInt(sProductPart);
//      System.out.println("nProductPart = " + nProductPart);

      int rtnVal = nProductPart + p1;

//      System.out.println("rtnVal = " + rtnVal);

//      System.out.println ("p1=" + p1 +
//         ", p2=" + p2 +
//         ", rangePart=" + rangePart +
//         ", xRandomPart=" + MathFP.toString(xRandomPart) +
//         ", nProductPart=" + nProductPart +
//         ", rtnVal=" + rtnVal);

      return (int) rtnVal;
   }

   public static int initRandomNumber(int p1, int p2) {

      int rtnVal = 0;
      int randomOne = 0;
      int randomTwo = 0;
      int randomThree = 0;

      randomOne = getRandomNumber(p1, p2);
      randomTwo = getRandomNumber(p1, p2);

      if (randomTwo > 5) {
         randomThree = getRandomNumber(p1, p2);
         rtnVal = (randomOne + randomThree) / 2;
      }
      else
         rtnVal = randomOne;

//      System.out.println ("p1 = " + p1 +
//         ", p2 = " + p2 +
//         ", random1=" + randomOne +
//         ", random2=" + randomTwo + ", random3=" + randomThree +
//         ", rtnVal=" + rtnVal);

      return rtnVal;
   }

   public static void initializeVars() {

      skipDetailedReports = false;  // R$

      currentYear = 0;      // Y%
      totalPeasants = 100;  // P
      totalLand = 600;      // L
      totalGrain = 4177;    // G

      cropYield = INITIAL_CROP_YIELD; // double C1

      D = 0;  // D

      peasants = new int[MAX_PEASANTS];

      peasants[0] = 96;  // peasants at start P(1)
      peasants[6] = -4;  // natural deaths P(7)
      peasants[7] = 8;   // births P(8)

      land = new int[MAX_LAND];

      land[0] = 600; // land at start L(1)

      grain = new int[MAX_GRAIN];

      grain[0] = 5193;   // grain at start G(1)
      grain[1] = -1344;  // used for food G(2)
      grain[3] = -768;   // seeding G(4)
      grain[7] = 1516;   // crop yield G(8)
      grain[8] = -120;   // castle expense G(9)
      grain[9] = -300;   // royal tax G(10)

      landFertility = new int[MAX_FERTILITY];   // S(6)

      landFertility[0] = 216;  // S(1) 100%
      landFertility[1] = 200;  // S(2) 80%
      landFertility[2] = 184;  // S(3) 60%

      unrest1 = 0;  // U1
      unrest2 = 0;  // U2

      randomValue = new int[MAX_RANDOM_VALUES];  // R(8)

      randomValue[0] = initRandomNumber(4,7);
      randomValue[1] = initRandomNumber(4,8);
      randomValue[2] = initRandomNumber(4,6);
      randomValue[3] = initRandomNumber(3,8);
      randomValue[4] = initRandomNumber(5,8);
      randomValue[5] = initRandomNumber(3,6);
      randomValue[6] = initRandomNumber(3,8);
      randomValue[7] = initRandomNumber(4,8);

      warWithKingStatus = 0;   // K%
   }

   public static void resetYearlyStats() {

      currentYear++;

      for (int i=0; i<MAX_PEASANTS; i++)
         peasants[i] = 0;

      for (int i=0; i<MAX_LAND; i++)
         land[i] = 0;

      for (int i=0; i<MAX_GRAIN; i++)
         grain[i] = 0;

      peasants[0] = totalPeasants;
      land[0] = totalLand;
      grain[0] = totalGrain;
   }

   public static String formatString(String stringValue, int fieldSize) {

      char[] spaces = new char[fieldSize];

      for (int i=0; i<fieldSize; i++)
         spaces[i] = ' ';

      String spacesStr = new String(spaces);

      int strLength = stringValue.length();

      String outputStr = spacesStr.substring(0, fieldSize-strLength);

      return (stringValue + outputStr);
   }

   public static String formatInteger(int integerValue, int fieldSize,
      boolean rightJustify) {

      char[] spaces = new char[fieldSize];

      for (int i=0; i<fieldSize; i++)
         spaces[i] = ' ';

      String spacesStr = new String(spaces);

      String integerStr = Integer.toString(integerValue);

      if (rightJustify) {

         int strLength = integerStr.length();
         String outputStr = spacesStr.substring(0, fieldSize-strLength);

         return (outputStr + integerStr);
      }
      else {

         if (integerValue < 0)
            return (integerStr);
         else
            return (" " + integerStr);
      }
   }

   public static void printTotals() {

      String s = new String();

      s = "Year " + currentYear +
         " Peasants " + totalPeasants +
         " Land " + totalLand +
         " Grain " + totalGrain;

      myDisplay.printTotals(s);

      System.out.print("\n\n\n");
      System.out.println("Year " + currentYear +
         " Peasants " + totalPeasants +
         " Land " + totalLand +
         " Grain " + totalGrain);
   }

   public static void printPeasants() {

      System.out.println();

      for (int i=0; i<MAX_PEASANTS; i++) {

         if ((peasants[i] != 0) || (i==0)) {

            System.out.println (formatString(peasantsText[i], 25) +
               formatInteger(peasants[i], 5, false));
         }
      }
      System.out.println (formatString("Peasants at end", 25) +
         formatInteger(totalPeasants, 5, false));
   }

   public static void printLand() {

      System.out.println();

      for (int i=0; i<MAX_LAND; i++) {

         if ((land[i] != 0) || (i == 0)) {

            System.out.println (formatString(landText[i], 25) +
              formatInteger(land[i], 5, false));
         }
      }
      System.out.println (formatString("Land at end of year", 25) +
         formatInteger(totalLand, 5, false));

      System.out.println();

      System.out.println (" 100%  80%  60%  40%  20% Depl");
      for (int i=0; i<MAX_FERTILITY; i++) {

         System.out.print (formatInteger(landFertility[i], 5, true));
      }
      System.out.println();
   }

   public static void printGrain() {

      System.out.println();

      for (int i=0; i<MAX_GRAIN; i++) {

         if (grain[i] != 0) {

            System.out.println (formatString(grainText[i],25) +
               formatInteger(grain[i], 5, false));
         }
      }
      System.out.println (formatString("Grain at end of year", 25) +
         formatInteger(totalGrain, 5, false));
   }

   public static void printLastYearsResults() {

      printTotals();
      if (skipDetailedReports)
         return;

      printPeasants();
      printLand();
      printGrain();

      if (currentYear <= 0) {

         System.out.println ("(Severe crop damage due to seven");
         System.out.println (" year locusts)");
      }
      System.out.println ("\n");
   }

   public static boolean unrestCheck() {

      if ((unrest1 > 88) || (unrest2 > 99)) {

         System.out.println ("The peasants tire of war and starvation");
         System.out.println ("You are deposed");

         return true;
      }
      else {

         return false;
      }
   }

   public static boolean playOneYear() {

      printLastYearsResults();
      resetYearlyStats();

      int grain = myDisplay.inputGrain();
      System.out.println("grain for food = " + grain);

//      if (endOfGameCondition())
//         return true;

//      if (feedPeasants())
//         return true;

//      buySellLand();

//      if (totalLand < 10) {

//         if (totalLandCheck())
//            return true;
//      }

//      if (warWithKing())
//         return true;

//      grainProduction();

//      if (updateWarStatus()) {

//         if (warWithNeighbor())
//          return true;
//      }

//      updatePopulation();

//      updateGrainTotal();

//      if (updateRoyalTax())
//         return true;

      return false;
   }

   public void run() {

//      myDisplay.showSplashScreen();
      initializeVars();
//      System.out.println("initializeVars() completed");

      char ans = myDisplay.skipDetailedReports();
      System.out.println("skip detail reports = " + ans);

      while (!stopThread.booleanValue()) {

         playOneYear();

         if (myDisplay.isQuit()) {

            if (exitGame()) {

               stopThread = new Boolean (true);
            }
         }
      }

      System.out.println("stopping the thread now");
   }
}
