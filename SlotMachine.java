package edu.stevens.cs570.assignments;
/**+
 * Name: SlotMachine.java
 * Assignment #: 1
 * author: Nicole Annika Gonsalves
 * University: Stevens Institute of Technology
 * Submitted on: 2 October 2019
*/



public class SlotMachine {

    double [] cul_odds;
    int numReels;
    int wagerUnitValue;
    double totalpayout=0;
    double totalpayin=0;


    public enum Symbol {
        BELLS("Bells", 10), FLOWERS("Flowers", 5), FRUITS("Fruits", 3),
        HEARTS("Hearts", 2), SPADES("Spades", 1);

        // symbol name
        private final String name;

        // payout factor (i.e. multiple of wager) when matching symbols of this type
        private final int payoutFactor;

        Symbol(String name, int payoutFactor) {
            this.name = name;
            this.payoutFactor = payoutFactor;
        }

        public String getName() {
            return name;
        }

        public int getPayoutFactor() {
            return payoutFactor;
        }

    }

    /**
     * Constructor
     * @param numReels number of reels in slot machine
     * @param odds odds for each symbol in a reel, indexed by its enum ordinal value; odds value is non-zero and sums to 1
     * @param wagerUnitValue unit value in cents of a wager
     */
    public SlotMachine(int numReels, double [] odds, int wagerUnitValue) {

        this.numReels=numReels;
        this.wagerUnitValue=wagerUnitValue;
        int len = odds.length;
        double sum = 0;
        this.cul_odds = new double[len];
        for (int i = 0; i<len;i++) {
             sum = sum + odds[i];
             this.cul_odds[i] = sum;
        }


    }

    
    /**
     * Get symbol for a reel when the user pulls slot machine lever
     * @return symbol type based on odds (use Math.random())
     */
    public Symbol getSymbolForAReel() {

        double ran = Math.random();
        int odd_val;
        int n=0;
        while(ran>cul_odds[n]){n++;}
        odd_val=n;
        Symbol sym = Symbol.values()[odd_val];


        return sym;
    }


    /**
     * Calculate the payout for reel symbols based on the following rules:
     * 1. If more than half but not all of the reels have the same symbol then payout factor is same as payout factor of the symbol
     * 2. If all of the reels have the same symbol then payout factor is twice the payout factor of the symbol
     * 3. Otherwise payout factor is 0
     * Payout is then calculated as wagerValue multiplied by payout factor
     * @param reelSymbols array of symbols one for each reel
     * @param wagerValue value of wager given by the user
     * @return calculated payout
     */
    public long calcPayout(Symbol[] reelSymbols, int wagerValue) {

        double len = reelSymbols.length;
        if (len%2==0){len++;}
        int m_half = (int)Math.round(len/2);
        int i_len = reelSymbols.length;

        int count = 0;
        int payoutFactor = 0;
        int multiplier = 0;

        System.out.println("Number of Reels: "+ numReels);
        if (numReels == 1){
          payoutFactor = reelSymbols[0].payoutFactor;
        }
        else {
            for (int i = 0; i < i_len; i++) {
                for (int j = 0; j < i_len; j++) {
                    if (reelSymbols[i] == reelSymbols[j]) {
                        count++;
                    }
                }
                if (count == i_len) {
                    payoutFactor = reelSymbols[i].payoutFactor * 2;
                    break;
                } else if (count == m_half) {
                    payoutFactor = reelSymbols[i].payoutFactor;
                    break;
                } else {
                    payoutFactor = 0;
                }
                count = 0;
            }
        }

        System.out.println("The payout factor is :" +payoutFactor);
        double userpayin = (double) wagerValue/100;
        long userpayout = (payoutFactor*wagerValue);
        totalpayin= totalpayin+userpayin;

        return userpayout;
    }

    /**
     * Called when the user pulls the lever after putting wager tokens
     * 1. Get symbols for the reels using getSymbolForAReel()
     * 2. Calculate payout using calcPayout()
     * 3. Display the symbols, e.g. Bells Flowers Flowers..
     * 4. Display the payout in dollars and cents e.g. $2.50
     * 5. Keep track of total payout and total receipts from wagers
     * @param numWagerUnits number of wager units given by the user
     */
    public void pullLever(int numWagerUnits) {
        Symbol[] combination = new Symbol[numReels];
        for (int i=0; i<numReels;i++){
            combination[i] = getSymbolForAReel();
            System.out.print(combination[i]+" ");
        }
        System.out.println();
        int wagerValue = numWagerUnits * wagerUnitValue;
        double userpayout = (double) (calcPayout(combination, wagerValue))/100;
        totalpayout= totalpayout+userpayout;
        System.out.println("Payout for this round: $" +userpayout);

    }

    /**
     * Get total payout to the user as percent of total wager value
     * @return e.g. 85.5
     */
    public double getPayoutPercent() {

        if (totalpayout == 0 && totalpayin==0){
                return 0.0;
        }
        else {
            double percentpayout = totalpayout / totalpayin * 100;
            System.out.println("The payout percent  to the user = " + percentpayout);
            return percentpayout;
        }
    }

    /**
     * Clear the total payout and wager value
     */
    public void reset() {
        totalpayout =0;
        totalpayin=0;
    }

    public static void main(String [] args) {


        double [] odds = new double[Symbol.values().length];

        odds[Symbol.HEARTS.ordinal()] = 0.3;
        odds[Symbol.SPADES.ordinal()] = 0.25;
        odds[Symbol.BELLS.ordinal()] = 0.05;
        odds[Symbol.FLOWERS.ordinal()] = 0.2;
        odds[Symbol.FRUITS.ordinal()] = 0.2;


        SlotMachine sm = new SlotMachine(3, odds, 25); // quarter slot machine

        //sm.pullLever(2);
        //sm.pullLever(1);
        //sm.pullLever(3);
        //System.out.println("Pay out percent to user = " + sm.getPayoutPercent());
        //sm.reset();
        //sm.pullLever(4);
        //sm.pullLever(1);
        //sm.pullLever(1);
        //sm.pullLever(2);
        System.out.println("Pay out percent to user = " + sm.getPayoutPercent());
    }



}

