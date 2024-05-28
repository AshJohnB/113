//PATCH 1.4  5/28/24 --- 7:00PM
import javax.swing.UIManager; // Importing the UIManager class to set the look and feel of the application.

public class App {
    public static void main(String[] args) {
        // Main method - the entry point of the application.
   
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); 
            // Set the look and feel of the application to Nimbus.
        } catch (Exception e) {
            e.printStackTrace(); 
            // Print the stack trace if setting the look and feel fails.
        }

        new Signup(); 
        // Create a new instance of the Signup class to start the application.
    }
}
