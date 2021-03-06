package com.example.expensetracker;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class OverviewFragment extends Fragment {

    private PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_pie, container,false);

        pieChart = (PieChart) view.findViewById(R.id.category_pieChart);
        setupPieChart();
        loadPieChartData();

        // Inflate the layout for this fragment
        return view;
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending by Category");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend One = pieChart.getLegend();
        One.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        One.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        One.setOrientation(Legend.LegendOrientation.VERTICAL);
        One.setDrawInside(false);
        One.setEnabled(true);
    }

    private void loadPieChartData(){
        ArrayList<PieEntry> entries = new ArrayList<>();

        //data
        entries.add(new PieEntry(0.2f, "Food"));
        entries.add(new PieEntry(0.15f, "Food"));
        entries.add(new PieEntry(0.10f, "Food"));
        entries.add(new PieEntry(0.25f, "Food"));
        entries.add(new PieEntry(0.3f, "Food"));

        //colours
        ArrayList<Integer> colours = new ArrayList<>();
        for(int colour: ColorTemplate.MATERIAL_COLORS){
            colours.add(colour);
        }

        for(int colour: ColorTemplate.VORDIPLOM_COLORS){
            colours.add(colour);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colours);

        //pie data object
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        //provide pie data object to pie chart
        pieChart.setData(data);
        pieChart.invalidate();
    }
}