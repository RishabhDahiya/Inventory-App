package com.nextuple.InventoryApi.model;
public enum BooleanValue
{
    TRUE("true"),
    FALSE("false");
    private final String value;
    BooleanValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
