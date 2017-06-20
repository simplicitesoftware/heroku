<%@ page import="javax.naming.*" %>
<pre>
<%
out.println("<p>Context entries<ul>");
try {
	InitialContext ctx = new InitialContext();
	NamingEnumeration<NameClassPair> list = ctx.list("java:comp/env" );
	while (list.hasMore()) {
		out.println("<li>" + list.next().getName() + "</li>");
	}
} catch (Exception e) {
	out.println("Error: " + e.getMessage());
	e.printStackTrace();
}
out.println("</ul></p>");
%>
</pre>
