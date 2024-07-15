package com.example.gamesradar.gamesfragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gamesradar.OnFeedItemClicked;
import com.example.gamesradar.R;
import com.example.gamesradar.WebViewFragment;
import com.example.gamesradar.model.game.GiveawayDetails;
import com.example.gamesradar.viewmodel.GiveawaysFragmentViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.makeramen.roundedimageview.RoundedImageView;


public class GiveawayDetailsFragment extends Fragment {


    private static final String ARG_GIVEAWAYID = "giveawayId";
    //private static final String ARG_PARAM2 = "param2";


//    private String mParam1;
//    private String mParam2;

    int giveawayId;
    GiveawaysFragmentViewModel viewModel;


    RoundedImageView giveawayImage;
    TextView giveawayTitle;
    TextView giveawayDesc;
    TextView readMore;

    TextView giveawayUsers;
    TextView instructionsHeading,giveawayInstructions;
    TextView moreInfoHeading,worthHeading, typeHeading, statusHeading, platformHeading,
            releaseDateHeading, endDateHeading;

    TextView giveawayWorth, giveawayType, giveawayStatus, giveawayPlatform,
            giveawayReleaseDate, giveawayEndDate;

    MaterialButton getGiveawayBtn;
    ImageView backBtn;
CircularProgressIndicator loadingIndicator;
    public GiveawayDetailsFragment() {
        // Required empty public constructor
    }


    public static GiveawayDetailsFragment newInstance(int Id) {
        GiveawayDetailsFragment fragment = new GiveawayDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_GIVEAWAYID, Id);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            giveawayId = getArguments().getInt(ARG_GIVEAWAYID);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewModel = new ViewModelProvider(this).get(GiveawaysFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_giveaway_details, container, false);
        giveawayImage = v.findViewById(R.id.giveaway_image);
        giveawayTitle = v.findViewById(R.id.giveaway_title);
        giveawayUsers = v.findViewById(R.id.giveaway_users);
        giveawayDesc = v.findViewById(R.id.giveaway_desc);

        readMore = v.findViewById(R.id.read_more_tv);

        getGiveawayBtn = v.findViewById(R.id.get_giveaway_btn);
        backBtn = v.findViewById(R.id.backbutton);
        loadingIndicator = v.findViewById(R.id.loading_giveaway);

        instructionsHeading = v.findViewById(R.id.giveaway_instructions_heading);
        giveawayInstructions = v.findViewById(R.id.giveaway_instructions);

        moreInfoHeading = v.findViewById(R.id.additional_info_heading);
        worthHeading = v.findViewById(R.id.worth_heading);
        statusHeading = v.findViewById(R.id.status_heading);
        typeHeading = v.findViewById(R.id.type_heading);
        platformHeading = v.findViewById(R.id.platform_heading);
        releaseDateHeading = v.findViewById(R.id.published_date_heading);
        endDateHeading = v.findViewById(R.id.end_date_heading);

        giveawayWorth = v.findViewById(R.id.giveaway_worth);
        giveawayStatus = v.findViewById(R.id.giveaway_status);
        giveawayType = v.findViewById(R.id.giveaway_type);
        giveawayPlatform = v.findViewById(R.id.giveaway_platform);
        giveawayReleaseDate = v.findViewById(R.id.giveaway_published_date);
        giveawayEndDate = v.findViewById(R.id.giveaway_end_date);

        setLoadingStatus();

        viewModel.getGiveaway(giveawayId).observe(getViewLifecycleOwner(), new Observer<GiveawayDetails>() {
            @Override
            public void onChanged(GiveawayDetails giveawayDetails) {
                initialize(giveawayDetails);
            }
        });





        return v;
    }

    private void initialize(GiveawayDetails giveawayDetails) {
        Glide.with(getContext()).load(giveawayDetails.getImage()).into(giveawayImage);
        giveawayTitle.setText(giveawayDetails.getTitle());
        giveawayDesc.setText(giveawayDetails.getDescription());
        if(giveawayDetails.getUsers()!=null){
            giveawayUsers.setText(giveawayDetails.getUsers().toString()+" users have claimed this giveaway");}
        else if (giveawayDetails==null) {
            giveawayUsers.setVisibility(View.GONE);
        }
        instructionsHeading.setText("Instructions:");
        giveawayInstructions.setText(giveawayDetails.getInstructions());
        getGiveawayBtn.setVisibility(View.VISIBLE);
        readMore.setText("Read More");
        readMore.setOnClickListener(view -> {
            Log.d("readmore","clicked");
            if (readMore.getText().equals("Read More")) {
                giveawayDesc.setMaxLines(Integer.MAX_VALUE);
                readMore.setText("Read Less");
            } else {
                giveawayDesc.setMaxLines(3);
                readMore.setText("Read More");


            }

        });
        backBtn.setOnClickListener(view -> {
            getParentFragmentManager().popBackStack();
        });

        getGiveawayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame, WebViewFragment.newInstance(giveawayDetails.getOpenGiveawayUrl())).commit();
            }
        });

        setupMoreInfo(giveawayDetails);

    }

    private void setupMoreInfo(GiveawayDetails giveawayDetails) {

        moreInfoHeading.setText("More Info:");
        setupTextView(giveawayDetails.getWorth(),giveawayWorth,"Worth:",worthHeading);
        setupTextView(giveawayDetails.getPlatforms(),giveawayPlatform,"Platforms:",platformHeading);
        setupTextView(giveawayDetails.getStatus(),giveawayStatus,"Status:",statusHeading);
        setupTextView(giveawayDetails.getType(),giveawayType,"Type:",typeHeading);
        setupTextView(giveawayDetails.getPublishedDate(),giveawayReleaseDate,"Published at:",releaseDateHeading);
        setupTextView(giveawayDetails.getEndDate(),giveawayEndDate,"Ends at:",endDateHeading);
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

    private void setLoadingStatus() {
        viewModel.getGiveawayLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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


}