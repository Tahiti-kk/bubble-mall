package org.jerrylee.bubblemall.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javafx.beans.binding.ObjectExpression;

/**
 * @author JerryLee
 * @date 2020/4/23
 * 通用返回类型
 */
@ApiModel(value = "通用返回类型")
public class CommonReturnType {
    /**
     * 对应请求的返回处理结果
     * "success" or "fail"
     */
    @ApiModelProperty(name = "status", value = "处理状态：\"success\" or \"fail\"")
    private String status;

    /**
     * success时data为json数据
     * fail时data为通用的错误码
     */
    @ApiModelProperty(name = "data", value = "返回数据：success时为json数据，fail时为错误码")
    private Object data;

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
