public class Hand {
    private Card[] cards;
    private Deck deck;
    private int suits[];  // holds the number of each suit in a hand
    private int values[]; // holds the number of each type card (A,2,3,4,...K)

    public Hand() {
        cards = new Card[5];
        suits = new int[5];      // uses indices 1..4
        values = new int[14];   // uses indices 1..13
        deck = new Deck();
    }

    public void newHand() {
        deck.shuffle();

        for (int i = 0; i < 5; i++) {
            cards[i] = deck.deal(); // Deal five cards.
            suits[cards[i].getSuit()]++; // Modify suits array.
            values[cards[i].getValue()]++; // Modify values array.
        }

        sort();
    }

    public void updateHand(boolean[] x) {
        for (int i = 0; i < 5; i++) {
            if (!x[i]) { // Remove card data for card i.
                suits[cards[i].getSuit()]--; // Modify suits array.
                values[cards[i].getValue()]--; // Modify values array.

                // Get a new card.
                cards[i] = deck.deal();

                // Update data for card i.
                suits[cards[i].getSuit()]++;
                values[cards[i].getValue()]++;
            }
        }

        sort();
    }

    public String[] getHand() {
        String[] cardsInHand = new String[5];

        for (int i = 0; i < 5; i++) {
            cardsInHand[i] = cards[i].getName();
        }

        return cardsInHand;
    }

    private void sort() { // Orders cards by value field; a helper function.
        int max; // Holds the position of the highest valued card.
        for (int place = 4; place > 0; place--) {
            max = 0;

            // Find the position of the highest valued card between 0 and place.
            // The position of the high card is stored in max.
            for (int i = 1; i <= place; i++) {
                if (cards[i].getValue() > cards[max].getValue())
                    max = i;
            }

            // Swap the highest valued card with the card in position place.
            Card temp = cards[place];
            cards[place] = cards[max];
            cards[max] = temp;
        }
    }

    public int evaluateHand() {
        if (royalFlush())         // Royal flush pays 250:1.
            return 250;
        else if (straightFlush()) // Straight flush pays 50:1.
            return 50;
        else if (fourOfAKind())   // Four of a kind pays 25:1.
            return 25;
        else if (fullHouse())     // Full house pays 9:1.
            return 9;
        else if (flush())         // Flush pays 6:1.
            return 6;
        else if (straight())      // Straight pays 4:1.
            return 4;
        else if (threeOfAKind())  // Three of a kind pays 3:1.
            return 3;
        else if (twoPair())       // Two pair pays 2:1.
            return 2;
        else if (pair())          // Jacks or better pays 1:1.
            return 1;
        return -1;                // Losing hand.
    }

    // Hand definitions...
    private boolean royalFlush() {
        //10, J,Q,K,A of the same suit
        boolean sameSuit = false;  // true if all same suit
        boolean isRoyalty = false; //  true if cards are 10,J,K,Q,A
        for (int i = 1; i <= 4; i++)
            if (suits[i] == 5)  // all five cards of one suit?
                sameSuit = true;
        isRoyalty = (values[1] == 1 &&
                values[10] == 1 &&
                values[11] == 1 &&
                values[12] == 1 &&
                values[13] == 1); //one Ace && one 10 && one J &&one Q&&one K
        return (sameSuit && isRoyalty); // true if both conditions are true
    }

    private boolean straightFlush() {
        boolean sameSuit = false;
        boolean ranksInOrder = false;
        for (int i = 1; i <= 4; i++)
            if (suits[i] == 5)
                sameSuit = true;  // same suit?
        // cards in sequence?
        ranksInOrder =
                cards[1].getValue() == (cards[0].getValue() + 1) &&
                        cards[2].getValue() == (cards[0].getValue() + 2) &&
                        cards[3].getValue() == (cards[0].getValue() + 3) &&
                        cards[4].getValue() == (cards[0].getValue() + 4);
        return (sameSuit && ranksInOrder);
    }

    private boolean flush() {
        for (int i = 1; i <= 4; i++)
            if (suits[i] == 5)  // all the same suit?
                return true;
        return false;
    }

    private boolean fourOfAKind() {
        for (int i = 1; i <= 13; i++)
            if (values[i] == 4)
                return true;
        return false;
    }

    private boolean fullHouse() {
        boolean three = false;
        boolean two = false;
        for (int i = 1; i <= 13; i++)
            if (values[i] == 3)  // three of one kind
                three = true;
            else if (values[i] == 2) // two of another kind
                two = true;
        return two && three; // both conditions
    }

    private boolean straight() {
        // cards in sequence?
        return
                // Ace precedes 2
                (cards[1].getValue() == (cards[0].getValue() + 1) &&
                        cards[2].getValue() == (cards[0].getValue() + 2) &&
                        cards[3].getValue() == (cards[0].getValue() + 3) &&
                        cards[4].getValue() == (cards[0].getValue() + 4)) ||
                        //Ace follows King
                        (values[1] == 1 && //Ace
                                values[10] == 1 &&  //Ten
                                values[11] == 1 &&  //Jack
                                values[12] == 1 && //Queen
                                values[13] == 1); //King
    }

    private boolean threeOfAKind() {
        for (int i = 1; i <= 13; i++) {
            if (values[i] == 3) {
                return true;
            }
        }

        return false;
    }

    private boolean twoPair() {
        int count = 0;
        for (int i = 1; i <= 13; i++)
            if (values[i] == 2)   // count the number of pairs
                count++;
        return (count == 2);
    }

    private boolean pair() // Jacks or Higher
    {
        if (values[1] == 2) //pair of aces
            return true;
        for (int i = 11; i <= 13; i++) // pair of Jacks or higher
            if (values[i] == 2)
                return true;
        return false;
    }
}
