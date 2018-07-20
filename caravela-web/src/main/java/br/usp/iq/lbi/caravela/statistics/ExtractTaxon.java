package br.usp.iq.lbi.caravela.statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ExtractTaxon {

    public static void main(String[] args) {

        Set<Result> resultList = new HashSet<>();
        String fileName = "/home/gianluca/readsOnContig-14713092.txt";

        Map<String, Taxonomy> trueGenusHashMap = TrueTaxonomyManagerFile.getTrueTaxonomy("/home/gianluca/Documents/testes-caravela/simulacao/simulacao-01-true-taxonomy.tsv");


        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(s -> {
                Taxonomy taxonomy = trueGenusHashMap.get(s.trim());
                String resultLine = new StringBuilder()
                        .append(taxonomy.getReference())
                        .append("\t")
                        .append(taxonomy.getScientificName())
                        .append("\t")
                        .append(taxonomy.getNCBITaxonomyId()).toString();
                System.out.println(resultLine);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


