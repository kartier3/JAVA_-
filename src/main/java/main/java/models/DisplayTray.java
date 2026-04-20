package main.java.models;


import main.java.utils.CustomLinkedList;
import java.io.Serializable;

public class DisplayTray implements Serializable {

    private String trayId;
    private String trayType;
    private CustomLinkedList<JewelleryItem> items;

    public DisplayTray(String trayId, String trayType) {
        this.trayId = trayId;
        this.trayType = trayType;
        this.items = new CustomLinkedList<>();
    }

    public String getTrayId() {
        return trayId;
    }

    public void setTrayId(String trayId) {
        this.trayId = trayId;
    }

    public String getTrayType() {
        return trayType;
    }

    public void setTrayType(String trayType) {
        this.trayType = trayType;
    }

    public CustomLinkedList<JewelleryItem> getItems() {
        return items;
    }
}
