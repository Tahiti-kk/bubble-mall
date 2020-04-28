package org.jerrylee.bubblemall.error;

/**
 * @author JerryLee
 * @date 2020/4/23
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
