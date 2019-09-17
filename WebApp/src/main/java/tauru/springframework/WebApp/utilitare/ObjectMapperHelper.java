package tauru.springframework.WebApp.utilitare;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectMapperHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperHelper.class);

    public String objectMapper(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        String json ="";

        try {
            json = mapper.writeValueAsString(object);
            LOGGER.info("Transforming object type " + object.getClass() + " into JSON");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }

}
