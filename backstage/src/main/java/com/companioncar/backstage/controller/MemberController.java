package com.companioncar.backstage.controller;

import com.companioncar.backstage.service.MemberService;
import com.companioncar.dal.model.Member;
import com.companioncar.dal.msg.ResponseCode;
import com.companioncar.dal.msg.ReturnMsgUtil;
import com.companioncar.dal.util.MD5Util;
import com.companioncar.dal.vo.BaseQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Api(value="memberController",tags={"会员操作接口"})
@Controller
@RequestMapping("/back/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "detail", method = RequestMethod.GET, name = "会员详情")
    @ApiOperation(value = "会员详情")
    @ResponseBody
    public ReturnMsgUtil<Member> detail(String memberId){
        Member user = memberService.selectBy(memberId);
        if(user == null) {
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "该会员不存在！");
        }
        return ReturnMsgUtil.success(user);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET, name = "会员列表")
    @ApiOperation(value = "会员列表")
    @ResponseBody
    public ReturnMsgUtil<List> list(Member member, BaseQueryParam baseQueryParam){
        PageHelper.startPage(baseQueryParam.getPageNum(),baseQueryParam.getPageSize());
        List<Member> list = memberService.list(member);
        if(list == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "找不到数据");
        }
        return ReturnMsgUtil.success(new PageInfo<>(list));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "修改会员")
    @ApiOperation(value = "修改会员")
    @ResponseBody
    public ReturnMsgUtil<Member> update(Member member){
        if(StringUtil.isEmpty(member.getMemberId())){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        member.setUpdateTime(new Date());
        int result = memberService.update(member);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "更新失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET, name = "删除会员")
    @ApiOperation(value = "删除会员")
    @ResponseBody
    public ReturnMsgUtil delete(String memberId){
        if(StringUtil.isEmpty(memberId)){
            return ReturnMsgUtil.fail(2, "缺少id参数");
        }
        int result = memberService.delete(memberId);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "删除失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST, name = "新增会员")
    @ApiOperation(value = "新增会员")
    @ResponseBody
    public ReturnMsgUtil insert(Member member){
        Member check_username = new Member();
        String username = member.getUsername();
        if(StringUtil.isEmpty(username)){
            return ReturnMsgUtil.fail(2,"缺少username参数");
        }
        check_username.setUsername(member.getUsername());
        List<Member> list = memberService.list(check_username);
        if(list != null){
            return ReturnMsgUtil.fail(ResponseCode.FOUND,"用户名已存在");
        }
        String pwd = member.getPassword();
        member.setPassword(MD5Util.getMd5(pwd, null));
        member.setCreateTime(new Date());
        member.setUpdateTime(new Date());
        int result = memberService.insert(member);
        if(result == 0){
            return ReturnMsgUtil.fail(2, "新增失败");
        }
        return ReturnMsgUtil.success(member);
    }
}
