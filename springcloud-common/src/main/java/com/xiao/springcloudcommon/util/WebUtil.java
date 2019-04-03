package com.xiao.springcloudcommon.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.xiao.springcloudcommon.exception.CustomBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebUtil {
    private final static Logger log = LoggerFactory.getLogger(WebUtil.class);

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        return map;
    }

    private static TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
    };

    public static Map<String, Object> getJsonParams(String json) {
        StringUtil.purify(json);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(json, typeRef);
            return map;
        } catch (Exception e) {
            throw new CustomBizException(ApiCodeEnum.FAIL, "参数格式错误 : " + json);
        }
    }

    public static void resWriter(HttpServletResponse response, String msg, String fileName) {
        try {
            response.addHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "utf-8"));
            resWriter(response, msg);
        } catch (Exception e) {
            throw new CustomBizException(ApiCodeEnum.FAIL, "resWriter error");
        }
    }

    public static void resWriter(HttpServletResponse response, String msg) {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(msg);
            writer.flush();
        } catch (Exception e) {
            throw new CustomBizException(ApiCodeEnum.FAIL, "resWriter error");
        }
    }

    public static Map<String, String> requestParams2Map(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            map.put(name, valueStr);
        }
        return map;
    }

    public static void setAttachment(HttpServletResponse response, String fileName) throws Exception {
        // 通知浏览器以下载方式打开发送过来的数据，如果文件名是中文，要经过URL编码
        String fn = URLEncoder.encode(fileName, EncodingUtil.UTF8_ENCODING);
        response.setHeader("content-disposition", "attachment;filename=" + fn);
    }

    public static JSONObject getJsonRequestParams(HttpServletRequest request){
        JSONObject jsonObject=null;
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            jsonObject= JSONObject.parseObject(responseStrBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }
}
