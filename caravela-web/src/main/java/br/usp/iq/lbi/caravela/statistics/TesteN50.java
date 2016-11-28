package br.usp.iq.lbi.caravela.statistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TesteN50 {
	
	public static void main(String[] args) {
		showN50BySampleFile("/tmp/all-300bp-contig-size-soap2.txt");
		
		
	}

	private synchronized static void showN50BySampleFile(String file) {
		List<Integer> lenghts = new ArrayList<>();
		N50 n50 = new N50();
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {

			String line;
			while ((line = br.readLine()) != null) {
				lenghts.add(Integer.parseInt(line));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(n50.calculate(lenghts));
	}
	
	

}
