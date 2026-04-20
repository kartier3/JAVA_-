package main.java.models;

import java.io.Serializable;

public class MaterialComponent implements Serializable {

    private String materialName;
    private String description;
    private double weight;
    private double caratValue;

    public MaterialComponent(String materialName, String description, double weight, double caratValue) {
        this.materialName = materialName;
        this.description = description;
        this.weight = weight;
        this.caratValue = caratValue;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCaratValue() {
        return caratValue;
    }

    public void setCaratValue(double caratValue) {
        this.caratValue = caratValue;
    }
    public double getValue() {
        return weight * caratValue;
    }
}
