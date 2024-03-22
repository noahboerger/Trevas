package fr.insee.vtl.sdmx;

import fr.insee.vtl.csv.CSVDataset;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

import static fr.insee.vtl.model.Structured.DataStructure;

public class DataStructureTest {

    // https://github.com/sdmx-twg/sdmx-ml/tree/master/samples
    @Test
    public void buildDataset() throws IOException {
        TrevasSDMXUtils u = new TrevasSDMXUtils();
        DataStructure structure = u.buildStructureFromSDMX3("src/test/resources/DSD_BPE_TOWN.xml", "BPE_CUBE_2021");

        var dataset = new CSVDataset(structure, new FileReader("src/test/resources/BPE_TOWN_SAMPLE.csv"));

        dataset.getDataAsMap().forEach(System.out::println);
    }
}
