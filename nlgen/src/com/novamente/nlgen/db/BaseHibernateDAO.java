package com.novamente.nlgen.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


public class BaseHibernateDAO {
	public class Fetch {
		String property;
		FetchMode fetchMode=FetchMode.JOIN;
		public String getProperty() {
			return property;
		}
		public void setProperty(String property) {
			this.property = property;
		}
		public FetchMode getFetchMode() {
			return fetchMode;
		}
		public void setFetchMode(FetchMode fetchMode) {
			this.fetchMode = fetchMode;
		}
		public Fetch(){
			this(null,FetchMode.JOIN);
		}
		public Fetch(String property, FetchMode fetchMode) {
			super();
			this.property = property;
			this.fetchMode = fetchMode;
		}
	}
	private static final Log log = LogFactory.getLog(BaseHibernateDAO.class);

	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}

	@SuppressWarnings("unchecked")
	public List getAll(Class c) {
		try {
			return getSession().createCriteria(c).list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List getAll(String s) {
		return getSession().createCriteria(s).list();
	}

	@SuppressWarnings("unchecked")
	public List search(String hql) {
		return search(hql, null, null, null);
	}

	@SuppressWarnings("unchecked")
	public List search(String hql, Map conditionMap, Integer pageOffset,
			Integer pageSize) {
		try {
			Query query = getSession().createQuery(hql);
			if (conditionMap != null) {
				for (Object keyObj : conditionMap.keySet()) {
					if (keyObj instanceof String) {
						query.setParameter(keyObj.toString(), conditionMap
								.get(keyObj));
					}
				}
			}
			if (pageOffset != null) {
				query.setFirstResult(pageOffset * pageSize);
			}
			if (pageSize != null) {
				query.setMaxResults(pageSize);
			}
			return query.list();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List search(Class c, Collection conditions) {
		return search(c, conditions, null, null);
	}

	@SuppressWarnings("unchecked")
	public List search(Class c, Collection conditions, Integer pageOffset,
			Integer pageSize) {
		Criteria cri = generateCriteriaFromMap(c, conditions);
		if (pageOffset != null) {
			cri.setFirstResult(pageOffset * pageSize);
		}
		if (pageSize != null) {
			cri.setMaxResults(pageSize);
		}
		return cri.list();
	}

	@SuppressWarnings("unchecked")
	public Integer getResultCount(Class c, Collection conditions) {
		Criteria cri = generateCriteriaFromMap(c, conditions);
		cri.setProjection(Projections.rowCount());
		return (Integer) cri.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public Integer getResultCount(String hql, Map conditionMap) {
		Query q = getSession().createQuery("select count(*) " + hql);
		if (conditionMap != null) {
			for (Object keyObj : conditionMap.keySet()) {
				if (keyObj instanceof String) {
					q.setParameter(keyObj.toString(), conditionMap.get(keyObj));
				}
			}
		}
		return (Integer) q.list().get(0);
	}

	@SuppressWarnings("unchecked")
	private Criteria generateCriteriaFromMap(Class c, Collection conditions) {
		Criteria cri = getSession().createCriteria(c);
		if (conditions != null) {
			for (Object obj : conditions) {
				if (obj instanceof Criterion) {
					cri.add((Criterion) obj);
				}else if (obj instanceof String) {
					cri.createAlias(obj.toString(), obj.toString());
				}else if (obj instanceof Order) {
					cri.addOrder((Order) obj);
				}else if(obj instanceof Fetch){
					Fetch f=(Fetch)obj;
					cri.setFetchMode(f.getProperty(), f.getFetchMode());
				}
				
			}
		}
		return cri;
	}

	public void save(Object transientInstance) {
		log.debug("saving Role instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Object persistentInstance) {
		log.debug("deleting Role instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public void delete(Object[] ids, Class c) {
		log.debug("deleting Role instance");
		try {
			Criteria criteria = getSession().createCriteria(c);
			List l = criteria.add(Restrictions.in("id", ids)).list();
			for (Object obj : l) {
				getSession().delete(obj);
			}
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void attachDirty(Object instance) {
		log.debug("attaching dirty Role instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Object instance) {
		log.debug("attaching clean Role instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void update(Object instance) {
		log.debug("attaching clean Role instance");
		try {
			getSession().update(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public Object load(Class c, Serializable id) {
		return getSession().load(c, id);
	}

	@SuppressWarnings("unchecked")
	public Object[] loadByIds(Class c, Serializable[] ids) {
		try {
			Criteria cri = getSession().createCriteria(c);
			cri.add(Expression.in("id", ids));
			return cri.list().toArray();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List lookupByProperty(Class c, String property, Object value ){
		Criteria cri=getSession().createCriteria(c);
		cri.add(Expression.eq(property, value));
		return cri.list();
	}
	public void closeSession() {
		HibernateSessionFactory.closeSession();
	}
}