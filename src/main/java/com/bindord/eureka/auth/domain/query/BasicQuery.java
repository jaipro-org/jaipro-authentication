package com.bindord.eureka.auth.domain.query;


import com.bindord.eureka.auth.domain.query.base.BaseQuery;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
public class BasicQuery extends BaseQuery {

    @Size(max = 70)
    private String name;
}
