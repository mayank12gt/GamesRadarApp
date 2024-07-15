package com.example.gamesradar.gamesfragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.gamesradar.R;
import com.example.gamesradar.WebViewFragment;
import com.example.gamesradar.model.game.FreeGameDetails;
import com.example.gamesradar.model.game.MinimumSystemRequirements;
import com.example.gamesradar.model.game.Screenshot;
import com.example.gamesradar.viewmodel.FreeGamesFragmentViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;


public class FreeGameDetailsFragment extends Fragment {


    private static final String ARG_GAMEID = "gameId";
    //private static final String ARG_PARAM2 = "param2";


//    private String mParam1;
//    private String mParam2;

    int gameID;
    private FreeGamesFragmentViewModel viewModel;

    ImageSlider gameImageSlider;
    TextView gameTitle;
    TextView gameDesc;
    TextView readMore;

    TextView moreInfo, gameGenre, gamePlatform, gameReleaseDate, gamePublisher, gameDeveloper;
    TextView  gameGenreHeading, gamePlatformHeading, gameReleaseDateHeading, gamePublisherHeading,
            gameDeveloperHeading;
ConstraintLayout minRequirementsLayout;
    TextView minRequirements, gameOS,gameProcessor, gameMemory, gameGraphics, gameStorage;
    TextView  gameOSHeading,gameProcessorHeading, gameMemoryHeading, gameGraphicsHeading,
            gameStorageHeading;
    ImageView backBtn;
    MaterialButton getGameBtn;
    CircularProgressIndicator loadingIndicator;

    public FreeGameDetailsFragment() {
        // Required empty public constructor
    }



