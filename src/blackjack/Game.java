package blackjack;

import java.util.*;

/**
 * Class that drives functionality for the game.
 * 
 * @author BretNorton 0948797
 */
public class Game {
    Scanner keyboard = new Scanner(System.in);
    String userInput = "";
    private Hand playerHand;
    private Hand dealerHand;
    private Deck deckInPlay = new Deck();
    private int betSize;
    public User user;
        
    public Game(User user)
    {
        this.user = user;
    }
    
    /**
     * Creates and shuffles the deck to be used.
     */
    public void initialDeckSetup()
    {
        deckInPlay.initialiseDeck();
        deckInPlay.shuffleDeck();
    }
    
    /**
     * Populates the dealer and player hands.
     * The player receives two cards while the
     * dealer will receive one card.
     */
    public void initialHandSetup()
    {
        deckInPlay.shuffleCheck();
        
        playerHand = new Hand();
        dealerHand = new Hand();
                
        getPlayerHand().getHand().add(deckInPlay.drawCard());
        getDealerHand().getHand().add(deckInPlay.drawCard());
        getPlayerHand().getHand().add(deckInPlay.drawCard());
    }
    
    /**
     * Gets user initial bet and then plays blackjack until the user opts
     * to cease play.
     */    
    public void playBlackjack()
    {   
        boolean keepPlayingFlag;    
        
        betSetter();
        
        System.out.println("The first hand is now being dealt.");
        
        do {
            initialHandSetup();
            keepPlayingFlag = gameplay();
        } while (keepPlayingFlag);
    }
    
    /**
     * Compares player hand to dealer hand and adjusts user credit appropriately.
     */    
    public void compareTotal()
    {
        int result = playerHand.calculateTotal() - dealerHand.calculateTotal();
        if(result > 0)
        {
            System.out.println("Congratulations, you win!");
            user.adjustCredits(betSize);
        }
        else if(result < 0)
        {
            System.out.println("Oh no, the dealer has a higher score, you lost!");
            user.adjustCredits(-betSize);
        }
        else
        {
            System.out.println("You tied with the dealer.");
        }
    }
    
    /**
     * Takes user input to determine how to play their hand.
     * 
     * @param handInPlay the hand to play.
     * @param str a string of the user input.
     */
    public void gameInputCheck(Hand handInPlay, String str)
    {
        switch (str){
            case "h": 
                handHit(handInPlay);
                break;
            case "s":
                handInPlay.setStandFlag(true);
                break;
            default:
                System.out.println("Did not recognise that input.");
        }
        
        bustCheck(handInPlay);
    }
    
    /**
     * Function to play each hand.
     * 
     * @return a boolean from the playCheck function.
     */
    public boolean gameplay()
    {
        if(!blackjackCheck(getPlayerHand()))
        {
            while (!getPlayerHand().isStandFlag())
            {
                if (getPlayerHand().calculateTotal() > 21){
                    System.out.println("Busted!");
                    user.adjustCredits(-betSize);
                    break;
                }
            gameStanding();
            System.out.println("Would you like to (h)it or (s)tand?");
            
            gameInputCheck(getPlayerHand(), keyboard.nextLine());
            }
            if (!getPlayerHand().isBustFlag())
                gamePlayDealer();
        }
        
        return playCheck();
    }
    
