package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void replaceCity(City city, City newcity);
    }
    private AddCityDialogListener listener;
    private City editCity = null;

    // Empty constructor for adding new cities
    public AddCityFragment() {
        this.editCity = null;
    }

    // Constructor which takes a city for editing
    public AddCityFragment(City city) {
        this.editCity = city;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String Title;
        String ButtonText;
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        if (editCity != null) {
            editCityName.setText(editCity.getName());
            editProvinceName.setText(editCity.getProvince());
            Title = "Edit City";
            ButtonText = "Update";
        }
        else {
            Title = "Add City";
            ButtonText = "Add";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(Title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(ButtonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (editCity != null) {
                        listener.replaceCity(editCity, new City(cityName, provinceName));
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}

