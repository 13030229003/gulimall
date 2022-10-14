package com.han.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.member.entity.MemberCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 11:50:43
 */
public interface MemberCollectSubjectService extends IService<MemberCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

