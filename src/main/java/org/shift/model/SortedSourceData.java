package org.shift.model;

import java.util.ArrayList;
import java.util.List;

public class SortedSourceData {
    private final List<String> intData = new ArrayList<>();
    private final List<String> doubleData = new ArrayList<>();
    private final List<String> stringData = new ArrayList<>();

    public List<String> getIntData() {
        return intData;
    }

    public List<String> getDoubleData() {
        return doubleData;
    }

    public List<String> getStringData() {
        return stringData;
    }

    public void addInteger(String data) {
        getIntData().add(data);
    }

    public void addDouble(String data) {
        getDoubleData().add(data);
    }

    public void addString(String data) {
        getStringData().add(data);
    }
}
