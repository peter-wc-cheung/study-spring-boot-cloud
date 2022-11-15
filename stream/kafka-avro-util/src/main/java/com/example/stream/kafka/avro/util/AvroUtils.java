package com.example.stream.kafka.avro.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.avro.AvroFactory;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@UtilityClass
public class AvroUtils {

    public static <T> Schema getSchema(Class<T> clazz) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper(new AvroFactory());
        AvroSchemaGenerator gen = new AvroSchemaGenerator();
        mapper.acceptJsonFormatVisitor(clazz, gen);
        AvroSchema schemaWrapper = gen.getGeneratedSchema();

        return schemaWrapper.getAvroSchema();
    }

    public static <T> GenericRecord toGenericRecord(T object) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new AvroFactory());
        AvroSchemaGenerator gen = new AvroSchemaGenerator();
        mapper.acceptJsonFormatVisitor(object.getClass(), gen);
        AvroSchema schemaWrapper = gen.getGeneratedSchema();

        Schema avroSchema = schemaWrapper.getAvroSchema();

        byte[] avroData = mapper.writer(schemaWrapper).writeValueAsBytes(object);
        GenericDatumReader<GenericRecord> datumReader = new GenericDatumReader<>(avroSchema);
        SeekableByteArrayInput inputStream = new SeekableByteArrayInput(avroData);
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);

        return datumReader.read(null, decoder);
    }

    public static <T> T toObject(GenericRecord genericRecord, Class<T> toClass) throws JsonMappingException {
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(genericRecord.getSchema());
        ObjectMapper mapper = new ObjectMapper(new AvroFactory());
        AvroSchemaGenerator gen = new AvroSchemaGenerator();
        mapper.acceptJsonFormatVisitor(toClass, gen);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
            writer.write(genericRecord, encoder);
            encoder.flush();

            byte[] bytes = outputStream.toByteArray();

            return mapper.readerFor(toClass)
                    .with(new AvroSchema(genericRecord.getSchema()))
                    .readValue(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(GenericRecord genericRecord) throws JsonMappingException {
        ObjectMapper mapper = new ObjectMapper(new AvroFactory());

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DatumWriter<GenericRecord> writer = new GenericDatumWriter<>(genericRecord.getSchema());
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
            writer.write(genericRecord, encoder);
            encoder.flush();

            byte[] bytes = outputStream.toByteArray();

            return mapper.readerFor(ObjectNode.class)
                    .with(new AvroSchema(genericRecord.getSchema()))
                    .readValue(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] convertToJson(GenericRecord record) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JsonEncoder jsonEncoder = EncoderFactory.get().jsonEncoder(record.getSchema(), outputStream);
            DatumWriter<GenericRecord> writer = record instanceof SpecificRecord ?
                    new SpecificDatumWriter<>(record.getSchema()) :
                    new GenericDatumWriter<>(record.getSchema());
            writer.write(record, jsonEncoder);
            jsonEncoder.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert to JSON.", e);
        }
    }

}
