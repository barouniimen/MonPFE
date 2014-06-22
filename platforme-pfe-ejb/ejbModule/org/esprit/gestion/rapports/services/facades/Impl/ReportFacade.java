package org.esprit.gestion.rapports.services.facades.Impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.esprit.gestion.rapports.persistence.Comments;
import org.esprit.gestion.rapports.persistence.KeyWord;
import org.esprit.gestion.rapports.persistence.Message;
import org.esprit.gestion.rapports.persistence.MessageAccess;
import org.esprit.gestion.rapports.persistence.MessageType;
import org.esprit.gestion.rapports.persistence.Project;
import org.esprit.gestion.rapports.persistence.Report;
import org.esprit.gestion.rapports.persistence.ReportKeyWord;
import org.esprit.gestion.rapports.persistence.ReportKeyWordPk;
import org.esprit.gestion.rapports.persistence.ReportState;
import org.esprit.gestion.rapports.persistence.Student;
import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRoleType;
import org.esprit.gestion.rapports.persistence.UserMessage;
import org.esprit.gestion.rapports.persistence.UserMessagePK;
import org.esprit.gestion.rapports.persistence.ValidationState;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Util.CommentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.KeyWordsQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.MessagesQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ProjectQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportKeyWordQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.ReportQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.StudentQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;
import org.esprit.gestion.rapports.services.CRUD.Util.UserMessageQualifier;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeLocal;
import org.esprit.gestion.rapports.services.facades.Interfaces.IReportFacadeRemote;

@Stateless
public class ReportFacade implements IReportFacadeLocal, IReportFacadeRemote {

	@Inject
	@StudentQualifier
	IServiceLocal<Student> studentServ;

	@Inject
	@CommentQualifier
	IServiceLocal<Comments> commentServ;

	@Inject
	@ProjectQualifier
	IServiceLocal<Project> projServ;

	@Inject
	@ReportQualifier
	IServiceLocal<Report> reportServ;

	@Inject
	@KeyWordsQualifier
	IServiceLocal<KeyWord> keyWordServ;

	@Inject
	@ReportKeyWordQualifier
	IServiceLocal<ReportKeyWord> reportKwServ;

	@Inject
	@TeacherQualifier
	IServiceLocal<Teacher> teacherServ;
	@Inject
	@MessagesQualifier
	IServiceLocal<Message> msgServ;

	@Inject
	@UserMessageQualifier
	IServiceLocal<UserMessage> userMsgServ;

	@Override
	public String getLastVersion(int idStudent) {
		List<Report> reportsList = new ArrayList<Report>();
		String version;

		// getall reports
		reportsList = listStudentReports(idStudent);

		if (reportsList.isEmpty()) {
			return null;
		}

		else {
			version = reportsList.get(0).getVersion();
			Date lastDate = reportsList.get(0).getUploadDate();

			for (int i = 1; i < reportsList.size(); i++) {
				int compare = lastDate.compareTo(reportsList.get(i)
						.getUploadDate());

				if (compare < 0) {
					version = reportsList.get(i).getVersion();
					lastDate = reportsList.get(i).getUploadDate();
				}
			}

			return version;
		}
	}

	/*********************************** getter && setter ************************************/

	@Override
	public List<Report> listStudentReports(int idStudent) {

		List<Report> reportsList = new ArrayList<Report>();

		// retrieve student
		Student st = new Student();
		st.setId(idStudent);
		st = (Student) studentServ.retrieve(st, "ID");

		// find project
		Project proj = new Project();
		proj = st.getProject();

		if (proj != null) {
			Report report = new Report();
			report.setProject(proj);

			// find all reports linked to project
			reportsList = reportServ.retrieveList(report, "proj");

		}
		return reportsList;

	}

