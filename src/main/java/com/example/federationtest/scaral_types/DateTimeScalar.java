package com.example.federationtest.scaral_types;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@DgsScalar(name = "DateTime")
public class DateTimeScalar implements Coercing<LocalDateTime, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Nullable
    @Override
    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof LocalDateTime dateTime) {
            return dateTime.format(formatter);
        } else {
            throw new CoercingSerializeException("Expected a LocalDateTime object.");
        }
    }

    @Nullable
    @Override
    public LocalDateTime parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
        try {
            if (input instanceof String str) {
                return LocalDateTime.parse(str, formatter);
            }
            throw new CoercingParseValueException("Expected a String");
        } catch (DateTimeParseException e) {
            throw new CoercingParseValueException(String.format("Not a valid datetime: '%s'.", input), e);
        }
    }

    @Nullable
    @Override
    public LocalDateTime parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale)
            throws CoercingParseLiteralException {
        if (input instanceof StringValue stringValue) {
            try {
                return LocalDateTime.parse(stringValue.getValue(), formatter);
            } catch (DateTimeParseException e) {
                throw new CoercingParseLiteralException("Unable to parse datetime from literal", e);
            }
        }
        throw new CoercingParseLiteralException("Expected a StringValue.");
    }
}
