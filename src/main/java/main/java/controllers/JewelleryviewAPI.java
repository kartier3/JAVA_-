package main.java.controllers;

import main.java.models.*;
import main.java.utils.CustomLinkedList;

public class JewelleryviewAPI {

    public String listAllStock(CustomLinkedList<DisplayCase> displayCases) {
        if (displayCases.size() == 0) return "Case is empty!";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < displayCases.size(); i++) {
            DisplayCase displayCase = displayCases.get(i);
            result.append("Display Case: ").append(displayCase.getCaseId())
                    .append(" - ").append(displayCase.getCaseName())
                    .append("\n");
            result.append(processTraysForList(displayCase.getTrays()));
            result.append("\n");
        }
        return result.toString();
    }
    
    public double getValue(CustomLinkedList<DisplayCase> displayCases) {
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

    private String processTraysForList(CustomLinkedList<DisplayTray> trays) {
        if (trays.size() == 0) return "No trays found!\n";
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < trays.size(); j++) {
            DisplayTray tray = trays.get(j);
            result.append("Tray: ").append(tray.getTrayId())
                    .append(" (").append(tray.getTrayType())
                    .append(")\n");
            result.append(processItemsForList(tray.getItems()));
        }
        return result.toString();
    }

    private String processItemsForList(CustomLinkedList<JewelleryItem> items) {
        if (items.size() == 0) return "No items found!\n";
        StringBuilder result = new StringBuilder();
        for (int t = 0; t < items.size(); t++) {
            JewelleryItem item = items.get(t);
            result.append(" | Item: ")
                    .append(item.getDescription())
                    .append(" | Type: ").append(item.getType())
                    .append(" | ID: ").append(item.getItemId())
                    .append("\n");
            
            CustomLinkedList<MaterialComponent> materials = item.getMaterials();
            if (materials.size() > 0) {
                double itemTotalValue = 0;
                result.append(" |   Materials (" + materials.size() + "):\n");
                for (int m = 0; m < materials.size(); m++) {
                    MaterialComponent mat = materials.get(m);
                    double matValue = mat.getValue();
                    itemTotalValue += matValue;
                    result.append(" |     - ").append(mat.getMaterialName())
                          .append(" (").append(mat.getDescription()).append(")")
                          .append(" | Weight: ").append(mat.getWeight())
                          .append(" | Value: $").append(String.format("%.2f", matValue))
                          .append("\n");
                }
                result.append(" |   Item Total Value: $").append(String.format("%.2f", itemTotalValue)).append("\n");
            } else {
                result.append(" |   (No materials)\n");
            }
        }
        return result.toString();
    }

    public String displayCaseDetails(CustomLinkedList<DisplayCase> displayCases) {
        if (displayCases.size() == 0) return "No display cases found!\n";

        StringBuilder result = new StringBuilder("--- DISPLAY CASE DETAILS ---\n");
        
        for (int i = 0; i < displayCases.size(); i++) {
            DisplayCase displayCase = displayCases.get(i);
            result.append("Case ID: ").append(displayCase.getCaseId()).append("\n");
            result.append("Case Name: ").append(displayCase.getCaseName()).append("\n");
            result.append("Location: ").append(displayCase.getLocation()).append("\n");
            result.append("Number of Trays: ").append(displayCase.getTrays().size()).append("\n");
            
            double totalItems = calculateTotalItems(displayCase.getTrays());
            result.append("Total Items: ").append(totalItems).append("\n");
            result.append("_____________________________________\n");
        }
        return result.toString();
    }

    private double calculateTotalItems(CustomLinkedList<DisplayTray> trays) {
        double totalItems = 0;
        for (int j = 0; j < trays.size(); j++) {
            DisplayTray tray = trays.get(j);
            totalItems += tray.getItems().size();
        }
        return totalItems;
    }
}
