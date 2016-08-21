package br.usp.iq.lbi.caravela.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sample")
public class Sample {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Treatment treatment;
	
	@Enumerated(EnumType.STRING)
	@Column(name="sample_status")
	private SampleStatus sampleStatus;
	
	private String name;
	private String description;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="sample", orphanRemoval=true)
	private List<SampleFile> sampleFiles;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="sample", orphanRemoval=true)
	private List<Contig> contigs;
	
	
	public Sample() {}
	
	public Sample(Treatment treatment, SampleStatus sampleStatus, String name, String description) {
		this.treatment = treatment;
		this.name = name;
		this.sampleStatus = sampleStatus;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	
	public Treatment getTreatment() {
		return treatment;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public SampleStatus getSampleStatus(){
		return sampleStatus;
	}
	
	public boolean isCreated(){
		return SampleStatus.CREATED.equals(this.sampleStatus);
	}
	
	public boolean isUploaded(){
		return SampleStatus.UPLOADED.equals(this.sampleStatus);
	}
	
	public boolean isProcessing(){
		return SampleStatus.PROCESSING.equals(this.sampleStatus);
	}
	
	public boolean isProcessed(){
		return SampleStatus.PROCESSED.equals(this.sampleStatus);
	}
	
	public List<SampleFile> getSampleFiles(){
		return sampleFiles;
	}
	
	public SampleFile getFileWithAllInformation(){
		return extractSampleFileByFileType(FileType.ALL_JSON);
	}
	
	public SampleFile getFileWithReadAndTaxonInformation(){
		return extractSampleFileByFileType(FileType.READ_WITH_TAXON_JSON);
	}
	
	public void toProcessed(){
		if(isProcessing()){
			this.sampleStatus = SampleStatus.PROCESSED;
		} 
	}
	
	public void toErrorToProcess(){
		if(isProcessing()){
			this.sampleStatus = SampleStatus.ERRO_TO_PROCESS;
		} 
	}
	
	public void toProcessing(){
		if(isUploaded()){
			this.sampleStatus = SampleStatus.PROCESSING;
		} 
		
	}
	
	public void toUploaded(){
		if(isCreated()){
			this.sampleStatus = SampleStatus.UPLOADED;
		} 
	}
	

	public void setSampleFiles(List<SampleFile> sampleFiles){
		this.sampleFiles = sampleFiles;
	}
	
	private SampleFile extractSampleFileByFileType(FileType fileType) {
		SampleFile sampleFile = null;
		for (SampleFile sFile : sampleFiles) {
			if(fileType.equals(sFile.getFileType())){
				sampleFile = sFile;
			}
		}
		return sampleFile;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Sample)){
			return false;
			
		}
		
		if(obj == this){
			return true;
		}
		
		Sample sample = (Sample) obj;
		
		return this.name.equals(sample.getName()) && 
				this.description.equals(sample.getDescription()) && 
				this.treatment.equals(sample.getTreatment()) && 
				this.sampleStatus.equals(sample.getSampleStatus()); 
		
	}
	
	
}
