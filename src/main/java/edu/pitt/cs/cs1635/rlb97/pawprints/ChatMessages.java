package edu.pitt.cs.cs1635.rlb97.pawprints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Randyll on 3/26/2018.
 * Activity which displays message history between two users and allow for
 * message upload.
 * As of this demonstration, this chat message will read/write to/from a log
 * (which will simulate a server's output) and display the chat log to the screen.
 */

public class ChatMessages extends AppCompatActivity{
    String[] messageHistory = new String[100];
    String[] userHistory = new String[100];    //Name of Who Sent
    String fromWhere = "";
    String fromWho = "";
    int numMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_messages);

        //Get Extras/Flags
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if(b!=null){
            fromWhere = (String) b.get("fromWhere");
            fromWho = (String) b.get("fromWho");
        }

        //Set Title
        setTitle(fromWho);

        //Load new Messages
        refreshMessages();

        //Listen for the Button
        addListenerOnButton();

    }

    private void addListenerOnButton() {
       Button sendMessage = (Button) findViewById(R.id.sendMessage);
       sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userMessageToPush = "";
                EditText submitMessage = null;

                submitMessage = (EditText)findViewById(R.id.submitMessage);
                userMessageToPush = submitMessage.getText().toString();
                pushMessage(userMessageToPush); //push message
                submitMessage.setText("");

            }
        });

    }


    /*
    Function to push user message to the server
     */
    private void pushMessage(String data) {
        userHistory[numMessages] = "Me";
        messageHistory[numMessages] = data;
        numMessages = numMessages + 1;

        refreshMessages();
    }

    /*
    Function to pull messageHistory from the server
     */
    private String pullMessage() {
        String nullForNow = null;
        return nullForNow;
    }

    /*
    Function to refresh the message history display
     */
    private void refreshMessages() {
        if(fromWhere.equals("Contacts") && numMessages == 0 ){
            numMessages = 1;
            userHistory[0] = fromWho;
            messageHistory[0] = "Hey, I have your dog at 218 North Craig Street. I will be home after work in twenty-four hours.";

        }else if(fromWhere.equals("viewPet") && numMessages == 0){
            numMessages = 0;
            userHistory[0] = "";
            messageHistory[0] = "";

        }


        if(numMessages == 0){
            TextView messageHistory = (TextView)findViewById(R.id.messageHistory);
            messageHistory.setText("Start a Conversation!");

        }else{
            StringBuilder toDisplay = new StringBuilder();
            toDisplay.append("");

            int i = 0;
            while(i < numMessages){
                String from = userHistory[i];
                toDisplay.append("From " + from + ": " + messageHistory[i] + "\n\n");

                i = i + 1;
            }

            TextView messageHistory = (TextView)findViewById(R.id.messageHistory);
            messageHistory.setText(toDisplay.toString());
        }

    }



}
//End of File
