<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>alert!</title>
<script>
	function alertpage(alertplace, alertmessage) {
		alert(alertplace +" 도중 오류가 발생하였습니다.\n"+ alertmessage);
		history.back();
	}	
</script>
</head>
<body onload='alertpage("${alertplace }", "${alertmessage }")'>
	

</body>
</html>

