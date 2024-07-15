package com.example.gamesradar.network.giveaways;

import com.example.gamesradar.model.game.Giveaway;
import com.example.gamesradar.model.GiveawayApiResponseWrapper;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class GiveawayResponseDeserialiser implements JsonDeserializer<Giveaway> {
    @Override
    public Giveaway deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        GiveawayApiResponseWrapper response = new GiveawayApiResponseWrapper();
        JsonObject jsonObject = json.getAsJsonObject();

        // Check if the "status" field exists in the JSON
        if (jsonObject.has("status")) {
            response.setStatus(jsonObject.get("status").getAsInt());
        }

        // Check if the "status_message" field exists in the JSON
        if (jsonObject.has("status_message")) {
            response.setStatus_message(jsonObject.get("status_message").getAsString());
        }


        return null;
    }
}
