<%@ page import="javax.naming.*" %>
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
<%
out.println("<pre>");
listContext(out, (Context)new InitialContext().lookup("java:comp/env"), "");
out.println("</pre>");
%>