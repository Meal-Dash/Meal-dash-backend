package dash.meal.mealdash.infrastructure.adapter.config.securityConfig;

public interface AllowedHost {
    String[] getPatterns();
    default String[] getMethods() {
        return AllowedHostMethods.getMethods();
    }
}
