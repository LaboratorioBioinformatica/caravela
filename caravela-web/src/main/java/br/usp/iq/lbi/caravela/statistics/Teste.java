package br.usp.iq.lbi.caravela.statistics;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Teste {

    public static final String TAB = "\t";


    public static void main(String[] args) {

        String datasetTaxonomyPath = "/home/gianluca/Documents/testes-caravela/simulacao/";
        String datasetResultPath = "/home/gianluca/Downloads/report-caravela-v2/";

        String datasetTemplateName = "DATASET[X1]---[X2]";


        String centrifuge = "CENTRIFUGE";
        String kraken = "KRAKEN";
        String clark = "CLARK";

        StringBuilder header = new StringBuilder()
                .append("Clssificador")
                .append(TAB)
                .append("Total")
                .append(TAB)
                .append("Número de acertos")
                .append(TAB)
                .append("Taxa de Acerto")
                .append(TAB)
                .append("Número de erros")
                .append(TAB)
                .append("Taxa de Erro");

        System.out.println(header);

        for (int i = 1; i <= 10; i++) {
            String numberOfDataSet = String.valueOf(i);

            System.out.println("DATASET: " + numberOfDataSet);

            String realTaxonomy;

            if (Integer.valueOf(numberOfDataSet) <= 9) {
                realTaxonomy = searchFilePath(datasetTaxonomyPath, "0" + numberOfDataSet);
            } else {
                realTaxonomy = searchFilePath(datasetTaxonomyPath, numberOfDataSet);
            }


            String datasetCENTRIFUGE = searchFilePath(datasetResultPath, datasetTemplateName.replace("[X1]", numberOfDataSet).replace("[X2]", centrifuge));
            String datasetKRAKEN = searchFilePath(datasetResultPath, datasetTemplateName.replace("[X1]", numberOfDataSet).replace("[X2]", kraken));
            String datasetCLARK = searchFilePath(datasetResultPath, datasetTemplateName.replace("[X1]", numberOfDataSet).replace("[X2]", clark));

            Map<String, Taxonomy> trueTaxonomy = TrueTaxonomyManagerFile.getTrueTaxonomy(realTaxonomy);

            verify(centrifuge, datasetCENTRIFUGE, trueTaxonomy);
            verify(kraken, datasetKRAKEN, trueTaxonomy);
            verify(clark, datasetCLARK, trueTaxonomy);
            System.out.println("###################################################################");

        }


    }

    private static String searchFilePath(String path, String key) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String fileNamePath = "";

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().contains(key)) {
                    if (fileNamePath.isEmpty()) {
                        fileNamePath = listOfFiles[i].getPath();
                    } else {
                        throw new InvalidParameterException("There are more than one file path for this key");
                    }
                }
            }
        }
        return fileNamePath;
    }


    private static void verify(String nome, String fileName, Map<String, Taxonomy> genusHashMap) {

        Set<Result> resultList = ClassifiedByContextManagerFile.getClassifiedByContext(fileName, true);


        Integer total = resultList.size();

        Integer novoTotal = 0;
        Integer acertos = 0;
        Integer erros = 0;
        Integer sequenceNotFound = 0;

        ArrayList<String> resultClassTeste = new ArrayList<>();

        for (Result result : resultList) {
            Taxonomy trueGenus = genusHashMap.get(result.getReference());
            if (trueGenus == null) {
//                System.out.println("Sequence not found " + result.getReference());
                sequenceNotFound++;
                continue;
            }
            if (result.getCTGenus() >= 0.4F && result.getCTVGenus() >= 0.7F && result.getBorder().equals(0) && result.getFlagAlignment() < 256) {

                novoTotal++;

                if (trueGenus.getNCBITaxonomyId().equals(result.getNCBITaxonomiyId())) {
                    acertos++;
                    resultClassTeste.add(createLineResult(result, 1));
                } else {
//                    System.out.println(result.getCTGenus()+" - "+ result.getCTVGenus() +" - "+ result.getBorder());
                    erros++;
                    resultClassTeste.add(createLineResult(result, -1));

                }
            }


        }

        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(fileName, ".tmp");
            FileWriter writer = new FileWriter(tmpFile);

            for (String resultLine : resultClassTeste) {
                writer.write(resultLine);
                writer.write("\n");
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        Float taxaDeAcerto = Float.valueOf(acertos) / Float.valueOf(novoTotal);
        Float taxaDeErro = Float.valueOf(erros) / Float.valueOf(novoTotal);


        StringBuilder resultValues = new StringBuilder()
                .append(nome)
                .append(TAB)
                .append(novoTotal)
                .append(TAB)
                .append(acertos)
                .append(TAB)
                .append(taxaDeAcerto)
                .append(TAB)
                .append(erros)
                .append(TAB)
                .append(taxaDeErro);


        System.out.println(resultValues);
    }

    private static synchronized String createLineResult(Result result, Integer value) {
        return new StringBuilder()
                .append(result.getContigReference())
                .append("\t")
                .append(result.getReference())
                .append("\t")
                .append(result.getCTGenus())
                .append("\t")
                .append(result.getCTVGenus())
                .append("\t")
                .append(result.getBorder())
                .append("\t")
                .append(result.getFlagAlignment())
                .append("\t")
                .append(result.getCigar())
                .append("\t")
                .append(value).toString();
    }


}

