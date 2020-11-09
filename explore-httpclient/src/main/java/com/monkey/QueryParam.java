package com.monkey;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParam {

    public String vid;
    public Set<String> gameIds;
    public Integer featureId;


}
