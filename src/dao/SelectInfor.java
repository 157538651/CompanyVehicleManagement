package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SelectInfor {
	
	public Object getSelectObject(String table,String id){
		SessionFactory sf = Connection.getConnection();// �������ݿ��������������
		Session session = sf.openSession();// ����sessionʵ��
		Transaction ts = session.beginTransaction();// ��������ʵ��
		Object result = null;// ��Ų�ѯ�Ľ��
		String hql;
		try {
			if(table.equals("Vehicle")){
			hql="from "+table+" where vid='"+id+"'";
			}else{
			hql="from "+table+" where id="+id;
			}
			Query query = session.createQuery(hql);
			result = query.list().get(0);// ����ѯ�������
			ts.commit();// �ύ

		} catch (Exception e) {
			e.printStackTrace();
			ts.rollback();// ���������лع�
		} finally {
			Connection.closeConnection(sf, session);// �ر����ݿ�����
		}
		return result;

	}

	@SuppressWarnings({ "rawtypes", })
	// ��ѯ�����ؽ��
	// hql ʹ��HQL�����в�ѯ
	// parametersΪ��ѯ����
	public List getSelectList(String hql, Object[] parameters) {
		SessionFactory sf = Connection.getConnection();// �������ݿ��������������
		Session session = sf.openSession();// ����sessionʵ��
		Transaction ts = session.beginTransaction();// ��������ʵ��
		List result = null;// ��Ų�ѯ�Ľ��
		try {
			Query query = session.createQuery(hql);
			if (parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					query.setParameter(i, parameters[i]);
				}
			}
			result = query.list();// ����ѯ�������
			ts.commit();// �ύ

		} catch (Exception e) {
			e.printStackTrace();
			ts.rollback();// ���������лع�
		} finally {
			Connection.closeConnection(sf, session);// �ر����ݿ�����
		}
		return result;

	}

	// ��ҳ��ѯ��ʵ��
	public PageSelect pageSelectList(PageSelect result, String hql,
			Object[] parameters) {
		SessionFactory sf = Connection.getConnection();// �������ݿ��������������
		Session session = sf.openSession();// ����sessionʵ��
		Transaction ts = session.beginTransaction();// ��������ʵ��
		int size = result.getSize();// ��ȡÿҳ����¼��
		try {
			if (!result.isChange()) { // ����ܼ�¼��Ϊ0����־��ʼ����Ҫ��ȡ�ܵļ�¼��
				result.setChange(true);
				Query query = session.createQuery(" select count(*)" + hql);// ��ȡ�����������ܼ�¼����ѯ���
				if (parameters.length > 0) {
					for (int i = 0; i < parameters.length; i++) {
						query.setParameter(i, parameters[i]);
					}
				}
				long count = ((Number) query.list().get(0)).longValue();// ��ȡ�����������ܼ�¼��
				result.setCount(count);
				if (count % result.getSize() == 0) { // ��ȡ�ܵ�ҳ��
					result.setPagecount((int) count / size);
				} else {
					result.setPagecount((int) count / size + 1);
				}
			}
			Query query1 = session.createQuery(hql);
			if (parameters.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					query1.setParameter(i, parameters[i]);
				}
			}
			query1.setFirstResult((result.getPagenow() - 1) * size); // ������ʼҳ��
			query1.setMaxResults(size); // ����ÿҳ����¼��
			result.setList(query1.list());// ����ѯ�������
			ts.commit();// �ύ

		} catch (Exception e) {
			e.printStackTrace();
			ts.rollback();// ���������лع�
		} finally {
			Connection.closeConnection(sf, session);// �ر����ݿ�����
		}
		return result;

	}

}
