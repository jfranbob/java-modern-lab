package com.jfranbob.modules.records.spring;

public record AppConfig(String name, String environment, int maxConnections, boolean featuresEnabled) {

    private static final int MIN_CONNECTIONS = 1;

    public AppConfig {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (environment == null || environment.isBlank()) {
            throw new IllegalArgumentException("environment must not be blank");
        }
        if (maxConnections < MIN_CONNECTIONS) {
            throw new IllegalArgumentException("maxConnections must be at least " + MIN_CONNECTIONS);
        }
    }
}
