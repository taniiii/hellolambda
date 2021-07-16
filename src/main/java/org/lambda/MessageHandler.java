package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class MessageHandler implements RequestHandler<Map<String, String>, Map<String, String>> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Map<String, String> handleRequest(Map<String, String> jsonMessage, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("EVENT: " + GSON.toJson(jsonMessage));

        return validateJson(jsonMessage);
    }

    private static Map<String, String> validateJson(Map<String, String> message){

        if(!message.containsKey("message") || "".equals(message.get("message"))){

            return Map.of("error", "Field message can not be null or empty");
        }

        StringBuilder sb = new StringBuilder();
        String temp = message.get("message");

        for(int i = 0; i < temp.length(); i = i+2){
            sb.append(temp.charAt(i));
        }

        return Map.of("message", sb.toString());
    }
}
