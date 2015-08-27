/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.htec.cmsrest.cms.rest;

import java.sql.Connection;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rs.htec.cmsrest.cms.DBBrocker;
import rs.htec.cmsrest.cms.News;
import rs.htec.cmsrest.cms.Objects;

/**
 *
 * @author stefan
 */
@Path("/hello")
public class RestEndpoint {

    Connection conn;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/world")
    public Response helloWorld() {
        return Response.ok("Hello world!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/news")
    public Response retrieveNews() {

        DBBrocker db = new DBBrocker();
        String readNews = db.readNews();

        return Response.ok(readNews).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/news/{index}")
    public Response retrieveByIndex(@PathParam("index") int index) {
        DBBrocker db = new DBBrocker();
        String s = db.readNewsAtIndex(index);
        return Response.ok(s).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/news/{title}/{body}")
    public Response addNews(@PathParam("title") String title, @PathParam("body") String body) {
        //Objects.niz[Objects.niz.length-1] = s;
        DBBrocker db = new DBBrocker();
        db.addNews(title, body);

        return Response.ok("News added.").build();

        //return Response.ok(index+"").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/news/{index}/{text}")
    public Response changeNewsBody(@PathParam("index") int index, @PathParam("text") String text) {
        //Objects.niz[Objects.niz.length-1] = s;
        DBBrocker db = new DBBrocker();
        db.changeNews(index, text);
        return Response.ok("News updated.").build();
    }
}
