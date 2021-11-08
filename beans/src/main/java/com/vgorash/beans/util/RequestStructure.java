package com.vgorash.beans.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class RequestStructure implements Serializable {
    private Map<String, String[]> params;
    private Long id;
    private int responseCode;
    private String message;
    private String requestBody;
}
