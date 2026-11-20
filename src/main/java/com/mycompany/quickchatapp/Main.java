package com.mycompany.quickchatapp;

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final Login login = new Login();
    
    // PART 3: PARALLEL ARRAYS
    private static final int MAX_MSG = 100;
    private static String[] sentMessages = new String[MAX_MSG];
    private static String[] disregardedMessages = new String[MAX_MSG];
    private static String[] storedMessages = new String[MAX_MSG];
    private static String[] messageHashes = new String[MAX_MSG];
    private static String[] messageIDs = new String[MAX_MSG];
    private static String[] messageRecipients = new String[MAX_MSG];
    
    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;

    public static void main(String[] args) {
        boolean sessionActive = false;

        // --- PART 1: Login Loop ---
        while (!sessionActive) {
            System.out.println("\n=== QuickChat ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int opt = readInt();
            if (opt == 1) {
                System.out.print("First name: "); String f = input.nextLine();
                System.out.print("Last name: "); String l = input.nextLine();
                System.out.print("Username: "); String u = input.nextLine();
                System.out.print("Password: "); String p = input.nextLine();
                System.out.print("Cell phone (+27...): "); String c = input.nextLine();
                System.out.println(login.registerUser(f, l, u, p, c));
            } else if (opt == 2) {
                System.out.print("Username: "); String u = input.nextLine();
                System.out.print("Password: "); String p = input.nextLine();
                sessionActive = login.loginUser(u, p);
                System.out.println(login.returnLoginStatus(sessionActive));
            } else if (opt == 3) {
                System.out.println("Goodbye!"); return;
            }
        }

        // --- PART 2 & 3: QuickChat Menu ---
        System.out.println("\nWelcome to QuickChat.");
        int choice;
        do {
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages"); // PART 3 OPTION
            System.out.print("Choose an option: ");

            choice = readInt();
            switch (choice) {
                case 1: handleSendMessages(); break;
                case 2: System.out.println("Coming Soon."); break;
                case 3: System.out.println("Goodbye!"); break;
                case 4: handleStoredMessagesMenu(); break;
                case 99: populateTestData(); break; // Hidden dev option for POE Data
            }
        } while (choice != 3);
    }

    private static void handleSendMessages() {
        System.out.print("How many messages? ");
        int num = readInt();
        for (int i = 0; i < num; i++) {
            System.out.println("\nMessage " + (i + 1) + ":");
            
            String recipient;
            while (true) {
                System.out.print("Recipient (+27XXXXXXXXX): ");
                recipient = input.nextLine().trim();
                if (recipient.matches("\\+27\\d{9}")) break;
                System.out.println("Incorrect format. Try again.");
            }
            
            String text;
            while (true) {
                System.out.print("Message: ");
                text = input.nextLine();
                if (text.length() <= 250) break;
                System.out.println("Exceeds 250 chars by " + (text.length() - 250));
            }

            Message msg = new Message(i + 1, recipient, text);
            System.out.println("1. Send\n2. Disregard\n3. Store");
            String action = input.nextLine().trim();
            msg.sendMessage(action);

            // POPULATE PARALLEL ARRAYS
            if (msg.getStatus().equals("Sent")) {
                addMessageToArray(msg, "sent");
            } else if (msg.getStatus().equals("Stored")) {
                addMessageToArray(msg, "stored");
            } else if (msg.getStatus().equals("Disregarded")) {
                addMessageToArray(msg, "disregarded");
            }
        }
    }

    private static void addMessageToArray(Message msg, String type) {
        if (type.equals("sent")) {
            sentMessages[sentCount] = msg.getMessageText();
            messageHashes[sentCount] = msg.getMessageHash();
            messageIDs[sentCount] = msg.getMessageID();
            messageRecipients[sentCount] = msg.getRecipient();
            sentCount++;
        } else if (type.equals("stored")) {
            storedMessages[storedCount] = msg.getMessageText();
            messageHashes[storedCount] = msg.getMessageHash();
            messageIDs[storedCount] = msg.getMessageID();
            messageRecipients[storedCount] = msg.getRecipient();
            storedCount++;
        } else if (type.equals("disregarded")) {
            disregardedMessages[disregardedCount] = msg.getMessageText();
            disregardedCount++;
        }
    }

    private static void handleStoredMessagesMenu() {
        System.out.println("\n--- Stored Messages ---");
        System.out.println("a. Display sender and recipient of all stored messages");
        System.out.println("b. Display the longest stored message");
        System.out.println("c. Search for a message ID");
        System.out.println("d. Search by recipient");
        System.out.println("e. Delete a message using the message hash");
        System.out.println("f. Display full report");
        System.out.print("Choose an option: ");
        String subChoice = input.nextLine().trim().toLowerCase();

        switch (subChoice) {
            case "a":
                for (int i = 0; i < storedCount; i++) {
                    System.out.println("Recipient: " + messageRecipients[i]);
                }
                break;
            case "b":
                String longest = "";
                for (int i = 0; i < storedCount; i++) {
                    if (storedMessages[i].length() > longest.length()) {
                        longest = storedMessages[i];
                    }
                }
                System.out.println("Longest Message: \"" + longest + "\"");
                break;
            case "c":
                System.out.print("Enter Message ID: ");
                String searchId = input.nextLine().trim();
                for (int i = 0; i < storedCount; i++) {
                    if (messageIDs[i].equals(searchId)) {
                        System.out.println("Recipient: " + messageRecipients[i]);
                        System.out.println("Message: \"" + storedMessages[i] + "\"");
                    }
                }
                break;
            case "d":
                System.out.print("Enter Recipient (+27...): ");
                String searchRec = input.nextLine().trim();
                for (int i = 0; i < storedCount; i++) {
                    if (messageRecipients[i].equals(searchRec)) {
                        System.out.println("\"" + storedMessages[i] + "\"");
                    }
                }
                break;
            case "e":
                System.out.print("Enter Message Hash to delete: ");
                String searchHash = input.nextLine().trim();
                boolean deleted = false;
                for (int i = 0; i < storedCount; i++) {
                    if (messageHashes[i].equals(searchHash)) {
                        System.out.println("Message: \"" + storedMessages[i] + "\" successfully deleted.");
                        // Shift elements left to delete
                        for (int j = i; j < storedCount - 1; j++) {
                            storedMessages[j] = storedMessages[j + 1];
                            messageHashes[j] = messageHashes[j + 1];
                            messageIDs[j] = messageIDs[j + 1];
                            messageRecipients[j] = messageRecipients[j + 1];
                        }
                        storedCount--;
                        deleted = true;
                        break;
                    }
                }
                if (!deleted) System.out.println("Hash not found.");
                break;
            case "f":
                System.out.println("\n--- Full Report ---");
                for (int i = 0; i < storedCount; i++) {
                    System.out.println("Hash: " + messageHashes[i] + " | Recipient: " + messageRecipients[i] + " | Message: \"" + storedMessages[i] + "\"");
                }
                break;
        }
    }

    // Hidden Option 99 to perfectly populate the exact Test Data from the POE
    private static void populateTestData() {
        System.out.println("Populating POE Test Data...");
        
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        m1.sendMessage("1");
        addMessageToArray(m1, "sent");

        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m2.sendMessage("3");
        addMessageToArray(m2, "stored");

        Message m3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        m3.sendMessage("2");
        addMessageToArray(m3, "disregarded");

        Message m4 = new Message(4, "0838884567", "It is dinner time !");
        m4.sendMessage("1");
        addMessageToArray(m4, "sent");

        Message m5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        m5.sendMessage("3");
        addMessageToArray(m5, "stored");

        System.out.println("Test Data Loaded!");
    }

    private static int readInt() {
        try { return Integer.parseInt(input.nextLine().trim()); } 
        catch (Exception e) { return -1; }
    }
}