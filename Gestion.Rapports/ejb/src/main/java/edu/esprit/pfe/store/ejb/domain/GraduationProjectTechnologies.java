package edu.esprit.pfe.store.ejb.domain;

import edu.esprit.pfe.store.ejb.domain.GraduationProjectReport;
import edu.esprit.pfe.store.ejb.domain.Technologie;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: GraduationProjectTechnologies
 * 
 */
@Entity
@Table(name = "eps_gpr_techno")
public class GraduationProjectTechnologies implements Serializable {

	@ManyToOne
	@JoinColumn(name = "REPORT_ID_PK", referencedColumnName = "GPR_ID", insertable = false, updatable = false)
	private GraduationProjectReport report;
	@ManyToOne
	@JoinColumn(name = "TECHNO_ID_PK", referencedColumnName = "TECHNO_ID", insertable = false, updatable = false)
	private Technologie thechnologie;
	@Column(name = "GPR_TECHNO_LEVEL")
	private int level;
	@EmbeddedId
	private GraduationProjectTechnologiesPK gptPK;
	private static final long serialVersionUID = 1L;

	public GraduationProjectTechnologies() {
		super();
	}

	public Technologie getThechnologie() {
		return this.thechnologie;
	}

	public void setThechnologie(Technologie thechnologie) {
		this.thechnologie = thechnologie;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public GraduationProjectReport getReport() {
		return report;
	}

	public void setReport(GraduationProjectReport report) {
		this.report = report;
	}

	public GraduationProjectTechnologies(GraduationProjectReport report,
			Technologie thechnologie, int level) {
		super();
		this.report = report;
		this.thechnologie = thechnologie;
		this.level = level;
		this.gptPK = new GraduationProjectTechnologiesPK(report.getIdGPR(),
				thechnologie.getIdTechno());
	}

}
