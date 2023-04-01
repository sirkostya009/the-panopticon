package ua.sirkostya009.ui.controller;

import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ModelUtils {

    public static Map<String, Object> defaults() {
        return defaults(null);
    }

    public static Map<String, Object> defaults(Map<String, Object> override) {
        var defaults = new HashMap<>(Map.of(
                "year", (Object) Calendar.getInstance().get(Calendar.YEAR),
                "site_name", "Panopticon",
                "home", "/",
                "login", "/login"
        ));

        if (override != null)
            defaults.putAll(override);

        return defaults;
    }

}
