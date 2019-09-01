#include <stdlib.h>
#include <stdio.h>

#define FNR(Q1,Q2) fnr(Q1, Q2)

#define FNX(Q1) fnx(Q1)

extern rand();

void long_report();

FILE *inFile_fp;

// Global variables

int V;
float X1;
int X2;
int X3;
int X4;
int X5;
int X6;
int nG;
int nL;
int nP;

int P[8+1];
int L[3+1];
int G[10+1];
int S[6+1];
int U[6+1];
int R[8+1];
char* szP[8+1];
char* szL[3+1];
char* szG[10+1];

int   F3;
float M;

char szR; // yes or no for skipping long reports

float C;
int nY;
float C1;
int U1;
int U2;
int K;
int D;

int numeric_data[] = {
   96, 0, 0, 0, 0, 0, -4, 8,
   600, 0, 0,
   5193, -1344, 0, -768, 0, 0, 0, 1516, -120, -300,
   216, 200, 184, 0, 0, 0
};

char* text_data[] = {
   "Peasants at start", "Starvations", "King's levy",
   "War casualties", "Looting victims", "Disease victims",
   "Natural deaths", "Births",
   "Land at start", "Bought/sold", "Fruits of war",
   "Grain at start", "Used for food", "Land deals",
   "Seedings", "Rat losses", "Mercenary hire",
   "Fruits of war", "Crop yield", "Castle expense",
   "Royal tax"
};

void start_new_game()
{
   int i, j;
   int Q1, Q2;

   C = 0.0;
   nY = 0;
   C1 = 3.95;
   U1 = 0;
   U2 = 0;
   K  = 0;
   D  = 0;
   nP = 100;
   nL = 600;
   nG = 4177;

   j = 0;
   for (i=1;i<=8;i++,j++)
      P[i] = numeric_data[j];
   for (i=1;i<=3;i++,j++)
      L[i] = numeric_data[j];
   for (i=1;i<=10;i++,j++)
      G[i] = numeric_data[j];
   for (i=1;i<=6;i++,j++)
      S[i] = numeric_data[j];

   j = 0;
   for (i=1;i<=8;i++,j++)
      szP[i] = text_data[j];
   for (i=1;i<=3;i++,j++)
      szL[i] = text_data[j];
   for (i=1;i<=10;i++,j++)
      szG[i] = text_data[j];

   Q1 = 4; Q2 = 7; R[1] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[1] = %d\n", Q1, Q2, R[1]);
           Q2 = 8; R[2] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[2] = %d\n", Q1, Q2, R[2]);
           Q2 = 6; R[3] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[3] = %d\n", Q1, Q2, R[3]);
   Q1 = 3; Q2 = 8; R[4] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[4] = %d\n", Q1, Q2, R[4]);
   Q1 = 5;         R[5] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[5] = %d\n", Q1, Q2, R[5]);
   Q1 = 3; Q2 = 6; R[6] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[6] = %d\n", Q1, Q2, R[6]);
           Q2 = 8; R[7] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[7] = %d\n", Q1, Q2, R[7]);
   Q1 = 4;         R[8] = gauss (Q1, Q2);
// printf ("Q1 = %d, Q2 = %d, R[8] = %d\n", Q1, Q2, R[8]);

   return;
}

float rnd(int i)
{
   int rv;
   float rc;

   if (i==0 || i==1)
   {
      // return a random number from 0.000 to 0.999
      rv = rand();
      if (rv < 1000)
         rv += 1000;
      rc = ((int)(rv % 1000) / 1000.0);
      // printf ("i = %d, rv = %d, rc = %5.3f\n", i, rv, rc);
      return rc;
   }
   else
   {
      // otherwise return a number from 1 to i inclusive
      rv = rand();
      if (rv < i)
         rv += i;
      rc = ((rv % i) + 1);
      // printf ("i = %d, rv = %d, rc = %5.3f\n", i, rv, rc);
      return rc;
   }
}

