package com.han.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * 会员收藏的商品
 *
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 11:50:43
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

