package main.java;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.java.controllers.JewelleryStoreAPI;
import main.java.models.JewelleryItem;
import main.java.models.DisplayCase;
import main.java.models.DisplayTray;
import main.java.models.MaterialComponent;
import main.java.utils.CustomLinkedList;

public class SimpleController {
    private final JewelleryStoreAPI store = new JewelleryStoreAPI();
    
    public TextArea outputArea;
    public ListView<String> caseList;
    public ListView<String> trayList;
    public TextArea drillDownDetails;
    
    public TextField caseNameInput;
    public TextField caseLocationInput;
    public ComboBox<String> caseSelectInput;
    public ComboBox<String> caseSelectForItemInput;
    public TextField trayTypeInput;
    public ComboBox<String> traySelectInput;
    public TextField itemIdInput;
    public TextField itemTypeInput;
    public TextField itemDescInput;
    public TextField deleteItemIdInput;
    public TextField searchInput;
    
    private DisplayCase selectedCase;
    private DisplayTray selectedTray;
    private JewelleryItem selectedItem;
    
    public void initialize() {
        try {
            store.load();
        } catch (Exception e) {
            System.out.println("Starting with empty store");
        }
        
        if (store.getDisplayCases().size() == 0) {
            loadSampleData();
        } else {
            listStockBtn();
        }
        
        populateCaseList();
        populateCaseComboBoxes();
        
        caseSelectForItemInput.setOnAction(e -> populateTrayComboBoxes());
    }
    
    private void populateCaseList() {
        caseList.getItems().clear();
        CustomLinkedList<DisplayCase> cases = store.getDisplayCases();
        for (int i = 0; i < cases.size(); i++) {
            DisplayCase caseItem = cases.get(i);
            caseList.getItems().add("Case " + (i + 1) + ": " + caseItem.getCaseName());
        }
    }
    
    private void loadSampleData() {
        DisplayCase mainCase = new DisplayCase("CASE-001", "Main Display Case", "Front of Store");
        DisplayCase wallCase = new DisplayCase("CASE-002", "Wall Display Case", "East Wall");
        
        DisplayTray ringTray = new DisplayTray("TRAY-001", "Ring");
        DisplayTray necklaceTray = new DisplayTray("TRAY-002", "Necklace");
        
        DisplayTray watchTray = new DisplayTray("TRAY-003", "Watch");
        
        ringTray.getItems().add(new JewelleryItem("ITEM-001", "Ring", "Ring "));
        JewelleryItem goldRing = ringTray.getItems().get(0);
        goldRing.getMaterials().add(new MaterialComponent("Gold", "Gold", 5.2, 1.5));
        goldRing.getMaterials().add(new MaterialComponent("Diamond", "Diamond", 0.3, 1.5));
        
        ringTray.getItems().add(new JewelleryItem("ITEM-002", "Ring", "Diamond Ring"));
        JewelleryItem diamondRing = ringTray.getItems().get(1);
        diamondRing.getMaterials().add(new MaterialComponent("Diamond", "VS1 Diamond", 0.5, 5000.0));
        
        necklaceTray.getItems().add(new JewelleryItem("ITEM-003", "Necklace", "Pearl Strand Necklace"));
        JewelleryItem pearlNecklace = necklaceTray.getItems().get(0);
        pearlNecklace.getMaterials().add(new MaterialComponent("Pearl", "White Akoya Pearls", 2.5, 0.0));
        pearlNecklace.getMaterials().add(new MaterialComponent("Gold", "14K Yellow Gold Clasp", 0.8, 0.0));
        
        watchTray.getItems().add(new JewelleryItem("ITEM-004", "Watch", "Luxury Swiss Watch - Leather Strap"));
        JewelleryItem luxuryWatch = watchTray.getItems().get(0);
        luxuryWatch.getMaterials().add(new MaterialComponent("Stainless Steel", "316L Stainless Steel", 25.0, 0.0));
        luxuryWatch.getMaterials().add(new MaterialComponent("Leather", "Genuine Italian Leather", 15.0, 1.0));
        luxuryWatch.getMaterials().add(new MaterialComponent("Sapphire Crystal", "Synthetic Sapphire Crystal", 2.0, 0.5));
        mainCase.getTrays().add(ringTray);
        mainCase.getTrays().add(necklaceTray);
        wallCase.getTrays().add(watchTray);
        
        store.getDisplayCases().add(mainCase);
        store.getDisplayCases().add(wallCase);
        
        populateCaseList();
        listStockBtn();
    }
    
    public void listStockBtn() {
        outputArea.setText(store.listAllStock());
    }
    
