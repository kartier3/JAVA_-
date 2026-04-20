package main.java.models;

import main.java.utils.CustomLinkedList;
import java.io.*;

public class JewelleryStore implements Serializable {

    private String storeName;
    private String address;
    private CustomLinkedList<DisplayCase> displayCases;

    public JewelleryStore(String storeName, String address) {
        this.storeName = storeName;
        this.address = address;
        this.displayCases = new CustomLinkedList<>();
    }


    String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomLinkedList<DisplayCase> getDisplayCases() {
        return displayCases;
    }
}
