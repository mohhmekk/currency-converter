package org.sample.currency.app.util.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * custom JSON serializer for java.sql.Time type
 *
 * Created by Mohamed Mekkawy.
 */
public class CustomTimeDeserializer extends JsonDeserializer<Time> {

    private final Logger logger = LoggerFactory.getLogger(CustomTimeDeserializer.class);

    @Override
    public Time deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Time time = null;
        try {
            logger.debug("converting time parameter from value " + jp.getText());
            Date date = formatter.parse("1970/01/01 " + jp.getText());
            time = new Time(date.getTime());
        } catch (ParseException e) {
            throw new TimeDeserializationException(e);
        }
        return time;
    }

}
