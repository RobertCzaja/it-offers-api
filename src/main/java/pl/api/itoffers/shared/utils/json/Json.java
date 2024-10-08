package pl.api.itoffers.shared.utils.json;

import com.google.gson.Gson;

public class Json {
    public static String convertToString(Object dto) {
        return new Gson().toJson(dto);
    }
}
