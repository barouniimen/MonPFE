package org.esprit.gestion.rapports.MB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class FileDownloadBean {

	@ManagedProperty(value = "#{manageReportsByCoachBean}")
	private ManageReportsByCoachBean manageReportBean;

	private StreamedContent file;
	private String sourcePath;
	private String sourceFileName;

	@PostConstruct
	public void init() {
		System.out.println("init file upoload");
		sourcePath = manageReportBean.getSelectedReport().getFilePath()
				+ manageReportBean.getSelectedReport().getFileName();
		sourcePath = sourcePath.replaceAll("\\\\", "/");
		sourceFileName = manageReportBean.getSelectedReport().getFileName();
		System.out.println("path!!!!!!!!!: " + sourcePath);
	}

	public FileDownloadBean() {

	}

	public void findPath(ActionEvent event)  throws FileNotFoundException{
		System.out.println("upload!!!!!!!!!!!");
		InputStream stream = new FileInputStream(new File(sourcePath));
		file = new DefaultStreamedContent(stream, "application/pdf",
				sourceFileName);
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setManageReportBean(ManageReportsByCoachBean manageReportBean) {
		this.manageReportBean = manageReportBean;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
}