	@Override
	public void createReport(Report report, int idStudent,
			List<String> keyWordNames) {

		System.out.println("on create facade!!!!!!!!!!!!!!!!!!!");
		Student st = new Student();
		st.setId(idStudent);
		st = (Student) studentServ.retrieve(st, "ID");

		// create report
		reportServ.create(report);
		
		System.out.println("report created!!!!!!!!!!!!!!");
		System.out.println(report.getFileName());
		System.out.println(report.getId());

		Project proj = new Project();
		proj = st.getProject();

		if (proj != null) {
			// update proj
			List<Report> projReports = new ArrayList<Report>();
			projReports = proj.getReports();

			projReports.add(report);

			if (report.getState().equals(ReportState.DEPOSED)) {
				proj.setValidationState(ValidationState.DEPOSED);
			}

			projServ.update(proj);
		}

		if (report.getState().equals(ReportState.DEPOSED)) {
			// ajouter les mots clés
			List<ReportKeyWord> reportKws = new ArrayList<ReportKeyWord>();

			for (int i = 0; i < keyWordNames.size(); i++) {
				KeyWord kw = new KeyWord();
				kw.setName(keyWordNames.get(i));
				kw = (KeyWord) keyWordServ.retrieve(kw, "NAME");
				ReportKeyWordPk pk = new ReportKeyWordPk();
				pk.setKeyWordId(kw.getId());
				pk.setReportId(report.getId());
				ReportKeyWord reportKw = new ReportKeyWord();
				reportKw.setPk(pk);
				report.setKeyWords(reportKws);
				reportServ.update(report);
				reportKwServ.create(reportKw);
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-m-yyy");

			int idReciever = -1;

			String uploadDate = dateFormat.format(report.getUploadDate());

			// notify coach
			List<TeacherRole> roles = new ArrayList<TeacherRole>();
			roles = proj.getTeacherRoles();

			if (!(roles.isEmpty())) {
				for (int i = 0; i < roles.size(); i++) {
					if (roles.get(i).getRole()
							.equals(TeacherRoleType.ENCADRANT)) {
						idReciever = roles.get(i).getPk().getTeacherId();
					}
				}

				if (idReciever != -1) {
					Message msgToCoach = new Message();

					msgToCoach.setContent("Bonjour,\n\n L'étudiant "
							+ st.getLastName() + " " + st.getFirstName()
							+ " que vous encadrez a déposé son rapport le "
							+ uploadDate + ".\n\n Cordialement");
					msgToCoach.setIdSender(-1);
					Date sendingDate = new Date();
					msgToCoach.setSendingDate(sendingDate);
					msgToCoach.setSubject("Dépôt de rapport");
					msgToCoach.setType(MessageType.SUBMIT_EVENT_NOTIF);
					msgToCoach.setIdReceiver(idReciever);

					msgServ.create(msgToCoach);

					UserMessagePK pkCoach = new UserMessagePK();
					pkCoach.setMessageId(msgToCoach.getId());
					pkCoach.setUserId(idReciever);

					UserMessage coachMsgCx = new UserMessage();
					coachMsgCx.setAccess(MessageAccess.TOREAD);
					coachMsgCx.setPk(pkCoach);

					userMsgServ.create(coachMsgCx);

				}

			}
		}

	}

	@Override
	public boolean deleteReport(Report report) {
		// delete document
		// path == null
		// size = 0

		String path = report.getFilePath() + "\\" + report.getFileName();
		System.out.println("path: " + path);
		File file = new File(path);
		if (file.delete()) {
			report.setFilePath(null);
			report.setSize(0);
			reportServ.update(report);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void changeToFinal(Report selectedReport) {

		int idReciever = -1;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-m-yyy");

		selectedReport.setVersion("final");
		selectedReport.setState(ReportState.DEPOSED);
		Date date = new Date();
		selectedReport.setUploadDate(date);

		reportServ.update(selectedReport);

		Project proj = new Project();
		proj = selectedReport.getProject();
		proj = (Project) projServ.retrieve(proj, "ID");
		Student student = new Student();
		student = proj.getStudent();
		proj.setValidationState(ValidationState.DEPOSED);
		projServ.update(proj);

		// TODO informer admin pour affecter rapporteur
		String uploadDate = dateFormat.format(selectedReport.getUploadDate());

		List<TeacherRole> roles = new ArrayList<TeacherRole>();
		roles = proj.getTeacherRoles();
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getRole().equals(TeacherRoleType.ENCADRANT)) {
				idReciever = roles.get(i).getPk().getTeacherId();
			}
		}

		if (idReciever != -1) {
			Message msgToCoach = new Message();

			msgToCoach.setContent("Bonjour,\n\n L'étudiant "
					+ student.getLastName() + " " + student.getFirstName()
					+ " que vous encadrez a déposé son rapport le "
					+ uploadDate + ".\n\n Cordialement");
			msgToCoach.setIdSender(-1);
			Date sendingDate = new Date();
			msgToCoach.setSendingDate(sendingDate);
			msgToCoach.setSubject("Dépôt de rapport");
			msgToCoach.setType(MessageType.SUBMIT_EVENT_NOTIF);
			msgToCoach.setIdReceiver(idReciever);

			msgServ.create(msgToCoach);

			UserMessagePK pkCoach = new UserMessagePK();
			pkCoach.setMessageId(msgToCoach.getId());
			pkCoach.setUserId(idReciever);

			UserMessage coachMsgCx = new UserMessage();
			coachMsgCx.setAccess(MessageAccess.TOREAD);
			coachMsgCx.setPk(pkCoach);

			userMsgServ.create(coachMsgCx);

		}

	}

	@Override
	public void addComment(Report report, int idCoach, String comments) {
		report = (Report) reportServ.retrieve(report, "ID");

		Comments comment = new Comments();
		comment.setContent(comments);
		Date date = new Date();
		comment.setDate(date);
		Teacher coach = new Teacher();
		coach.setId(idCoach);
		coach = (Teacher) teacherServ.retrieve(coach, "ID");

		comment.setEditorName(coach.getLastName() + " " + coach.getFirstName());
		comment.setReport(report);

		commentServ.create(comment);

		List<Comments> listComment = new ArrayList<Comments>();
		listComment = report.getComments();

		listComment.add(comment);

		report.setComments(listComment);

		reportServ.update(report);

		// notify student

		Project proj = new Project();
		proj = report.getProject();

		proj = (Project) projServ.retrieve(proj, "ID");

		Student st = new Student();
		st = proj.getStudent();

		Message msgStudent = new Message();
		msgStudent
				.setContent("Bonjour,\n\n Votre encadrant a commenté le document ["
						+ report.getFileName()
						+ "] que vous avez téléchargé le ["
						+ report.getUploadDate()
						+ "].\n Commentaire:\n "
						+ comment.getContent() + "\n\n\n Cordialement");

		msgStudent.setIdReceiver(st.getId());
		msgStudent.setIdSender(-1);
		msgStudent.setIncludedRef(report.getId());
		Date sendingDate = new Date();
		msgStudent.setSendingDate(sendingDate);
		msgStudent.setSubject("Nouveau commentaire");
		msgStudent.setType(MessageType.ByCOACH);

		msgServ.create(msgStudent);

		UserMessagePK pkStudent = new UserMessagePK();
		pkStudent.setMessageId(msgStudent.getId());
		pkStudent.setUserId(st.getId());

		UserMessage stMsgCx = new UserMessage();
		stMsgCx.setAccess(MessageAccess.TOREAD);
		stMsgCx.setPk(pkStudent);

		userMsgServ.create(stMsgCx);

	}

	@Override
	public void update(Report report) {
		reportServ.update(report);

	}

	@Override
	public Report findReport(Report report) {

		report = (Report) reportServ.retrieve(report, "ID");
		List<Comments> commList = new ArrayList<Comments>();

		Comments comment = new Comments();
		comment.setReport(report);
		commList = commentServ.retrieveList(comment, "report");

		report.setComments(commList);

		return report;
	}

}
