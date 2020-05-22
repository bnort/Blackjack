package blackjack;

/**
 * Card class with two variables, rank where all 'face' cards
 * (11, 12 and 13) are converted to 10's and one's are converted
 * to 11's, and suit using an enum.
 * 
 * @author BretNorton 0948797
 */
public class Card {
    
    private int rank;
    private Suit suit;
    
    public Card(int rank, int suit)
    {
        switch(rank){
            case(1):
                this.rank = 11;
                break;
            case(11):
            case(12):
            case(13):
                this.rank = 10;
                break;
            default:
                this.rank = rank;
        }
        switch(suit){
            case(0):
                this.suit = Suit.HEARTS;
                break; 
            case(1):
                this.suit = Suit.CLUBS;
                break;
            case(2):
                this.suit = Suit.SPADES;
                break; 
            case(3):
                this.suit = Suit.DIAMONDS;
                break;
        }
    }

    /**
     * @return the rank
     */
    public int getRank() {
        return rank;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }   
    
    public String toString()
    {
        return this.rank + " of " + this.suit;
    }
}