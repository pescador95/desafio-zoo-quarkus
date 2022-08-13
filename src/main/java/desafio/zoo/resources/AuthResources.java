//package desafio.zoo.resources;
//
//import desafio.zoo.controller.AuthController;
//import desafio.zoo.model.Auth;
//
//import javax.annotation.security.PermitAll;
//import javax.inject.Inject;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.SecurityContext;
//
//
//@Path("/")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public class AuthResources {
//
//    @Inject
//    AuthController authController;
//    Auth auth;
//
//    @POST
//    @Path("/auth")
//    @PermitAll
//    public Response auth(Auth data) {
//        auth = authController.login(data);
//        return Response.ok(auth).status(200).build();
//    }
//
//    @POST
//    @Path("/auth/refresh_token")
//    @PermitAll
//    public Response refreshToken(Auth data, @Context SecurityContext context) {
//        auth = authController.refreshToken(data, context);
//        return Response.ok(auth).status(200).build();
//    }
//}