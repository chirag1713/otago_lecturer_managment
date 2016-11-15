<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form:form action="uploadimage"  modelAttribute="uploadForm" enctype="multipart/form-data">

<table id="fileTable">
				<tr>
					<td><input name="files[0]" type="file" id="moviefile" /></td>
				</tr>
				<tr>
					<td><input name="files[1]" type="file" id="moviefile" /></td>
				</tr>
			</table>
			<br />
			<input type="submit" value="Upload" />

</form:form>
</body>
</html>