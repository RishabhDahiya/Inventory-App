package com.nextuple.InventoryApi.model;

public enum LocationType {
    STORE("Store"),
    DC("DC"),
    VENDOR("Vendor");
    private final String value;
    LocationType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
