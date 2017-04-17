package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
	public static Configuration cf=null; 
	public static SessionFactory sf=null;
	public static Session session=null;
	
	//����Hibernate�����ļ������Ҵ���SessionFactoryʵ��
	@SuppressWarnings("deprecation")
	public static SessionFactory getConnection(){
		cf=new Configuration().configure(); 
		sf=cf.buildSessionFactory();
		return sf;
		
	}
	//�ر�����
	public static void closeConnection(SessionFactory sf,Session session){
		if(session!=null){
			session.close(); //�ر�Session
		}
		if(sf!=null){
			sf.close();	 //�ر�SessionFactory
		}
		
	}


}
