package com.nextuple.InventoryApi.model;

public enum ItemCategory {
    TOPWEAR("Top-Wear"),
    BOTTOMWEAR("Bottom-Wear"),
    FOOTWEAR("Foot-Wear"),
    ACCESSORIES("Accessories");
    private final String value;
    ItemCategory(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    
}
