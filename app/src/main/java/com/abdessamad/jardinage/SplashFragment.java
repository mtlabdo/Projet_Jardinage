package com.abdessamad.jardinage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autoai.plantation.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class SplashFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splash_screen, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2*1000);

                    Navigation.findNavController(rootView).navigate(R.id.action_splashFragment_to_potagersFragment);
                } catch (Exception e) {
                }
            }
        };
        background.start();
        return rootView;
    }
}
