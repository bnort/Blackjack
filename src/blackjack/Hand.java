package blackjack;
import java.util.*;

/**
 * Hand class containing a linked list of all cards in the
 * hand. Has two booleans one to determine if the hand has
 * stood, the other to determine if the hand has busted.
 * 
 * @author BretNorton 0948797
 */
public class Hand {
    private LinkedList<Card> hand = new LinkedList<Card>();
    private boolean standFlag = false;
    private boolean bustFlag = false;
    
    /**
     * Calculates the value of the hand by adding together
     * all of the ranks of the cards in the hand.
     * Aces are counted as 11 unless the value of the hand
     * exceeds 21 at which point they will be counted as 1.
     * 
     * @return the total value of the hand
     */
    public int calculateTotal(){
        int total = 0;
        for (Card c : hand)
            total += c.getRank();
        if (total > 21){
            total = 0;
            for (Card c : hand) {
                if (c.getRank() == 11)
                    c.setRank(1);
                total += c.getRank();
            }
        }
        return total;
    }

    /**
     * @return the hand
     */
    public LinkedList<Card> getHand() {
        return hand;
    }

    /**
     * @return the standFlag
     */
    public boolean isStandFlag() {
        return standFlag;
    }

    /**
     * @param standFlag the standFlag to set
     */
    public void setStandFlag(boolean standFlag) {
        this.standFlag = standFlag;
    }

    /**
     * @return the bustFlag
     */
    public boolean isBustFlag() {
        return bustFlag;
    }

    /**
     * @param bustFlag the bustFlag to set
     */
    public void setBustFlag(boolean bustFlag) {
        this.bustFlag = bustFlag;
    }
}
