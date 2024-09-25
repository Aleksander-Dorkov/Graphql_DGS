package com.example.federationtest.advice;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ResultPath;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {

    private static final DefaultDataFetcherExceptionHandler DEFAULT_HANDLER = new DefaultDataFetcherExceptionHandler();

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {

        return switch (handlerParameters.getException()) {
            case NullPointerException npe -> CompletableFuture.completedFuture(
                    buildExceptionResult(
                            "An even number was generated: " + npe.getMessage(),
                            ErrorType.INTERNAL,
                            handlerParameters.getPath()
                    )
            );
            case IllegalArgumentException iae -> CompletableFuture.completedFuture(
                    buildExceptionResult(
                            "An odd number was generated: " + iae.getMessage(),
                            ErrorType.BAD_REQUEST,
                            handlerParameters.getPath()
                    )
            );
            default -> DEFAULT_HANDLER.handleException(handlerParameters);
        };
    }

    private DataFetcherExceptionHandlerResult buildExceptionResult(String message, ErrorType errorType, ResultPath path) {
        return DataFetcherExceptionHandlerResult.newResult()
                .error(TypedGraphQLError.newBuilder()
                        .message(message)
                        .path(path)
                        .errorType(errorType)
                        .build())
                .build();
    }
}
