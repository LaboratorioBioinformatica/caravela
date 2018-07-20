package br.usp.iq.lbi.caravela.statistics;

import java.util.Set;

public class ClassifiedByContextVerify {
    public static void main(String[] args) {

        Set<Result> classifiedByContext = ClassifiedByContextManagerFile
                .getClassifiedByContextWithoutMultiAlignment("/home/gianluca/Downloads/report-14-129-ZOO-DIA78-CENTRIFUGE-genus.tsv");

        System.out.println(classifiedByContext.size());





    }
}

