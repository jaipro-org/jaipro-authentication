package com.bindord.eureka.auth.validator;

import java.util.concurrent.CompletableFuture;

public interface Validator {

    CompletableFuture<Void> validateUUIDFormat(String uuid);
}
