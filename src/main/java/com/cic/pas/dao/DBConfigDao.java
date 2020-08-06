package com.cic.pas.dao;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cic.pas.service.ServerContext;

/**
 * 数据库服务类
 * <p>
 * 项目名称：preAcquistionSystem.3.2 类名称：ConfigDao 类描述： 创建人：lenovo 创建时间：2014-3-26
 * 下午02:15:05 修改人：lenovo 修改时间：2014-3-26 下午02:15:05 修改备注：
 */
public class DBConfigDao {

    /**
     * 系统数据库模板
     */

    public static JdbcTemplate jdbcTemplate;
    // 用来建立连接的，该类就是连接池，使用单例设计模式
    public static SqlSessionFactory sqlSessionFactory;
    public static String dbType = "";
    // 用来实现连接池的，该类类似Map集合。
    private static final ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();

    /**
     * 系统配置文件(服务器端属性集)
     */
    public static Properties serverConfigPro;

    private static Logger logger=Logger.getLogger(DBConfigDao.class);

    static {
        loadProperties();
        ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext(
                getApplicationContext());
        jdbcTemplate = (JdbcTemplate) cpx.getBean("jdbcTemplate");
        try {
            dbType = jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
        }
        sqlSessionFactory = (SqlSessionFactory) cpx.getBean("sqlSessionFactory");
        try {
            //sqlSessionFactory=sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
        }
    }

    public final static String getApplicationContext() {
        return serverConfigPro.getProperty("ApplicationContext");
    }

    /**
     * 根据配置文件的参数，装载属性集。
     */
    public static void loadProperties() {
        serverConfigPro = new Properties();
        InputStream is = null;
        try {
            is = ServerContext.class.getResourceAsStream("/system.properties");
            serverConfigPro.load(is);
        } catch (FileNotFoundException e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
        } catch (IOException e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            logger.error(exception);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                String exception = baos.toString();
                logger.error(exception);
            }
        }
    }

    /**
     * 取得数据库连接对象
     *
     * @return Session
     * @throws
     */
    public static SqlSession getSession() {
        // 先从ThreadLocal中取得连接。
        SqlSession session = (SqlSession) threadLocal.get();

        // 如果手头没有连接，则取得一个新的连接
        if (session == null) {
            session = sqlSessionFactory.openSession();
            // 把取得出的连接记录到ThreadLocal中，以便下次使用。
            threadLocal.set(session);
        }
        return session;
    }

    /**
     * 连接关闭的方法
     *
     * @throws
     */
    public static void closeSession() {
        SqlSession session = (SqlSession) threadLocal.get();
        // 将ThreadLocal清空，表示当前线程已经没有连接。
        threadLocal.set(null);
        // 连接放回到连接池
        if (session != null) {
            session.close();
        }
    }
}
