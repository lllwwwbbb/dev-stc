package com.sinosteel.web;

import com.sinosteel.framework.core.web.Request;
import com.sinosteel.framework.core.web.Response;
import com.sinosteel.framework.core.web.ResponseType;
import com.sinosteel.service.TestPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * The {@code TestPlanController} class 用来处理对测试计划的各种请求 <br>
 * 包括按照用户查询、按照工程查询、按照id查询；添加测试计划；删除测试计划；修改测试计划
 *
 * @author LBW
 * @author SQW
 *
 */

@RestController
public class TestPlanController extends BaseController {
    @Autowired
    private TestPlanService testPlanService;

    /**
     * 按照用户或者工程查询测试计划
     * @param request
     *        Request类型参数，里面包括username和clientDigest用于用户身份验证
     * @param projectID
     *        测试计划绑定工程的id，required值为false <br>
     *        传入的参数中有该参数候按照工程查询，
     *        没有时按照request里的user查询
     * @return The {@code response}是返回的具体内容 <br>
     *         查询成功时有data和status两个内容，
     *         data为测试计划具体内容，status值SUCCESS <br>
     *         查询失败时status值为FAILURE
     */
    @RequestMapping(value = "/v1/testPlan", method = RequestMethod.GET)
    public Response queryTestPlans(Request request, @RequestParam(value = "projectID", required = false) String projectID)
    {
        if(projectID == null) {
            return queryTestPlans(request);
        }

        Response response = new Response();

        try
        {
            response.data = testPlanService.queryTestPlansByProject(projectID);
            response.status = ResponseType.SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.status = ResponseType.FAILURE;
            response.message = e.getMessage();
        }

        return response;
    }
    /**
     * 按照用户查询测试计划，用于上面的函数调用
     * @param request Request类型参数 <br>
     *                包含username和clientDigest传递用户信息并且验证身份
     * @return The {@code response}是返回的具体内容 <br>
     *         查询成功时有data和status两个内容，
     *         data为测试计划具体内容，status值SUCCESS <br>
     *         查询失败时status值为FAILURE
     */
    private Response queryTestPlans(Request request) {
        Response response = new Response();

        try
        {
            response.data = testPlanService.queryTestPlans(request.getUser());
            response.status = ResponseType.SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.status = ResponseType.FAILURE;
            response.message = e.getMessage();
        }

        return response;

    }

    /**
     * 根据测试计划的具体id查询测试计划
     * @param id String类型参数，测试计划的id
     * @param request Request类型参数 <br>
     *                包含username和clientDigest传递用户信息并且验证身份，
     * @return The {@code response}是返回的具体内容 <br>
     *         查询成功时有data和status两个内容，
     *         data为测试计划具体内容，status值SUCCESS <br>
     *         查询失败时status值为FAILURE
     */
    //根据ID查询测试计划具体信息
    @RequestMapping(value = "/v1/testPlan/{id}", method = RequestMethod.GET)
    public Response queryTestPlanByID(@PathVariable String id, Request request) {
        Response response = new Response();

        try
        {
            response.data = testPlanService.queryTestPlanByID(id);
            response.status = ResponseType.SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.status = ResponseType.FAILURE;
            response.message = e.getMessage();
        }
        return response;
    }

    /**
     * 修改测试计划
     * @param request Request类型参数 <br>
     *                包含username和clientDigest传递用户信息并且验证身份 <br>
     *                还包括需要修改的测试计划id以及修改之后的全新内容（body）
     * @return The {@code response}是返回的具体内容 <br>
     *         修改成功时有data和status两个内容，
     *         data为测试计划具体内容，status值SUCCESS <br>
     *         修改失败时status值为FAILURE
     */
    @RequestMapping(value = "/v1/testPlan",method = RequestMethod.PUT)
    public Response editTestPlan(Request request)
    {
        Response response = new Response();

        try {
            response.data = testPlanService.editTestPlan(request.getParams(), request.getFiles(), request.getUser());
            response.status = ResponseType.SUCCESS;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.status = ResponseType.FAILURE;
            response.message = e.getMessage();
        }
        return response;

    }
    /**
     * 添加测试计划
     * @param request Request类型参数 <br>
     *                包含username和clientDigest传递用户信息并且验证身份 <br>
     *                还包括一个params，包括绑定工程的id以及测试计划的具体内容（body）
     * @return The {@code response}是返回的具体内容 <br>
     *         添加成功时有data和status两个内容，
     *         data为测试计划具体内容，status值SUCCESS <br>
     *         添加失败时status值为FAILURE
     */
    @RequestMapping(value = "/v1/testPlan",method=RequestMethod.POST)
    public Response addTestPlan(Request request, @RequestParam(value = "projectID") String projectID)
    {
        Response response=new Response();

        try{
            response.data = testPlanService.addTestPlan(projectID, request.getParams(),request.getFiles(),request.getUser());
            response.status=ResponseType.SUCCESS;
        }
        catch(Exception e)
        {
            e.printStackTrace();

            response.status=ResponseType.FAILURE;
            response.message=e.getMessage();
        }
        return response;
    }
    /**
     * 删除测试计划
     * @param request Request类型参数 <br>
     *                包含username和clientDigest传递用户信息并且验证身份 <br>
     *                还包括需要删除的测试计划id
     * @return The {@code response}是返回的具体内容 <br>
     *         删除成功时status值SUCCESS <br>
     *         删除失败时status值为FAILURE
     */
    @RequestMapping(value = "/v1/testPlan",method=RequestMethod.DELETE)
    public Response deleteTestPlan(Request request)
    {
        Response response=new Response();

        try{
            testPlanService.deleteTestPlan(request.getParams());
            response.status=ResponseType.SUCCESS;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.status=ResponseType.FAILURE;
            response.message=e.getMessage();
        }
        return response;
    }
}

