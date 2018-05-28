package com.juraj.hdbsClient.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Juraj on 31.10.2017..
 */
public class ToObservableList2D {

    public static ObservableList<ObservableList<Object>> convertFrom2DList(List<List<Object>> input){
        ObservableList<ObservableList<Object>> rows = FXCollections.observableArrayList();

        for(List<Object> i: input){
            rows.add(FXCollections.observableArrayList(i));
        }

        return rows;
    }

    public static ObservableList<ObservableList<Object>> convertFromDataSet(DataSet input){
        List<List<Object>> rows = new ArrayList<>();

        for (Map<String, String> row : input.getData()){
            List<Object> _row = new ArrayList<>();
            for (String key : row.keySet()) {
                _row.add(row.get(key));
            }
            rows.add(_row);
        }

        return convertFrom2DList(rows);
    }

}
