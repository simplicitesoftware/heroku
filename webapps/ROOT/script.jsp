<%@ page import="javax.script.*" %>
<pre>
<%
ScriptEngineManager factory = new ScriptEngineManager();
ScriptEngine engine = factory.getEngineByName("javascript");
engine.put("input", "Hello");
engine.eval("var output = input + ' world !'; java.lang.System.out.println(output);");
out.println(engine.get("output"));
%>
</pre>