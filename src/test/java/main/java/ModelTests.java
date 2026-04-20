package main.java;

import main.java.models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelTests {



    @Test
    public void testDisplayCaseConstructor() {
        DisplayCase case1 = new DisplayCase("C001", "Front Case", "Main Hall");
        assertEquals("C001", case1.getCaseId());
        assertEquals("Front Case", case1.getCaseName());
        assertEquals("Main Hall", case1.getLocation());
        assertNotNull(case1.getTrays());
    }

    @Test
    public void testDisplayTrayConstructor() {
        DisplayTray tray = new DisplayTray("T001", "Rings");
        assertEquals("T001", tray.getTrayId());
        assertEquals("Rings", tray.getTrayType());
        assertNotNull(tray.getItems());
    }

    @Test
    public void testJewelleryItemConstructor() {
        JewelleryItem item = new JewelleryItem("J001", "Ring", "Gold engagement ring");
        assertEquals("J001", item.getItemId());
        assertEquals("Ring", item.getType());
        assertEquals("Gold engagement ring", item.getDescription());
        assertNotNull(item.getMaterials());
    }

    @Test
    public void testMaterialComponentConstructor() {
        MaterialComponent material = new MaterialComponent("Gold", "18K gold", 5.0, 24.0);
        assertEquals("Gold", material.getMaterialName());
        assertEquals("18K gold", material.getDescription());
        assertEquals(5.0, material.getWeight());
        assertEquals(24.0, material.getCaratValue());
    }

    

    @Test
    public void testDisplayCaseSetters() {
        DisplayCase case1 = new DisplayCase("ID1", "Name1", "Loc1");
        case1.setCaseId("ID2");
        case1.setCaseName("Name2");
        case1.setLocation("Loc2");
        assertEquals("ID2", case1.getCaseId());
        assertEquals("Name2", case1.getCaseName());
        assertEquals("Loc2", case1.getLocation());
    }

    @Test
    public void testDisplayTraySetters() {
        DisplayTray tray = new DisplayTray("T1", "Type1");
        tray.setTrayId("T2");
        tray.setTrayType("Type2");
        assertEquals("T2", tray.getTrayId());
        assertEquals("Type2", tray.getTrayType());
    }

    @Test
    public void testMaterialComponentSetters() {
        var material = new MaterialComponent("Silver", "Sterling silver", 10.0, 12.0);
        material.setMaterialName("Platinum");
        material.setDescription("Pure platinum");
        material.setWeight(8.0);
        material.setCaratValue(18.0);
        assertEquals("Platinum", material.getMaterialName());
        assertEquals("Pure platinum", material.getDescription());
        assertEquals(8.0, material.getWeight());
        assertEquals(18.0, material.getCaratValue());
    }

    @Test
    public void testNestedStructure() {
        JewelleryStore store = new JewelleryStore("Test Store", "Test Address");
        DisplayCase case1 = new DisplayCase("C1", "Case 1", "Floor");
        DisplayTray tray1 = new DisplayTray("T1", "Necklaces");
        JewelleryItem item1 = new JewelleryItem("J1", "Necklace", "Pearl necklace");
        MaterialComponent pearl = new MaterialComponent("Pearl", "White pearl", 2.0, 8.0);

        item1.getMaterials().add(pearl);
        tray1.getItems().add(item1);
        case1.getTrays().add(tray1);
        store.getDisplayCases().add(case1);

        assertEquals(1, store.getDisplayCases().size());
        assertEquals(1, case1.getTrays().size());
        assertEquals(1, tray1.getItems().size());
        assertEquals(1, item1.getMaterials().size());
        assertEquals("Pearl", pearl.getMaterialName());
    }
}
