package nh.publy.backend.graphql;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeCoercing implements Coercing<LocalDateTime, String> {

  @Override
  public String serialize(@NotNull Object dataFetcherResult) throws CoercingSerializeException {
    if (dataFetcherResult instanceof LocalDateTime localDateTime) {
      return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
    }

    throw new CoercingSerializeException("Invalid type");
  }

  @Override
  public @NotNull LocalDateTime parseValue(@NotNull Object input) throws CoercingParseValueException {
    return null;
  }

  @Override
  public @NotNull LocalDateTime parseLiteral(@NotNull Object input) throws CoercingParseLiteralException {
    return null;
  }
}