int fnr(int Q1, int Q2)
{
   int x = rnd(1)*(1+Q2-Q1)+Q1;
   // printf ("Q1 = %d, Q2 = %d, FNR() = %d\n", Q1, Q2, x);
   return x;
}

int fnx(int Q1)
{
   int y = FNR(-F3,F3)+R[Q1];
   // printf ("Q1 = %d, FNX() = %d\n", Q1, y);
   return y;
}

int gauss(int Q1, int Q2)
{
   int Q3, G0;

   Q3 = FNR(Q1,Q2);
   if (FNR(Q1,Q2) > 5)
      G0 = (Q3 + FNR(Q1,Q2))/2;
   else
      G0 = Q3;

   return G0;
}

char getYesNo()
{
   char inStr[80];
   char rv;
   int  done = 0;
   char *fileOk = NULL;

   while (!done)
   {
      printf ("? ");
      fileOk = NULL;
      if (inFile_fp && !feof(inFile_fp))
      {
         fileOk = fgets(inStr, 80, inFile_fp);
         if (fileOk)
            printf ("%s\n", inStr);
      }
      if (fileOk == NULL)
      {
         if (inFile_fp)
         {
            fclose (inFile_fp);
            inFile_fp = NULL;
         }
         fgets(inStr, 80, stdin);
      }
      if (!strncmp(inStr,"quit", 4))
      {
         printf ("\n");
         exit(0);
      }
      rv = toupper(inStr[0]);
      if (!(rv == 'Y' || rv == 'N'))
         printf ("Please answer Yes or No: ");
      else
         done = 1;
   }

   return rv;
}

int getValue()
{
   char inStr[80];
   int rv;
   int done = 0;
   char *fileOk = NULL;

   while (!done)
   {
      printf ("? ");
      fileOk = NULL;
      if (inFile_fp && !feof(inFile_fp))
      {
         fileOk = fgets(inStr, 80, inFile_fp);
         if (fileOk)
            printf ("%s\n", inStr);
      }
      if (fileOk == NULL)
      {
         if (inFile_fp)
         {
            fclose (inFile_fp);
            inFile_fp = NULL;
         }
         fgets(inStr, 80, stdin);
      }
      if (!strncmp(inStr,"quit", 4))
      {
         printf ("\n");
         exit(0);
      }
      rv = atoi(inStr);
      if (rv >= 0)
         done = 1;
      else
         printf ("Please enter a non-negative number: ");
   }

   return rv;
}

void insufficientGrain()
{
   printf ("But you don't have enough grain\n");
   printf ("You have %d HL. of grain left,\n", nG);
   if (X1 >= 4.0)
      printf ("Enough to buy %d HA. of land\n", (int) ((float)(nG)/X1));
   printf ("Enough to plant %d HA. of land\n", (int) nG/2);
   return;
}

void notEnoughLand()
{
   printf ("But you don't have enough land\n");
   printf ("You only have %d HA. of land left\n", nL);
   return;
}

void notEnoughPeasants()
{
   printf ("But you don't have enough peasants\n");
   printf ("Your peasants can only plant %d HA. of land\n", 4*nP);
   return;
}

void initialize()
{
   return;
}

void introduction()
{
   char ans;

   printf ("\nD U K E D O M\n");
   printf ("Microcomputer Version\n");
   printf ("\nDo you want instructions");
   ans = getYesNo();
   if (ans == 'Y')
      printf ("sorry - not ready yet\n");

   F3 = 2;
   M = 1.95;
   return;
}

