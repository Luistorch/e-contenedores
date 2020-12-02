package com.example.recycle.activity;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycle.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView nombreCubo;
    private BarChart plasticChart;
    private LineChart lineChart;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreCubo = itemView.findViewById(R.id.nombreCubo);
        plasticChart = itemView.findViewById(R.id.bar_chart);
        lineChart = itemView.findViewById(R.id.line_chart);
    }

    // Devuelve si es un cubo o un boton en base a si tiene nombreCubo o no
    public int getHolderType() {
        if(getNombreCubo() != null) {
            return 1; // Cubo
        } else {
            return 2; // Boton
        }
    }

    // Devuelve la vista
    public TextView getNombreCubo(){
        return nombreCubo;
    }

    public BarChart getPlasticChart() {
        return plasticChart;
    }
    public LineChart getLineChart() {
        return lineChart;
    }
}