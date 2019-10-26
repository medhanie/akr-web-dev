package io.medhanie.akrweb.be;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.medhanie.akrweb.be.controllers.BaseController;

/**
 * Created by Medhanie on 3/28/2016.
 */
@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mvc;

    @Autowired
    private Gson gson;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) {
        return gson.toJson(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    protected void setUp(BaseController controller) {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

}