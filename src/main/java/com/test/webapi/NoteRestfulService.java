package com.test.webapi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import java.net.URI;
import java.util.List;

/**
 * Root resource (exposed at "note" path)
 */
@Path("/notes")
public class NoteRestfulService {

    static NoteModel model = new NoteModel();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getNotes(@QueryParam("query") String query) {
        List<Note> notes = null;
        if (query != null) {
            notes = model.getNotesByQuery(query);
        } else {
            notes = model.getAllNotes();
        }
        return notes;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Note getNote(@PathParam("id") Integer id) {
        Note note = model.get(id);
        return note;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Note note = null;
        try {
            note = mapper.readValue(json, Note.class);
        }
        catch (Exception e) {
            return Response.status(501).build();
        }
        return saveNewDTO(note);
    }

    private Response saveNewDTO(Note note) {
        Integer id = model.insert(note);
        URI createdURI = URI.create("http://localhost:8080/api/notes/" + id);
        note.setId(id);
        return Response
                .status(201)
                .contentLocation(createdURI)
                .entity(note)
                .build();
    }
}
