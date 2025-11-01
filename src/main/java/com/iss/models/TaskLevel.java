package com.iss.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskLevel {
	HIGH,
	MEDIUM,
	LOW;
	
	@JsonCreator
    public static TaskLevel fromString(String value) {
        if (value == null) {
            return null;
        }
        for (TaskLevel level : TaskLevel.values()) {
            if (level.name().equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid TaskLevel: " + value);
	}
}
