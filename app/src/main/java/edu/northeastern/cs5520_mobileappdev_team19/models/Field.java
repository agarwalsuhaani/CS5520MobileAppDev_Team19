package edu.northeastern.cs5520_mobileappdev_team19.models;

public class Field {
    private final FieldType fieldType;
    private final String fieldValue;

    public Field(FieldType fieldType, String fieldValue) {
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
    }


    public FieldType getFieldType() {
        return fieldType;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
