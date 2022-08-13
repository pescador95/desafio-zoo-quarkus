package desafio.zoo.resources;

import desafio.zoo.model.Medicacao;
import desafio.zoo.controller.MedicacaoController;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/medicacao")
public class MedicacaoResources {

    @Inject
    MedicacaoController controller;
    Medicacao medicacao;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response get(Medicacao pMedicacao) {
        medicacao = controller.getMedicacao(pMedicacao);
        return Response.ok(medicacao).status(200).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response add(Medicacao pMedicacao) {
        controller.addMedicacao(pMedicacao);
        return Response.ok().status(201).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response update(Medicacao pMedicacao) {
        controller.updateMedicacao(pMedicacao);
        return Response.ok().status(200).build();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response delete(Medicacao pMedicacao) {
        controller.deleteMedicacao(pMedicacao);
        return Response.ok().status(200).build();
    }
}
