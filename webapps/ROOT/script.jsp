<%@ page import="javax.script.*" %>
<html>
<head><title>Sripting tests</title></head>
<body>
<pre>
<%
try {
	ScriptEngineManager factory = new ScriptEngineManager();
	ScriptEngine engine = factory.getEngineByName("javascript");
	engine.put("input", "It");
	engine.eval(
		"var output = input + ' works!';\n" +
		"java.lang.System.out.println('Debug: ' + output);"
	);
	out.println(engine.get("output"));
} catch (Exception e) {
	out.println("Error: " + e.getMessage());
	e.printStackTrace();
}
%>
</pre>
</body>
</html>