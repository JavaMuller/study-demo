package com.gether.bigdata.web.view;

import com.gether.bigdata.domain.user.User;
import com.gether.bigdata.service.UserService;
import com.gether.bigdata.service.UserServiceImpl;
import com.gether.bigdata.util.JsonUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author: myp
 * @date: 16/10/22
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户批量添加", notes = "")
    @RequestMapping(value = "/batchAdd", method = RequestMethod.GET)
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public void userbatchAdd() {
        UserServiceImpl.i = 0;
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        Long id1 = System.currentTimeMillis();
        Long id2 = System.currentTimeMillis() + 1;
        userService.add(id1, "123sadasd", 1);
        userService.add(id2, "name2", 2);
        userService.add(System.currentTimeMillis() + 2, "name3", 3);
        userService.add(System.currentTimeMillis() + 3, "name4", 4);
        userService.add(System.currentTimeMillis() + 4, "name5", 5);
        // 查数据库，应该有5个用户
        //Assert.assertEquals(5, userSerivce.list().size());

        System.out.println(JsonUtils.toJsonStrWithNull(userService.getById(id1)));
        System.out.println(JsonUtils.toJsonStrWithNull(userService.getById(id2)));

        userService.update(id2, "change name", 10);
        System.out.println(JsonUtils.toJsonStrWithNull(userService.getById(id2)));

        // 删除两个用户
        userService.delete(id1);
        userService.delete(id2);
    }

    @ApiOperation(value = "获取用户列表", notes = "")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList() {
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        return userService.list();
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postUser(@ModelAttribute User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        userService.add(user.getId(), user.getName(), user.getAge());
        return "success";
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return userService.getById(id);
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        userService.update(user.getId(), user.getName(), user.getAge());
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        userService.delete(id);
        return "success";
    }

    @RequestMapping(value = "/insertFlow", method = RequestMethod.GET)
    public String insertFlow() throws ParseException {
        userService.insertFlow();
        return "success";
    }
}