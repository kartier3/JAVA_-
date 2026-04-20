package main.java.models;

import main.java.utils.CustomLinkedList;
import java.io.Serializable;

public class DisplayCase implements Serializable {

    private String caseId;
    private String caseName;
    private String location;
    private CustomLinkedList<DisplayTray> trays;

    public DisplayCase(String caseId, String caseName, String location) {
        this.caseId = caseId;
        this.caseName = caseName;
        this.location = location;
        this.trays = new CustomLinkedList<>();
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CustomLinkedList<DisplayTray> getTrays() {
        return trays;
    }
}
