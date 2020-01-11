package com.companioncar.dal.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;

public class BaseQueryParam {

    //页码
    private int pageNum = 1;

    //显示数量
    private int pageSize = 10;

    //关键字（待使用）
    @ApiParam(hidden = true)
    private String keyword;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
