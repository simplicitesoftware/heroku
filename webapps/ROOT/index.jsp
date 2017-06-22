<%@page import="java.nio.charset.Charset"%>
<html>
<head><title>Index</title></head>
<body>
<pre>
It works!

Generic
- Date: <%= new java.util.Date() %>
- Encoding: <%= Charset.defaultCharset()  %>
- OS: <%= System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version") %>
- JVM: <%= System.getProperty("java.version") + " " + System.getProperty("java.vendor") + " " + System.getProperty("java.vm.name") + " " + System.getProperty("java.vm.version") %>
<%
try
{
	out.println("- Server info: " + request.getSession().getServletContext().getServerInfo());
}
catch (Exception e)
{
	out.println("- Unable to get server info (" + e.getMessage() + ")"); 
}
%>
Specific:
- Auto-upgrade: <%= System.getProperty("platform.autoupgrade")  %>
- Git base directory: <%= System.getProperty("git.basedir")  %>
</pre>
</body>
</html>
