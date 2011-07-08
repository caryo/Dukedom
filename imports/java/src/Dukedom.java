
import java.io.*;

class Dukedom {

   static boolean skipDetailedReports;   // R$

   static int totalPeasants;  // P
   static int totalLand;      // L
   static int totalGrain;     // G

   static int currentYear;    // Y%

   static double cropYield;   // C1

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

   static final double INITIAL_CROP_YIELD = 3.95;  // C1 initial value

   static final double WAR_FACTOR = 1.95; // M

   static final int MAX_RANDOM_VALUES = 8;

   static int[] randomValue;    // R(8)

   static int warWithKingStatus;   // K%

   public static void printVars() {

      System.out.println ("skipDetailReports = " + skipDetailedReports);
      System.out.println ("cropYield = " + cropYield);
      System.out.println ("D = " + D);
      System.out.println ("unrest1 = " + unrest1);
      System.out.println ("unrest2 = " + unrest2);
      System.out.println ("warWithKingStatus = " + warWithKingStatus);

      System.out.println ();
   }

   public static void initializeVars() {

      skipDetailedReports = false;  // R$

      currentYear = 0;      // Y%
      totalPeasants = 100;  // P
      totalLand = 600;      // L
      totalGrain = 4177;    // G

      cropYield = INITIAL_CROP_YIELD; // C1

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

   public static int getRandomNumber(int p1, int p2) {

      double randomPart = (double)((int)(Math.random() * 1000000.0) / 
         1000000.0);

      int rangePart = 1 + p2 - p1;

      int productPart = (int) (randomPart * (double) rangePart);

      int rtnVal = productPart + p1;

//      System.out.println ("p1=" + p1 +
//         ", p2=" + p2 +
//         ", rangePart=" + rangePart +
//         ", randomPart=" + randomPart +
//         ", productPart=" + productPart +
//         ", rtnVal=" + rtnVal);

      return (int) rtnVal;
   }

   public static int getRandomNumber(int p) {

      int rtnVal;
      int randomOne;

      randomOne = getRandomNumber(-RANDOM_RANGE,RANDOM_RANGE);

      rtnVal = randomOne + randomValue[p-1];

//      System.out.println ("random1=" + randomOne +
//         ", randomValue[" + p + "]=" + randomValue[p-1] +
//         ", rtnVal=" + rtnVal);

      return rtnVal;
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

   public static char getYesNo() {

      boolean exitLoop = false;
      String text = null;
      char c = '\0';

      while (!exitLoop) {

         try {
            InputStreamReader converter = new
InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            text = in.readLine();

            if ((text == null) || (text.length() == 0)) {

               System.out.println();
               System.out.print("Please answer yes or no : ");
               continue;
            }

            c = text.charAt(0);

            if (c == 'Y' || c == 'y' ||
                c == 'N' || c == 'n') {

               exitLoop = true;
            }
            else {

               System.out.println();
               System.out.print("Please answer yes or no : ");
            }
         }
         catch (IOException e) {

            System.out.println();
            System.out.println("caught an exception");
         }
      }

      if (text == null)
         text = new String("N");

      return Character.toUpperCase(text.charAt(0));
   }

   public static int getInteger() {

      boolean exitLoop = false;
      String text = null;
      int intValue = 0;

      while (!exitLoop) {

         try {
            InputStreamReader converter = new
InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            text = in.readLine();

            if ((text != null) && (text.length() > 0)) {

               intValue = Integer.parseInt(text);

               if (intValue < 0) {

                  System.out.println();
                  System.out.print("Please enter a non-negative #: ");
               }
               else {

                  exitLoop = true;
               }
            }
            else {

               System.out.println();
               System.out.print("Please enter a non-negative #: ");
            }
         }
         catch (IOException e) {

            System.out.println();
            System.out.println("caught an exception");
         }
      }

      if ((text == null) || (text.length() == 0))
         intValue = 0;

      return (intValue);
   }

   public static void printTotals() {

      System.out.print("\n\n\n");
      System.out.println("Year " + currentYear +
         " Peasants " + totalPeasants +
         " Land " + totalLand +
         " Grain " + totalGrain);
   }

   public static String formatString(String stringValue, int fieldSize)
{

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
         String outputStr = spacesStr.substring(0,
fieldSize-strLength);

         return (outputStr + integerStr);
      }
      else {

         if (integerValue < 0)
            return (integerStr);
         else
            return (" " + integerStr);
      }
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

   public static boolean totalPeasantCheck() {

      if (totalPeasants >= 33)
         return false;

      System.out.println ("You have so few peasants left that");
      System.out.println ("the High King has abolished your Ducal");
      System.out.println ("right");

      return true;
   }

   public static boolean totalGrainCheck() {

      if (totalGrain >= 429)
         return false;

      System.out.println ("You have so little grain left that");
      System.out.println ("the High King has abolished your Ducal");
      System.out.println ("right");

      return true;
   }

   public static boolean totalLandCheck() {

      if (totalLand >= 198)
         return false;

      System.out.println ("You have so little land left that");
      System.out.println ("the High King has abolished your Ducal");
      System.out.println ("right");

      return true;
   }

   public static boolean endOfGameCondition() {

      // too few peasants
      if (totalPeasantCheck())
         return true;

      // or

      // too little land left
      if (totalLandCheck())
         return true;

      // or

      // high disapproval rating
      if (unrestCheck())
         return true;

      // or

      // too little grain
      if (totalGrainCheck())
         return true;

      // or
      // reached age of retirement
      //
      if ((currentYear > 45) && (warWithKingStatus == 0)) {

         System.out.println("You have reached the age of retirement");
         return true;
      }

      unrest1 = 0;

      if (warWithKingStatus > 0) {

         warWithKingStatus = 2;

         System.out.println("The King demands twice the royal tax in");
         System.out.print("THE HOPE TO PROVOKE WAR.  WILL YOU PAY? ");

         char ans = getYesNo();

         if (ans == 'N')
            warWithKingStatus = -1;
      }

      return false;
   }

   public static boolean playOneYear() {

      printLastYearsResults();
      resetYearlyStats();

//      printVars();

      if (endOfGameCondition())
         return true;

      if (feedPeasants())
         return true;

      buySellLand();

      if (totalLand < 10) {

         if (totalLandCheck())
            return true;
      }

      if (warWithKing())
         return true;

      grainProduction();

      if (updateWarStatus()) {

         if (warWithNeighbor())
            return true;
      }

      updatePopulation();

      updateGrainTotal();

      if (updateRoyalTax())
         return true;

      return false;
   }

   public static void insufficientGrain(int purchasePrice) {

      System.out.println ("But you don't have enough grain");
      System.out.print ("You have " + totalGrain + " HL. ");
      System.out.println ("of grain left,");

      if (purchasePrice >= 4) {

         System.out.println ("Enough to buy " + (totalGrain /
purchasePrice) +
            " HA. of land");
      }
      else {

         System.out.println ("Enough to plant " + (totalGrain / 2) +
            " HA. of land");
      }
   }

   public static boolean feedPeasants() {

      boolean validInput = false;
      int grainForFood = 0;

      while (!validInput) {

         System.out.print ("Grain for food = ");

         grainForFood = getInteger();

         if (grainForFood < 100)
            grainForFood = grainForFood * totalPeasants;

         if (grainForFood > totalGrain) {

            insufficientGrain(0);
            continue;
         }

         if (((grainForFood/totalPeasants) < 11) &&
             (grainForFood != totalGrain)) {

            System.out.println ("Some peasants demonstrate before the");
            System.out.println ("castle with sharpened scythes");

            unrest1 = unrest1 + 3;  // U1
            continue;
         }

         validInput = true;
      }

      if (validInput) {

         grain[1] = grainForFood * -1;
         totalGrain = totalGrain - grainForFood;
         int grainPerPeasant = grainForFood / totalPeasants;
         if (grainPerPeasant < 13) {

            System.out.println ("Some peasants have starved");
            int peasantsFed = grainForFood / 13;
            int peasantsStarved = totalPeasants - peasantsFed;
            peasants[1] = peasantsStarved * -1;
            totalPeasants = totalPeasants - peasantsStarved;
         }

         int extraGrainPerPeasant = grainPerPeasant - 14;
         if (extraGrainPerPeasant > 4)
            extraGrainPerPeasant = 4;

         unrest1 = unrest1 + (3 * peasants[1]) - (2 * extraGrainPerPeasant);

         if (unrest1 > 88) {

            System.out.println ("The peasants tire of war and starvation");
            System.out.println ("You are deposed");
            return true;
         }

         if (totalPeasants < 33)
            return true;
      }

      return false;
   }

   public static void randomNumber_test() {

      for (int i=0;i<MAX_RANDOM_VALUES;i++) {

         System.out.println ("randomValue[" + i + "] = " +
            randomValue[i]);
      }

      System.out.println ("Allowable Range: 7.9 + x - 5");

      double tempFactor = INITIAL_CROP_YIELD;

      for (int i=0;i<20;i++) {

         double randomPart = getRandomNumber(1);

         double tempValue = (2*tempFactor) + randomPart - (double) 5;

         System.out.println (i + " : " + randomPart + "-->" + tempValue);
      }
   }

   public static void buySellLand() {

      double tempFactor = cropYield;

      int randomOne = getRandomNumber(1);

      int purchasePrice = (int) ((2 * tempFactor) + (double) randomOne - 5.0);

      if (purchasePrice < 4)
         purchasePrice = 4;

      boolean validInput = false;
      int landToBuy = 0;
      int grainForPurchase = 0;

      while (!validInput) {

         System.out.print("Land to buy at " + purchasePrice + " HL./HA.  = ");
         landToBuy = getInteger();

         grainForPurchase = landToBuy * purchasePrice;
         if (grainForPurchase > totalGrain) {

            insufficientGrain (purchasePrice);
            continue;
         }

         validInput = true;
      }

      if (landToBuy > 0) {

         grain[2] = grainForPurchase * -1;
         land[1] = landToBuy;
         landFertility[2] = landFertility[2] + landToBuy;
         totalGrain = totalGrain - grainForPurchase;
         totalLand = totalLand + landToBuy;
      }
      else {

         int landAvailableToSell = landFertility[0] +
            landFertility[1] + landFertility[2];
         int sellingPrice = purchasePrice - 1;
         int landToSell = 0;
         int grainForLandSale = 0;

         validInput = false;

         for (int i = 0; (i < 3) && (!validInput); sellingPrice--, i++) {

            System.out.print("Land to sell at " + sellingPrice +
               " HL./HA. = ");
            landToSell = getInteger();

            if (landToSell > landAvailableToSell) {

               System.out.println("But you only have " + landAvailableToSell +
                  " HA. of good land");
               continue;
            }

            grainForLandSale = landToSell * sellingPrice;
            if (grainForLandSale > 4000) {

               System.out.println("No buyers have that much grain try less");
            }
            else {

               validInput = true;
            }
         }

         if (!validInput) {

            System.out.println ("Buyers have lost interest");
         }
         else {

            if (landToSell > 0) {

               land[1] = landToSell * -1;

               int tempLandToSell = landToSell;
               boolean exitLoop = false;
               int fertilityIndex = 2;
               for (int i=0; (i<3) && (!exitLoop); i++) {

                  if (tempLandToSell <= landFertility[fertilityIndex])
{

                     exitLoop = true;
                  }
                  else {

                     tempLandToSell = tempLandToSell -
landFertility[fertilityIndex];
                     landFertility[fertilityIndex] = 0;
                     fertilityIndex = fertilityIndex - 1;
                  }
               }

               if (!exitLoop) {

                  System.out.println ("Land selling loop error");

                  RuntimeException re = new 
                     RuntimeException("Land selling loop error");
                  throw re;
               }

               landFertility[fertilityIndex] =
landFertility[fertilityIndex] -
                  tempLandToSell;
               totalLand = totalLand - landToSell;

               if (sellingPrice < 4) {

                  System.out.println("The High King appropriates half");
                  System.out.println("of your earnings as punishment");
                  System.out.println("for selling at such a low price");

                  grainForLandSale = grainForLandSale / 2;
               }

               totalGrain = totalGrain + grainForLandSale;
               grain[2] = grainForLandSale;
            }
         }
      }
   }

   public static boolean warWithKing() {

      if (warWithKingStatus != -2)
         return false;

      System.out.println ("The King's army is about to attack");
      System.out.println ("your duchy");
      int merceneriesHired = totalGrain / 100;
      System.out.println ("You have hired " + merceneriesHired +
         " foreign mercenaries");
      System.out.println ("at 100 HL. each (pay in advance)");

      if (((8 * merceneriesHired) + totalPeasants) > 2399) {

         System.out.println ("Wipe the blood from the crown - you");
         System.out.println ("are High King!  A nearby monarchy");
         System.out.println ("THREATENS WAR; HOW MANY .........");
      }
      else {

         System.out.println("Your head is placed atop of the");
         System.out.println("castle gate.");
      }

      return true;
   }

   public static void grainProduction() {

      int landToPlant = 0;
      int grainForSeeding = 0;
      boolean exitLoop = false;

      while (!exitLoop) {

         System.out.print("Land to be planted = ");
         landToPlant = getInteger();

         if (landToPlant > totalLand) {

            System.out.println ("But you don't have enough land");
            System.out.println ("You only have " + totalLand +
               " HA. of land left");
            continue;
         }

         if (landToPlant > (4 * totalPeasants)) {

            System.out.println ("But you don't have enough peasants");
            System.out.println ("Your peasants can only plant " +
               (4 * totalPeasants) + " HA. of land");
            continue;
         }

         grainForSeeding = 2 * landToPlant;

         if (grainForSeeding > totalGrain) {

            insufficientGrain(0);
            continue;
         }

         exitLoop = true;
      }

      if (!exitLoop) {

         System.out.println("Land to plant error");
         RuntimeException re = new RuntimeException("Land to plant error");
         throw re;
      }

      int[] landPlanted = new int[MAX_FERTILITY];

      if (landToPlant > 0) {

         for (int i=0; i<6; i++)
            landPlanted[i] = 0;

         grain[3] = grainForSeeding * -1;
         totalGrain = totalGrain - grainForSeeding;
         grain[7] = landToPlant;   // to be used later for crop yield

         int tempLandToPlant = landToPlant;
         int cropIndex = 0;

         exitLoop = false;

         for (int i=0; (i<6) && (!exitLoop); i++) {

            if (tempLandToPlant <= landFertility[cropIndex]) {

               exitLoop = true;
               continue;
            }

            tempLandToPlant = tempLandToPlant -
landFertility[cropIndex];
            landPlanted[cropIndex] = landFertility[cropIndex];
            landFertility[cropIndex] = 0;
            cropIndex = cropIndex + 1;
         }

         if (!exitLoop) {

            System.out.println ("Land selling loop error");

            RuntimeException re = new RuntimeException("Land table error");
            throw re;
         }

         landPlanted[cropIndex] = tempLandToPlant;
         landFertility[cropIndex] = landFertility[cropIndex] -
            tempLandToPlant;
      }

      landFertility[0] = landFertility[0] + landFertility[1];
      landFertility[1] = 0;

      for (int i=2; i<6; i++) {

         landFertility[i - 2] = landFertility[i - 2] + landFertility[i];
         landFertility[i] = 0;
      }

      if (landToPlant > 0) {

         for (int i=0; i<5; i++) {

            landFertility[i+1] = landFertility[i+1] + landPlanted[i];
         }

         landFertility[5] = landFertility[5] + landPlanted[5];
      }

      // the following value can be determined using one of two modifiers
      // added to the random number:  Kaapke uses 3.0 and Anderson uses 9.0

      double workingCropYield = (double) getRandomNumber(2) + 3.0; // C

//      System.out.println("optimal crop yield = " + workingCropYield);

      if ((currentYear % 7) == 0) {

         System.out.println("Seven year locusts");

         // the crop yield should be impacted by locust damage-  Kaapke
         // uses 0.50 while Anderson uses 0.65

         workingCropYield = workingCropYield * 0.50;

//         System.out.println("updated effective crop yield = " + 
//            workingCropYield);
      }

      double weightedCropYield = 0.0;

      int cropIndex = 1;

      for (int i=0; i<5; i++,cropIndex++) {

         weightedCropYield = weightedCropYield +
            ((double)(landPlanted[i]) * (1.2 - (0.2 * (double) cropIndex)));

//         System.out.println("crop " + (i+1) + ": " + landPlanted[i] +
//            " HA. planted, sub-total Yield = " + weightedCropYield);
      }

      if (grain[7] == 0) {

         cropYield = 0.0;
         workingCropYield = 0.0;
      }
      else {

//         System.out.println("grain[7] = " + grain[7]);

//         System.out.println("weightedCropYield / landPlanted = " +
//            (weightedCropYield / (double) grain[7]) );

//         System.out.println("workingCropYield = " + workingCropYield);

         cropYield = (double) ((int) ((workingCropYield * (weightedCropYield /
            (double) grain[7])) * 100.0) / 100.0);

         workingCropYield = cropYield;
      }

      System.out.println("Yield = " + workingCropYield + " HL./HA.");

      int randomThree = getRandomNumber(3) + 3;

      if (randomThree < 9)
         return;

      int grainInfested = (randomThree * totalGrain) / 83;

      grain[4] = grainInfested * -1;

      totalGrain = totalGrain - grainInfested;

      System.out.println ("Rats infest the granery");

      if ((totalPeasants < 67) || (warWithKingStatus == -1))
         return;

      int randomFour = getRandomNumber(4);

      if (randomFour > (totalPeasants / 30))
         return;

      System.out.println("The king requires " + randomFour + " peasants for");
      System.out.println("his estate and mines.  Will you supply");
      System.out.println("them (Y)es or pay " + (100 * randomFour) + " HL. of");
      System.out.print("grain instead (N)o ? ");

      char ans = getYesNo();

      if (ans == 'N') {

         int grainLevy = randomFour * 100;

         grain[9] = grainLevy * -1;

         totalGrain = totalGrain - grainLevy;
      }
      else {

         peasants[2] = randomFour * -1;

         totalPeasants = totalPeasants - randomFour;
      }
   }

   public static boolean updateWarStatus() {

      if (warWithKingStatus != -1)
         return true;

      System.out.println("The High King calls for peasant levies");
      System.out.println("and hires many foreign mercenaries");

      warWithKingStatus = -2;   // K%

      return false;
   }

   public static boolean warWithNeighbor() {

      int x1 = (int)(11.0 - 1.5 * cropYield);
      int x2 = 0;

      if (x1 < 2)
         x1 = 2;

      if ((warWithKingStatus != 0) || (totalPeasants <= 109) ||
         (((17 * (totalLand - 400)) + totalGrain) <= 10600)) {

         x2 = 0;
      }
      else {

         System.out.println("The High King grows uneasy and may");
         System.out.println("be subsidizing wars against you");

         x1 = x1 + 2;
         x2 = currentYear + 5;
      }

      int x3 = getRandomNumber(5);

      if (x3 > x1) {

         return false;
      }

      System.out.println("A nearby Duke threatens war; ");

      x2 = (int)(x2 + 85 + 18 * getRandomNumber(6));

      double x4 = 1.2 - ((double) unrest1 / 16.0);

      int x5 = (int) ((double) totalPeasants * x4) + 13;

      System.out.print("Will you attack first ? ");
      char ans = getYesNo();

      int warCasualties = 0;

      if (ans == 'N') {

         if (x2 >= x5) {

            System.out.println("First strike failed - you need");
            System.out.println("professionals");

            warCasualties = x3 + x1 + 2;
            peasants[3] = warCasualties * -1;
            x2 = x2 + (3 * peasants[3]);
         }
         else {

            System.out.println("Peace negotiations successful");
            warCasualties = x1 + 1;
            peasants[3] = warCasualties * -1;
            x2 = 0;
         }

         totalPeasants = totalPeasants - warCasualties;

         if (x2 < 1) {

            unrest1 = unrest1 - (2 * peasants[3]) - (3 * peasants[4]);

            return false;
         }
      }

      boolean exitLoop = false;
      int merceneriesHired = 0;

      while (!exitLoop) {

         System.out.println("How many mercenaries will you hire");
         System.out.print("at 40 HL. each = ");

         merceneriesHired = getInteger();

         if (merceneriesHired > 75) {

            System.out.println("There are only 75 available for hire");
            continue;
         }
         exitLoop = true;
      }

      x2 = (int) ((double) x2 * WAR_FACTOR);
      x5 = (int) ((double) totalPeasants * x4) + (7 * merceneriesHired) +13;
      int x6 = x2 - (4 * merceneriesHired) - (int)(0.25 * (double) x5);

      x2 = x5 - x2;
      land[2] = (int) (0.8 * (double) x2);

      if ((double) (land[2] * -1) > (double) (0.67 * totalLand)) {

         System.out.println("You have been overrun and have lost");
         System.out.println("your entire Dukedom");
         return true;
      }

      x1 = land[2];

      int landIndex=3;
      for (int i=0;i<3;i++,landIndex--) {

         x3 = (int) (x1 / landIndex);
         if ((x3 * -1) <= landFertility[i]) {

            x5 = x3;
         }
         else {

            x5 = landFertility[i] * -1;
         }

         landFertility[i] = landFertility[i] + x5;
         x1 = x1 - x5;
      }

      if (land[2] >= 399) {

         System.out.println("You have overrun the enemy and annexed");
         System.out.println("his entire Dukedom");

         grain[6] = 3513;
         totalGrain = totalGrain + grain[6];
         x6 = -47;
         x4 = 0.55;

         if (warWithKingStatus <= 0) {

            warWithKingStatus = 1;

            System.out.println("The King fears for his throne and");
            System.out.println("may be planning direct action");
         }
      }
      else {

         if (x2 >= 1) {

            System.out.println("You have won the war");
            x4 = 0.67;
            grain[6] = (int) (1.7 * land[2]);
            totalGrain = totalGrain + grain[6];
         }
         else {

            System.out.println("You have lost the war");
            x4 = grain[7] / totalLand;
         }

         if (x6 <= 9)
            x6 = 0;
         else
            x6 = (int) (x6 / 10);
      }

      if (x6 > totalPeasants)
         x6 = totalPeasants;

      peasants[3] = peasants[3] - x6;
      totalPeasants = totalPeasants - x6;
      grain[7] = grain[7] + (int)(x4 * (double) land[2]);

      x6 = 40 * merceneriesHired;
      if (x6 <= totalGrain) {

         grain[5] = (x6 * -1);
      }
      else {

         grain[5] = (totalGrain * -1);
         peasants[4] = (-1 * (int)((x6 - totalGrain) / 7)) - 1;
         System.out.println("There isn't enough grain to pay the");
         System.out.println("merceneries");
      }

      totalGrain = totalGrain + grain[5];
      if ((peasants[4] * -1) > totalPeasants) {

         peasants[4] = totalPeasants * -1;
      }
      totalPeasants = totalPeasants + peasants[4];
      totalLand = totalLand + land[2];
      unrest1 = unrest1 - (2 * peasants[3]) - (3 * peasants[4]);

      return false;
   }

   public static void updatePopulation() {

      // Kaapke uses 7 as the parameter to getRandomNumber
      // Anderson uses 8 as the parameter with an additional +1 modifier

      double x1 = getRandomNumber(7);

      System.out.println ("getRandomNumber(7) = " + x1);

      int x2 = 0;

      if ((int) x1 > 3) {

         ;
      }
      else {

         if ((int) x1 != 1) {

            System.out.println("A POX EPIDEMIC has broken out");
            x2 = (int) (x1 * 5.0);
            peasants[5] = (-1 * (int)(totalPeasants / x2));
            totalPeasants = totalPeasants + peasants[5];
         }
         else {

            System.out.println ("D = " + D);

            if (D > 0) {

               ;
            }
            else {

               System.out.println("The BLACK PLAGUE has struck the area");
               D = 13;
               x2 = 3;
               peasants[5] = (-1 * (int) (totalPeasants / x2));
               totalPeasants = totalPeasants + peasants[5];
            }
         }
      }

      x1 = getRandomNumber(8) + 4;

      if (peasants[4] != 0)
         x1 = 4.5;

      peasants[7] = (int) (totalPeasants / x1);
      peasants[6] = (int) (0.3 - (totalPeasants / 22));
      totalPeasants = totalPeasants + peasants[6] + peasants[7];
      D = D - 1;

      return;
   }

   public static void updateGrainTotal() {

      grain[7] = (int)(cropYield * grain[7]);
      totalGrain = totalGrain + grain[7];

      int x1 = grain[7] - 4000;      // determine castle tax

      if (x1 > 0)
         grain[8] = (-1 * (int) (0.1 * x1));

      grain[8] = grain[8] - 120;
      totalGrain = totalGrain + grain[8];
   }

   public static boolean updateRoyalTax() {

      if (warWithKingStatus >= 0) {

         int x1 = (-1 * (int)(totalLand / 2));

         if (warWithKingStatus >= 2)
            x1 = x1 * 2;

         if ((-1 * x1) > totalGrain) {

            System.out.println("You have insufficient grain to pay");
            System.out.println("the royal tax");

            return true;
         }

         grain[9] = grain[9] + x1;
         totalGrain = totalGrain + x1;
      }

      unrest2 = (int) (unrest2 * 0.85) + unrest1;

      return false;
   }

   public static void main(String args[]) {

      initializeVars();

      System.out.println("\nD U K E D O M\n");

      System.out.print("Do you want to skip detailed reports ? ");

      char ans = getYesNo();

      if (ans == 'Y') {

         skipDetailedReports = true;

         System.out.println();
         System.out.println("Skipping detailed reports ...");
      }
      else {

         skipDetailedReports = false;

//         System.out.println();
//         System.out.println("Print detailed reports ...");
      }

      boolean exitGame = false;

      while (!exitGame)
         exitGame = playOneYear();
   }

}
