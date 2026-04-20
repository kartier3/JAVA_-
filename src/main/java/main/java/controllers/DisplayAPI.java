package main.java.controllers;

import main.java.models.DisplayCase;
import main.java.models.DisplayTray;
import main.java.models.JewelleryItem;
import main.java.utils.CustomLinkedList;

public class DisplayAPI {
    public String listAllStock(CustomLinkedList<DisplayCase> displayCases) {
        if (displayCases.size() == 0) return "Case is empty!\n";
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
            result.append("\n");
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

    public String findItemsByType(CustomLinkedList<DisplayCase> displayCases, String itemType) {
        StringBuilder result = new StringBuilder("--- ITEMS OF TYPE: " + itemType + " ---\n");
        boolean found = false;

        for (int i = 0; i < displayCases.size(); i++) {
            DisplayCase displayCase = displayCases.get(i);
            for (int j = 0; j < displayCase.getTrays().size(); j++) {
                DisplayTray tray = displayCase.getTrays().get(j);
                for (int t = 0; t < tray.getItems().size(); t++) {
                    JewelleryItem item = tray.getItems().get(t);
                    if (item.getType().equalsIgnoreCase(itemType)) {
                        result.append("Found in Case ").append(displayCase.getCaseId())
                                .append(", Tray ").append(tray.getTrayId())
                                .append(": ").append(item.getDescription())
                                .append(" (ID: ").append(item.getItemId()).append(")\n");
                        found = true;
                    }
                }
            }
        }

        if (!found) {
            result.append("No items of type '").append(itemType).append("' found.\n");
        }
        
        return result.toString();
    }
}
