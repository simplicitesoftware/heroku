<%@ page import="javax.naming.*" %>
<html>
<head><title>JNDI context tests</title></head>
<body>
<%!
private static final void listContext(JspWriter out, Context ctx, String indent) throws Exception {
	NamingEnumeration<Binding> list = ctx.listBindings("");
	while (list.hasMore()) {
		Binding item = (Binding)list.next();
		String className = item.getClassName();
		String name = item.getName();
		out.println(indent + className + " " + name + " = " + item.getObject().toString());
		Object o = item.getObject();
		if (o instanceof javax.naming.Context) {
			listContext(out, (Context) o, indent + "\t");
		}
	}
}
%>
<pre>
<%
try {
	listContext(out, (Context)new InitialContext().lookup("java:comp/env"), "");
} catch (Exception e) {
	out.println("Error: " + e.getMessage());
	e.printStackTrace();
}

%>
</pre>
</body>
</html>