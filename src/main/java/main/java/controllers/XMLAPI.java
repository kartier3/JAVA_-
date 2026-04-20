package main.java.controllers;

import main.java.models.DisplayCase;
import main.java.models.DisplayTray;
import main.java.models.JewelleryItem;
import main.java.models.MaterialComponent;
import main.java.utils.CustomLinkedList;
import java.io.*;

public class XMLAPI {
    
    public void save(CustomLinkedList<DisplayCase> displayCases) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("jewstore.xml"))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<JewelleryStore>");
            
            for (int i = 0; i < displayCases.size(); i++) {
                DisplayCase displayCase = displayCases.get(i);
                saveDisplayCase(writer, displayCase);
            }
            
            writer.println("</JewelleryStore>");
        }
    }
    
    private void saveDisplayCase(PrintWriter writer, DisplayCase displayCase) {
        writer.println("  <DisplayCase>");
        writer.println("    <caseId>" + escapeXml(displayCase.getCaseId()) + "</caseId>");
        writer.println("    <caseName>" + escapeXml(displayCase.getCaseName()) + "</caseName>");
        writer.println("    <location>" + escapeXml(displayCase.getLocation()) + "</location>");
        writer.println("    <trays>");
        
        CustomLinkedList<DisplayTray> trays = displayCase.getTrays();
        for (int i = 0; i < trays.size(); i++) {
            DisplayTray tray = trays.get(i);
            saveDisplayTray(writer, tray);
        }
        
        writer.println("    </trays>");
        writer.println("  </DisplayCase>");
    }
    
    private void saveDisplayTray(PrintWriter writer, DisplayTray tray) {
        writer.println("      <DisplayTray>");
        writer.println("        <trayId>" + escapeXml(tray.getTrayId()) + "</trayId>");
        writer.println("        <trayType>" + escapeXml(tray.getTrayType()) + "</trayType>");
        writer.println("        <items>");
        
        CustomLinkedList<JewelleryItem> items = tray.getItems();
        for (int i = 0; i < items.size(); i++) {
            JewelleryItem item = items.get(i);
            saveJewelleryItem(writer, item);
        }
        
        writer.println("        </items>");
        writer.println("      </DisplayTray>");
    }
    
    private void saveJewelleryItem(PrintWriter writer, JewelleryItem item) {
        writer.println("          <JewelleryItem>");
        writer.println("            <itemId>" + escapeXml(item.getItemId()) + "</itemId>");
        writer.println("            <type>" + escapeXml(item.getType()) + "</type>");
        writer.println("            <description>" + escapeXml(item.getDescription()) + "</description>");
        writer.println("            <materials>");
        
        CustomLinkedList<MaterialComponent> materials = item.getMaterials();
        for (int i = 0; i < materials.size(); i++) {
            MaterialComponent material = materials.get(i);
            saveMaterialComponent(writer, material);
        }
        
        writer.println("            </materials>");
        writer.println("          </JewelleryItem>");
    }
    
    private void saveMaterialComponent(PrintWriter writer, MaterialComponent material) {
        writer.println("              <MaterialComponent>");
        writer.println("                <materialName>" + escapeXml(material.getMaterialName()) + "</materialName>");
        writer.println("                <description>" + escapeXml(material.getDescription()) + "</description>");
        writer.println("                <weight>" + material.getWeight() + "</weight>");
        writer.println("                <caratValue>" + material.getCaratValue() + "</caratValue>");
        writer.println("              </MaterialComponent>");
    }
    
    private String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }

    public CustomLinkedList<DisplayCase> load() throws IOException {
        CustomLinkedList<DisplayCase> displayCases = new CustomLinkedList<>();
        File file = new File("jewstore.xml");
        
        if (!file.exists()) {
            return displayCases;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            DisplayCase currentCase = null;
            DisplayTray currentTray = null;
            JewelleryItem currentItem = null;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                if (line.equals("<DisplayCase>")) {
                    currentCase = new DisplayCase("", "", "");
                } else if (line.equals("</DisplayCase>")) {
                    if (currentCase != null) {
                        displayCases.add(currentCase);
                        currentCase = null;
                    }
                } else if (line.startsWith("<caseId>") && line.endsWith("</caseId>") && currentCase != null) {
                    currentCase.setCaseId(extractTagContent(line, "caseId"));
                } else if (line.startsWith("<caseName>") && line.endsWith("</caseName>") && currentCase != null) {
                    currentCase.setCaseName(extractTagContent(line, "caseName"));
                } else if (line.startsWith("<location>") && line.endsWith("</location>") && currentCase != null) {
                    currentCase.setLocation(extractTagContent(line, "location"));
                } else if (line.equals("<DisplayTray>") && currentCase != null) {
                    currentTray = new DisplayTray("", "");
                } else if (line.equals("</DisplayTray>")) {
                    if (currentTray != null && currentCase != null) {
                        currentCase.getTrays().add(currentTray);
                        currentTray = null;
                    }
                } else if (line.startsWith("<trayId>") && line.endsWith("</trayId>") && currentTray != null) {
                    currentTray.setTrayId(extractTagContent(line, "trayId"));
                } else if (line.startsWith("<trayType>") && line.endsWith("</trayType>") && currentTray != null) {
                    currentTray.setTrayType(extractTagContent(line, "trayType"));
                } else if (line.equals("<JewelleryItem>") && currentTray != null) {
                    currentItem = new JewelleryItem("", "", "");
                } else if (line.equals("</JewelleryItem>")) {
                    if (currentItem != null && currentTray != null) {
                        currentTray.getItems().add(currentItem);
                        currentItem = null;
                    }
                } else if (line.startsWith("<itemId>") && line.endsWith("</itemId>") && currentItem != null) {
                    currentItem.setItemId(extractTagContent(line, "itemId"));
                } else if (line.startsWith("<type>") && line.endsWith("</type>") && currentItem != null) {
                    currentItem.setType(extractTagContent(line, "type"));
                } else if (line.startsWith("<description>") && line.endsWith("</description>") && currentItem != null) {
                    currentItem.setDescription(extractTagContent(line, "description"));
                } else if (line.equals("<materials>")) {
                } else if (line.equals("</materials>")) {
                } else if (line.equals("<MaterialComponent>") && currentItem != null) {
                    MaterialComponent material = new MaterialComponent("", "", 0.0, 0.0);
                    currentItem.getMaterials().add(material);
                } else if (line.equals("</MaterialComponent>")) {
                } else if (line.startsWith("<materialName>") && line.endsWith("</materialName>") && currentItem != null) {
                    if (currentItem.getMaterials().size() > 0) {
                        MaterialComponent currentMaterial = currentItem.getMaterials().get(currentItem.getMaterials().size() - 1);
                        currentMaterial.setMaterialName(extractTagContent(line, "materialName"));
                    }
                } else if (line.startsWith("<description>") && line.endsWith("</description>") && currentItem != null) {
                    if (currentItem.getMaterials().size() > 0) {
                        MaterialComponent currentMaterial = currentItem.getMaterials().get(currentItem.getMaterials().size() - 1);
                        currentMaterial.setDescription(extractTagContent(line, "description"));
                    }
                } else if (line.startsWith("<weight>") && line.endsWith("</weight>") && currentItem != null) {
                    if (currentItem.getMaterials().size() > 0) {
                        MaterialComponent currentMaterial = currentItem.getMaterials().get(currentItem.getMaterials().size() - 1);
                        try {
                            double weight = Double.parseDouble(extractTagContent(line, "weight"));
                            currentMaterial.setWeight(weight);
                        } catch (NumberFormatException e) {
                        }
                    }
                } else if (line.startsWith("<caratValue>") && line.endsWith("</caratValue>") && currentItem != null) {
                    if (currentItem.getMaterials().size() > 0) {
                        MaterialComponent currentMaterial = currentItem.getMaterials().get(currentItem.getMaterials().size() - 1);
                        try {
                            double caratValue = Double.parseDouble(extractTagContent(line, "caratValue"));
                            currentMaterial.setCaratValue(caratValue);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }
        
        return displayCases;
    }
    
    private String extractTagContent(String line, String tagName) {
        int startIndex = line.indexOf("<" + tagName + ">") + tagName.length() + 2;
        int endIndex = line.indexOf("</" + tagName + ">");
        return line.substring(startIndex, endIndex);
    }
}
