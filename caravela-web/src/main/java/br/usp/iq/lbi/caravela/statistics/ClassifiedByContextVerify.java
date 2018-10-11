package br.usp.iq.lbi.caravela.statistics;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassifiedByContextVerify {


    public static void main(String[] args) {


        final Set<String> zc3bFiles = searchFiles("/home/gianluca/Downloads/report-caravela/zc3-b/", "");

        zc3bFiles.forEach(file -> {

            final Set<Result> classifiedByContext = ClassifiedByContextManagerFile
                    .getClassifiedByContextWithoutMultiAlignment(file);
            final Set<Result> softFilterResults = softFilter(classifiedByContext);

            System.out.println(file + "\t" + softFilterResults.size());

        });

    }

    private static Set<Result> softFilter(Set<Result> classifiedByContext) {
        Set<Result> classifiedByContextResult = new HashSet<>();

        for (Result result: classifiedByContext) {
            if(result != null && result.getCTGenus() >= 0.4F && result.getCTVGenus() >= 0.4F && result.getBorder() <= 1){
                classifiedByContextResult.add(result);
            }

        }
        return classifiedByContextResult;
    }

    private static Set<String> searchFiles(String path, String key) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        Set<String> foundFiles = new HashSet<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().contains(key)) {
                    foundFiles.add(listOfFiles[i].getPath());
                }
            }
        }

        return foundFiles;
    }

}

