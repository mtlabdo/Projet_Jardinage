package com.abdessamad.jardinage.ViewModels;


import android.widget.Spinner;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

@InverseBindingMethods({
        @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItemPosition"),
})
public class PotagerrViewModel extends BaseObservable {
    private String text;
    private int position;
    public String getText() {
        return text;
    }



    @Bindable
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }

}
