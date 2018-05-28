package com.juraj.hdbsClient.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Juraj on 24.4.2018..
 */
public class DataSet {

    private List<Map<String, String>> data;

    private List<String> columnNames;

    public DataSet(String csvWithHeader){
        String[] csvSplit = csvWithHeader.split("\n", 2);
        setColumnName(csvSplit[0]);
        setData(csvSplit[1]);
    }

    private void setColumnName(String header){
        columnNames = Arrays.asList(header.split(","));
    }

    private void setData(String csv){
        data = new ArrayList<>();
        for (String line : csv.split("\n")){
            String[] linesplit = line.split(",");

            Map<String, String> row = new HashMap<>();
            for (int i = 0; i < columnNames.size(); i++) {
                row.put(columnNames.get(i), linesplit[i]);
            }
            data.add(row);
        }
    }

    public List<String> getColumnByName(String columnName){
        return data.stream().map(x -> x.get(columnName)).collect(Collectors.toList());
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<Map<String, String>> getData() {
        return data;
    }
}
