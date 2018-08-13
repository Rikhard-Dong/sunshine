package com.hfmes.sunshine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "handler" })
public class PrdtType {
    private int prdtTypeId = 0;
    private String title = "";
    private String spec = "";
    private String type = "";
    private String serial = "";
    private float rat = 0;
}
