package com.example.android.projetshoping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Crock on 13/05/2018.
 */

public class Adapter extends ArrayAdapter<ModelListesCourses> {

    public Adapter(Context context, List<ModelListesCourses> objects) {
        super(context, 0 , objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ModelListesCourses itemAcheter = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.model_courses_listes, parent, false);
            /** tentative d'adapter different selon la view
            if (convertView.getContext().equals(ModeListe.class)){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.model_courses_listes, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.model_courses, parent, false);
            }
             */
        }

        TextView course = convertView.findViewById(R.id.model_list);
        course.setText(itemAcheter.getaAcheter());

        return convertView;
    }
}
