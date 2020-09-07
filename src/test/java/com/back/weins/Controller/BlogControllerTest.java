package com.back.weins.Controller;

import com.back.weins.WeinsApplicationTests;
import com.back.weins.servicesImpl.BlogServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class BlogControllerTest extends WeinsApplicationTests {

    @Test
    public void contextLoads() {}

    @Autowired
    WebApplicationContext context;

    @Autowired
    private BlogController blogController;

    @MockBean
    private BlogServiceImpl blogService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        System.out.println("set up");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
        System.out.println("tear down");
    }

    @Test
    public void label() throws Exception {
        MvcResult resultSet = mockMvc.perform(get("/blog/setLabel?label=CHINA!").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult resultGet = mockMvc.perform(get("/blog/getLabels").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult resultGetFuzzy = mockMvc.perform(get("/blog/findFuzzyLabels?lab=CH").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getBlogs() throws Exception {
        MvcResult setBlog = mockMvc.perform(post("/blog/setBlog").content(
                "{ \"uid\" : 1, \"type\" : 3, \"content\" : \"hello world\", \"post_day\" : \"2020-09-01 08:00:00\", " +
                        "\"video\" : \"video\", \"imag\" : \"image\", \"label\" : \"labels\", \"username\" : \"weins\" }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

        MvcResult getPub = mockMvc.perform(get("/blog/getPublicBlogs").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult getPubPage = mockMvc.perform(get("/blog/page/getPublicBlogs?index=0&num=5").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult getLab = mockMvc.perform(get("/blog/getBlogsByLabel?lid=1&uid=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult getLabPage = mockMvc.perform(get("/blog/page/getBlogsByLabel?lid=1&uid=1&index=0&num=5").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult getLogin = mockMvc.perform(get("/blog/getBlogsLogined?uid=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult getLoginPage = mockMvc.perform(get("/blog/page/getBlogsLogined?uid=1&index=0&num=5").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult getById = mockMvc.perform(get("/blog/getBlogsById?uid=1&to_see_uid=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult getSingle = mockMvc.perform(get("/blog/getSingleBlog?bid=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult getRecommendPage = mockMvc.perform(get("/blog/page/recommend?uid=1&index=0&num=5").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult getRecommendNotLoginPage = mockMvc.perform(get("/blog/page/recommendNotLogin?index=0&num=5").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void CounterOps() throws Exception {
        MvcResult like = mockMvc.perform(get("/blog/like?uid=1&bid=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult removeLike = mockMvc.perform(get("/blog/removeLike?uid=1&bid=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult collect = mockMvc.perform(get("/blog/collect?uid=1&bid=1&flag=true").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult unCollect = mockMvc.perform(get("/blog/collect?uid=1&bid=1&flag=false").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult comment = mockMvc.perform(post("/blog/setComment").content(
                "{ \"uid\" : 1, \"to_uid\" : 2, \"post_time\" : \"2020-09-01 08:00:00\", \"bid\" : 1, " +
                        "\"to_cid\" : 1, \"content\" : \"I agree with you\", \"root_cid\": 1 }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult removeComment = mockMvc.perform(post("/blog/removeComment?uid=1&cid=1&type=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        MvcResult reBlog = mockMvc.perform(post("/blog/setReblog").content(
                "{ \"uid\" : 1, \"bid\" : 1, \"post_day\" : \"2020-09-01 08:00:00\", \"type\" : 3, " +
                        "\"username\" : \"weins\", \"content\" : \"I agree with you\" }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult removeBlog = mockMvc.perform(get("/blog/removeBlog?uid=1&bid=1&type=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        MvcResult changeBlog = mockMvc.perform(post("/blog/changeBlog").content(
                "{ \"uid\" : 1, \"bid\" : 1, \"type\" : 3, \"content\" : \"I agree with you\" }"
        ).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }
}
