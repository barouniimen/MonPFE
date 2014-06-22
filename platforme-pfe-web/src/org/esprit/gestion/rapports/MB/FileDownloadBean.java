package org.esprit.gestion.rapports.MB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class FileDownloadBean {

	@ManagedProperty(value = "#{tabViewIndexBean}")
	private TabViewIndexBean tabViewBean;

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

	}

	public FileDownloadBean() {

	}

	public void findPath(ActionEvent event) throws FileNotFoundException {
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

	public void setTabViewBean(TabViewIndexBean tabViewBean) {
		this.tabViewBean = tabViewBean;
	}
}