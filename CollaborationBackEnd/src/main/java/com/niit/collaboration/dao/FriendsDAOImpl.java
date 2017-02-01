package com.niit.collaboration.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.model.Friends;

@SuppressWarnings("deprecation")
@EnableTransactionManagement
@Repository("friendsDAO")
public class FriendsDAOImpl implements FriendsDAO {
	
	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(FriendsDAOImpl.class);
	
		@Autowired
		private SessionFactory sessionFactory;
		public FriendsDAOImpl(SessionFactory sessionFactory)
		{
			this.sessionFactory=sessionFactory;
		}
		
		private Integer getMaxId()
		{
			String hql = "select max{id} from friend";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			Integer maxID = (Integer) query.uniqueResult();
			return maxID;
		}
		
		@Transactional
		public boolean save(Friends friends){	
			
			try{
			  sessionFactory.getCurrentSession().save(friends);
		return true;
			}catch (Exception e ){
				//TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}	
		@Transactional
		public boolean update(Friends friends){
			
			try{
				sessionFactory.getCurrentSession().update(friends);
		return true;
			} catch (Exception e){
				//TODO Auto-generated catch block
		       e.printStackTrace();
		       return false;
			}
		}
		
	/*	@Transactional
		public boolean delete(Friends friends){
			try{
		       sessionFactory.getCurrentSession().delete(friends);
		return true;
			} catch (Exception e){
				//TODO Auto-generated catch block
		       e.printStackTrace();
		       return false;
			}
		}*/
		
		@Transactional
		public Friends get(String userID, String friendID) {
			String hql = "from Friends where userID=" + "'" + userID + "' and friendID= '" + friendID + "'";
			
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			
			List<Friends> list = (List<Friends>) query.list();
			
			if(list != null && !list.isEmpty()) {
				return list.get(0);
			}
			return null;
			
		}

		@Transactional
		public boolean delete(String userID, String friendID) {
			Friends friend = new Friends();
			friend.setFriendID(friendID);
			friend.setUserID(userID);
			sessionFactory.getCurrentSession().delete(friend);
			return true;
			
		}

		@Transactional
		public List<Friends> getMyFriend(String userID) {
			String hql = "from Friends where userID=" + "'" + userID + "' and status = '" + "N'";
			@SuppressWarnings("rawtypes")
			Query query =sessionFactory.getCurrentSession().createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<Friends> list = (List<Friends>) query.list();
			return list;
			
		}

		@Transactional
		public List<Friends> getNewFriendRequests(String userID) {
			String hql = "from Friends where friendID=" + "'" + userID + "' and status = '" + "N'";
			@SuppressWarnings("rawtypes")
			Query query =sessionFactory.getCurrentSession().createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<Friends> list = (List<Friends>) query.list();
			return list;
		}

	
		@Transactional
		public void setOnline(String userID) {
			Logger.debug("Starting of the method setOnline");
			String hql = "UPDATE Friend SET isOnline = 'Y' where userID ='" + "'";
			Logger.debug("hql: " + hql);
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			Logger.debug("Ending of the method setOnline");
		}
		
		@Transactional
		public void setOffLine(String userID) {
			Logger.debug("Starting of the method setOffline");
			String hql = "UPDATE Friends SET isOnline = 'N' where userID = '" + userID + "'";
			Logger.debug("hql: " + hql);
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			Logger.debug("Ending of the method setOffline");
		}

		@Transactional
		public Friends get(int id) 
		{

			String hql="from Friends where id = " + "'" + id + "'";
	
			@SuppressWarnings({ "rawtypes" })
			Query query=sessionFactory.getCurrentSession().createQuery(hql);
			@SuppressWarnings({ "unchecked" })
			List<Friends> list=query.list();
			if(list==null || list.isEmpty())
			{
				return null;
			}
			else
			{
				return list.get(0);
			}
		}
		@Transactional
		public List<Friends> getAllMyFriends(String userID) {
			String hql1="select friendID as id from Friends where userID='"+userID+"' and status='A'";
			String hql2 ="select userID as id  from Friends where friendID='"+userID+"' and status='A'";
			Query query1=sessionFactory.getCurrentSession().createQuery(hql1);
			Query query2=sessionFactory.getCurrentSession().createQuery(hql2);
			List<Friends> list1=query1.list();
			List<Friends> list2=query2.list();
		    list1.addAll(list2);
		    return list1;
		}
}