    /**
     * Automated dealer gameplay once player has finished making decisions.
     */
    public void gamePlayDealer()
    {
        while(dealerHand.calculateTotal() < 17)
        {
            dealerHand.getHand().add(deckInPlay.drawCard());
            System.out.println("The dealer drew a(n) ");
            try {
                Thread.sleep(750);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            System.out.println(dealerHand.getHand().getLast().getRank());
            gameStanding();
        }
        
        bustCheck(dealerHand);
        
        if(!dealerHand.isBustFlag())
            compareTotal();
        else
        {
            System.out.println("Dealer has busted, you win!");
            user.adjustCredits(betSize);
        }
            
    }
    
    /**
     * Announces current totals of dealer and player hands.
     */
    public void gameStanding()
    {
         System.out.println("You have " + playerHand.calculateTotal() + " and the dealer has "
         + dealerHand.calculateTotal() + ".");
    }
    
    /**
     * Player 'hit' functionality. Will add a card to the player's hand
     * (with a short pause for excitement) every time they request one.
     * 
     * @param handToHit the player hand in play.
     */
    public void handHit(Hand handToHit)
    {
        handToHit.getHand().add(deckInPlay.drawCard());
        System.out.println("You drew a ");
            try {
                Thread.sleep(750);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            System.out.println(playerHand.getHand().getLast().getRank());
    }
    
    /**
     * Checks if the player hand is a 'blackjack' (a face card and an ace)
     * and gives them the extra credits associated with getting a blackjack.
     * 
     * @param handToCheck the hand in play.
     * @return a boolean of true if player received a blackjack and false
     *         otherwise
     */
    public boolean blackjackCheck(Hand handToCheck)
    {
        if(handToCheck.calculateTotal() == 21)
        {
            System.out.println("Wow you got a blackjack! Nice job!");
            user.adjustCredits(betSize + (betSize/2));
            return true;
        }
        else
            return false;
    }
    
    /**
     * Checks to see if the player has busted (gone over 21).
     * 
     * @param handToCheck the hand in play.
     */
    public void bustCheck(Hand handToCheck)
    {
        if(handToCheck.calculateTotal() > 21)
            handToCheck.setBustFlag(true);
    }
    
    /**
     * Checks to see if the player has enough credits to continue playing.
     * If they do it checks if they want to keep playing or change their bet size.
     * If they do not it checks if they want to reload.
     * 
     * @return a boolean advising whether the player wants to keep playing or not.
     */
    public boolean playCheck()
    {
        char returnKey = ' ';
        boolean returnBool = false;
        
        if (user.getCredits() > 0) {
            System.out.println("You now have " + user.getCredits() + " credits.");
            System.out.println("Would you like to play another hand? Please enter (y)es, (n)o or (c)hange bet size.");
            do {
                userInput = keyboard.nextLine();
                switch (userInput){
                        case "y":
                            returnKey = 'y';
                            break;
                        case "n":
                            returnKey = 'n';
                            break;
                        case "c":
                            betSetter();
                            returnKey = 'y';
                            break;
                        default:
                            System.out.println("Unrecognised input. Please enter 'y' for another hand, 'n' to stop playing or 'c' to change your bet size.");
                }
            } while(returnKey == ' ');
        }
        
        else {
            System.out.println("Oops, you ran out of credits. Would you like to reload 500 credits - (y)es or (n)o?");
            userInput = keyboard.nextLine();
            do {
                switch (userInput){
                    case "y":
                        user.adjustCredits(500);
                        System.out.println("Your account has had 500 credits added to it, let's play some more!");
                        betSetter();
                        returnKey = 'y';
                        break;
                    case "n":
                        returnKey = 'n';
                        break;
                    default:
                        System.out.println("Unrecognised input. Please enter 'y' to reload to 500 credits or 'n' to stop playing.");
                }
            } while (returnKey == ' ');
        }
        
        if (returnKey == 'y')
            returnBool = true;
        
        return returnBool;
    }
    
    /**
     * Function to set bet size for the player. Checks to see if a negative
     * integer was entered or if the bet was more than the credits of the user.
     */
    public void betSetter()
    {
        System.out.println("You have " + user.getCredits() + " credits.");
        System.out.println("How much would you like to bet?");
        
        int betSetterBet = 0;
        
        while (betSetterBet <= 0)
        {
            try {
                betSetterBet = Integer.parseInt(keyboard.next());
            }
            catch (NumberFormatException ex) {
            }
            if (betSetterBet <= 0)
                System.out.println("Please enter an integer over 0.");
        } 
        
        if (betSetterBet <= user.getCredits()) {
            setBetSize(betSetterBet);
            keyboard.nextLine();
        }
        else {
            System.out.println("Sorry you do not have that many credits!");
            keyboard.nextLine();
            betSetter();
        }
    }

    /**
     * @return the playerHand
     */
    public Hand getPlayerHand() {
        return playerHand;
    }

    /**
     * @return the dealerHand
     */
    public Hand getDealerHand() {
        return dealerHand;
    }
    
    /**
     * @param betSize the betSize to set
     */
    public void setBetSize(int betSize) {
        this.betSize = betSize;
    }
}
