package com.NoBrokerage.NoBroDirectAI.repository;

import com.NoBrokerage.NoBroDirectAI.model.Property;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CsvPropertyRepository {

    private final List<Property> properties = new ArrayList<>();

    @PostConstruct
    public void init() {
        String path = "src/main/resources/Data/ProjectInformationCombine.csv";
        log.info("Initializing CsvPropertyRepository from file: {}", path);

        try (Reader reader = Files.newBufferedReader(Paths.get(path));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreSurroundingSpaces()
                     .withTrim())) {

            for (CSVRecord record : csvParser) {
                try {
                    Property p = Property.builder()
                            .projectName(record.get(2).trim())
                            .propertyType(record.get(1).trim())
                            .bhk(record.get(16).trim())
                            .landmark(record.get(12).trim())
                            .bathrooms(parseInt(record.get(19)))
                            .balconies(parseInt(record.get(22)))
                            .furnishingType(record.get(23).trim())
                            .price(parseLong(record.get(29)))
                            .status(record.get(5).trim())
                            .fullAddress(record.get(13).trim())
                            .ctaSlug(record.get(4).trim())
                            .build();
                    properties.add(p);
                } catch (Exception e) {
                    log.warn("Skipping malformed CSV record: {}", record);
                }
            }

            log.info("✅ Loaded {} properties from CSV successfully", properties.size());

        } catch (IOException e) {
            log.error("❌ Error reading CSV file: {}", e.getMessage(), e);
        }
    }

    public List<Property> findAll() {
        return properties;
    }

    private Integer parseInt(String s) {
        try {
            return (s == null || s.isEmpty()) ? null : Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }

    private Long parseLong(String s) {
        try {
            return (s == null || s.isEmpty()) ? null : Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }
}
