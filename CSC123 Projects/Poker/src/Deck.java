import java.util.Random;

public class Deck {
    private Card[] deck;
    private int next;

    public Deck() {
        deck = new Card[53]; // Does not use position 0, uses 1..52.

        for (int rank = 1; rank <= 13; rank++) {
            // Place cards in order in deck.
            deck[rank] = new Card(1, rank); // Hearts.
            deck[rank + 13] = new Card(2, rank); // Diamonds.
            deck[rank + 26] = new Card(3, rank); // Clubs.
            deck[rank + 39] = new Card(4, rank); // Spades.
        }

        next = 1;
    }

    public void shuffle() {
        Random rand = new Random();

        for (int card = 1; card <= 52; card++) {
            int r = rand.nextInt(52) + 1;
            // Swap deck[card] with deck[r].
            Card temp = deck[card];
            deck[card] = deck[r];
            deck[r] = temp;
        }

        next = 1; // Top card of the deck.
    }

    public Card deal() {
        if (next > 52) { // If deck is depeleted...
            shuffle();
        }

        Card card = deck[next];
        next++;
        return card;
    }
}