package lwq.java.web.ssm_swagger.config;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @ Created by liwenqiang on 2017/5/3 0003.
 * @ Description:
 */
@JsonPropertyOrder(value = {"status", "msg", "data"})
public class Result {
    //状态,成功：1，失败：0
    private int status;
    //消息
    private String msg;
    //数据
    private Object data;

    public Result() {
    }

    public Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"Result\":{"
                + "\"status\":"
                + status
                + ",\"msg\":\""
                + msg + '\"'
                + ",\"data\":"
                + data
                + "}}";

    }
}
