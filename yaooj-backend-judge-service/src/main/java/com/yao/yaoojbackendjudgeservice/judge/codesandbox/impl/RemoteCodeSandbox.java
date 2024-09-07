package com.yao.yaoojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yao.yaoojbackendcommon.common.ErrorCode;
import com.yao.yaoojbackendcommon.exception.BusinessException;
import com.yao.yaoojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeDTO;
import com.yao.yaoojbackendmodel.codesandbox.ExecuteCodeVO;

/**
 * ClassName: RemoteCodeSandbox
 * Package: com.yao.yaoojbackendmy.judge.codesandbox.impl
 * Description:
 * 远程代码沙箱示例（实际调用接口的沙箱）
 *
 * @Author Joy_瑶
 * @Create 2024/7/27 17:06
 * @Version 1.0
 */
public class RemoteCodeSandbox implements CodeSandbox {
    //定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER="auth";
    private static final String AUTH_REQUEST_SECRET="secretKey";

    @Override
    public ExecuteCodeVO executeCode(ExecuteCodeDTO executeCodeDTO) {
        System.out.println("调用远程代码沙箱");
        String url="http://192.168.150.3:8090/executeCode";
        String jsonStr = JSONUtil.toJsonStr(executeCodeDTO);
        //发送http请求，调用远程代码沙箱服务
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(jsonStr)
                .execute()
                .body();
        if(StrUtil.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"executeCode remoteSandbox error,message="+responseStr);
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeVO.class);
    }
}
