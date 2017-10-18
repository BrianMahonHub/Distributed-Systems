package server;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Player;


@Path("/players")
public class PlayerResource {
	
	@GET
	@Produces({MediaType.APPLICATION_XML, 
				MediaType.APPLICATION_JSON,
				MediaType.TEXT_XML})
	public List<Player> getPlayers(){
		return PlayerDAO.instance.getPlayers();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, 
				MediaType.APPLICATION_JSON,
				MediaType.TEXT_XML})
	@Path("{playerId}")
	public Player getPlayer(@PathParam("playerId")String id){
		return PlayerDAO.instance.getPlayer(Integer.parseInt(id));
	}
	@POST
	@Produces (MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postPlayer(
	@FormParam("id") String id,
	@FormParam("name") String name,
	@FormParam("position") String position,
	@FormParam("age") String age,
	@Context HttpServletResponse servletResponse) throws IOException {

	Player player = new Player();
	player.setId(Integer.parseInt(id));
	player.setName(name);
	player.setPosition(position);
	player.setAge(Integer.parseInt(age));
	PlayerDAO.instance.create(player);
	}
	
	@PUT
	@Produces (MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{playerId}")
	public void putPlayer(
	@PathParam("playerId") String id,
	@FormParam("name") String name,
	@FormParam("position") String position,
	@FormParam("age") String age,
	@Context HttpServletResponse servletResponse) throws IOException {

	Player player = new Player();
	player.setId(Integer.parseInt(id));
	player.setName(name);
	player.setPosition(position);
	player.setAge(Integer.parseInt(age));
	PlayerDAO.instance.create(player);
	}


	@DELETE
	@Produces(MediaType.TEXT_HTML)
	@Path("{playerId}")
	public void deletePlayer(@PathParam("playerId") String id) throws IOException {
	System.out.println("Delete id = " + id);
	PlayerDAO.instance.delete(Integer.parseInt(id));
	}
}
