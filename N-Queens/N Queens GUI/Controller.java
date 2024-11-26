import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Controller implements ActionListener {
    private Model model;

    public Controller(Model mdl) {
        model = mdl;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        model.respond(command);
    
        // Check if the game is finished
        if (model.isGameFinished()) {
            // Display victory sign
            JOptionPane.showMessageDialog(null, "Victory! 🎉", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
