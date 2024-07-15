package com.example.gamesradar.model;

import com.example.gamesradar.model.game.Giveaway;

import java.util.List;

public class GiveawayApiResponseWrapper {


        private int status;
        private String status_message;
        private List<Giveaway> giveaways;

        // Getters and setters for the fields


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public List<Giveaway> getGiveaways() {
        return giveaways;
    }

    public void setGiveaways(List<Giveaway> giveaways) {
        this.giveaways = giveaways;
    }
}
