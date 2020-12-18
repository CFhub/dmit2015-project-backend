package dmit2015.cfourie1.project.resource;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dmit2015.cfourie1.project.model.Birthday;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BirthdayResourceJaxRsClient {

    static final String BASE_URI = "http://localhost:8080/dmit2015-project-backend-start/webapi/";
    static final String BASE_URI_BIRTHDAY = "http://localhost:8080/dmit2015-project-backend-start/webapi/Birthday";
    Client jaxrsClient = ClientBuilder.newClient();

    public List<Birthday> getAll() {
        List<Birthday> birthdayList = new ArrayList<>();
        Response response = jaxrsClient
                .target(BASE_URI_BIRTHDAY)
                .register(JacksonJsonProvider.class)
                .request()
                .get();
        if(response.getStatus() == Response.Status.OK.getStatusCode()) {
            GenericType<List> responseType = new GenericType<>() {};
            birthdayList = response.readEntity(responseType);
        }
        return birthdayList;
    }

    public Optional<Birthday> getOneById(Long id) {
        Optional<Birthday> optionalBirthday = Optional.empty();
        Response response = jaxrsClient
                .target(BASE_URI_BIRTHDAY)
                .path("{id}")
                .resolveTemplate("id", id)
                .register(JacksonJsonProvider.class)
                .request()
                .get();
        if(response.getStatus() == Response.Status.OK.getStatusCode()) {
            Birthday existingBirthday = response.readEntity(Birthday.class);
            optionalBirthday = Optional.of(existingBirthday);
        }
        return optionalBirthday;
    }

    public Optional<Birthday> getOneByLocation(String location) {
        Optional<Birthday> optionalBirthday = Optional.empty();
        Response response = jaxrsClient
                .target(location)
                .register(JacksonJsonProvider.class)
                .request()
                .get();
        if(response.getStatus() == Response.Status.OK.getStatusCode()) {
            Birthday existingBirthday = response.readEntity(Birthday.class);
            optionalBirthday = Optional.of(existingBirthday);
        }
        return optionalBirthday;
    }

    public String create(Birthday newBirthday) {
        String resourceLocation = "";
        Response response = jaxrsClient
                .target(BASE_URI_BIRTHDAY)
                .register(JacksonJsonProvider.class)
                .request()
                .post(Entity.json(newBirthday));
        if(response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            resourceLocation = response.getLocation().toString();
        }
        return resourceLocation;
    }

    public boolean update(Long id, Birthday existingBirthday) {
        boolean success = false;
        Response response = jaxrsClient
                .target(BASE_URI_BIRTHDAY)
                .path("{id}")
                .resolveTemplate("id", id)
                .register(JacksonJsonProvider.class)
                .request()
                .put(Entity.json(existingBirthday));
        if(response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            success = true;
        }
        return success;
    }

    public boolean delete(Long id) {
        boolean success = false;
        Response response = jaxrsClient
                .target(BASE_URI_BIRTHDAY)
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .delete();
        if(response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            success = true;
        }
        return success;
    }
}
