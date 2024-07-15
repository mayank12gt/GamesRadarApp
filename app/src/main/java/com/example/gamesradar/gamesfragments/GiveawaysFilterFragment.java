package com.example.gamesradar.gamesfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.gamesradar.R;
import com.example.gamesradar.gamesfragments.listeners.GetGiveawayFilterValues;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class GiveawaysFilterFragment extends BottomSheetDialogFragment {


    private static final String ARG_PLATFORM = "platform";
    private static final String ARG_TYPE = "type";
    private static final String ARG_SORTBY = "sortBy";

//    private String mParam1;
//    private String mParam2;
private Button apply;
    private Button reset;
    AutoCompleteTextView platformSpinner;
    String platforms[];
    String platform=null;
    ChipGroup giveawayTypeChipGroup;
    String giveawayType=null;
    ChipGroup sortByChipGroup;
    String sortBy=null;

    GetGiveawayFilterValues callback;


    static GiveawaysFilterFragment instance;
    public GiveawaysFilterFragment() {
        // Required empty public constructor
    }


    public static GiveawaysFilterFragment newInstance(String platform,String type, String sortBy) {
        if (instance==null) {
            GiveawaysFilterFragment fragment = new GiveawaysFilterFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PLATFORM, platform);
            args.putString(ARG_TYPE, type);
            args.putString(ARG_SORTBY, sortBy);
            fragment.setArguments(args);
            instance =fragment;
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            platform= getArguments().getString(ARG_PLATFORM);
            giveawayType = getArguments().getString(ARG_TYPE);
            sortBy = getArguments().getString(ARG_SORTBY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_giveaways_filter, container, false);
        apply = v.findViewById(R.id.apply_btn);
        reset = v.findViewById(R.id.reset_btn);
        platformSpinner = v.findViewById(R.id.platform_spinner);
        sortByChipGroup = v.findViewById(R.id.sortby_chipgroup);
        giveawayTypeChipGroup = v.findViewById(R.id.giveaway_type_chipgroup);

        initalizeViews();
        setupPlatformSpinner();


        giveawayTypeChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                Chip chip = v.findViewById(group.getCheckedChipId());
                if(chip!=null) {
                    switch(chip.getId()){
                        case R.id.Game_chip:
                            giveawayType = "game";
                            break;
                        case R.id.Loot_chip:
                            giveawayType = "loot";
                            break;
                        case R.id.Beta_chip:
                            giveawayType ="beta";
                            break;

                        default:
                            giveawayType=null;
                    }
                }
                else{
                    giveawayType=null;
                }

                Log.d("giveawayType",giveawayType==null?"null":giveawayType);
            }
        });

        sortByChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                Chip chip = v.findViewById(group.getCheckedChipId());
                if(chip!=null) {
                    switch(chip.getId()){
                        case R.id.Date_chip:
                            sortBy = "date";
                            break;
                        case R.id.Popularity_chip:
                            sortBy = "popularity";
                            break;
                        case R.id.Value_chip:
                            sortBy = "value";
                            break;

                        default:
                            sortBy=null;
                    }

                }
                else{
                    sortBy=null;
                }

                Log.d("sortBy",sortBy==null?"null":sortBy);
            }

        });

        apply.setOnClickListener(view -> {

            //callback.getFilterValues(platform,genre,sortBy);
            Bundle result = new Bundle();
            result.putString("platform", platform);
            result.putString("type", giveawayType);
            result.putString("sortBy", sortBy);
            if(getParentFragment()!=null){
                Log.d("result","fragmentnotnull");
            }
            getParentFragmentManager().setFragmentResult("requestKey",result);
            dismiss();

        });

        reset.setOnClickListener(view -> {
            giveawayTypeChipGroup.clearCheck();
            giveawayType=null;
            sortByChipGroup.clearCheck();
            sortBy=null;
            platformSpinner.setText("");
            platform=null;
        });


        return v;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (GetGiveawayFilterValues) getParentFragment();
    }
    private void initalizeViews() {

        platformSpinner.setText(platform);

        if(giveawayType!=null) {
            switch (giveawayType) {
                case "game":
                    giveawayTypeChipGroup.check(R.id.Game_chip);
                    break;
                case "loot":
                    giveawayTypeChipGroup.check(R.id.Loot_chip);
                    break;
                case "beta":
                    giveawayTypeChipGroup.check(R.id.Beta_chip);
                    break;
                default:
                    giveawayTypeChipGroup.clearCheck();
            }
        } else if (giveawayType==null) {
            giveawayTypeChipGroup.clearCheck();
        }
        if(sortBy!=null) {
            switch (sortBy) {
                case "popularity":
                    sortByChipGroup.check(R.id.Popularity_chip);
                    break;
                case "date":
                    sortByChipGroup.check(R.id.Date_chip);
                    break;
                case "value":
                    sortByChipGroup.check(R.id.Value_chip);
                    break;
                default:
                    sortByChipGroup.clearCheck();
            }
        } else if (sortBy==null) {
            sortByChipGroup.clearCheck();
        }
    }
    private void setupPlatformSpinner() {
        platformSpinner.setText(platform!=null?platform.substring(0, 1).toUpperCase() + platform.substring(1):platform);
        platforms =getResources().getStringArray(R.array.platforms);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.drop_down_item,platforms);
        platformSpinner.setAdapter(adapter);

        platformSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                platform=platformSpinner.getText().toString();
                Log.d("platform",platform);
            }

        });
    }
}