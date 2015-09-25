package br.usp.iq.lbi.caravela.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sample_file")
public class SampleFile {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne()
	private Sample sample;
	
	@Enumerated(EnumType.STRING)
	@Column(name="file_type")
	private FileType fileType;
	
	@Enumerated(EnumType.STRING)
	@Column(name="file_status")
	private FileStatus fileStatus;
	
	@Column(name="file_path")
	private String filePath;
	
	
	public SampleFile(){}
	
	public SampleFile(Sample sample, FileType fileType, FileStatus fileStatus, String providerName, String filePath){
		this.sample = sample;
		this.fileType = fileType;
		this.fileStatus = fileStatus;
		this.filePath = filePath;
	}

	public Long getId() {
		return id;
	}

	public Sample getSample() {
		return sample;
	}

	public FileType getFileType() {
		return fileType;
	}
	
	public FileStatus getFileStatus() {
		return fileStatus;
	}

	public String getFilePath() {
		return filePath;
	}
	
	public boolean isAll(){
		return FileType.ALL_JSON.equals(this.fileType);
	}
	
	public boolean isReadWithTaxon(){
		return FileType.READ_WITH_TAXON_JSON.equals(this.fileType);
	}
	
	public boolean isUploaded(){
		return FileStatus.UPLOADED.equals(this.fileStatus);
	}
	
	
	public void toProccessed(){
		if(FileStatus.UPLOADED.equals(this.getFileStatus()) || FileStatus.LOADED.equals(this.getFileStatus()) ){
			this.fileStatus = FileStatus.PROCCESSED;
		} 
	}
	
	public void toLoaded(){
		if(FileStatus.UPLOADED.equals(this.getFileStatus())){
			this.fileStatus = FileStatus.LOADED;
		}
	}
	
}