void long_report()
{
   int j;

   for (j=1;j<=8;j++)
   {
      if ((P[j] != 0) || (j==1))
         printf ("%-20.20s %d\n", szP[j], P[j]);
   }
   printf ("%-20.20s %d\n\n", "Peasants at end", nP);

   for (j=1;j<=3;j++)
   {
      if ((L[j] != 0) || (j==1))
         printf ("%-20.20s %d\n", szL[j], L[j]);
   }
   printf ("%-20.20s %d\n\n", "Land at end", nL);

   printf ("100%%  80%%  60%%  40%%  20%% Depl\n");
   printf ("%4d", S[1]);
   for (j=2;j<=6;j++)
      printf ("%5d", S[j]);
   printf ("\n\n");

   for (j=1;j<=10;j++)
   {
      if ((G[j] != 0) || (j==1))
         printf ("%-20.20s %d\n", szG[j], G[j]);
   }
   printf ("%-20.20s %d\n\n", "Grain at end", nG);

   if (nY <= 0)
      printf ("(Severe crop damage due to seven year locusts)\n");
   return;
}

void last_years_results()
{
   int i;

   printf ("\n\nYear %d Peasants %d Land %d Grain %d\n\n", nY, nP, nL, nG);
   if (szR == 'N')
      long_report();
   printf ("\n\n");

   nY = nY +1;
   for (i=1;i<=8;i++)
      P[i] = 0;
   for (i=1;i<=3;i++)
      L[i] = 0;
   for (i=1;i<=10;i++)
      G[i] = 0;

   P[1] = nP; L[1] = nL; G[1] = nG;

   return;
}

void deposed()
{
   printf ("The peasants tire of war and starvation\n");
   printf ("You are deposed\n\n");
   return;
}

int end_of_game_check()
{
   char ans;

   if (nP < 33)
   {
      printf ("You have so few peasants left that\n");
      printf ("the High King has abolished your Ducal right\n\n");
      return (1);
   }
   if (nL < 199)
   {
      printf ("You have so little land left that\n");
      printf ("the High King has abolished your Ducal right\n\n");
      return (1);
   }
   if ((nG < 429) || (U1 > 88) || (U2 > 99))
   {
      deposed();
      return (1);
   }
   if ((nY > 45) && (K == 0))
   {
      printf ("You have reached the age of mandatory retirement\n");
      return (1);
   }

   U1 = 0;
   if (K > 0)
   {
      printf ("The King demands twice the royal tax in the\n");
      printf ("hope of provoking war.  Will you pay");
      ans = getYesNo();
      K = 2;
      if (ans == 'N')
         K = -1;
   }
   return (0);
}

void feed_the_peasants()
{
   int done = 0;

   while (!done)
   {
      printf ("Grain for food = ");
      V = getValue();
      if (V < 100)
         V = V * nP;
      if (V > nG)
      {
         insufficientGrain();
         continue;
      }

      if (((V / nP) < 11) && (V != nG))
      {
         printf ("The peasants demonstrate before the castle\n");
         printf ("with sharpened scythes\n");
         U1 = U1+3;
         continue;
      }

      done = 1;
   }

   G[2] = -V;
   nG = nG + G[2];

   return;
}

int starvation_and_unrest()
{
   X1 = V / nP;
   if (X1 < 13.0)
   {
      printf ("Some peasants have starved during the winter\n");
      P[2] = -(int)(nP - (V/13));
      nP = nP + P[2];
   }

   X1 = X1 - 14.0;
   if (X1 > 4.0)
      X1 = 4.0;
   U1 = U1 - (3 * P[2]) - (2.0 * X1);
   if (U1 > 88)
   {
      deposed();
      return (1);
   }
   if (nP < 33)
      return (end_of_game_check());

   return (0);
}

int purchase_land()
{
   int done = 0;

   C = C1;
   X1 = (int)(2.0 * C + FNX(1) - 5.0);
   if (X1 < 4.0)
      X1 = 4.0;

   while (!done)
   {
      printf ("Land to buy at %d HL.HA. = ", (int) X1);
      V = getValue();
      G[3] = -V * X1;
      if (-G[3] > nG)
         insufficientGrain();
      else
         done = 1;
   }

   L[2] = V;
   S[3] = S[3] + V;

   if (V > 0)
   {
      nL = nL + L[2];
      nG = nG + G[3];
   }

   return V;
}

