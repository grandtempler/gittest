<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>​
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>유저 CRUD</title>
	
<style type="text/css">
    .orange {
        color: #fef4e9;
        border: solid 1px #da7c0c;
        background: #f78d1d;
        background: -webkit-gradient(linear, left top, left bottom, from(#faa51a), to(#f47a20));
        background: -moz-linear-gradient(top,  #faa51a,  #f47a20);
        filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#faa51a', endColorstr='#f47a20');
        vertical-align:middle;
        cursor:pointer;
        color:black;
        font-weight:bold;
        font-size:13;
        font-family:굴림; 
        align:right;
        width:45px;
        height:22px;
        margin-right:5px;
    }
    
.line1{border-bottom:1px solid red;}

.line2{border-bottom:1px solid red; border-right:1px solid red;}

.line3{border-bottom:1px solid black; border-right:1px solid black;}

.line4{border-bottom:1px solid black;}

</style>

</head>
<body>

<table width=500><tr><td><h1>사용자 CRUD</h1></td></tr></table>

<div style="width:800">
<table cellspacing=1 border=0 align=center>
	<tr style="border-right: none; border-left: hidden; border-top: hidden;"><td  class="line1" colspan=9 >page : ${page}/${paginationVO.getTotalpage() }</td></tr>
	<tr bgcolor=yellow>
		<td width=200 class="line2"><p align=center><strong>사용자명</strong></p></td>
		<td width=80 class="line2"><p align=center><strong>나이</strong></p></td>
		<td width=80 class="line2"><p align=center><strong>성별</strong></p></td>
		<td width=200 class="line2"><p align=center><strong>주소1</strong></p></td>
		<td width=300 class="line2"><p align=center><strong>주소2</strong></p></td>
		<td width=300 class="line2"><p align=center><strong>핸드폰번호</strong></p></td>
		<td width=80 class="line2"><p align=center><strong>통신사</strong></p></td>
		<td width=50 class="line2"><p align=center><strong>수정</strong></p></td>
		<td width=50 class="line1"><p align=center><strong>삭제</strong></p></td>
	</tr>
		<c:forEach items="${users}" var="user">
			<c:choose>
		      	<c:when test="${fn:length(user.phoneList) == 0}">
					<tr>
						<td class="line3"><p align=center>${user.name }</p></td>
						<td class="line3"><p align=center>${user.age }</p></td>
						<td class="line3"><p align=center>${user.sex }</p></td>
						<td class="line3"><p align=center>${user.address1 }</p></td>
						<td class="line3"><p align=center>${user.address2 }</p></td>
		      			<td class="line3"><p align=center>&emsp;</p></td>
						<td class="line3"><p align=center>&emsp;</p></td>
						<td class="line3"><p align=center><input type=button value="수정" OnClick="window.location='user_update?id=${user.id}'" class="orange" style="width:100%; height:100%;"></p></td>
						<td class="line4"><p align=center><input type=button value="삭제" OnClick="window.location='user?key=delete'" class="orange" style="width:100%; height:100%;"></p></td>
					</tr>
		      	</c:when>
		      	<c:when test="${fn:length(user.phoneList) > 0}">
					<c:forEach items="${user.phoneList}" var="phone" varStatus="status">
			      		<c:if test="${status.count == 1 }">
			      			<tr>
								<td class="line3"><p align=center>${user.name }${status.count}</p></td>
								<td class="line3"><p align=center>${user.age }</p></td>
								<td class="line3"><p align=center>${user.sex }</p></td>
								<td class="line3"><p align=center>${user.address1 }</p></td>
								<td class="line3"><p align=center>${user.address2 }</p></td>
								<td class="line3"><p align=center>${phone.phonenumber }</p></td>
								<td class="line3"><p align=center>${phone.telecom }</p></td>
								<td class="line3"><p align=center><input type=button value="수정" OnClick="window.location='user_update?id=${user.id}&listindex=${status.index}'" class="orange" style="width:100%; height:100%;"></p></td>
								<td class="line4"><p align=center><input type=button value="삭제" OnClick="window.location='user?key=delete'" class="orange" style="width:100%; height:100%;"></p></td>

							</tr>
						</c:if>
						
						<c:if test="${status.count > 1 }">
							<tr>
								<td class="line3"><p align=center>&emsp;${status.count}</p></td>
								<td class="line3"><p align=center>&emsp;</p></td>
								<td class="line3"><p align=center>&emsp;</p></td>
								<td class="line3"><p align=center>&emsp;</p></td>
								<td class="line3"><p align=center>&emsp;</p></td>
								<td class="line3"><p align=center>${phone.phonenumber }</p></td>
								<td class="line3"><p align=center>${phone.telecom }</p></td>
								<td class="line3"><p align=center><input type=button value="수정" OnClick="window.location='user_update?id=${user.id}'" class="orange" style="width:100%; height:100%;"></p></td>
								<td class="line4"><p align=center><input type=button value="삭제" OnClick="window.location='user?key=delete'" class="orange" style="width:100%; height:100%;"></p></td>
								<td>${status.index}</td>
							</tr>
						</c:if>
			 		</c:forEach>
		      	</c:when>
			</c:choose>
		</c:forEach>
	</tr>
</table>



</div>

</body>
</html>