public class Bankroll {
    private int bankroll;

    public Bankroll(int n) { bankroll = n;
    }

    public int getBankroll() {
        return bankroll;
    }

    public void alterBankroll(int n) {
        bankroll += n;
    }
}