package com.agh.as.master.utils;

import com.agh.as.master.model.Node;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MapReader {

    public static List<Node> readCSVMap() {
        log.info("Start reading csv map files");
        String absolute = System.getProperty("user.dir");
        String nodeFilePath = absolute.concat("/master/src/main/resources/map/nodes.csv");
        List<Node> nodes = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(nodeFilePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                nodes.add(new Node(values));
            }
        } catch (Exception e) {
            log.error("Error occured while reading nodes csv file");
            e.printStackTrace();
        }
        return nodes;
    }
}
