<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.sql.*" %>
<html>
<head><title>Database tests</title></head>
<body>
<pre>
<%
try {
	DataSource ds = (DataSource)new InitialContext().lookup("java:comp/env/jdbc/simplicite");

	Connection c = ds.getConnection();
	c.setAutoCommit(false);

	DatabaseMetaData md = c.getMetaData();
	out.println("Driver = [" + md.getDriverName() + "]");
	out.println("URL = [" + md.getURL() + "]");
	out.println("Database = [" + md.getDatabaseProductName() + " " + md.getDatabaseProductVersion() + "]");

	Statement s = c.createStatement();

	out.println("Dropping test table if exists");
	s.executeUpdate("drop table if exists test");
	c.commit();

	out.println("Creatint test table");
	s.executeUpdate("create table test(id integer, lib varchar(100))");
	
	out.println("Inserting data into test table");
	s.executeUpdate("insert into test values (1, 'Test 1')");
	s.executeUpdate("insert into test values (2, 'Test 2 - " + new java.util.Date() + "')");
	c.commit();

	out.println("Selecting data from test table");
	ResultSet rs = s.executeQuery("select * from test");
	while (rs.next()) {
		out.println(rs.getInt("id") + " = [" + rs.getString("lib") + "]");
	}
	rs.close();

	out.println("Dropping test table");
	s.executeUpdate("drop table test");
	c.commit();

	s.close();
	c.close();
} catch (Exception e) {
	out.println("Error: " + e.getMessage());
	e.printStackTrace();
}
%>
</pre>
</body>
</html>