package br.usp.iq.lbi.caravela.statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrueTaxonomyManagerFile {


    public static final String TAB = "\t";
    public static final int REFERENCE_INDEX = 0;
    public static final int GENUS_INDEX = 6;
    public static final int RANK_INDEX = 0;
    public static final int SCIENTIFICA_NAME_INDEX = 1;
    public static final int NCBI_TAXONOMY_ID_INDEX = 2;

    public static Map<String, Taxonomy> getTrueTaxonomy (String trueTaxonomyFilePath){
        Set<Taxonomy> genusList = new HashSet<>();

        try (Stream<String> stream = Files.lines(Paths.get(trueTaxonomyFilePath))) {
            genusList = stream.map(line -> {
                String[] lineArray = line.split(TAB);
                String reference = lineArray[REFERENCE_INDEX];

                try {
                    String[] genusArray = lineArray[GENUS_INDEX].split("\\|");
                    return new Taxonomy(reference, genusArray[RANK_INDEX], genusArray[SCIENTIFICA_NAME_INDEX], Long.parseLong(genusArray[NCBI_TAXONOMY_ID_INDEX]));
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    return new Taxonomy(reference, "NO", "NO", 0L);
                }

            }).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Taxonomy> genusHashMap = new HashMap<>();

        for (Taxonomy taxonomy : genusList) {
            genusHashMap.put(taxonomy.getReference(), taxonomy);
        }
        return genusHashMap;
    }
}
