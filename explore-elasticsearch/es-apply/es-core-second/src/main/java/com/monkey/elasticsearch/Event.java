package com.monkey.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String gameId;
    private Long receiveTimestamp;
    private Integer gameExposure_time;
    private String app_version;
}
