package com.example.akinwaleyetunde.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ImageButton send_Btn;
    EditText edt_send;
    FirebaseListAdapter adapter;
    ListView listview;
    TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        displayMsg();

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        username.setText(message);

        mAuth = FirebaseAuth.getInstance();
        send_Btn = findViewById(R.id.im_send);

        send_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_send = findViewById(R.id.ed_send);
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMsg(edt_send.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                displayMsg();
                // Clear the input
                edt_send.setText("");

            }
        });

    }

    public void displayMsg() {
        listview = findViewById(R.id.listview);
        adapter = new FirebaseListAdapter<ChatMsg>(this, ChatMsg.class,
                R.layout.chat_msg, FirebaseDatabase.getInstance().getReference()) {

            @Override
            protected void populateView(View v, ChatMsg model, int position) {
                // Get references to the views of message.xml

                TextView messageText = v.findViewById(R.id.tv_chatmessage);
                TextView messageUser = v.findViewById(R.id.tv_username);
                TextView messageTime = v.findViewById(R.id.tv_chattime);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());


                // Format the date before showing it
                messageTime.setText(DateFormat.format("HH:mm",
                        model.getMessageTime()));
            }
        };

        listview.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {

            LogoutUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == currentUser) {
            displayMsg();
        }

    }

    public void LogoutUser() {
        Intent intentLogout = new Intent(ChatActivity.this, LoginActivity.class);
        intentLogout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentLogout);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_logout) {
            mAuth.signOut();
            LogoutUser();

        }
        return true;
    }


}
