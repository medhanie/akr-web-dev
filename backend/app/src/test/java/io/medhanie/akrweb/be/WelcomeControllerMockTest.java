package io.medhanie.akrweb.be;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class WelcomeControllerMockTest extends AbstractControllerTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testGreeting() throws Exception {
        String uri = "/greeting";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !content.trim().isEmpty());
    }

    @Test
    public void testMemberGreeting() throws Exception {
        String uri = "/member/greeting";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !content.trim().isEmpty());
    }
}