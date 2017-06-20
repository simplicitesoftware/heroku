<%@ page import="javax.naming.*" %>
<pre>
<%
try {
	InitialContext ctx = new InitialContext();
	NamingEnumeration<NameClassPair> list = ctx.list("");
	while (list.hasMore()) {
		out.println("<br/>" + list.next().getName());
	}
} catch (Exception e) {
	out.println("Error: " + e.getMessage());
	e.printStackTrace();
}
%>
</pre>
