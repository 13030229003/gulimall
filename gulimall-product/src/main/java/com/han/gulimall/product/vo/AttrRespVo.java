package com.han.gulimall.product.vo;

import lombok.Data;

/**
 * @author xsh
 */
@Data
public class AttrRespVo extends AttrVo{

    private String catelogName;
    private String groupName;

    private Long[] catelogPath;
}
