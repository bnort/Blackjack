package blackjack;
import java.util.*;
import java.io.*;

/**
 * A one on one game of blackjack against the dealer.
 * 
 * @author BretNorton 0948797
 */
public class Blackjack {
    
    static Scanner mainKeyboard = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap userList = new HashMap();
        char keepPlayingFlag;
        
        loadUserList(userList);        
        
        System.out.println("Welcome to Blackjack!");
                               
        Game blackjackGame = new Game(confirmUser(userList));
        blackjackGame.initialDeckSetup();
        blackjackGame.playBlackjack();
        
        userList.replace(blackjackGame.user.getName(), blackjackGame.user.getCredits());
        
        saveUserList(userList);
        
        System.out.println("Thanks for playing!");
    }
    
    /**
     * Takes user input to determine the user playing blackjack.
     * 
     * @param userList a hash map of users to check against the user input.
     * 
     * @return the User (class) that will be playing blackjack.
     */
    public static User confirmUser(HashMap userList)
    {
        User currentPlayer;
        String userInput;
        
        System.out.println("Who is playing today?");
        
        userInput = mainKeyboard.nextLine().toLowerCase();
        
        if (!userList.containsKey(userInput))
        {
            currentPlayer = new User(userInput);
            userList.put(userInput, currentPlayer.getCredits());
        }
        else
        {
            currentPlayer = new User(userInput);
            currentPlayer.setCredits((int)userList.get(userInput));
        }
        
        return currentPlayer;
    }
    
    /**
     * Loads a user list from a text file.
     * 
     * @param userList a hash map to populate from the text file.
     */
    public static void loadUserList(HashMap userList)
    {
        try {
            FileReader fr = new FileReader("userlist.txt");
            BufferedReader inputStream = new BufferedReader(fr);
            String line = null;
            while((line = inputStream.readLine())!=null)
            {
                StringTokenizer st = new StringTokenizer(line," ,");
                while(st.hasMoreTokens())
                {
                    userList.put(st.nextToken(), Integer.parseInt(st.nextToken()));
                }
            }
                
            inputStream.close();
        }
        catch(FileNotFoundException e) {
            
        }
        catch(IOException e) {
            
        }
    }
    
    /**
     * Writes a user list to a text file.
     * 
     * @param userList a hash map of users to save to text.
     */
    public static void saveUserList(HashMap userList)
    {
        PrintWriter pw = null;
        
        Object[] userListKeys = userList.keySet().toArray();
        Object[] userListValues = userList.values().toArray();
        
        try{
            pw = new PrintWriter(new FileOutputStream("userlist.txt"));
            
            for (int i = 0; i < userList.size(); i++)
                pw.println(userListKeys[i] + ", " + userListValues[i] + ", ");
            
            pw.close();
        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