    public void valueStockBtn() {
        double totalValue = 0.0;
        int totalItems = 0;
        int totalMaterials = 0;
        StringBuilder report = new StringBuilder();
        
        report.append("JEWELLERY STORE VALUATION REPORT\n");
        
        
        for (int i = 0; i < store.getDisplayCases().size(); i++) {
            DisplayCase displayCase = store.getDisplayCases().get(i);
            report.append("Case: ").append(displayCase.getCaseName()).append(" (").append(displayCase.getLocation()).append(")\n");
 
            for (int j = 0; j < displayCase.getTrays().size(); j++) {
                DisplayTray tray = displayCase.getTrays().get(j);
                report.append("  Tray: ").append(tray.getTrayType()).append("\n");
 
                for (int k = 0; k < tray.getItems().size(); k++) {
                    JewelleryItem item = tray.getItems().get(k);
                    totalItems++;
                    double itemValue = 0.0;
 
                    for (int m = 0; m < item.getMaterials().size(); m++) {
                        MaterialComponent material = item.getMaterials().get(m);
                        itemValue += material.getValue();
                        totalMaterials++;
                    }
 
                    totalValue += itemValue;
                    report.append("    Item: ").append(item.getItemId()).append(" - ").append(item.getType()).append(": $").append(String.format("%.2f", itemValue)).append("\n");
                }
            }
        }
 
        report.append("Total Value: $").append(String.format("%.2f", store.getValue())).append("\n");
    
        outputArea.setText(report.toString());
    }
    
    
    
    public void addSampleBtn() {
        JewelleryItem ring = new JewelleryItem("ITEM-" + System.currentTimeMillis(), "Ring", "Gold Diamond Ring");
        ring.getMaterials().add(new MaterialComponent("Gold", "18K Yellow Gold", 5.0, 40.0));
        ring.getMaterials().add(new MaterialComponent("Diamond", "Round Brilliant Cut", 0.5, 10000.0));
        store.smartAddItem(ring);
        
        JewelleryItem necklace = new JewelleryItem("ITEM-" + System.currentTimeMillis(), "Necklace", "Silver Pearl Necklace");
        necklace.getMaterials().add(new MaterialComponent("Silver", "Sterling Silver", 15.0, 15.0));
        necklace.getMaterials().add(new MaterialComponent("Pearl", "Freshwater Pearl", 10.0, 50.0));
        store.smartAddItem(necklace);
        
        JewelleryItem watch = new JewelleryItem("ITEM-" + System.currentTimeMillis(), "Watch", "Platinum Watch");
        watch.getMaterials().add(new MaterialComponent("Platinum", "Pure Platinum", 20.0, 80.0));
        watch.getMaterials().add(new MaterialComponent("Steel", "Stainless Steel", 8.0, 5.0));
        store.smartAddItem(watch);
        
        outputArea.setText("Sample items added successfully!");
    }
    
    
    
    public void saveBtn() {
        try {
            store.save();
            outputArea.setText("Data saved successfully!");
        } catch (Exception ex) {
            outputArea.setText("Save failed: " + ex.getMessage());
        }
    }
    
    public void loadBtn() {
        try {
            outputArea.setText("Loading data from XML...");
            store.load();
            outputArea.setText("Data loaded successfully!\nItems loaded: " + countTotalItems() + "\nTotal materials: " + countTotalMaterials());
        } catch (Exception ex) {
            outputArea.setText("Load failed: " + ex.getMessage());
        }
    }
    
    private int countTotalItems() {
        int totalItems = 0;
        for (int i = 0; i < store.getDisplayCases().size(); i++) {
            DisplayCase displayCase = store.getDisplayCases().get(i);
            for (int j = 0; j < displayCase.getTrays().size(); j++) {
                DisplayTray tray = displayCase.getTrays().get(j);
                totalItems += tray.getItems().size();
            }
        }
        return totalItems;
    }
    
    private int countTotalMaterials() {
        int totalMaterials = 0;
        for (int i = 0; i < store.getDisplayCases().size(); i++) {
            DisplayCase displayCase = store.getDisplayCases().get(i);
            for (int j = 0; j < displayCase.getTrays().size(); j++) {
                DisplayTray tray = displayCase.getTrays().get(j);
                for (int k = 0; k < tray.getItems().size(); k++) {
                    JewelleryItem item = tray.getItems().get(k);
                    totalMaterials += item.getMaterials().size();
                }
            }
        }
        return totalMaterials;
    }
    
