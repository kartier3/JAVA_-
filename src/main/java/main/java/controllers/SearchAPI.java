package main.java.controllers;

import main.java.models.DisplayCase;
import main.java.models.DisplayTray;
import main.java.models.JewelleryItem;
import main.java.models.MaterialComponent;
import main.java.utils.CustomLinkedList;

public class SearchAPI {

    
    public String smartAddItem(CustomLinkedList<DisplayCase> displayCases, JewelleryItem item) {

        if (displayCases.size() == 0) {
            DisplayCase firstCase = new DisplayCase("CASE-001", "Main Display Case", "Front Store");
            DisplayTray firstTray = new DisplayTray("TRAY-A1", "Standard");

            firstTray.getItems().add(item);
            firstCase.getTrays().add(firstTray);
            displayCases.add(firstCase);

            return "Smart Add: Store was empty! Built Case 1, Tray 1-1, and added item.";
        }

        String newDescription = item.getDescription().toLowerCase();
        String keyword = "";
        CustomLinkedList<String> keywords = new CustomLinkedList<>();
        keywords.add("diamond");
        keywords.add("gold");
        keywords.add("silver");
        keywords.add("bronze");
        keywords.add("copper");
        keywords.add("nickel");
        keywords.add("pearl");
        keywords.add("emerald");

        for (int i = 0; i < keywords.size(); i++) {
            String k = keywords.get(i);
            if (newDescription.contains(k)) {
                keyword = k;
                break;
            }
        }

        if (!keyword.isEmpty()) {
            for (int i = 0; i < displayCases.size(); i++) {
                DisplayCase displayCase = displayCases.get(i);
                for (int j = 0; j < displayCase.getTrays().size(); j++) {
                    DisplayTray tray = displayCase.getTrays().get(j);
                    for (int t = 0; t < tray.getItems().size(); t++) {
                        if (tray.getItems().get(t).getDescription().toLowerCase().contains(keyword)) {
                            tray.getItems().add(item);
                            return "Smart Add: Placed with other " + keyword + " items in [Case " + displayCase.getCaseId() + " | Tray " + tray.getTrayId() + "]";
                        }
                    }
                }
            }
        }

        for (int i = 0; i < displayCases.size(); i++) {
            DisplayCase displayCase = displayCases.get(i);
            for (int j = 0; j < displayCase.getTrays().size(); j++) {
                DisplayTray tray = displayCase.getTrays().get(j);
                if (tray.getItems().size() == 0) {
                    tray.getItems().add(item);
                    return "Smart Add: Placed in empty tray " + tray.getTrayId() + " | Case " + displayCase.getCaseId();
                }
            }
        }

        DisplayCase first = displayCases.get(0);
        if (first.getTrays().size() > 0) {
            DisplayTray firstTray = first.getTrays().get(0);
            firstTray.getItems().add(item);
            return "Smart Add: Store is crowded! Defaulted to case " + first.getCaseId() + " | Tray " + firstTray.getTrayId();
        }

        return "ERROR: COULD NOT FIND!!!!!";
    }

    private CustomLinkedList<MaterialComponent> materialsTable = new CustomLinkedList<>();

    public void addMaterialToTable(String materialName, String description, double weight, double caratValue) {
        MaterialComponent material = new MaterialComponent(materialName, description, weight, caratValue);
        materialsTable.add(material);
    }

    public String listMaterialsTable() {
        if (materialsTable.size() == 0) {
            return "Materials table is empty!";
        }
        StringBuilder result = new StringBuilder("Materials Table:\n");
        for (int i = 0; i < materialsTable.size(); i++) {
            MaterialComponent m = materialsTable.get(i);
            result.append("[").append(i).append("] ")
                  .append(m.getMaterialName())
                  .append(" (").append(m.getDescription()).append(")")
                  .append(" | Weight: ").append(m.getWeight())
                  .append(" | Value: $").append(String.format("%.2f", m.getValue()))
                  .append("\n");
        }
        return result.toString();
    }

    public String searchByKeywords(CustomLinkedList<DisplayCase> displayCases, String[] searchWords) {
        if (searchWords == null || searchWords.length == 0) {
            return "No search words provided!";
        }
        
        StringBuilder result = new StringBuilder("Search Results:\n");
        boolean foundAny = false;
        
        for (int i = 0; i < displayCases.size(); i++) {
            DisplayCase displayCase = displayCases.get(i);
            for (int j = 0; j < displayCase.getTrays().size(); j++) {
                DisplayTray tray = displayCase.getTrays().get(j);
                for (int k = 0; k < tray.getItems().size(); k++) {
                    JewelleryItem item = tray.getItems().get(k);
                    String itemDesc = item.getDescription().toLowerCase();
                    String itemType = item.getType().toLowerCase();
                    String itemId = item.getItemId().toLowerCase();
                    
                    for (String word : searchWords) {
                        String searchWord = word.toLowerCase().trim();
                        if (searchWord.isEmpty()) continue;
                        
                        if (itemDesc.contains(searchWord) || 
                            itemType.contains(searchWord) || 
                            itemId.contains(searchWord) ||
                            hasMaterialWithKeyword(item, searchWord)) {
                            
                            foundAny = true;
                            result.append("[Case ").append(displayCase.getCaseId())
                                  .append(" | Tray ").append(tray.getTrayId()).append("]\n")
                                  .append("  Item: ").append(item.getItemId())
                                  .append(" | ").append(item.getType())
                                  .append(" | ").append(item.getDescription())
                                  .append("\n  Matched keyword: ").append(word).append("\n\n");
                            break;
                        }
                    }
                }
            }
        }
        
        if (!foundAny) {
            return "No items found matching the search words!";
        }
        return result.toString();
    }
    
    private boolean hasMaterialWithKeyword(JewelleryItem item, String keyword) {
        CustomLinkedList<MaterialComponent> materials = item.getMaterials();
        for (int i = 0; i < materials.size(); i++) {
            MaterialComponent mat = materials.get(i);
            if (mat.getMaterialName().toLowerCase().contains(keyword) ||
                mat.getDescription().toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}