int sell_land()
{
   int j1, valid = 0;

   X2 = S[1] + S[2] + S[3];

   for (j1=1; (j1<=3) && !(valid); j1++)
   {
      X1 = X1 - 1.0;
      printf ("Land to sell at %8.2f HL.HA. = ", X1);
      V = getValue();
      if (V > X2)
      {
         printf ("But you only have %d HA. of good land\n", X2);
      }
      else
      {
         G[3] = V * X1;
         if (G[3] > 4000)
            printf ("No buyers have that much grain - sell less\n");
         else
            valid = 1;
      }
   }

   if (!valid)
   {
      printf ("Buyers have lost interest\n");
      V = 0;
      G[3] = 0;
   }

   L[2] = -V;

   valid = 0;
   for (j1=3; (j1>=1) && !(valid); j1--)
   {
      if (V <= S[j1])
         valid = 1;
      else
      {
         V = V - S[j1];
         S[j1] = 0;
      }
   }

   if (!valid)
   {
      printf ("LAND SELLING LOOP ERROR - CONTACT PROGRAM AUTHOR IF\n");
      printf ("ERROR IS NOT YOURS IN ENTERING PROGRAM,\n");
      printf ("AND SEEMS TO BE FAULT OF PROGRAM'S LOGIC.\n");
      exit (-1);
   }

   S[j1] = S[j1] - V;
   nL = nL + L[2];

   if (nL < 10)
   {
      if (end_of_game_check())
         return (-1);
   }
   else
   {
      if ((L[2] < 0) && (X1 < 4.0))
      {
         G[3] = G[3] / 2;
         printf ("The High King appropriates half your earnings\n");
         printf ("as punishment for selling at such a low price\n");
      }
   }

   nG = nG + G[3];

   return 0;
}

int war_with_the_king()
{
   if (K != -2)
      return (0);

   printf ("The King's army is about to attack your duchy\n");
   X1 = nG / 100.0;
   printf ("at 100 HL. each (payment in advance)\n");
   printf ("you have hired %8.2f foreign mercenaries\n", X1);
   if (nG * X1 + nP > 2399.0)
   {
      printf ("Wipe the blood from the crown - you are now High King!\n\n");
      printf ("A nearby monarchy threatens war; ");
      printf ("how many .........\n\n\n\n");
      exit (0);
   }
   else
   {
      printf ("The placement of your head atop the castle gate\n");
      printf ("signifies that ");
      printf ("the High King has abolished your Ducal right\n\n");
      return (1);
   }
}

void grain_production()
{
   int done = 0;

   while (!done)
   {
      printf ("Land to plant = ");
      V = getValue();
      if (V > nL)
      {
         notEnoughLand();
         continue;
      }
      if (V > (4*nP))
      {
         notEnoughPeasants();
         continue;
      }
      G[4] = -2 * V;
      if (-G[4] > nG)
      {
         insufficientGrain();
         continue;
      }
      done = 1;
   }
   G[8] = V;
   nG = nG + G[4];
   return;
}

void update_land_tables()
{
   int i, j1, valid = 0;

   for (i=1;i<=6;i++)
      U[i] = 0;

   for (j1=1; (j1<=6) && !(valid); j1++)
   {
      if (V <= S[j1])
      {
         valid = 1;
         break;  // important to have this here so that j1 doesn't increment
      }
      else
      {
         V = V - S[j1];
         U[j1] = S[j1];
         S[j1] = 0;
      }
   }

   if (!valid)
   {
      printf ("LAND TABLE UPDATING ERROR - PLEASE CONTACT PROGRAM AUTHOR\n");
      printf ("IF ERROR IS NOT A FAULT OF ENTERING THE PROGRAM, BUT RATHER\n");
      printf ("FAULT OF THE PROGRAM LOGIC.\n");
      exit (-1);
   }

   U[j1] = V;
   S[j1] = S[j1] - V;
   S[1] = S[1] + S[2];
   S[2] = 0;

   for (j1=3; j1<=6; j1++)
   {
      S[j1-2] = S[j1-2] + S[j1];
      S[j1] = 0;
   }

   for (j1=1; j1<=5; j1++)
      S[j1+1] = S[j1+1] + U[j1];

   S[6] = S[6] + U[6];

   return;
}

