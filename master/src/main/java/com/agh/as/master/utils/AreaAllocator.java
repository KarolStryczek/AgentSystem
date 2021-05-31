package com.agh.as.master.utils;

import com.agh.as.master.model.Area;
import com.agh.as.master.model.Map;
import com.agh.as.master.model.Node;
import com.opencsv.CSVReader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AreaAllocator {


    public static List<Area> getAreasFormConfig() {
        log.info("Start reading csv areas files");
        String absolute = System.getProperty("user.dir");
        String areaFilePath = absolute.concat("/master/src/main/resources/map/areas.csv");
        List<Area> areas = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(areaFilePath))) {
            String[] values;
            Area newArea = null;
            while ((values = csvReader.readNext()) != null) {
                if (newArea != null && !newArea.getAreaId().equals(Integer.valueOf(values[0]))) {
                    areas.add(newArea);
                    newArea = null;
                }
                if(newArea == null) {
                    newArea = new Area(Integer.valueOf(values[0]));
                }
                newArea.addNode(new Node(values[1]));
            }
            areas.add(newArea);
        } catch (Exception e) {
            log.error("Error occured while reading nodes csv file");
            e.printStackTrace();
        }
        return areas;
    }

}
