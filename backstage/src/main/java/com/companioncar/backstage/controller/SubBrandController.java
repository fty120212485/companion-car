package com.companioncar.backstage.controller;

import com.companioncar.backstage.service.SubBrandService;
import com.companioncar.dal.model.SubBrand;
import com.companioncar.dal.msg.ResponseCode;
import com.companioncar.dal.msg.ReturnMsgUtil;
import com.companioncar.dal.vo.BaseQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value = "subBrandController", tags = "子品牌管理")
@Controller
@RequestMapping("/back/subBrand")
public class SubBrandController {

    @Autowired
    private SubBrandService subBrandService;

    @RequestMapping(value = "detail", method = RequestMethod.GET, name = "子品牌详情")
    @ApiOperation(value = "子品牌详情")
    @ResponseBody
    public ReturnMsgUtil detail(String subBrandId){
        SubBrand subBrand = subBrandService.detail(subBrandId);
        if(subBrand == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "子品牌不存在");
        }
        return ReturnMsgUtil.success(subBrand);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET, name = "删除子品牌")
    @ApiOperation(value = "删除子品牌")
    @ResponseBody
    public ReturnMsgUtil delete(String subBrandId){
        int result = subBrandService.delete(subBrandId);
        if(result == 0){
            return ReturnMsgUtil.fail(ResponseCode.DEL_FAIL, "删除失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "修改子品牌")
    @ApiOperation(value = "修改子品牌")
    @ResponseBody
    public ReturnMsgUtil update(SubBrand subBrand){
        int result = subBrandService.update(subBrand);
        if(result == 0){
            return ReturnMsgUtil.fail(ResponseCode.UPD_FAIL, "修改失败");
        }
        return ReturnMsgUtil.success(null);
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST, name = "新增子品牌")
    @ApiOperation(value = "新增子品牌")
    @ResponseBody
    public ReturnMsgUtil insert(SubBrand subBrand){
        int result = subBrandService.insert(subBrand);
        if(result == 0){
            return ReturnMsgUtil.fail(ResponseCode.UPD_FAIL, "新增失败");
        }
        return ReturnMsgUtil.success(subBrand);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST, name = "子品牌列表")
    @ApiOperation(value = "子品牌列表")
    @ResponseBody
    public ReturnMsgUtil<List> list(SubBrand subBrand, BaseQueryParam baseQueryParam){
        PageHelper.startPage(baseQueryParam.getPageNum(), baseQueryParam.getPageSize());
        List<SubBrand> list = subBrandService.list(subBrand);
        if(list == null){
            return ReturnMsgUtil.fail(ResponseCode.NOT_FOUND, "找不到数据");
        }
        return ReturnMsgUtil.success(new PageInfo<>(list));
    }
}