void crop_yield_and_losses()
{
   int j1;
   char ans;

   C = FNX(2) + 3.0;
   if (((int)(nY/7) * 7) == nY)
   {
      printf ("Seven year locusts\n");
      C = C / 2.0;
   }
   X1 = 0.0;
   for (j1=1;j1<=5;j1++)
      X1 = X1 + U[j1] * (1.2 - 0.2 * j1);
   if (G[8] == 0)
   {
      C1 = 0.0;
      C = 0.0;
   }
   else
   {
      C1 = (int)(C * (float)((X1/G[8]) * 100) / 100.0);
      C = C1;
   }
   printf ("Yield = %8.2f HL./HA.\n", C);
   X1 = (float)(FNX(3) + 3.0);
   if (X1 < 9.0)
      return;
   G[5] = -(int)((X1 * nG) / 83);
   nG = nG + G[5];
   printf ("Rats infest the grainery\n");
   if ((nP < 67) || (K == -1))
      return;
   X1 = (float)(FNX(4));
   if (X1 > (nP / 30.0))
      return;
   printf ("The High King requires %d peasants for his estates\n", (int) X1);
   printf ("and mines.  Will you supply them (Yes) or pay %d\n",
      (int)(X1 * 100.0));
   printf ("HL. of grain instead (No)");
   ans = getYesNo();
   if (ans == 'N')
   {
      G[10] = -100.0 * X1;
      nG = nG + G[10];
      return;
   }
   P[3] = -X1;
   nP = nP + P[3];

   return;
}

