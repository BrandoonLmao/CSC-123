public class PokerGame {
    private Bankroll bankroll;
    private Bet bet;
    private Hand hand;
    private Player player;
    private boolean[] holdCards;

    public PokerGame(Bet coinsBet, Bankroll br, Player pl) {
        bankroll = br;
        bet = coinsBet;
        player = pl;
        hand = new Hand();
        holdCards = new boolean[5];
    }

    int updateBankroll(int payoff) {
        int winnings = payoff * (bet.getBet());   // negative for a loss
        bankroll.alterBankroll(winnings);
        return winnings;
    }

    public void viewInitialHand() {
        hand.newHand();
        player.displayHand(hand.getHand());
    }

    public void discardOrHoldCards() {
        player.getDiscard(holdCards);
        hand.updateHand(holdCards);
        player.displayHand(hand.getHand());
        int payoff = hand.evaluateHand();
        int winnings = updateBankroll(payoff);
        player.displayResults(payoff, winnings);
    }
}
