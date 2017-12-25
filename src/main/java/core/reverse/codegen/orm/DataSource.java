package core.reverse.codegen.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataSource
{
//	private String driver = "oracle.jdbc.OracleDriver";
//	private String url = "jdbc:oracle:thin:bhdb_tms/bhdb08@192.168.168.9:1521:orcl";
//	private String username = "bhdb_tms";
//	private String password = "bhdb08";
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/smart_buy?user=root&password=123456";
	private String username = "root";
	private String password = "123456";
	
	/*
	private String driver = "com.mysql.jdbc.Driver";
	//private String url = "jdbc:mysql://PC201404151354:3306/boomhopeoa?user=root&password=admin";
	private String url = "jdbc:mysql://192.168.168.9:3306/boomhopeoa?user=root&password=root";
	private String username = "root";
	private String password = "admin";
*/
	private Connection getConnection()
	{
		try
		{
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url);
			return conn;
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Object[]> query(String sql)
	{
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			List<Object[]> result = new LinkedList<Object[]>();
			while(rs.next()){
				int nc = rs.getMetaData().getColumnCount();
				Object[] arr = new Object[nc];
				for(int i=1;i<=nc;i++)
				{
					arr[i-1] = rs.getObject(i);
				}
				result.add(arr);
			}
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(sm!=null)
			{
				try
				{
					sm.close();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
