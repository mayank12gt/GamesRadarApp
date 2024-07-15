package com.example.gamesradar.radarfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gamesradar.R;
import com.example.gamesradar.adapter.MoreVideosItemsAdapter;
import com.example.gamesradar.model.Radar.YTParser.Entry;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.DefaultPlayerUiController;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PlayerFragment extends Fragment {

    private static final String ARG_VIDEO = "video";
   private static final String ARG_MORE_VIDEOS= "more_videos";

   private Entry video;
//    private String mParam2;
    List<Entry> moreVideos;


    YouTubePlayerView playerView;

    TextView videoTitle;
    TextView videoChannel;
    TextView videoDesc;
    TextView readMore;
    RecyclerView moreVideosRV;
    MoreVideosItemsAdapter adapter;
    public PlayerFragment() {
        // Required empty public constructor
    }


    public static PlayerFragment newInstance(Entry video, ArrayList<Entry> moreVideos) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
          args.putSerializable(ARG_VIDEO, video);
        args.putSerializable(ARG_MORE_VIDEOS, moreVideos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           video = (Entry) getArguments().getSerializable(ARG_VIDEO);
            moreVideos = (List<Entry>) getArguments().getSerializable(ARG_MORE_VIDEOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_player, container, false);
    playerView = v.findViewById(R.id.youtube_player_view);
    videoTitle = v.findViewById(R.id.video_title);
    videoChannel = v.findViewById(R.id.video_channel);
    videoDesc = v.findViewById(R.id.video_desc);
    readMore  =v.findViewById(R.id.readMore);
    moreVideosRV = v.findViewById(R.id.more_videos_rv);



        videoTitle.setText(video.getTitle());
        videoDesc.setText(video.getMediaGroup().getMediaDescription());
        videoChannel.setText(video.getAuthor().getName());
        readMore.setOnClickListener(view -> {
            if(readMore.getText().toString().equals("More")) {
                videoDesc.setMaxLines(Integer.MAX_VALUE);
                readMore.setText("Less");
            }
            else if(readMore.getText().toString().equals("Less")){
                videoDesc.setMaxLines(2);
                readMore.setText("More");
            }
        });

        getLifecycle().addObserver(playerView);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(playerView, youTubePlayer);
                defaultPlayerUiController.showVideoTitle(true);
                defaultPlayerUiController.showYouTubeButton(false);
                defaultPlayerUiController.showCurrentTime(true);
                defaultPlayerUiController.showDuration(true);
                defaultPlayerUiController.showMenuButton(false);
                defaultPlayerUiController.showBufferingProgress(true);
                defaultPlayerUiController.showFullscreenButton(true);


                playerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());
                youTubePlayer.loadVideo(video.getVideo_id(), 0F);
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).fullscreen(0).build();
      playerView.initialize(listener,true, options);
      adapter = new MoreVideosItemsAdapter(moreVideos,getActivity());


        Log.d("list2",moreVideos==null?"null":moreVideos.toString());
      moreVideosRV.setLayoutManager(new LinearLayoutManager(getActivity()));
      moreVideosRV.setAdapter(adapter);


    return v;
    }
}