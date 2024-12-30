package io.positivinh.virtuoso.data.mongodb.autoconfigure.configuration

import org.springframework.core.convert.converter.Converter
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

object ZonedDateTimeReadConverter : Converter<Date, ZonedDateTime> {

    override fun convert(source: Date): ZonedDateTime? = source.toInstant().atZone(ZoneOffset.UTC)
}

object ZonedDateTimeWriteConverter : Converter<ZonedDateTime, Date> {

    override fun convert(source: ZonedDateTime): Date? = Date.from(source.toInstant())
}

object OffsetDateTimeReadConverter : Converter<Date, OffsetDateTime> {

    override fun convert(source: Date): OffsetDateTime? = source.toInstant().atOffset(ZoneOffset.UTC)
}

object OffsetDateTimeWriteConverter : Converter<OffsetDateTime, Date> {

    override fun convert(source: OffsetDateTime): Date? = Date.from(source.toInstant())
}
