import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
   
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Signup();
    }
}
