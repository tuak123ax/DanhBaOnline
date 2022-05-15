package com.example.danhba;

import android.text.Editable;

import java.io.Serializable;

public class Data implements Serializable {
    public String name;
    public String sdt;

    public Data(){

    }

    public Data(String name, String sdt) {
        this.name = name;
        this.sdt = sdt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
