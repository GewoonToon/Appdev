package com.example.appdev;

import static android.content.Context.WINDOW_SERVICE;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Switch darkmodeSwitch;
    Switch sizeSwitch;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View aa = inflater.inflate(R.layout.fragment_settings, container, false);


        darkmodeSwitch = aa.findViewById(R.id.darkmodeSwitch);
        //Darkmode on click
        darkmodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        //Make switch be active after refreshing if darkmode is active
        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlags == Configuration.UI_MODE_NIGHT_YES){
            darkmodeSwitch.setChecked(true);
        }

        // Change text font to twice the size
        sizeSwitch = aa.findViewById(R.id.sizeSwitch);
        sizeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Configuration configuration = getResources().getConfiguration();
                    configuration.fontScale = 2;
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    WindowManager wm = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                    wm.getDefaultDisplay().getMetrics(metrics);
                    metrics.scaledDensity = configuration.fontScale * metrics.density;
                    getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);

                }
                else{
                    Configuration configuration = getResources().getConfiguration();
                    configuration.fontScale = 1;
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    WindowManager wm = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                    wm.getDefaultDisplay().getMetrics(metrics);
                    metrics.scaledDensity = configuration.fontScale * metrics.density;
                    getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
                }
            }
        });

        float size =
                getContext().getResources().getConfiguration().fontScale;
        if(size == 2){
            sizeSwitch.setChecked(true);
        }

        return aa;
    }
}