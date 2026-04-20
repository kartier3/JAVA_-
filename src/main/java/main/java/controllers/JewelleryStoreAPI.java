package main.java.controllers;

import main.java.models.DisplayCase;
import main.java.models.DisplayTray;
import main.java.models.JewelleryItem;
import main.java.models.MaterialComponent;
import main.java.utils.CustomLinkedList;
import java.io.IOException;

public class JewelleryStoreAPI {

    private CustomLinkedList<DisplayCase> displayCases;

    private final XMLAPI xmlAPI;
    private final JewelleryviewAPI printerAPI;
    private final SearchAPI searchAddAPI;

    public JewelleryStoreAPI() {
        this.displayCases = new CustomLinkedList<>();
        this.xmlAPI = new XMLAPI();
        this.printerAPI = new JewelleryviewAPI();
        this.searchAddAPI = new SearchAPI();
    }

    public void addDisplayCase(DisplayCase displayCase) {
        displayCases.add(displayCase);
    }

    public CustomLinkedList<DisplayCase> getDisplayCases() {
        return displayCases;
    }

    public void reset(){
        this.displayCases = new CustomLinkedList<>();
    }

    public boolean removeItem(int caseIndex, int trayIndex, int itemIndex) {
        if (caseIndex >=0 && caseIndex < displayCases.size()) {
            DisplayCase displayCase = displayCases.get(caseIndex);
            CustomLinkedList<DisplayTray> trays = displayCase.getTrays();

            if (trayIndex >= 0 && trayIndex < trays.size()) {
                DisplayTray tray = trays.get(trayIndex);
                CustomLinkedList<JewelleryItem> items = tray.getItems();

                if (itemIndex >= 0 && itemIndex < items.size()) {
                    items.remove(itemIndex);
                    return true;
                }
            }
        }
        return false;
    }

    public String listAllStock() {
        return printerAPI.listAllStock(this.displayCases);
    }

    public String displayCaseDetails() {
        return printerAPI.displayCaseDetails(this.displayCases);
    }

  
    public String smartAddItem(JewelleryItem item) {
        return searchAddAPI.smartAddItem(this.displayCases, item);
    }

    public String searchByKeywords(String[] searchWords) {
        return searchAddAPI.searchByKeywords(this.displayCases, searchWords);
    }

    public void addMaterialToTable(String materialName, String description, double weight, double caratValue) {
        searchAddAPI.addMaterialToTable(materialName, description, weight, caratValue);
    }

    public String listMaterialsTable() {
        return searchAddAPI.listMaterialsTable();
    }

    public void save() throws IOException {
        xmlAPI.save(this.displayCases);
    }

    public void load() throws IOException, ClassNotFoundException {
        this.displayCases = xmlAPI.load();
    }

    public double getValue() {
        double total = 0;
        for (int i = 0; i < displayCases.size(); i++) {
            DisplayCase displayCase = displayCases.get(i);
            CustomLinkedList<DisplayTray> trays = displayCase.getTrays();
            for (int j = 0; j < trays.size(); j++) {
                DisplayTray tray = trays.get(j);
                CustomLinkedList<JewelleryItem> items = tray.getItems();
                for (int k = 0; k < items.size(); k++) {
                    JewelleryItem item = items.get(k);
                    CustomLinkedList<MaterialComponent> materials = item.getMaterials();
                    for (int m = 0; m < materials.size(); m++) {
                        total += materials.get(m).getValue();
                    }
                }
            }
        }
        return total;
    }
}
