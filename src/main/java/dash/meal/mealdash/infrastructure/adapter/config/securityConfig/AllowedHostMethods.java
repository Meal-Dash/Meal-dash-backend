package dash.meal.mealdash.infrastructure.adapter.config.securityConfig;

public class AllowedHostMethods {
    private static final String[] METHODS = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};

    public static String[] getMethods() {
        return METHODS;
    }
}
