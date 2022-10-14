package com.han.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 11:50:43
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

