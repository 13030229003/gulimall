package com.han.gulimall.member.controller;

import com.han.gulimall.common.exception.BizCodeEnum;
import com.han.gulimall.common.utils.PageUtils;
import com.han.gulimall.common.utils.R;
import com.han.gulimall.member.config.UserProperties;
import com.han.gulimall.member.entity.MemberEntity;
import com.han.gulimall.member.exception.PhoneException;
import com.han.gulimall.member.exception.UsernameException;
import com.han.gulimall.member.feign.CouponFeignService;
import com.han.gulimall.member.service.MemberService;
import com.han.gulimall.member.vo.MemberUserLoginVo;
import com.han.gulimall.member.vo.MemberUserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 会员
 *
 * @author xushuhan
 * @email 10086@gmail.com
 * @date 2022-10-11 11:50:43
 */
@RestController
@RequestMapping("/member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private UserProperties userProperties;

    @PostMapping(value = "/register")
    public R register(@RequestBody MemberUserRegisterVo vo) {

        try {
            memberService.register(vo);
        } catch (PhoneException e) {
            return R.error(BizCodeEnum.PHONE_EXISTS_EXCEPTION.getCode(),BizCodeEnum.PHONE_EXISTS_EXCEPTION.getMsg());
        } catch (UsernameException e) {
            return R.error(BizCodeEnum.USER_EXISTS_EXCEPTION.getCode(),BizCodeEnum.USER_EXISTS_EXCEPTION.getMsg());
        }

        return R.ok();
    }


    @PostMapping(value = "/login")
    public R login(@RequestBody MemberUserLoginVo vo) {

        MemberEntity memberEntity = memberService.login(vo);

        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGIN_ACCOUNT_PASSWORD_INVALID.getCode(),BizCodeEnum.LOGIN_ACCOUNT_PASSWORD_INVALID.getMsg());
        }
    }





//    @RequestMapping("/openFeignTest")
//    public R testOpenFeign() {
//
//        R r = couponFeignService.testOpenFeign();
//
//        return R.ok().put("coupon",r.get("coupon")).put("user",userProperties);
//
//    }




    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
