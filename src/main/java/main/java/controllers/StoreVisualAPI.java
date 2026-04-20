package main.java.controllers;

import main.java.models.*;

public class StoreVisualAPI {

    private final JewelleryStoreAPI store = new JewelleryStoreAPI();

    public void initialize() {
        try {
            store.load();
            System.out.println("Welcome to the Jewellery Store! Database autoloaded successfully.");
        } catch (Exception e) {
            System.out.println("Welcome to the Jewellery Store! No existing database found. Starting with an empty store.");
        }
    }

    public void listStockBtn() {
        String allStock = store.listAllStock();
        System.out.println(allStock);
    }

    public void valueStockBtn() {
        double value = store.getValue();
        System.out.println("Stock details: " + value);
    }

    public void displayDetailsBtn() {
        String details = store.displayCaseDetails();
        System.out.println(details);
    }

    public void searchBtn() {
        System.out.println("Search method called - needs search field integration");
    }

    public void saveBtn() {
        try {
            store.save();
            System.out.println("Save successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadBtn() {
        try {
            store.load();
            System.out.println("Load successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetBtn() {
        store.reset();
        System.out.println("Reset successfully!");
    }

    public void smartAddBtn() {
        System.out.println("Smart Add method called - needs form field integration");
    }

    public void caseClicked() {
        System.out.println("Case clicked method called");
    }

    public void trayClicked() {
        System.out.println("Tray clicked method called");
    }

    private String generateItemId() {
        return "ITEM-" + System.currentTimeMillis();
    }

    public static void main(String[] args) {
        StoreVisualAPI api = new StoreVisualAPI();
        api.initialize();
        
        api.smartAddBtn();
        api.smartAddBtn();
        api.listStockBtn();
        api.searchBtn();
        api.saveBtn();
    }
}