    public static FreeGameDetailsFragment newInstance(int gameId) {
        FreeGameDetailsFragment fragment = new FreeGameDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GAMEID, gameId);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gameID = getArguments().getInt(ARG_GAMEID);

        }
        viewModel = new ViewModelProvider(FreeGameDetailsFragment.this).get(FreeGamesFragmentViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_game_details, container, false);
       gameImageSlider = v.findViewById(R.id.game_image_slider);
       gameTitle = v.findViewById(R.id.game_title);
       gameDesc = v.findViewById(R.id.game_desc);
       readMore = v.findViewById(R.id.read_more_tv);
       //backBtn = v.findViewById(R.id.backbutton);
        loadingIndicator = v.findViewById(R.id.loading_game);

        //more info textviews
        moreInfo = v.findViewById(R.id.additional_info_heading);
        gameGenreHeading=v.findViewById(R.id.genre_heading);
        gamePlatformHeading=v.findViewById(R.id.platform_heading);
        gameReleaseDateHeading=v.findViewById(R.id.ReleaseDate_heading);
        gameDeveloperHeading=v.findViewById(R.id.developer_heading);
        gamePublisherHeading=v.findViewById(R.id.publisher_heading);

        gameGenre = v.findViewById(R.id.game_genre);
        gamePlatform = v.findViewById(R.id.game_platform);
        gameReleaseDate = v.findViewById(R.id.game_releaseDate);
        gamePublisher = v.findViewById(R.id.game_publisher);
        gameDeveloper = v.findViewById(R.id.game_developer);

        //min requirements textviews
        minRequirementsLayout = v.findViewById(R.id.min_requirements_layout);
        minRequirements = v.findViewById(R.id.min_requirements_heading);
        gameOSHeading = v.findViewById(R.id.os_heading);
        gameProcessorHeading = v.findViewById(R.id.processor_heading);
        gameGraphicsHeading = v.findViewById(R.id.graphics_heading);
        gameMemoryHeading = v.findViewById(R.id.memory_heading);
        gameStorageHeading = v.findViewById(R.id.storage_heading);

        gameOS = v.findViewById(R.id.game_os);
        gameProcessor = v.findViewById(R.id.game_processor);
        gameGraphics = v.findViewById(R.id.game_graphics);
        gameMemory = v.findViewById(R.id.game_memory);
        gameStorage = v.findViewById(R.id.game_storage);

        getGameBtn = v.findViewById(R.id.get_game_btn);
        backBtn = v.findViewById(R.id.backbutton);


        setLoadingStatus();

       viewModel.getGame(gameID).observe(getViewLifecycleOwner(), new Observer<FreeGameDetails>() {
           @Override
           public void onChanged(FreeGameDetails freeGameDetails) {
               initalize(freeGameDetails);
           }
       });



       return v;
    }
    private void setLoadingStatus() {
        viewModel.getGameLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loadingIndicator.setVisibility(View.VISIBLE);
                    Log.d("loading","true");
                }
                else{
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    Log.d("loading","false");
                }

            }
        });
    }
    private void initalize(FreeGameDetails freeGameDetails) {
        //Glide.with(getContext()).load(freeGame.getThumbnail()).into(gameImage);
        setupImageSlider(freeGameDetails);
        gameTitle.setText(freeGameDetails.getTitle());
        gameDesc.setText(freeGameDetails.getShortDescription()+"\n"+ freeGameDetails.getDescription());
        readMore.setText("Read More");
        getGameBtn.setVisibility(View.VISIBLE);
        readMore.setOnClickListener(view -> {
            Log.d("readmore","clicked");
            if (readMore.getText().equals("Read More")) {
                gameDesc.setMaxLines(Integer.MAX_VALUE);
                readMore.setText("Read Less");
            } else {
                gameDesc.setMaxLines(3);
                readMore.setText("Read More");


            }

        });


        setupMoreInfo(freeGameDetails);

        setupMinimumRequirements(freeGameDetails);
        getGameBtn.setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame, WebViewFragment.newInstance(freeGameDetails.getGameUrl())).commit();
        });

        backBtn.setOnClickListener(view -> {
            getParentFragmentManager().popBackStack();
        });



    }

    private void setupMoreInfo(FreeGameDetails freeGameDetails) {
        moreInfo.setText("More Info:");
        setupTextView(freeGameDetails.getGenre(),gameGenre,"Genre:",gameGenreHeading);
        setupTextView(freeGameDetails.getPlatform(),gamePlatform,"Platform:",gamePlatformHeading);
        setupTextView(freeGameDetails.getReleaseDate(),gameReleaseDate,"Release Date:",gameReleaseDateHeading);
        setupTextView(freeGameDetails.getPublisher(),gamePublisher,"Publisher:",gamePublisherHeading);
        setupTextView(freeGameDetails.getDeveloper(),gameDeveloper,"Developer:",gameDeveloperHeading);
    }

    private void setupMinimumRequirements(FreeGameDetails freeGameDetails) {
        if(freeGameDetails.getMinimumSystemRequirements()!=null){
            minRequirementsLayout.setVisibility(View.VISIBLE);
            minRequirements.setText("Minimum Requirements");
            MinimumSystemRequirements requirements = freeGameDetails.getMinimumSystemRequirements();
            setupTextView(requirements.getOs(),gameOS,"OS:",gameOSHeading);
            setupTextView(requirements.getMemory(),gameMemory,"Memory:",gameMemoryHeading);
            setupTextView(requirements.getProcessor(),gameProcessor,"Processor:",gameProcessorHeading);
            setupTextView(requirements.getGraphics(),gameGraphics,"Graphics:",gameGraphicsHeading);
            setupTextView(requirements.getStorage(),gameStorage,"Storage:",gameStorageHeading);
            //setupMinimumRequirementsTextView(requirements.getOs(),gameOS,"OS:",gameOSHeading);

        }
        else{
            minRequirementsLayout.setVisibility(View.GONE);
        }

    }

    private void setupTextView(String text, TextView textView,String heading, TextView headingTextView) {
        if(text!=null){
            headingTextView.setText(heading);
            textView.setText(text);
        }
        else if(text==null){
//            textView.setVisibility(View.GONE);
//            headingTextView.setVisibility(View.GONE);
            headingTextView.setText(heading);
            textView.setText("N/A");
        }
    }

//    private void setupTextView(String text, TextView textView, TextView headingTextView) {
//        if(text!=null){
//            headingTextView.setVisibility(View.VISIBLE);
//            textView.setText(text);
//            textView.setVisibility(View.VISIBLE);
//        }
//        else{
//            textView.setVisibility(View.INVISIBLE);
//            headingTextView.setVisibility(View.INVISIBLE);
//        }
//    }

    private void setupImageSlider(FreeGameDetails freeGameDetails) {

        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(freeGameDetails.getThumbnail(),ScaleTypes.CENTER_CROP));
        for (Screenshot screenshot: freeGameDetails.getScreenshots()) {
            imageList.add(new SlideModel(screenshot.getImage(), ScaleTypes.CENTER_CROP));
        }



        gameImageSlider.setImageList(imageList);
        //gameImageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE);
    }
}