package com.han.gulimall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.han.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoxinjia
 */
@Data
public class AttrGroupWithAttrsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;


    private List<AttrEntity> attrs;
}
