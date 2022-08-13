package desafio.zoo.resources;

import desafio.zoo.controller.AdministracaoMedicacaoController;
import desafio.zoo.model.AdministracaoMedicacao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/administracaoMedicacao")
public class AdministracaoMedicacaoResources {

    @Inject
    AdministracaoMedicacaoController controller;
    AdministracaoMedicacao administracaoMedicacao;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(AdministracaoMedicacao pAdministracaoMedicacao) {
        administracaoMedicacao = controller.getAdministracaoMedicacao(pAdministracaoMedicacao);
        return Response.ok(administracaoMedicacao).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(AdministracaoMedicacao pAdministracaoMedicacao) {
        controller.addAdministracaoMedicacao(pAdministracaoMedicacao);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(AdministracaoMedicacao pAdministracaoMedicacao) {
        controller.updateAdministracaoMedicacao(pAdministracaoMedicacao);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(AdministracaoMedicacao pAdministracaoMedicacao) {
        controller.deleteAdministracaoMedicacao(pAdministracaoMedicacao);
        return Response.ok().status(200).build();
    }
}
