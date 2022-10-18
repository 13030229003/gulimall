package com.han.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @author xsh
 */
@Data
public class MemberPrice {
    private Long id;
    private String name;
    private BigDecimal price;
}
