package api.commons;

import org.modelmapper.ModelMapper;

public class Utils {
    private static final ModelMapper MAPPER = new ModelMapper();

    public static final <D> D mapObject(Object source, Class<D> destinationType) {
        return MAPPER.map(source, destinationType);
    }

    public static final void mapObject(Object source, Object destinationType) {
        MAPPER.map(source, destinationType);
    }
}
