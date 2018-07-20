package br.usp.iq.lbi.caravela.statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Teste2 {

    public static final int REFERENCE_INDEX = 0;
    public static final int NCBI_TAXONOMY_ID_INDEX = 1;
    public static final int SCIENTIFIC_NAME_INDEX = 2;
    public static final int CT_GENUS_INDEX = 3;
    public static final int CTV_GENUS_INDEX = 4;
    public static final int BORDER_INDEX = 5;
    public static final int CONTIG_REFERENCE_INDEX = 6;

    public static void main(String[] args) {
        Set<Result> resultList = new HashSet<>();

        String fileName = "/home/gianluca/Downloads/report-1-8-ZC4-day-78-soap2-mytaxa-genus.tsv";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            resultList = stream.skip(1).map(line->{
                String[] resultArray = line.split("\t");
                return  new Result(resultArray[REFERENCE_INDEX],
                        Long.parseLong(resultArray[NCBI_TAXONOMY_ID_INDEX]),
                        resultArray[SCIENTIFIC_NAME_INDEX],
                        Float.valueOf(resultArray[CT_GENUS_INDEX].replace(",",".")),
                        Float.valueOf(resultArray[CTV_GENUS_INDEX].replace(",",".")),
                        Integer.parseInt(resultArray[BORDER_INDEX]),
                        resultArray[CONTIG_REFERENCE_INDEX]);

            }).collect(Collectors.toSet());


        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total Geral: " + resultList.size());

        Integer totalFiltrado = 0;
        for (Result result: resultList) {
            if(result.getCTGenus() >= 0.4F && result.getCTVGenus() >= 0.7F && result.getBorder().equals(0)){
                totalFiltrado++;
            }
        }

        System.out.println("Total Filtrado: " + totalFiltrado);






    }
}
