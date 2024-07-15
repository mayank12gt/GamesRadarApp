package com.example.gamesradar.newsfragment;

import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.gamesradar.model.news.Source;

public interface SetDrawableCallback {
    public void setDrawable(ImageButton imageButton, Source source);
}
