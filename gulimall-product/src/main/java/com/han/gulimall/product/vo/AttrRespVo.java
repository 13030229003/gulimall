package com.han.gulimall.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xsh
 */
@Data
public class AttrRespVo extends AttrVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String catelogName;
    private String groupName;

    private Long[] catelogPath;
}
