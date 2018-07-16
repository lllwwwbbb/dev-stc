package com.sinosteel.Repository;


import com.sinosteel.FrameworkApplication;
import com.sinosteel.repository.ProjectRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.sinosteel.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * 这个类用来测试project类对应repository的增删查功能
 *<table border="1">
 *     <tr>
 *     </tr><th>测试内容</th>
 *     <th>对应操作</th>
 *     <th>测试结果</th>
 *     </tr>
 *     <tr>
 *         <td>新建项目/td>
 *         <td>new + save</td>
 *         <td>FindById成功</td>
 *          </tr>
 *          <td>删除项目</td>
 *         <td>delete对应Id</td>
 *         <td>FindById为空</td>
 *         </tr>
 * <table>
 * @author FW
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(FrameworkApplication.class)
public class ProjectRepositoryTests {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @Transactional
    public void testProject(){

        Project project = new Project();
        project.setId("2018");
        projectRepository.save(project);
        Project projectfind = projectRepository.findById("2018");
        Assert.assertNotNull("project为空",projectfind);

       // project.setContract(null);
        projectRepository.save(project);
        projectRepository.delete("2018");

        projectfind = projectRepository.findById("2018");

        Assert.assertNull("project不为空",projectfind);
    }
}