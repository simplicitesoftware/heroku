<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.sql.*" %>
<pre>
<%
DataSource ds = (DataSource)new InitialContext().lookup("java:comp/env/jdbc/test");
Connection c = ds.getConnection();
c.setAutoCommit(false);

DatabaseMetaData md = c.getMetaData();
out.println("Driver = [" + md.getDriverName() + "]");
out.println("URL = [" + md.getURL() + "]");
out.println("Database = [" + md.getDatabaseProductName() + " " + md.getDatabaseProductVersion() + "]");

Statement s = c.createStatement();

s.executeUpdate("drop table test if exists");
c.commit();

s.executeUpdate("create table test(id integer, lib varchar(100))");
s.executeUpdate("insert into test values (1, 'Test 1')");
s.executeUpdate("insert into test values (2, 'Test 2 - " + new java.util.Date() + "')");
c.commit();

ResultSet rs = s.executeQuery("select * from test");
while (rs.next()) {
	out.println(rs.getInt("id") + " = [" + rs.getString("lib") + "]");
}
rs.close();

s.close();
c.close();
%>
</pre>