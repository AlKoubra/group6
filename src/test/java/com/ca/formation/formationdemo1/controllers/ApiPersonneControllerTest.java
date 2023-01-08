package com.ca.formation.formationdemo1.controllers;

import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.models.Role;
import com.ca.formation.formationdemo1.services.PersonneService;
import jdk.jfr.ContentType;
import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApiPersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonneService personneService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/v2/personnes";
    }

    private String tokenRequest;

    void ApiPersonControllerTest() {
    }

    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
    void hello() throws Exception {
            RequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get("/api/v2/personnes/hello")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
                    .contentType(MediaType.APPLICATION_JSON);

            MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString();

            System.out.println(contentAsString);
            assertNotNull(contentAsString);


    }
    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "ADMIN" })
    void byebye() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/personnes/bye")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
        assertNotNull(contentAsString);
        Assert.assertEquals(contentAsString, "Bye bye");


    }
    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "ADMIN" })
    public void addPersonne() throws Exception {

        Personne personne = new Personne("Ndéye Khady", "Niang", 40);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest);
        HttpEntity<Personne> entity = new HttpEntity<Personne>(null, headers);
        ResponseEntity<Personne> responseEntity = restTemplate
                .exchange(getRootUrl() , HttpMethod.POST, entity, Personne.class, personne);
        assertNotNull(responseEntity);
    }
    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "ADMIN" })
    public void updatePersonne() throws Exception {
        String body = "{\n" +
                "    \"nom\": \"Koubra\",\n" +

                "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(getRootUrl()+"/3")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String token = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
        tokenRequest = token;
        System.out.println(body);

    }

    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
    public void getPersonne() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/personnes/2")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
        assertNotNull(contentAsString);

    }

    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
    public void getToutPersonne() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/personnes")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
        assertNotNull(contentAsString);

    }

    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
    void deletePerson() throws Exception {
        // TODO : add test deletePerson
        when(personneService.getPersonne(3L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v2/personnes/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertNotNull(status().isOk());
    }

    @Test
    @WithMockUser(username = "michel@formation.sn", password = "Passer@123", authorities = { "READ" })
    public void getPersonneParNom() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/personnes/search/Abdel")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRequest)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        System.out.println(contentAsString);
        assertNotNull(contentAsString);

    }


}
