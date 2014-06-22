package org.esprit.gestion.rapports.MB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.esprit.gestion.rapports.persistence.Domain;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.services.facades.Interfaces.IDomainFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IProjectFacadeLocal;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@ViewScoped
public class TeacherStatisticsBean {

	private PieChartModel pieModelCoach;

	private PieChartModel pieModelCorrector;

	private boolean renderCoachStat;

	private boolean renderCorrectorStat;

	@ManagedProperty(value = "#{coachSpaceBean}")
	private CoachSpaceBean coachSpaceBean;

	@ManagedProperty(value = "#{authenticationBean}")
	private AuthenticationBean authBean;

	@EJB
	private IDomainFacadeLocal domFacade;
	@EJB
	private IProjectFacadeLocal projFacade;

	/************************************** init method **********************************/
	@PostConstruct
	public void init() {
		
		List<String> domainsCoach = new ArrayList<String>();

		List<Project> coachedProj = new ArrayList<Project>();
		coachedProj = coachSpaceBean.getCoachedProjList();
		
		

		if (coachedProj == null) {
			renderCoachStat = false;
		} else {
			renderCoachStat = true;

			for (int i = 0; i < coachedProj.size(); i++) {

				List<Domain> projDomainCoach = new ArrayList<Domain>();
				projDomainCoach = domFacade.listProjectDomain(coachedProj
						.get(i));

				if (!(projDomainCoach.isEmpty())) {
					for (int j = 0; j < projDomainCoach.size(); j++) {
						domainsCoach
								.add(projDomainCoach.get(j).getDomainName());

					}
				}
			}
		}

		// hashset to remove duplicated values!
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(domainsCoach);

		List<String> domainsNamesCoach = new ArrayList<String>();
		domainsNamesCoach.addAll(hs);

		pieModelCoach = new PieChartModel();

		for (int j = 0; j < domainsNamesCoach.size(); j++) {
			int count = 0;
			for (int i = 0; i < domainsCoach.size(); i++) {
				if (domainsNamesCoach.get(j).equals(domainsCoach.get(i))) {
					count = count + 1;
				}
			}
			pieModelCoach.set(domainsNamesCoach.get(j), count);

		}

		List<Project> correctorProjList = new ArrayList<Project>();
		correctorProjList = projFacade.listProjCorrector(authBean.getUser()
				.getId());
		if (correctorProjList.isEmpty()) {
			
			renderCorrectorStat = false;
		}

		else {

			List<String> domainsCorrector = new ArrayList<String>();

			for (int i = 0; i < correctorProjList.size(); i++) {

				List<Domain> projDomainCorrector = new ArrayList<Domain>();
				projDomainCorrector = domFacade
						.listProjectDomain(correctorProjList.get(i));

				if (!(projDomainCorrector.isEmpty())) {
					for (int j = 0; j < projDomainCorrector.size(); j++) {
						domainsCoach.add(projDomainCorrector.get(j)
								.getDomainName());

					}
				}
			}

			// hashset to remove duplicated values!
			HashSet<String> hsCorrector = new HashSet<String>();
			hsCorrector.addAll(domainsCoach);

			List<String> domainsNamesCorrector = new ArrayList<String>();
			domainsNamesCorrector.addAll(hs);

			pieModelCorrector = new PieChartModel();

			for (int j = 0; j < domainsNamesCorrector.size(); j++) {
				int countCorrecotr = 0;
				for (int i = 0; i < domainsCorrector.size(); i++) {
					if (domainsNamesCorrector.get(j).equals(
							domainsCorrector.get(i))) {
						countCorrecotr = countCorrecotr + 1;
					}
				}
				pieModelCorrector.set(domainsNamesCorrector.get(j),
						countCorrecotr);
			}

		}

	}

	/********************************* getter && setter ****************************************/

	public void setCoachSpaceBean(CoachSpaceBean coachSpaceBean) {
		this.coachSpaceBean = coachSpaceBean;
	}

	public void setAuthBean(AuthenticationBean authBean) {
		this.authBean = authBean;
	}

	public PieChartModel getPieModelCorrector() {
		return pieModelCorrector;
	}

	public void setPieModelCorrector(PieChartModel pieModelCorrector) {
		this.pieModelCorrector = pieModelCorrector;
	}

	public PieChartModel getPieModelCoach() {
		return pieModelCoach;
	}

	public void setPieModelCoach(PieChartModel pieModelCoach) {
		this.pieModelCoach = pieModelCoach;
	}

	public boolean isRenderCoachStat() {
		return renderCoachStat;
	}

	public void setRenderCoachStat(boolean renderCoachStat) {
		this.renderCoachStat = renderCoachStat;
	}

	public boolean isRenderCorrectorStat() {
		return renderCorrectorStat;
	}

	public void setRenderCorrectorStat(boolean renderCorrectorStat) {
		this.renderCorrectorStat = renderCorrectorStat;
	}

}
