package org.esprit.gestion.rapports.services.CRUD.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.esprit.gestion.rapports.persistence.Teacher;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceLocal;
import org.esprit.gestion.rapports.services.CRUD.Interfaces.IServiceRemote;
import org.esprit.gestion.rapports.services.CRUD.Util.TeacherQualifier;

@TeacherQualifier
@Stateless
public class TeacherService implements IServiceLocal<Teacher>,IServiceRemote<Teacher> {

	@PersistenceContext
	EntityManager em;

	@Override
	public void delete(Object object) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");

	}

	@Override
	public void create(Object object) {
		try {
			em.persist(object);
			} catch (EntityExistsException exist) {
			System.out.println("!!!!!!!!!!!!!!!!!!!!!User existe!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
	}

	@Override
	public Object retrieve(Object object, String searchBy) {
		Teacher teacherSearched = null;
		
		
	/***************************    search by ID    *********************************************/	
		if(searchBy == "ID"){
			TypedQuery<Teacher> query = em.createNamedQuery("Teacher.findById", Teacher.class);
			query.setParameter("id",((Teacher) object).getId());
			teacherSearched = query.getSingleResult();
			}
		
		return teacherSearched;
	}
	

	@Override
	public void update(Object object) {
		em.merge(object);
	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("isn't implemented!!!!!!!");

	}


	@Override
	public List<Teacher> retrieveList(Object object, String searchBy) {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		

		/*********************** Search by coachinghours ***************************/
		if (searchBy == "HOURS") {
			TypedQuery<Teacher> query = em.createNamedQuery("Teacher.findByCoachingHours", Teacher.class);
			query.setParameter("coachingHours",((Teacher) object).getCoachingHours());
			teacherList = query.getResultList();
		}
		
		/*********************** Search by ID ***************************/
		
		else if(searchBy == "ID"){
			TypedQuery<Teacher> query = em.createNamedQuery("Teacher.findById", Teacher.class);
			query.setParameter("id",((Teacher) object).getId());
			teacherList = query.getResultList();
		}
			
		/*********************** Search ALL ***************************/
		else if(searchBy == "ALL"){
			TypedQuery<Teacher> query = em.createNamedQuery("Teacher.findAll", Teacher.class);
			teacherList = query.getResultList();
			
		}	
		
		System.out.println("on retreive: "+teacherList);
		return teacherList;
	}

}
