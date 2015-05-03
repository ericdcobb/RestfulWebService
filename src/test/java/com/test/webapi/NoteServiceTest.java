package com.test.webapi;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class NoteServiceTest {

    public static Client client = null;

    @BeforeClass
    public static void Before() {
        NoteServiceTest.client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void After() {
        client.close();
    }

    @Test
    public void testPOSTaNewTask() throws Exception {

        WebTarget target = client.target("http://localhost:8080/api").path("notes");
        Note note = new Note();
        note.setId(100);
        note.setBody("POST test");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(note);
        Response rs = target.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));
        Note noteResponse = rs.readEntity(Note.class);
        assertNotNull(noteResponse);
        assertEquals(noteResponse.getBody(), note.getBody());
    }

    @Test
    public void testGETaNoteList() throws Exception {

        WebTarget target = client.target("http://localhost:8080/api");
        WebTarget resource = target.path("/notes");
        Invocation.Builder invocationBuilder =
                resource.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        String notes = response.readEntity(String.class);
        assertNotNull(notes);
        ObjectMapper mapper = new ObjectMapper();
        Note[] noteArray = mapper.readValue(notes, Note[].class);
        assertNotNull(noteArray);
        assertTrue(noteArray.length >= 1);
    }

    @Test
    public void testGEToneNote() {

        WebTarget target = client.target("http://localhost:8080/api");
        WebTarget resource = target.path("/notes/1");
        Invocation.Builder invocationBuilder =
                resource.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Note note = response.readEntity(Note.class);
        assertNotNull(note);
        assertNotNull(note.getId());
        assertTrue(note.getId() >= 1);
    }

    @Test
    public void testGETnotesByQuery() {

        WebTarget target = client.target("http://localhost:8080/api");
        WebTarget resource = target.path("/notes").queryParam("query", "rent");

        Invocation.Builder invocationBuilder =
                resource.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Note note = response.readEntity(Note.class);

        assertNull(note.getBody());
    }

}
