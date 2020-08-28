package com.example.healtyapp.adapter_item;

public class ItemHistory {

    ItemHistory(){
    }

    public ItemHistory(String name, int size){
        this.name = name;
        this.size = size;

    }
    private  String name;
    private  int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
