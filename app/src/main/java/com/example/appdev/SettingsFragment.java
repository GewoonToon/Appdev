package com.example.appdev;

import static android.content.Context.WINDOW_SERVICE;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.util.Locale;
import java.util.Objects;


public class SettingsFragment extends Fragment {
    Switch darkmodeSwitch;
    Switch sizeSwitch;
    Switch languageSwitch;
    TextView title;



    public SettingsFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        title = getActivity().findViewById(R.id.AppTitle);
        title.setText("Settings");

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

        languageSwitch = aa.findViewById(R.id.languageSwitch);

        languageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setLanguage("nl");

                }
                else{
                    setLanguage("en");
                }
            }
        });

        if(Objects.equals(getContext().getResources().getConfiguration().locale, new Locale("nl"))){
            languageSwitch.setChecked(true);
        }

        return aa;
    }

    private void setLanguage(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
            }
}