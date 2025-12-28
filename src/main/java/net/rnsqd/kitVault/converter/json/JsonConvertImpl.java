package net.rnsqd.kitVault.converter.json;

import com.google.gson.Gson;
import net.rnsqd.kitVault.services.ConvertService;

import java.io.Reader;

public final class JsonConvertImpl implements ConvertService {
    public final Gson gson = new Gson();

    @Override
    public String serialize(Object object) {
        return this.gson.toJson(object);
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> clazz) {
        return this.gson.fromJson(reader, clazz);
    }
}
