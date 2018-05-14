package com.sinosteel.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosteel.activiti.ConsignActiviti;
import com.sinosteel.domain.Consign;
import com.sinosteel.domain.User;
import com.sinosteel.framework.config.system.SystemConfig;
import com.sinosteel.framework.core.web.Request;
import com.sun.java.swing.action.OpenAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LBW
 */
@Service
public class ConsignActivitiService
{

    @Autowired
    private ConsignActiviti consignActiviti;

    public JSONObject queryConsignState(String processInstanceID)
    {
        String state = consignActiviti.getProcessState(processInstanceID);

        JSONObject queryResultJson = new JSONObject();
        queryResultJson.put("processInstanceID",  processInstanceID);
        queryResultJson.put("state", state);

        return queryResultJson;
    }

    public String createConsignProcess(JSONObject params, User user)
    {
        Consign consign = JSONObject.toJavaObject(params, Consign.class);

        return consignActiviti.createConsignProcess(consign.getId(), user.getId());
    }

    public JSONObject updateConsignState(String processInstanceID, Request request)
    {
        JSONObject params = request.getParams();
        String operation = params.getString("operation");

        if (operation.equals("submit")) {
            consignActiviti.submitConsign(processInstanceID, request.getUser().getId());
        }
        else if (operation.equals("pass")) {
            consignActiviti.checkConsign(true, processInstanceID, request.getUser().getId());
        }
        else if (operation.equals("reject")) {
            consignActiviti.checkConsign(false, processInstanceID, request.getUser().getId());
        }

        return queryConsignState(processInstanceID);
    }
}
