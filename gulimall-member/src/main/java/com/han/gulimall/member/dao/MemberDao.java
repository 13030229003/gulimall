package com.han.gulimall.member.dao;

import com.han.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 11:50:43
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
