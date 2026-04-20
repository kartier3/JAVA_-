package main.java.models;

import main.java.utils.CustomLinkedList;
import java.io.Serializable;

public class JewelleryItem implements Serializable {

    private String itemId;
    private String type;    
    private String description;
    private CustomLinkedList<MaterialComponent> materials;

    public JewelleryItem(String itemId, String type, String description) {
        this.itemId = itemId;
        this.type = type;
        this.description = description;
        this.materials = new CustomLinkedList<>();
    }
    
    public JewelleryItem(String itemId, String type, String description, CustomLinkedList<MaterialComponent> materials) {
        this.itemId = itemId;
        this.type = type;
        this.description = description;
        this.materials = materials;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomLinkedList<MaterialComponent> getMaterials() {
        return materials;
    }
}
