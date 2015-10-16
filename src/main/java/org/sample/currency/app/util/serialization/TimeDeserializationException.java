package org.sample.currency.app.util.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Custom exception thrown when it was not possible to deserialize a time field.
 * @see CustomTimeDeserializer
 *
 * Created by Mohamed Mekkawy.
 */
public class TimeDeserializationException extends JsonProcessingException {

    protected TimeDeserializationException(Throwable rootCause) {
        super(rootCause);
    }

}
