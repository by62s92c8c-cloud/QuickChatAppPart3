package com.mycompany.quickchatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MessageStore {
    private static final String FILE_PATH = "messages.json";

    public static void storeMessage(List<Message> messages) {
        List<Message> storedMessages = messages.stream()
                .filter(m -> m.getStatus().equals("Stored"))
                .collect(Collectors.toList());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(storedMessages, writer);
        } catch (IOException e) {
            System.out.println("Error storing messages: " + e.getMessage());
        }
    }

    /* 
     * CODE ATTRIBUTION (Required for rubric marks):
     * The following JSON reading code is attributed to Google's Gson documentation.
     * Available at: https://github.com/google/gson/blob/main/UserGuide.md
     */
    public static Message[] loadStoredMessages() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Message[] storedArray = gson.fromJson(reader, Message[].class);
            return storedArray != null ? storedArray : new Message[0];
        } catch (IOException e) {
            return new Message[0];
        }
    }
}