package com.proud.console.util.view;

import com.proud.console.FxUtilTest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * DateAxis test
 * Created by Fabrizio Faustinoni on 13/10/2016.
 */
public class DateAxisTest extends FxUtilTest {

    private static DateAxis dateAxis;

    @BeforeClass
    public static void setUpClass()
            throws InterruptedException {
        FxUtilTest.setUpClass();
        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        ObservableList<XYChart.Data<Date, Number>> series1Data = FXCollections.observableArrayList();
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2012, 11, 15).getTime(), 2));
        series1Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 5, 3).getTime(), 4));

        ObservableList<XYChart.Data<Date, Number>> series2Data = FXCollections.observableArrayList();
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 0, 13).getTime(), 8));
        series2Data.add(new XYChart.Data<Date, Number>(new GregorianCalendar(2014, 7, 27).getTime(), 4));

        series.add(new XYChart.Series<>("Series1", series1Data));
        series.add(new XYChart.Series<>("Series2", series2Data));

        NumberAxis numberAxis = new NumberAxis();
        dateAxis = new DateAxis();
        LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis, series);
    }

    @Test
    public void getZeroPosition() throws Exception {
        assertEquals(0.0, dateAxis.getZeroPosition(), 0.0);
    }

    @Test
    public void toNumericValue() throws Exception {
        assertEquals(1.3895676E12,dateAxis.toNumericValue(new GregorianCalendar(2014, 0, 13).getTime()),0.0);
    }
}