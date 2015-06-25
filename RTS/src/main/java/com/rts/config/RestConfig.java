package com.rts.config;

import com.rts.resource.*;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class RestConfig extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public RestConfig() {
        register(AdminRest.class);
        register(BuyTicketRest.class);
        register(SearchRest.class);
        register(SystemRest.class);
        register(UserTransactionRest.class);

        // Reg Jackson
        register(JacksonFeature.class);
    }
}
