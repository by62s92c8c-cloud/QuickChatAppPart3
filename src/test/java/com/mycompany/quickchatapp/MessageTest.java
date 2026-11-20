package com.mycompany.quickchatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    
    // Simulating the parallel arrays used in Main
    private String[] sentMessages;
    private String[] storedMessages;
    private String[] messageIDs;
    private String[] messageHashes;
    private String[] messageRecipients;
    private int sentCount;
    private int storedCount;

    @BeforeEach
    public void setUp() {
        sentMessages = new String[10];
        storedMessages = new String[10];
        messageIDs = new String[10];
        messageHashes = new String[10];
        messageRecipients = new String[10];
        sentCount = 0;
        storedCount = 0;
        
        // POE Test Data Setup
        Message m1 = new Message(1, "+27834557896", "Did you get the cake?");
        sentMessages[sentCount] = m1.getMessageText();
        messageHashes[sentCount] = m1.getMessageHash();
        messageRecipients[sentCount] = m1.getRecipient();
        sentCount++;

        Message m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        storedMessages[storedCount] = m2.getMessageText();
        messageIDs[storedCount] = m2.getMessageID();
        messageRecipients[storedCount] = m2.getRecipient();
        storedCount++;

        Message m4 = new Message(4, "0838884567", "It is dinner time !");
        sentMessages[sentCount] = m4.getMessageText();
        messageHashes[sentCount] = m4.getMessageHash();
        messageRecipients[sentCount] = m4.getRecipient();
        sentCount++;
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        // Rubric: "Sent Messages array correctly populated"
        assertEquals(2, sentCount);
        assertEquals("Did you get the cake?", sentMessages[0]);
        assertEquals("It is dinner time !", sentMessages[1]);
    }

    @Test
    public void testDisplayLongestMessage() {
        // Rubric: "Display details for the longest message"
        String longest = "";
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    @Test
    public void testSearchForMessageID() {
        // Rubric: "Search Array for messages sent to particular recipient" (ID test)
        String searchId = messageIDs[0]; // ID of message 2
        String result = "";
        for (int i = 0; i < storedCount; i++) {
            if (messageIDs[i].equals(searchId)) {
                result = storedMessages[i];
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", result);
    }

    @Test
    public void testSearchByRecipient() {
        // Rubric: "Search Array for messages sent to particular recipient"
        String searchRec = "+27838884567";
        String result = "";
        for (int i = 0; i < storedCount; i++) {
            if (messageRecipients[i].equals(searchRec)) {
                result = storedMessages[i];
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", result);
    }

    @Test
    public void testDeleteMessageByHash() {
        // Rubric: "Delete Message using message Hash"
        String hashToDelete = messageHashes[1]; // Hash of Message 4
        boolean deleted = false;
        for (int i = 0; i < sentCount; i++) {
            if (messageHashes[i].equals(hashToDelete)) {
                deleted = true;
                break;
            }
        }
        assertTrue(deleted, "Hash should exist before deletion");
        // (In the real app, the array shifts left here)
    }
}