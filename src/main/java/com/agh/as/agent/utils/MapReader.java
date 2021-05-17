package com.agh.as.agent.utils;

import com.agh.as.agent.model.Branch;
import com.agh.as.agent.model.Node;
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
        String nodeFilePath = absolute.concat("/agent/src/main/resources/map/nodes.csv");
        String branchFilePath = absolute.concat("/agent/src/main/resources/map/branches.csv");
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
        try (CSVReader csvReader = new CSVReader(new FileReader(branchFilePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                Branch branch = new Branch(values[2]);
                Integer sourceNode = Integer.valueOf(values[0]);
                Integer targetNode = Integer.valueOf(values[1]);
                Node source = nodes.get(sourceNode - 1);
                source.addBranch(branch);
                Node target = nodes.get(targetNode - 1);
                target.addBranch(branch);
            }
        } catch (Exception e) {
            log.error("Error occured while reading branches csv file");
            e.printStackTrace();
        }
        return nodes;
    }


}
