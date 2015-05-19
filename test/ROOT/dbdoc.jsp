<%@page import="java.util.*"%>
<%@page import="javax.naming.*" %>
<%@ page import="java.nio.file.*" %>
<%@page import="java.nio.charset.*"%>
<pre>
<%
InitialContext ctx = new InitialContext();
String d = (String)ctx.lookup("java:comp/env/dataDir") + "/dbdoc";
out.println("DBDoc dir: " + d);
Files.createDirectories(Paths.get(d));

String f = d + "/test.txt";
Path file = Paths.get(f);

out.println("Writing file: " + f);
ArrayList<String> inData = new ArrayList<String>();
inData.add("Hello");
inData.add("World !");
inData.add("Date=" + new Date());
Files.write(file, inData, Charset.defaultCharset());

out.println("Reading file: " + f);
List<String> outData = Files.readAllLines(file, Charset.defaultCharset());
int i = 0;
for (String l : outData) out.println("Line " + (++i) + " = [" + l + "]");
%>
</pre>