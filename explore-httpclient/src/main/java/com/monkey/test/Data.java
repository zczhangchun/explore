package com.monkey.test;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@lombok.Data
public class Data {
    private String gameId;
    private Integer pos;
    private String passthrough;
}
