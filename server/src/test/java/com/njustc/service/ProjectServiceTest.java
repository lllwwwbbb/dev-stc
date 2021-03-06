package com.njustc.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njustc.FrameworkApplication;
import com.njustc.domain.User;
import com.njustc.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 本测试用来测试Project Service
 * <table border = "1" summary="">
 *      <tr>
 *          <th>测试内容</th>
 *          <th>测试操作</th>
 *          <th>测试结果</th>
 *      </tr>
 *      <tr>
 *          <td>通过用户查询工程</td>
 *          <td>queryProjects</td>
 *          <td>查询成功</td>
 *      </tr>
 *      <tr>
 *          <td>新建工程</td>
 *          <td>addProject</td>
 *          <td>新建成功</td>
 *      </tr>
 *      <tr>
 *          <td>通过工程ID查询工程</td>
 *          <td>queryProjectByID</td>
 *          <td>查询成功</td>
 *      </tr>
 *      <tr>
 *          <td>通过工程查询工程</td>
 *          <td>queryProjectsByProject</td>
 *          <td>查询成功</td>
 *      </tr>
 *      <tr>
 *          <td>编辑工程内容</td>
 *          <td>editProject</td>
 *          <td>编辑成功</td>
 *      </tr>
 *      <tr>
 *          <td>删除工程</td>
 *          <td>deleteProject</td>
 *          <td>queryProjectByID为空,删除成功</td>
 *      </tr>
 * </table>
 *
 *
 * 
 * @author Lumpy
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(FrameworkApplication.class)
//@Transactional
public class ProjectServiceTest {

    private User markerUser;
    private User customer1;
    private User customer2;
    private User tester;
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ConsignService consignService;

    @Autowired
    private UserRepository userRepository;


    /**
     * 获取用户信息,将其作为之后测试方法时的参数
     */
    @Before
    public void getUsers(){
        markerUser = userRepository.findByUsername("marketing");
        customer1 = userRepository.findByUsername("customer1");
        customer2 = userRepository.findByUsername("customer2");
        tester = userRepository.findByUsername("testing");
    }

    /**
     * 根据用户查询工程
     */
    @Ignore
    public void testqueryProjects() {
        System.out.println("开始测试工作人员获取工程");
        try {
            JSON result = projectService.queryProjects(tester);

            Assert.assertNotNull("工作人员 - 工程查询失败",result);

            System.out.println(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("开始测试用户获取工程");
        try {
            JSON result = projectService.queryProjects(customer1);

            Assert.assertNotNull("用户 - 工程查询失败",result);

            System.out.println(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 所测试的方法:新建工程,根据ID查询工程,删除工程
     */
    @Test
    public void testProject() {
        System.out.println("=====tester 增加一个工程=====");
        JSONObject project = new JSONObject();
        //project.put("projectation", "这是tester测试中新建的一个工程");
        JSONObject consign = new JSONObject();
        try {

            //test_addproject
            JSONObject jsonConsign = consignService.addConsign(consign,null,tester);
            String consign_id = jsonConsign.getString("id");
            JSONObject jsonResult = projectService.addProject(consign_id,project, null, tester);
            String id = jsonResult.getString("id");
            Assert.assertNotNull("工程新建失败",id);
            System.out.println("新建成功。工程的ID为: " + id);
            System.out.println("工程信息为" + jsonResult);

            //test_queryprojectByID
            System.out.println("=====通过ID查询该工程=====");
            JSONObject jsonProject = projectService.queryProjectById(id);
            Assert.assertNotNull("通过ID工程查询失败",jsonProject);
            System.out.println(jsonProject);


            /*
            //test_editproject
            System.out.println("=====编辑该工程=====");
            String edit_object = "projectation";
            String edit_contents = "这是customer1在测试中修改的工程";
            jsonProject.put(edit_object,edit_contents );
            jsonProject = projectService.editProject(jsonProject, null, customer1);
            Assert.assertEquals(edit_contents,jsonProject.getString(edit_object));  //检验工程内容修改是否符合预期
            System.out.println(jsonProject);
            */

            //test_deleteproject
            System.out.println("=====删除该工程=====");
            projectService.deleteProject(jsonProject);
            try {
                JSONObject jsonDel = projectService.queryProjectById(id);
                Assert.assertNull("工程删除失败", jsonDel);
            } catch (Exception e) {
                System.out.println("工程删除成功");
            }
            consignService.deleteConsign(jsonConsign);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
