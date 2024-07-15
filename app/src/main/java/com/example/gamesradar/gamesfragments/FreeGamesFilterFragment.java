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
import com.example.gamesradar.gamesfragments.listeners.GetGamesFilterValues;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class FreeGamesFilterFragment extends BottomSheetDialogFragment {


    private static final String ARG_PLATFORM = "platform";
    private static final String ARG_GENRE = "genre";
    private static final String ARG_SORTBY = "sortBy";
    private static final String ARG_CALLBACK = "callback";

//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private Button apply;
   private Button reset;
   AutoCompleteTextView genreSpinner;
   String genres[];
   String genre=null;
   ChipGroup platformChipGroup;
   String platform=null;
   ChipGroup sortByChipGroup;
   String sortBy=null;
   GetGamesFilterValues callback;
    static  FreeGamesFilterFragment instance;




    public FreeGamesFilterFragment() {
        // Required empty public constructor
    }



    public static FreeGamesFilterFragment newInstance(String platform,String genre, String sortBy) {

        if(instance==null) {
            FreeGamesFilterFragment fragment = new FreeGamesFilterFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PLATFORM, platform);
            args.putString(ARG_GENRE, genre);
            args.putString(ARG_SORTBY, sortBy);
            //args.put(ARG_CALLBACK, callback);
            fragment.setArguments(args);
            instance = fragment;
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           platform = getArguments().getString(ARG_PLATFORM);
            genre = getArguments().getString(ARG_GENRE);
            sortBy = getArguments().getString(ARG_SORTBY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_free_games_filter, container, false);
        apply = v.findViewById(R.id.apply_btn);
        reset = v.findViewById(R.id.reset_btn);
        platformChipGroup = v.findViewById(R.id.platform_chipgroup);
        sortByChipGroup = v.findViewById(R.id.sortby_chipgroup);
        genreSpinner = v.findViewById(R.id.genre_spinner);

        initializeViews();



        platformChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                Chip chip = v.findViewById(group.getCheckedChipId());
                if(chip!=null) {
                    switch(chip.getId()){
                        case R.id.All_chip:
                            platform = "all";
                            break;
                        case R.id.PC_chip:
                            platform = "pc";
                            break;
                        case R.id.Browser_chip:
                            platform="browser";
                            break;

                        default:
                            platform=null;
                    }
                }
                else{
                    platform=null;
                }

                Log.d("plat",platform==null?"null":platform);
            }
        });

        sortByChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                Chip chip = v.findViewById(group.getCheckedChipId());
                if(chip!=null) {
                    switch(chip.getId()){
                        case R.id.Popularity_chip:
                            sortBy = "popularity";
                            break;
                        case R.id.Relevance_chip:
                            sortBy = "relevance";
                            break;
                        case R.id.ReleaseDate_chip:
                            sortBy = "release-date";
                            break;
                        case R.id.Alphabetical_chip:
                            sortBy = "alphabetical";
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


        setupGenreSpinner();

        apply.setOnClickListener(view -> {

            //callback.getFilterValues(platform,genre,sortBy);

            Bundle result = new Bundle();
            result.putString("platform", platform);
            result.putString("genre", genre);
            result.putString("sortBy", sortBy);
            if(getParentFragment()!=null){
                Log.d("result","fragmentnotnull");
            }
            getParentFragmentManager().setFragmentResult("requestKey",result);
            dismiss();

        });
        reset.setOnClickListener(view -> {
            platformChipGroup.clearCheck();
            platform=null;
            sortByChipGroup.clearCheck();
            sortBy=null;
            genreSpinner.setText("");
            genre=null;
        });


        return v;
    }

    private void initializeViews() {
        genreSpinner.setText(genre);

        if(platform!=null) {
            switch (platform) {
                case "all":
                    platformChipGroup.check(R.id.All_chip);
                    break;
                case "pc":
                    platformChipGroup.check(R.id.PC_chip);
                    break;
                case "browser":
                    platformChipGroup.check(R.id.Browser_chip);
                    break;
                default:
                    platformChipGroup.clearCheck();
            }
        } else if (platform==null) {
            platformChipGroup.clearCheck();
        }
        if(sortBy!=null) {
            switch (sortBy) {
                case "popularity":
                    sortByChipGroup.check(R.id.Popularity_chip);
                    break;
                case "relevance":
                    sortByChipGroup.check(R.id.Relevance_chip);
                    break;
                case "release-date":
                    sortByChipGroup.check(R.id.ReleaseDate_chip);
                    break;
                case "alphabetical":
                    sortByChipGroup.check(R.id.Alphabetical_chip);
                    break;
                default:
                    sortByChipGroup.clearCheck();
            }
        } else if (sortBy==null) {
            sortByChipGroup.clearCheck();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (GetGamesFilterValues) getParentFragment();
    }

    private void setupGenreSpinner() {
        genreSpinner.setText(genre!=null?genre.substring(0, 1).toUpperCase() + genre.substring(1):genre);
        genres =getResources().getStringArray(R.array.game_genres);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.drop_down_item,genres);
        genreSpinner.setAdapter(adapter);

        genreSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                genre=genreSpinner.getText().toString();
                Log.d("genre",genre);
            }

        });
    }
}