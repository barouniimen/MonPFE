package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.TeacherRole;
import org.esprit.gestion.rapports.persistence.TeacherRolePK;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.TecherRoleQualifier;

@Stateless
@TecherRoleQualifier
public class TeacherRoleService implements IServiceLocal<TeacherRole>,
		IServiceRemote<TeacherRole> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		em.remove(object);

	}

	@Override
	public void create(Object object) {
		em.persist(object);
	}

	@Override
	public Object retrieve(Object object, String searchBy) {

		TeacherRole roleFound = null;

		/****************** search by PK (object == TeacherRolePK) ***********************/
		if (searchBy.equals("PK")) {
			TypedQuery<TeacherRole> query = em.createNamedQuery(
					"Teacherrole.findByTeacherIdANDProjectId",TeacherRole.class);
			query.setParameter("teacherId",((TeacherRolePK) object).getTeacherId());
			query.setParameter("projectId",((TeacherRolePK) object).getProjectId());
			
				roleFound = query.getSingleResult();
			
		}
		
		else if(searchBy.equals("roleAndProj") ){
			TypedQuery<TeacherRole> query = em.createNamedQuery(
					"Teacherrole.findByRoleAndProjectId",TeacherRole.class);
			query.setParameter("role",((TeacherRole) object).getRole());
			query.setParameter("projectId",((TeacherRolePK) object).getProjectId());
			try {
				roleFound = query.getSingleResult();
			} catch (NoResultException nullResult) {
				System.out.println("introuvable role searched!!!!");
				return null;
			}
		}

		return roleFound;
	}

	@Override
	public List<TeacherRole> retrieveList(Object object, String searchBy) {
		List<TeacherRole >listRoles = null;
		
		
		if(searchBy.equals("ROLE")){
			TypedQuery<TeacherRole> query = em.createNamedQuery("Teacherrole.findByRole",
					TeacherRole.class);
			query.setParameter("role", ((TeacherRole) object).getRole());
			listRoles = query.getResultList();

		}
		
		else if(searchBy.equals("ALL")){
			TypedQuery<TeacherRole> query = em.createNamedQuery("Teacherrole.findAll",
					TeacherRole.class);
			listRoles = query.getResultList();
		}
		
		else if(searchBy.equals("RoleAndCoach")){
			
			TypedQuery<TeacherRole> query = em.createNamedQuery("Teacherrole.findByRoleAndTeacherId",
					TeacherRole.class);
			query.setParameter("role", ((TeacherRole) object).getRole());
			query.setParameter("teacherId", ((TeacherRole) object).getPk().getTeacherId());
			listRoles = query.getResultList();
			
		}
		
		
		else if(searchBy.equals("projId")){
			TypedQuery<TeacherRole> query = em.createNamedQuery("Teacherrole.findByProjectId",
					TeacherRole.class);
			query.setParameter("projectId", ((TeacherRole) object).getPk().getProjectId());
			listRoles = query.getResultList();
			
		}
		
		return listRoles;
	}

	@Override
	public void update(Object object) {
		throw new UnsupportedOperationException("pas encore implémentée");

	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("pas encore implémentée");

	}

}
