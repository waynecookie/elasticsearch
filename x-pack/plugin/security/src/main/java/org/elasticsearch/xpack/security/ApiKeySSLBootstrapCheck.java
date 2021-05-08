/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.security;

import org.elasticsearch.bootstrap.BootstrapCheck;
import org.elasticsearch.bootstrap.BootstrapContext;
import org.elasticsearch.xpack.core.XPackSettings;

import java.util.Locale;

/**
 * Bootstrap check to ensure that the user has enabled HTTPS when using the api key service
 */
public final class ApiKeySSLBootstrapCheck implements BootstrapCheck {

    @Override
    public BootstrapCheckResult check(BootstrapContext context) {
        final Boolean httpsEnabled = XPackSettings.HTTP_SSL_ENABLED.get(context.settings());
        final Boolean apiKeyServiceEnabled = XPackSettings.API_KEY_SERVICE_ENABLED_SETTING.get(context.settings());
        if (httpsEnabled == false && apiKeyServiceEnabled) {
            final String message = String.format(
                    Locale.ROOT,
                    "HTTPS is required in order to use the API key service; "
                            + "please enable HTTPS using the [%s] setting or disable the API key service using the [%s] setting",
                    XPackSettings.HTTP_SSL_ENABLED.getKey(),
                    XPackSettings.API_KEY_SERVICE_ENABLED_SETTING.getKey());
            return BootstrapCheckResult.failure(message);
        }
        return BootstrapCheckResult.success();
    }


}
