import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ButtonListener implements ActionListener{
	
	public void actionPerformed(ActionEvent e){
		
		JButton source = (JButton) e.getSource();
		String currentText = source.getText();
		if(currentText.equals("_")){
			source.setText("X");
		}else if(currentText.equals("Quit?")){
			System.exit(0);
		}else if(currentText.equals("New?")){
			
		}else{
			source.setText("_");
		}
		
	}
}