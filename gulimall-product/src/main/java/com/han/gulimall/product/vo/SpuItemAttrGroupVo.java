package com.han.gulimall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoxinjia
 */
@ToString
@Data
public class SpuItemAttrGroupVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String groupName;
    private List<Attr> attrs;
}
