package contract;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dao.RoleMapper;
import com.pojo.User;
import com.pojo.UserExample;
import com.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisTest {

	@Autowired
	UserService userService;

	@Autowired
	RoleMapper roleMapper;

	@Test
	public void testadd() {
		/*
		 * User user = new User(); for (int i = 0; i < 10; i++) {
		 * user.setId(UUIDUtil.getUUID()); user.setName(String.valueOf(i));
		 * user.setPassword("sdsda"); user.setPhone("sdasd");
		 * userService.insert(user); }
		 */

		User user = new User();
		user.setPhone("15505611310");
		// 手机号查重
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(user.getPhone());
		if (userService.selectByExample(userExample).size() > 0) {
			System.out.println("虚假进行测试");
			List<User> list = userService.selectByExample(userExample);
			for (User user2 : list) {
				System.out.println(user2.toString());
			}
		} else {
			System.out.println("存在手机号");
		}
		// System.out.println(JSON.toJSONString(userService.selectByExample(new
		// UserExample())));
		/*
		 * User user = new User(); user.setId(UUIDUtil.getUUID());
		 * user.setName("1"); userService.insert(user);
		 */

		/*
		 * Role role = new Role(); role.setId(UUIDUtil.getUUID());
		 * role.setRole("超级管理员"); roleMapper.insert(role);
		 * 
		 * role.setId(UUIDUtil.getUUID()); role.setRole("普通员工");
		 * roleMapper.insert(role);
		 * 
		 * role.setId(UUIDUtil.getUUID()); role.setRole("审核人员");
		 * roleMapper.insert(role);
		 */

	}

}
