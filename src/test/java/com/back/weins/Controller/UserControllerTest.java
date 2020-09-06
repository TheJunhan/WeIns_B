package com.back.weins.Controller;

import com.alibaba.fastjson.JSON;
import com.back.weins.WeinsApplicationTests;
import com.back.weins.servicesImpl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest extends WeinsApplicationTests {
    @Test
    public void contextLoads() {}

    private Object MvcResult;
    private MockMvc mockMvc;
    private final String avatar = "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg";

    @Autowired
    WebApplicationContext context;

    @MockBean
    UserServiceImpl userService;

    @Autowired
    UserController userController;

    @BeforeEach
    public void setUp() {
        System.out.println("set up");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tear down");
    }

    @Test
    public void getTest() throws Exception {

        MvcResult resultOne = mockMvc.perform(get("/user/getOne?id=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult resultPlainOne = mockMvc.perform(get("/user/getPlainOne?id=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult resultName = mockMvc.perform(get("/user/getByName?name=weins").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult resultFuzzyName = mockMvc.perform(get("/user/getByFuzzyName?name=weins").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult resultAll = mockMvc.perform(get("/user/getAll").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void RegAndLogin() throws Exception {
        List<Integer> interests = new ArrayList<Integer>();
        interests.add(1);
        interests.add(2);
        interests.add(3);
        String interest = JSON.toJSONString(interests);

        MvcResult reg1 = mockMvc.perform(post("/user/register").content(
                "{ \"id\" : -1, \"name\" :  \"Zangby\", \"password\" : \"success\", \"phone\" : \"11111111111\", \"birthday\" : \"2000-01-01\", \"avatar\" : \"" + avatar + "\", \"interests\" : " + interest + " }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        MvcResult reg2 = mockMvc.perform(post("/user/register").content(
                "{ \"id\" : -1, \"name\" :  \"Zangby\", \"password\" : \"phone error\", \"phone\" : \"11111111111\", \"birthday\" : \"2000-01-01\", \"avatar\" : \"" + avatar + "\", \"interests\" : " + interest + " }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        MvcResult reg3 = mockMvc.perform(post("/user/register").content(
                "{ \"id\" : -1, \"name\" :  \"Zangby\", \"password\" : \"name error\", \"phone\" : \"11111111111\", \"birthday\" : \"2000-01-01\", \"avatar\" : \"" + avatar + "\", \"interests\" : " + interest + " }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        MvcResult login1 = mockMvc.perform(post("/user/login?ph=-1&pwd=111111").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult login2 = mockMvc.perform(post("/user/login?ph=-2&pwd=111111").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult login3 = mockMvc.perform(post("/user/login?ph=-3&pwd=111111").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void update() throws Exception {
        MvcResult = mockMvc.perform(post("/user/update").content(
                "{ \"id\" : -1, \"name\" :  \"Zangby\", \"password\" : \"name error\", \"phone\" : \"11111111111\", \"birthday\" : \"2000-01-01\", \"avatar\" : \"" + avatar + "\" }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void follow() throws Exception {
        MvcResult follow1 = mockMvc.perform(post("/user/follow?sub=1&obj=1&flag=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult follow2 = mockMvc.perform(post("/user/follow?sub=1&obj=2&flag=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult follow3 = mockMvc.perform(post("/user/follow?sub=1&obj=2&flag=-1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult follow4 = mockMvc.perform(post("/user/follow?sub=1&obj=2&flag=11").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void auth() throws Exception {
        MvcResult auth1 = mockMvc.perform(post("/user/auth?sub=1&obj=1&tar=8").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult auth2 = mockMvc.perform(post("/user/auth?sub=1&obj=1&tar=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult auth3 = mockMvc.perform(post("/user/auth?sub=1&obj=2&tar=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }
}

