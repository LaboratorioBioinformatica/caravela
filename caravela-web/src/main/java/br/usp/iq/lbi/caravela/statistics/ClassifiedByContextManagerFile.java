package br.usp.iq.lbi.caravela.statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassifiedByContextManagerFile {

    public static final String TAB = "\t";

    public static final int HEADER_INDEX = 1;
    public static final int REFERENCE_INDEX = 0;
    public static final int NCBI_TAXONOMY_ID_INDEX = 1;
    public static final int SCIENTIFIC_NAME_INDEX = 2;
    public static final int CT_GENUS_INDEX = 3;
    public static final int CTV_GENUS_INDEX = 4;
    public static final int BORDER_INDEX = 5;
    public static final int CONTIG_REFERENCE_INDEX = 6;
    public static final int FLAG_ALIGNMENT_INDEX = 7;
    public static final int CIGAR = 8;

    public static Set<Result> getClassifiedByContext(String fileName, boolean hasflagAndCigar){
        Set<Result> resultList = new HashSet<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            resultList = stream.skip(HEADER_INDEX).map(line -> {
                String[] resultArray = line.split(TAB);
                if(hasflagAndCigar){
                    return getResultWithFlagAndCigar(resultArray);
                } else {
                    return getResult(resultArray);
                }

            }).collect(Collectors.toSet());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;

    }

    public static Set<Result> getClassifiedByContextWithoutMultiAlignment(String fileName){
        Set<Result> resultList = new HashSet<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            resultList = stream.skip(HEADER_INDEX).map(line -> {
                String[] resultArray = line.split(TAB);
                    if(Integer.parseInt(resultArray[FLAG_ALIGNMENT_INDEX]) < 256 ){
                        return getResultWithFlagAndCigar(resultArray);
                    }
                    return null;
            }).collect(Collectors.toSet());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;

    }

    private static Result getResult(String[] resultArray) {
        return new Result(resultArray[REFERENCE_INDEX],
                Long.parseLong(resultArray[NCBI_TAXONOMY_ID_INDEX]),
                resultArray[SCIENTIFIC_NAME_INDEX],
                Float.valueOf(resultArray[CT_GENUS_INDEX].replace(",",".")),
                Float.valueOf(resultArray[CTV_GENUS_INDEX].replace(",",".")),
                Integer.parseInt(resultArray[BORDER_INDEX]),
                resultArray[CONTIG_REFERENCE_INDEX]);
    }

    private static Result getResultWithFlagAndCigar(String[] resultArray) {
        return new Result(resultArray[REFERENCE_INDEX],
                Long.parseLong(resultArray[NCBI_TAXONOMY_ID_INDEX]),
                resultArray[SCIENTIFIC_NAME_INDEX],
                Float.valueOf(resultArray[CT_GENUS_INDEX].replace(",",".")),
                Float.valueOf(resultArray[CTV_GENUS_INDEX].replace(",",".")),
                Integer.parseInt(resultArray[BORDER_INDEX]),
                resultArray[CONTIG_REFERENCE_INDEX],
                Integer.parseInt(resultArray[FLAG_ALIGNMENT_INDEX]),
                        resultArray[CIGAR]);
    }
}
