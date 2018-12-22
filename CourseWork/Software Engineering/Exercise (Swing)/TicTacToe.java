import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe{
	
	class ButtonListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
			
			JButton source = (JButton) e.getSource();
			String currentText = source.getText();
			if(currentText.equals("_")){
				source.setText("X");
			}else if(currentText.equals("Quit?")){
				System.exit(0);
			}else if(currentText.equals("New?")){
				wipeBoard();
			}else{
				source.setText("_");
			}
			
		}
	}
	
	//MISC NOTES
	//JFRAME API: https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html
	//look at JFrame api and look up WindowConstants
	
	//Frames
	JFrame _frame = new JFrame("Tic-Tac-Toe");
	//Panels: Sub-Sections of frames
	JPanel _ttt = new JPanel();
	JPanel _newPanel = new JPanel();
	//Buttons
	JButton[] _buttons = new JButton[9];
	
	//Constructor
	TicTacToe(){
		//Frame details
		_frame.setSize(400, 400);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//Panel details
		_ttt.setLayout(new GridLayout(3,3));		//3x3 layout
		_newPanel.setLayout(new FlowLayout());		//1,2,3,1,2,3,
		
		//Add Panels to Frame
		_frame.add(_ttt, BorderLayout.NORTH);
		_frame.add(_newPanel, BorderLayout.SOUTH);
		
		//Create Nine buttons for _ttt
		for(int j=0; j<9; j++){
			_buttons[j] = new JButton("_");	//make a new buttons
			ActionListener buttonListener = new ButtonListener();
			_buttons[j].addActionListener(buttonListener);
			_buttons[j].setFont(new Font("Courier", Font.PLAIN, 48));
			_ttt.add(_buttons[j]);
		}
		
		//Create buttons for _newPanel
		JButton _quit = new JButton("Quit?");
		ActionListener buttonListener = new ButtonListener();
		_quit.addActionListener(buttonListener);
		_quit.setFont(new Font("Courier", Font.PLAIN, 48));
		_newPanel.add(_quit);
		
		JButton _new = new JButton("New?");
		ActionListener buttonListener2 = new ButtonListener();
		_new.addActionListener(buttonListener2);
		_new.setFont(new Font("Courier", Font.PLAIN, 48));
		_newPanel.add(_new);
		
		
		//Refresh
		_frame.setVisible(true);	//make the frame visible
		
	}
	
	public void wipeBoard(){
		_buttons[2] = new JButton("Y");
		_frame.setVisible(true);
	}

	
	public static void main(String[] args){
		new TicTacToe();
		
	}
}