int war()
{
   int done;
   char ans;
   int j1;

   if (K == -1)   // line 5190
   {
      printf ("The High King calls for peasant levies\n");
      printf ("and hire many foreign mercenaries\n");
      K = -2;
      return (0);  // goto 6340
   }
   X1 = (int)(11.0 - (1.5 * C));  // line 5240
   if (X1 < 2.0)
      X1 = 2.0;
   if ((K != 0) || (nP <= 109) || ((17 * (nL - 400) + nG) <= 10600))
   {
      X2 = 0;     // line 5350
   }
   else
   {
      printf ("The High King grows uneasy and may\n");
      printf ("be subsidizing wars against you\n");
      X1 = X1 + 2.0;  // line 5310
      X2 = nY + 5;
   }
   X3 = (int) FNX(5);  // line 5380
   if (X3 > X1)
      return (0);   // goto 6340
   printf ("A nearby Duke threatens war; ");
   X2 = (int)(X2 + 85.0 + (18.0 * FNX(6)));
   X4 = 1.2 - (U1 / 16);  // line 5440
   X5 = (int)(nP * X4) + 13;
   printf ("Will you attack first");
   ans = getYesNo();
   if (ans == 'N')
   {
      ;  // goto 5590 - fall through
   }
   else
   {
      if (X2 >= X5)  // goto 5540
      {
         printf ("First strike failed - you need professionals\n");
         P[4] = -X3 - X1 - 2;
         X2 = X2 + (3 * P[4]);
         ;   // fall through to line 5570
      }
      else
      {
         printf ("Pease negotiations were successful\n");
         P[4] = -X1 - 1;  // line 5510
         X2 = 0;
         ;   // goto 5570
      }
      nP = nP + P[4];  // line 5570
      if (X2 < 1)
      {
         U1 = U1 - (2*P[4]) - (3*P[5]);
         return (0);   // goto line 6340
      }
   }
   done = 0;   // line 5590
   while (!done)
   {
      printf ("How many mercenaries will you hire at 40 HL. each = ");
      V = getValue();
      if (V > 75)
      {
         printf ("There are only 75 mercenaries available for hire\n");
         continue;
      }
      else
         done = 1;
   }
   X2 = (int)((float) X2 * M);            // line 5640
   X5 = (int)((nP * X4) + (7 * V) + 13);
   X6 = X2 - (4*V) - (int)(0.25 * X5);
   X2 = X5 - X2;
   L[3] = (int)(0.8 * X2);
   if (-L[3] > (int)(0.67 * nL))
   {
      printf ("You have been overrun and have lost the entire Dukedom\n");
      printf ("The placement of your head atop the castle gate\n");
      printf ("signifies that ");
      printf ("the High King has abolished your Ducal right\n\n");
      return (1);
   }
   X1 = L[3];                             // line 5720
   for (j1=1;j1<=3;j1++)
   {
      X3 = (int)(X1 / (4 - j1));
      if (-X3 <= S[j1])
         X5 = X3;
      else
         X5 = -S[j1];
      S[j1] = S[j1] + X5;
      X1 = X1 - X5;
   }
   for (j1=4;j1<=6;j1++)
   {
      if (-X1 <= S[j1])
         X5 = X1;
      else
         X5 = -S[j1];
      S[j1] = S[j1] + X5;
      X1 = X1 - X5;
   }
   if (L[3] < 399)                       // line 5900
   {
      if (X2 >= 0)                       // line 6010
      {
         printf ("You have won the war\n");
         X4 = 0.67;
         G[7] = (int)(1.7 * L[3]);
         nG = nG + G[7];
         ; // goto 6090 - fall through
      }
      else
      {
         printf ("You have lost the war\n");  // line 6070
         X4 = G[8] / nL;
         ; // fall through to line 6090
      }
      if (X6 <= 9)   // line 6090
         X6 = 0;
      else
         X6 = (int)(X6 / 10);
      ; // fall through to line 6130
   }
   else
   {
      ; // line 5900 (continued)

      printf ("You have overrun the enemy and annexed his entire Dukedom\n");
      G[7] = 3513;
      nG = nG + G[7];
      X6 = -47;
      X4 = 0.55;
      if (K > 0)
      {
         ; // goto 6130 - fall through
      }
      else
      {
         K = 1;  // line 5970
         printf ("The King fears for his throne and\n");
         printf ("may be planning direct action\n");
         ; // goto 6130 - fall through
      }
   }

   if (X6 > nP)    // line 6130
      X6 = nP;
   P[4] = P[4] - X6;
   nP = nP - X6;
   G[8] = G[8] + (int)(X4 * L[3]);
   X6 = 40 * V;
   if (X6 <= nG)
   {
      G[6] = -X6;
      // what is P[5] in this case?
   }
   else
   {
      G[6] = -nG;
      P[5] = -(int)((X6 - nG) / 7) - 1;
      printf ("There isn't enough grain to pay the mercenaries\n");
   }
   nG = nG + G[6];
   if (-P[5] > nP)
      -nP;
   nP = nP + P[5];
   nL = nL + L[3];
   U1 = U1 - (2 * P[4]) - (3 * P[5]);
   return (0);
}


void population_changes()
{
   X1 = FNX(7);     // line 6340
   printf ("X1 = %5.3f, D = %d\n", X1, D);
   if (X1 > 3.0)
   {
      ; // goto 6500 - fall through
   }
   else
   {
      if (X1 != 1.0)
      {
         ; // goto 6440
         printf ("A POX EPIDEMIC has broken out\n");  // line 6440
         X2 = X1 * 5.0;
         P[6] = -(int)(nP / X2);
         nP = nP + P[6];
         ; // fall through to line 6550
      }
      else
      {
         if (D > 0)
         {
            ; // goto 6500 - fall through
         }
         else
         {
            printf ("The BLACK PLAGUE has struck the area\n");
            D = 13;
            X2 = 3;
            P[6] = -(int)(nP / X2);
            nP = nP + P[6];
         }
      }
   }
   ; // line 6550
   X1 = FNX(8) + 4;
   if (P[5] != 0)
      X1 = 4.5;
   P[8] = (int)(nP/X1);
   P[7] = (int)(0.3 - (nP / 22));
   nP = nP + P[7] + P[8];
   D = D - 1;
   return;
}

