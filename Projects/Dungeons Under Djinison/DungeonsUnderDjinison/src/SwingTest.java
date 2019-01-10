import java.awt.EventQueue;
import javax.swing.JFrame;

public class SwingTest extends JFrame {

    public SwingTest() {

        initUI();
    }

    private void initUI() {
        
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            SwingTest ex = new SwingTest();
            ex.setVisible(true);
        });
    }
}
//End of File