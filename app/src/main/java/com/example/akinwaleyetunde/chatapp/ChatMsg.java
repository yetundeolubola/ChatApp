package com.example.akinwaleyetunde.chatapp;

import java.util.Date;

public class ChatMsg {
    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMsg(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMsg(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
