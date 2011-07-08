
1.  The price of land seems very high - is it working properly? - Should be -
    the instructions indicate that it can be about 30 HL./HA. so I think it is
    OK.  - Actually the Anderson version uses a higher modifier add to the 
    random number for determining the crop yield.

2.  When the king requires peasants - shouldn't it display the amount of grain
    that I'm willing to spend in lieu of sending peasants ? Yes - this will be
    fixed in the next version.

3.  I expected to see seven year locust during crop yield report for year 8 but      didn't see it. - OK it's there - it usually displays just on the yearly
    report for years that are evenly divisible by 7- (ex. you should
    see it on the detailed reports for years 0, 7, 14, etc.  It has a short
    string that reads "Seven years locust".

4.  Why does it keep displaying that the high king grows uneasy and may be
    subsidizing wars against me?  - this is just how it works.  At one point
    this message didn't appear when I expected it to.  It is possible that
    the total grain amount was below the threshold at the time this calculation
    was being made.

5.  When I enter 1 more HA of total land to be planted it allows it - shouldn't
    it give me an error?  Yes, this check was missing in the BASIC listing and
    will be fixed in the next version.

6.  The castle expense is 120 HL in years when there is no crop yield -
    shouldn't it be more?  No, this tax is based on crop yield for the year
    with a minimum tax of 120 HL. grain.  By the way the royal tax is always
    calculated as 1/2 of total land paid in HL. of grain.

7.  If I don't plant any land - should the price of land the following year 
    be very low?  Yes - this value is heavily based on the previous crop yield.
    The lowest possible price of land in this case is 4 HL. of grain per
    hectare.

8.  Does it do any good for me to feed peasants more than 18 HL. of grain?  The
    instructions say that 18 is the cutoff for payment of good will by the
    people.

9.  The 300 HL. of grain to pay the king in lieu of sending peasants seems to 
    be getting calculated twice into the overall grain total at the end of the
    year - it does seem to included in the Royal tax only once, however. - Yes,
    this was being calculated incorrectly at the end of the year when the
    royal tax was computed - this will be fixed in the next version.

10. I've never seen the black plague strike - can this happen?  Yes, it can
    happen when the random number generated is equal to 1 - at least this is
    what I have observed when using the Anderson method.  Anderson uses the
    8 random index while Kaapke uses the 7 index table.  It is possible that
    the plague may not strike when using Kaapke's method.

11. When grain gets too low you should be able to input the total amount of
    grain left as valid input.

12. How is the starvation's calculated?  It always seems to be somewhere around
    12-14 peasants when I feed them 11 HL./peasant.

13. Shouldn't it ask me if I want to play another game?


