package com.sinosteel.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosteel.activiti.ProcessInstanceService;
import com.sinosteel.domain.Project;
import com.sinosteel.domain.TestWorkCheck;
import com.sinosteel.domain.User;
import com.sinosteel.repository.ProjectRepository;
import com.sinosteel.repository.TestWorkCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LBW & SQW
 */

@Service
public class TestWorkCheckService extends BaseService<TestWorkCheck> {
    @Autowired
    private TestWorkCheckRepository testWorkCheckRepository;
    @Autowired
    private ProjectRepository projectRepository;

    //testWorkCheck不需要添加状态
    //@Autowired
    //private ProcessInstanceService processInstanceService;


    //以用户的工程为来源查询testWorkCheck
    public JSON queryTestWorkChecks(User user) throws Exception {
        if (user != null)
            System.out.println("queryTestRecords--> query user role: " + user.getRoles().get(0).getRoleName());
        if (user.getRoles().get(0).getRoleName().equals("普通客户"))
        {
            List<Project> projects = user.getProjects();
            List<TestWorkCheck> testWorkChecks = new ArrayList<TestWorkCheck>();
            for (Project project: projects){
                testWorkChecks.add(project.getTestWorkCheck());
            }

            //System.out.println(testWorkChecks);
            return processTestWorkChecks(testWorkChecks);
        }
        else
        {
            List<TestWorkCheck> testWorkChecks = testWorkCheckRepository.findByAllTestWorkChecks();
            //有效处理testWorkCheck为空
            return processTestWorkChecks(testWorkChecks);
        }
    }

    //前端从工程中获取testWorkCheck
    public JSON queryTestWorkCheckByProject(String projectID) throws Exception{
        if (projectRepository.findById(projectID) == null) {
            throw new Exception("can't find project by id :" + projectID);
        }
        Project project = projectRepository.findById(projectID);
        TestWorkCheck testWorkCheck = project.getTestWorkCheck();

        return processTestWorkCheck(testWorkCheck);
    }

    public JSONObject queryTestWorkCheckByID(String id) throws Exception{
        TestWorkCheck testWorkCheck = testWorkCheckRepository.findById(id);
        if (testWorkCheck == null)
            throw new Exception("Not found");
        return JSON.parseObject(JSONObject.toJSONString(testWorkCheck));
    }

    //改动testWorkCheck
    public JSONObject editTestWorkCheck(JSONObject params, List<MultipartFile> files, User user) throws Exception
    {
        TestWorkCheck temptestWorkCheck = JSONObject.toJavaObject(params, TestWorkCheck.class);
        TestWorkCheck testWorkCheck;
        if ((testWorkCheck = this.findEntityById(temptestWorkCheck.getId())) == null) {
            throw new Exception("Not found");
        }
        //编辑测试结果时只编辑内容
        //testWorkCheck = temptestWorkCheck;
        testWorkCheck.setVersion(temptestWorkCheck.getVersion());
        testWorkCheck.setAcendtime(temptestWorkCheck.getAcendtime());
        testWorkCheck.setClient(temptestWorkCheck.getClient());
        testWorkCheck.setFcendtime(temptestWorkCheck.getFcendtime());
        testWorkCheck.setSoftwarename(temptestWorkCheck.getSoftwarename());
        testWorkCheck.setTestworker(temptestWorkCheck.getTestworker());
        testWorkCheck.setStarttime(temptestWorkCheck.getStarttime());

        this.updateEntity(testWorkCheck, user);

        testWorkCheck = testWorkCheckRepository.findById(temptestWorkCheck.getId());
        return processTestWorkCheck(testWorkCheck);
    }

    //增加testWorkCheck
    public JSONObject addTestWorkCheck(JSONObject params,List<MultipartFile> files,User user) throws Exception {

        //使用工程id作为id
        String uid = params.getString("id");
        //check project
        if (projectRepository.findById(uid) == null)
            throw new Exception("Can't find project with ID: " + uid);

        Project project = projectRepository.findById(uid);

        TestWorkCheck testWorkCheck=JSONObject.toJavaObject(params,TestWorkCheck.class);
        testWorkCheck.setId(uid);

        project.setTestWorkCheck(testWorkCheck);
        projectRepository.save(project);

        testWorkCheck.setProject(project);
        this.saveEntity(testWorkCheck, user);


        testWorkCheck = testWorkCheckRepository.findById(uid);
        return processTestWorkCheck(testWorkCheck);
    }


    //删除testWorkCheck
    public void deleteTestWorkCheck(JSONObject params)
    {
        String uid=params.getString("id");

        Project project = projectRepository.findById(uid);
        project.setTestWorkCheck(null);

        this.deleteEntity(uid);
    }


    private JSONObject processTestWorkCheck(TestWorkCheck testWorkCheck) throws Exception {
        //String processState = (String) processInstanceService.queryProcessState(testRecord.getProcessInstanceID()).get("state");
        JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(testWorkCheck));
        //jsonObject.put("state", processState);
        return jsonObject;

    }


    private  JSONArray processTestWorkChecks(List<TestWorkCheck> testWorkChecks) throws Exception {
        JSONArray resultArray = new JSONArray();
        for (TestWorkCheck testWorkCheck: testWorkChecks) {
            JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(testWorkCheck));
            //jsonObject.remove("testWorkCheck");
            //String processState = (String) processInstanceService.queryProcessState(testRecord.getProcessInstanceID()).get("state");
            //jsonObject.put("state", processState);
            resultArray.add(jsonObject);
        }

        return resultArray;
    }
}