int harvest_grain()
{
   G[8] = (int) (C * G[8]);
   nG = nG + G[8];
   X1 = G[8] - 4000;
   if (X1 > 0)
      G[9] = -(int)(0.1 * X1);
   G[9] = G[9] - 120;
   nG = nG + G[9];

   if (K < 0)
   {
      return (0);
   }

   X1 = -(int)(nL / 2);  // line 6710
   if (K >= 2)
      X1 = (2.0 * X1);

   if (-X1 > nG)
   {
      printf ("You have insufficient grain to pay the royal tax\n");
      printf ("the High King has abolished your Ducal right\n\n");
      return (1); // goto 3000
   }
   G[10] = G[10] + X1;
   nG = nG + X1;
   return (0);
}

void update_unrest()
{
   U2 = (int)(U2 * 0.85) + U1;
   return;
}

int play_again()
{
   char ans;

   printf ("\nDo you wish to try another game");
   ans = getYesNo();
   if (ans == 'Y')
      return (1);
   else
      return (0);
}

main (int argc, char *argv[])
{
    char inFile[80];
    char ans;
    int  ans2;
    int  done = 0;
    int  play_again_flag = 1;
    int  seed = 1;

    printf ("argc = %d\n", argc);
    if (argc >= 3)
    {
       printf ("argv[1] = %s, argv[2] = %s\n", argv[1], argv[2]);
       if (!strcmp(argv[1], "-i"))
          strcpy (inFile, argv[2]);
       printf ("inFile = %s\n", inFile);

       inFile_fp = fopen (inFile, "r");
       if (inFile_fp == NULL)
          printf ("failed to open input file - %s\n", inFile);
       else
          printf ("reading commands from input file - %s\n", inFile);

       if (argc == 4)
       {
          printf ("argv[3] = %s\n", argv[1]);
          seed = atoi(argv[3]);
          printf ("using specified random seed = %d\n", seed);
       }
       else
       {
          printf ("using default random seed = %d\n", seed);
       }
    }
    else if (argc == 2)
    {
       printf ("argv[1] = %s\n", argv[1]);
       seed = atoi(argv[1]);
       printf ("using specified random seed = %d\n", seed);
    }
    else if (argc == 1)
    {
       seed = (((int) time(0)) % 100000) + 2;
       printf ("using generated random seed = %d\n", seed);
    }

    srand(seed);

    initialize();
    introduction();
    printf ("Do you wish to skip the detailed reports ");
    printf ("at the end of each year");
    ans = getYesNo();
    szR = ans;

    printf ("Do you want to play");
    ans = getYesNo();
    if (ans == 'Y')
    {
       printf ("okay - let's give the wheel a spin\n\n");
       play_again_flag = 1;
    }
    else
    {
       printf ("ok - see ya round\n");
       play_again_flag = 0;
    }

    while (play_again_flag)
    {
       start_new_game();
       done = 0;
       while (!done)
       {
          last_years_results();
          if (end_of_game_check())
          {
             done = 1;
             continue;
          }
          feed_the_peasants();
          if (starvation_and_unrest())
          {
             done = 1;
             continue;
          }
          if (purchase_land() == 0)
          {
             if (sell_land())
             {
                done = 1;
                continue;
             }
          }
          if (war_with_the_king())
          {
             done = 1;
             continue;
          }
          grain_production();
          update_land_tables();
          crop_yield_and_losses();
          if (war())
          {
             done = 1;
             continue;
          }
          population_changes();
          if (harvest_grain())
          {
             done = 1;
             continue;
          }
          update_unrest();
       }
       play_again_flag = play_again();
    }

    exit(0);
}