    public void resetBtn() {
        store.reset();
        outputArea.setText("Store reset successfully!");
    }
    public void searchBtn() {
        String searchText = searchInput.getText();
        if (searchText == null || searchText.trim().isEmpty()) {
            outputArea.setText("Please enter search words in the Search field (comma separated)");
            return;
        }
        String[] searchWords = searchText.split(",");
        String result = store.searchByKeywords(searchWords);
        outputArea.setText(result);
    }
    
    public void listMaterialsTableBtn() {
        outputArea.setText(store.listMaterialsTable());
    }
    
    public void addMaterialToTableBtn() {
        try {
            String name = itemIdInput.getText();
            String desc = itemTypeInput.getText();
            double weight = Double.parseDouble(itemDescInput.getText());
            double value = Double.parseDouble(deleteItemIdInput.getText());
            store.addMaterialToTable(name, desc, weight, value);
            outputArea.setText("Material added to table: " + name);
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage() + "\nUse Item ID field for name, Type for description, Desc for weight, Delete ID for value");
        }
    }
    public void caseClicked() {
        int selectedIndex = caseList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            selectedCase = store.getDisplayCases().get(selectedIndex);
            if (selectedCase != null) {
                populateTrayList();
                drillDownDetails.setText("Selected: " + selectedCase.getCaseName() + "\nLocation: " + selectedCase.getLocation());
            }
        }
    }
    
    public void trayClicked() {
        int selectedIndex = trayList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedCase != null) {
            selectedTray = selectedCase.getTrays().get(selectedIndex);
            showTrayItems();
        }
    }
    
    private void populateTrayList() {
        trayList.getItems().clear();
        if (selectedCase != null) {
            CustomLinkedList<DisplayTray> trays = selectedCase.getTrays();
            for (int i = 0; i < trays.size(); i++) {
                DisplayTray tray = trays.get(i);
                trayList.getItems().add("Tray " + (i + 1) + ": " + tray.getTrayType());
            }
        }
    }
    
    private void showTrayItems() {
        if (selectedTray != null) {
            StringBuilder items = new StringBuilder();
            items.append("Tray: ").append(selectedTray.getTrayType()).append("\n\n");
            items.append("Items in this tray:\n");
            items.append("(Click on item number to select for deletion)\n\n");
            
            CustomLinkedList<JewelleryItem> itemList = selectedTray.getItems();
            if (itemList.size() == 0) {
                items.append("No items in this tray.");
                selectedItem = null;
            } else {
                for (int i = 0; i < itemList.size(); i++) {
                    JewelleryItem item = itemList.get(i);
                    items.append("[").append(i + 1).append("] ID: ").append(item.getItemId()).append(" [SELECTABLE]\n");
                    items.append("   Type: ").append(item.getType()).append("\n");
                    items.append("   Description: ").append(item.getDescription()).append("\n");
                    
                    CustomLinkedList<MaterialComponent> materials = item.getMaterials();
                    if (materials.size() > 0) {
                        double itemTotalValue = 0;
                        items.append("   Materials (").append(materials.size()).append("):\n");
                        for (int m = 0; m < materials.size(); m++) {
                            MaterialComponent mat = materials.get(m);
                            double matValue = mat.getValue();
                            itemTotalValue += matValue;
                            items.append("     - ").append(mat.getMaterialName())
                                 .append(" (").append(mat.getDescription()).append(")")
                                 .append(" | Weight: ").append(mat.getWeight())
                                 .append(" | Value: $").append(String.format("%.2f", matValue))
                                 .append("\n");
                        }
                        items.append("   Item Total Value: $").append(String.format("%.2f", itemTotalValue)).append("\n");
                    } else {
                        items.append("   (No materials)\n");
                    }
                    items.append("\n");
                }
            }
            
            drillDownDetails.setText(items.toString());
            drillDownDetails.setEditable(false);
        }
    }
    
    public void detailsClicked() {
        if (selectedTray == null || drillDownDetails.getText().trim().isEmpty()) {
            return;
        }
        
        String text = drillDownDetails.getText();
        String[] lines = text.split("\n");
        
        for (String line : lines) {
            if (line.matches("^\\[\\d+\\].*")) {
                try {
                    String numberStr = line.substring(1, line.indexOf(']'));
                    int itemNumber = Integer.parseInt(numberStr);
                    selectItemByNumber(itemNumber);
                    return;
                } catch (Exception e) {
                }
            }
        }
    }
    
    public void selectItemByNumber(int itemNumber) {
        if (selectedTray != null && itemNumber > 0 && itemNumber <= selectedTray.getItems().size()) {
            selectedItem = selectedTray.getItems().get(itemNumber - 1);
            outputArea.setText("✅ Selected item: " + selectedItem.getItemId() + " - " + selectedItem.getType() + "\nClick 'Delete Displayed Item' to remove it.");
        }
    }
    
    public void deleteItemBtn() {
        if (selectedItem == null) {
            outputArea.setText("❌ No item selected. Click on an item number in the Drill Down section first.");
            return;
        }
        
        if (selectedTray == null) {
            outputArea.setText("❌ No tray selected.");
            return;
        }
        
        int itemIndex = -1;
        for (int i = 0; i < selectedTray.getItems().size(); i++) {
            JewelleryItem item = selectedTray.getItems().get(i);
            if (item.getItemId().equals(selectedItem.getItemId())) {
                itemIndex = i;
                break;
            }
        }
        
        if (itemIndex >= 0) {
            JewelleryItem removed = selectedTray.getItems().remove(itemIndex);
            
            if (removed != null) {
                String deletedItemId = removed.getItemId();
                String deletedItemType = removed.getType();
                
                selectedItem = null;
                
                showTrayItems();
                
                outputArea.setText(" Item deleted successfully: " + deletedItemId + " - " + deletedItemType);
            } else {
                outputArea.setText(" Failed to delete item.");
            }
        } else {
            outputArea.setText(" Item not found in tray.");
        }
    }
    
    public void deleteItemByIdBtn() {
        String itemIdToDelete = deleteItemIdInput.getText();
        
        outputArea.setText("Debug: Attempting to delete ID: '" + itemIdToDelete + "'");
        
        if (itemIdToDelete == null || itemIdToDelete.trim().isEmpty()) {
            outputArea.setText("❌ Please enter an Item ID to delete.");
            return;
        }
        
        boolean itemFound = false;
        String deletedItemInfo = "";
        
        outputArea.setText("Debug: Searching through " + store.getDisplayCases().size() + " cases...");
        
        for (int caseIndex = 0; caseIndex < store.getDisplayCases().size(); caseIndex++) {
            DisplayCase displayCase = store.getDisplayCases().get(caseIndex);
            
            outputArea.setText("Debug: Checking case " + caseIndex + ": " + displayCase.getCaseName() + " with " + displayCase.getTrays().size() + " trays");
            
            for (int trayIndex = 0; trayIndex < displayCase.getTrays().size(); trayIndex++) {
                DisplayTray tray = displayCase.getTrays().get(trayIndex);
                
                outputArea.setText("Debug: Checking tray " + trayIndex + ": " + tray.getTrayType() + " with " + tray.getItems().size() + " items");
                
                for (int itemIndex = 0; itemIndex < tray.getItems().size(); itemIndex++) {
                    JewelleryItem item = tray.getItems().get(itemIndex);
                    
                    outputArea.setText("Debug: Found item: " + item.getItemId() + " (looking for: " + itemIdToDelete.trim() + ")");
                    
                    if (item.getItemId().equals(itemIdToDelete.trim())) {
                        JewelleryItem removed = tray.getItems().remove(itemIndex);
                        
                        if (removed != null) {
                            deletedItemInfo = removed.getItemId() + " - " + removed.getType();
                            itemFound = true;
                            
                            deleteItemIdInput.clear();
                            
                            if (selectedTray == tray) {
                                showTrayItems();
                            }
                            
                            outputArea.setText("✅ Item deleted successfully: " + deletedItemInfo);
                            return;
                        }
                    }
                }
            }
        }
        
        if (!itemFound) {
            outputArea.setText("❌ Item with ID '" + itemIdToDelete + "' not found in any tray.");
        }
    }
    
    public void smartAddBtn() {
        outputArea.setText("Smart add functionality moved to Smart Add tab!");
    }
    
    public void addCaseBtn() {
        String caseName = caseNameInput.getText();
        String location = caseLocationInput.getText();
        
        if (caseName == null || caseName.trim().isEmpty()) {
            outputArea.setText("Error: Please enter a case name");
            return;
        }
        
        if (location == null || location.trim().isEmpty()) {
            location = "Unspecified";
        }
        
        String caseId = "CASE-" + String.format("%03d", store.getDisplayCases().size() + 1);
        DisplayCase newCase = new DisplayCase(caseId, caseName.trim(), location.trim());
        store.getDisplayCases().add(newCase);
        
        caseNameInput.clear();
        caseLocationInput.clear();
        
        populateCaseList();
        populateCaseComboBoxes();
        
        outputArea.setText("✅ Case added successfully: " + caseName);
    }
    
    public void addTrayBtn() {
        String selectedCaseName = caseSelectInput.getValue();
        String trayType = trayTypeInput.getText();
        
        outputArea.setText("Debug: selectedCaseName = " + selectedCaseName + ", trayType = " + trayType);
        
        if (selectedCaseName == null || selectedCaseName.trim().isEmpty()) {
            outputArea.setText("Error: Please select a case");
            return;
        }
        
        if (trayType == null || trayType.trim().isEmpty()) {
            outputArea.setText("Error: Please enter a tray type");
            return;
        }
        
        DisplayCase targetCase = null;
        for (int i = 0; i < store.getDisplayCases().size(); i++) {
            DisplayCase caseItem = store.getDisplayCases().get(i);
            outputArea.setText("Debug: Checking case " + i + ": " + caseItem.getCaseName());
            if (caseItem.getCaseName().equals(selectedCaseName)) {
                targetCase = caseItem;
                outputArea.setText("Debug: Found target case: " + targetCase.getCaseName());
                break;
            }
        }
        
        if (targetCase == null) {
            outputArea.setText("Error: Selected case not found");
            return;
        }
        
        String trayId = "TRAY-" + String.format("%03d", targetCase.getTrays().size() + 1);
        DisplayTray newTray = new DisplayTray(trayId, trayType.trim());
        targetCase.getTrays().add(newTray);
        
        outputArea.setText("Debug: Added tray " + trayId + " to case " + targetCase.getCaseName());
        
        trayTypeInput.clear();
        
        populateTrayComboBoxes();
        
        outputArea.setText("✅ Tray added successfully to " + selectedCaseName);
    }
    
    public void addItemBtn() {
        String selectedCaseName = caseSelectForItemInput.getValue();
        String selectedTrayType = traySelectInput.getValue();
        String itemId = itemIdInput.getText();
        String itemType = itemTypeInput.getText();
        String itemDesc = itemDescInput.getText();
        
        if (selectedCaseName == null || selectedTrayType == null ||
            itemId == null || itemId.trim().isEmpty() ||
            itemType == null || itemType.trim().isEmpty() ||
            itemDesc == null || itemDesc.trim().isEmpty()) {
            outputArea.setText("Error: Please fill all fields");
            return;
        }
        
        DisplayCase targetCase = null;
        DisplayTray targetTray = null;
        
        for (int i = 0; i < store.getDisplayCases().size(); i++) {
            DisplayCase caseItem = store.getDisplayCases().get(i);
            if (caseItem.getCaseName().equals(selectedCaseName)) {
                targetCase = caseItem;
                
                for (int j = 0; j < caseItem.getTrays().size(); j++) {
                    DisplayTray tray = caseItem.getTrays().get(j);
                    if (tray.getTrayType().equals(selectedTrayType)) {
                        targetTray = tray;
                        break;
                    }
                }
                break;
            }
        }
        
        if (targetCase == null) {
            outputArea.setText("Error: Selected case not found");
            return;
        }
        
        if (targetTray == null) {
            outputArea.setText("Error: Selected tray not found");
            return;
        }
        
        JewelleryItem newItem = new JewelleryItem(itemId.trim(), itemType.trim(), itemDesc.trim());
        targetTray.getItems().add(newItem);
        
        itemIdInput.clear();
        itemTypeInput.clear();
        itemDescInput.clear();
        
        outputArea.setText("✅ Item added successfully to " + selectedCaseName + " -> " + selectedTrayType);
    }
    
    private void populateCaseComboBoxes() {
        caseSelectInput.getItems().clear();
        caseSelectForItemInput.getItems().clear();
        
        for (int i = 0; i < store.getDisplayCases().size(); i++) {
            DisplayCase caseItem = store.getDisplayCases().get(i);
            caseSelectInput.getItems().add(caseItem.getCaseName());
            caseSelectForItemInput.getItems().add(caseItem.getCaseName());
        }
    }
    
    private void populateTrayComboBoxes() {
        traySelectInput.getItems().clear();
        
        String selectedCaseName = caseSelectForItemInput.getValue();
        if (selectedCaseName != null) {
            for (int i = 0; i < store.getDisplayCases().size(); i++) {
                DisplayCase caseItem = store.getDisplayCases().get(i);
                if (caseItem.getCaseName().equals(selectedCaseName)) {
                    for (int j = 0; j < caseItem.getTrays().size(); j++) {
                        DisplayTray tray = caseItem.getTrays().get(j);
                        traySelectInput.getItems().add(tray.getTrayType());
                    }
                    break;
                }
            }
        }
    }
}
