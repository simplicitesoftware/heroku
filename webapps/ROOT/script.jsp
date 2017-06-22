<%@ page import="javax.script.*" %>
<html>
<head><title>Index</title></head>
<body>
<pre>
<%
try {
	ScriptEngineManager factory = new ScriptEngineManager();
	ScriptEngine engine = factory.getEngineByName("javascript");
	engine.put("input", "Hello");
	engine.eval("var output = input + ' world !'; java.lang.System.out.println(output);");
	out.println(engine.get("output"));
} catch (Exception e) {
	out.println("Error: " + e.getMessage());
	e.printStackTrace();
}

%>
</pre>
</body>
</html>