package blackjack;

/**
 * User class, requires a String, the name of the user.
 * Uses an integer to keep track of the credits available 
 * to the user. Will give 500 credits to user on creation of class.
 * @author BretNorton 0948797
 */
public class User {
    private String name;
    private int credits;

    public User(String name)
    {
        this.credits = 500;
        this.name = name;
    }
    
    /**
     * @param adjustment the amount to adjust
     * the credits by in integer form.
     */
    public void adjustCredits(int adjustment)
    {
        this.credits += adjustment;
    }
            
    /**
     * @return the id
     */
    public String getName() {
        return name;
    }

    /**
     * @return the credits
     */
    public int getCredits() {
        return credits;
    }
    /**
     * @param credits the credits to set
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }
}
