package com.mycompany.quickchatapp;

import java.util.Random;
import java.util.regex.Pattern;
import com.google.gson.GsonBuilder;

public class Message {
    private static final Pattern RECIPIENT_PATTERN = Pattern.compile("\\+27\\d{9}");
    private static final int MAX_LENGTH = 250;

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;

    // No-args constructor for Gson
    public Message() {
        this.messageID = "0000000000";
        this.messageNumber = 0;
        this.recipient = "";
        this.messageText = "";
        this.messageHash = "";
        this.status = "Pending";
    }

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.messageText   = messageText;
        Random random = new Random();
        this.messageID = String.format("%010d", 1_000_000_000L + random.nextInt(1_000_000_000));
        this.messageHash = createMessageHash();
        this.status = "Pending";
    }
//check details
    public boolean checkMessageID() { return messageID.length() <= 10; }
    
    public boolean checkRecipientCell() { return RECIPIENT_PATTERN.matcher(recipient).matches(); }
    
    public String checkMessageLength() {
        if (messageText.length() <= MAX_LENGTH) return "Message ready to send.";
        int excess = messageText.length() - MAX_LENGTH;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    public String createMessageHash() {
        String trimmed = messageText.trim();
        if (trimmed.isEmpty()) return (messageID.substring(0, 2) + ":" + messageNumber + ":").toUpperCase();
        String[] words = trimmed.split("\\s+");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + words[0] + words[words.length - 1]).toUpperCase();
    }

    public String sendMessage(String choice) {
        switch (choice) {
            case "1": status = "Sent"; return "Message successfully sent.";
            case "2": status = "Disregarded"; return "Press 0 to delete the message.";
            case "3": status = "Stored"; return "Message successfully stored.";
            default: return "Invalid option.";
        }
    }

    public String getMessageID() { return messageID; }
    public String getStatus() { return status; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public void setStatus(String status) { this.status = status; }
}