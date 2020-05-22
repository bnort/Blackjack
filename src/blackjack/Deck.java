package blackjack;

import java.util.*;

/**
 * Deck class which contains 52 cards to form a deck of playing cards
 * by using a linked list of cards.
 * 
 * Also contains an integer 'cut' which uses a random number added to
 * one tenth of the deck size to determine when the deck needs to be
 * shuffled.
 * 
 * @author BretNorton 0948797
 */
public class Deck {
    private LinkedList<Card> cards;
    final private int nSUITS = 4;
    final private int nRANKS = 13;
    final private int DECKSIZE = 52;
    private int cut;
    Random rand = new Random(System.currentTimeMillis());
 
    /**
     * Creates the deck by starting with no cards and adding
     * a card of every rank and suit until the deck is complete.
     */
    public void initialiseDeck(){
        this.cards = new LinkedList<Card>();
        
        for (int i = 0; i < nSUITS; i++)
        {
            for (int j = 0; j < nRANKS; j++)
            {
                getCards().add(new Card((j + 1), i));
            }
        }
    }
    
    /**
     * Shuffles the deck by creating a temporary linked list and
     * adding random cards to it until the original list is empty
     * and then saving the temporary list on to the original list.
     * 
     * The cut is also determined in this function.
     */
    public void shuffleDeck(){
        LinkedList<Card> tempDeck = new LinkedList<Card>();
        int randNum;
        
        System.out.println("The deck is now being shuffled.");
        
        while (!this.cards.isEmpty())
        {
            randNum = rand.nextInt(cards.size());
            tempDeck.add(this.cards.get(randNum));
            cards.remove(randNum);
        }
        this.cut = rand.nextInt(DECKSIZE/10) + (DECKSIZE/10);
        this.cards = tempDeck;
    }
    
    /**
     * Compares cards left in deck to the 'cut' to determine
     * whether the deck needs to be shuffled before the next hand.
     */
    public void shuffleCheck(){
        if(this.cards.size() < this.cut)
        {
            initialiseDeck();
            shuffleDeck();
        }
    }
    
    /**
     * Draws a card from the linked list.
     * 
     * @return the next card from the deck.
     */
    public Card drawCard(){
        return this.cards.pop();
    }

    /**
     * @return the cards
     */
    public LinkedList<Card> getCards() {
        return cards;
    }
}

