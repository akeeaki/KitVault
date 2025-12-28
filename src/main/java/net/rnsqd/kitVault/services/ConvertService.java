package net.rnsqd.kitVault.converter;

import java.io.Reader;

public interface ConvertService {
    String serialize(Object input);
    <T> T deserialize(Reader reader, Class<T> clazz);
}
