package com.iss.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SubmissionType {
	IMAGE_UPLOAD,  
    URL_LINK,      
    TEXT_BLOCK;
	
	@JsonCreator
    public static SubmissionType fromString(String value) {
        if (value == null) {
            return null;
        }
        for (SubmissionType type : SubmissionType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SubmissionType: " + value);
    }
}
