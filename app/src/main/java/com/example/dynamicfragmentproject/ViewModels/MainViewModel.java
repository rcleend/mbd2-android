package com.example.dynamicfragmentproject.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private String[] names = {"Roel", "Anne", "Jelle"};
    private MutableLiveData<String> selectedName = new MutableLiveData<>();

    public String[] getNames() {
        return this.names;
    }

    public MutableLiveData<String> getSelectedName(){
        return this.selectedName;
    }

    public void setSelectedName(String selectedName){
        this.selectedName.setValue(selectedName);
    }

}
