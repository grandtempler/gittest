<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
	<title>고객 관리 프로그램</title>
	
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.css">
<script
  src="https://code.jquery.com/jquery-3.1.1.min.js"
  integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.js"></script>

<SCRIPT>
	// 공백만 있을 때 체크 함수
	function checkBlank(obj) {
	    var obj2 = obj.replace(/ /gi, ""); // 모든 공백을 제거
	    if(!(obj2 == '') && !(obj2 == null)) {
	        return true;
	    } else {
	        alert('모든 항목을 입력하세요.');
	    }
	}
	
	// 이름 한글 체크 함수
	function checkName(obj) {
	    var pattern1 = /^[가-힣]*$/;
	    var pattern2 = /^[A-Z a-z]*$/;
	    if (pattern1.test(obj) || pattern2.test(obj)) {
	        return true;
	    } else {
	        alert('이름은 한글이나 영어만 가능합니다(혼용불가).');
	    }
	}
	
	 // 나이 숫자 체크 함수
	function checkNum(obj) {
	    var pattern = /^[0-9]*$/;
	    if (pattern.test(obj) && obj <= 150 && obj > 0) {
	        return true;
	    } else {
	        alert('나이는 150 이하의 양수만 입력가능합니다.');
	    }
	}

	// 길이 체크 함수
	function checkLength(obj, objname, maxByte) { // onkeyup에서 글자 제한 바이트 단위로 계산..
		// 자바 스크립트 함수 chkword... 이름에 들어오는 문자열의 길이, 특수문자, 공백 판별을 통해 적절히 처리
		var strValue = obj;
		var strLen = strValue.length; // 문자열 길이를 저장할 변수
		var totalByte = 0; // 총 Byte를 저장할 변수
		var len = 0;       // 특정 시점까지 저장된 문자 수를 저장할 변수
		var oneChar = "";  // 한 글자가 임시로 저장될 변수
		var str2 = "";     // 처리 결과를 반환할 문자열이 저장될 변수

		for (var i = 0; i < strLen; i++) { // 문자열 길이만큼 반복할 for문
	        oneChar = strValue.charAt(i);  // 한 글자씩 끊어와서 oneChar에 저장
	        if (escape(oneChar).length > 4) { // escape 함수를 써서 유니코드 값을 뱉어내도록 해줌
	            totalByte += 2;               // 유니코드 값이 4 자리를 넘으면 총 바이트에 +2
	        } else {
	            totalByte++;                  // 유니코드 값이 4 이하면 총 바이트에 +1
	        }
	        // 입력한 문자 길이보다 넘치면 잘라내기 위해 저장
	        if (totalByte <= maxByte) {
	            // 처음에 지정받은 maxByte보다 작을 때 까지 문자열 자를 길이 len 을 +1 해줌.. 이렇게 함으로써 한글이 잘려 에러가 나는걸 방지가능
	            len = i + 1;
	        }
	    }

	    if (totalByte <= maxByte) { // 넘어가는 글자는 자른다.
	    	return true;
	    } else {	
	        alert(objname+' 최대 '+maxByte+'바이트까지 입력 가능합니다.'); // 문자열 초과시 경고창 띄움
	    }
	}
	
	 // 전화번호 체크 함수
	function checkPhone(obj) {
	    var pattern = /^[0-9]*$/;
	    if (pattern.test(obj)) {
	        return true;
	    } else {
	        alert('나이는 숫자만 가능합니다.');
	    }
	}
    
    function phoneNum(phoneNumber) { // 연락처 출력에 필요한 메서드
    	return phoneNumber.replace(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/, "$1-$2-$3");
    }
    
    function phoneNumChk(phoneNumber) {
    	if (phoneNumber.match(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})?([0-9]{3,4})?([0-9]{4})$/)) {
    		return true;
    	} else {
    		alert("연락처에는 숫자만 입력가능하며, 연락처 형식에 맞아야합니다.\nex) 01734253645 or 024536435");
    	}
    }
    
    function submitFunction(mode) {

        if(mode == "user_insert") {
    		var name = fm.name.value;
    		var age = fm.age.value;
    		var sex = fm.sex.value;
    		var address1 = fm.address1.value;
    		var address2 = fm.address2.value;
    		if(checkBlank(name)&&checkName(name)&&checkLength(name, '이름은', 20)
    		 &&checkBlank(age)&&checkNum(age)&&checkBlank(sex)&&checkBlank(address1)&&checkBlank(address2)) {
        	    fm.action = "user_insert";
            	fm.submit();
    		}
        } else if (mode == "user_update") {
    		var name = fm.name.value;
    		var age = fm.age.value;
    		var sex = fm.sex.value;
    		var address1 = fm.address1.value;
    		var address2 = fm.address2.value;
    		if(checkBlank(name)&&checkName(name)&&checkLength(name, '이름은', 20)
    	     &&checkBlank(age)&&checkNum(age)&&checkBlank(sex)&&checkBlank(address1)&&checkBlank(address2)) {
            	fm.action = "user_update";
            	fm.submit();
        	}
        } else if(mode == "phone_insert") {
    		var phonenumber = fm.phonenumber.value;
    		var telecom = fm.telecom.value;
        	if(checkBlank(phonenumber)&&phoneNumChk(phonenumber)) {
            	fm.action = "phone_insert";
            	fm.submit();
        	}
        } else if (mode == "phone_update") {
    		var phonenumber = fm.phonenumber.value;
    		var telecom = fm.telecom.value;
        	if(checkBlank(phonenumber)&&phoneNumChk(phonenumber)) {
            	fm.action = "phone_update";
            	fm.submit();
        	}
        }
    }
    
    function urlMaker(url, mode1, mode2, mode3, mode4, mode5) {
    	
   		if (mode1 != null) {
   			if(mode1.length != 0) {
   				url = url + "&searchusername=" + mode1;
   			}
   		}
   		
   		if (mode2 != null) {
   			if (mode2.length != 0) {
   				url = url + "&searchphonenumber=" + mode2;
   			}
   		}
   		if (mode3 != null) {
   			if(mode3.length != 0) {
   				url = url + "&page=" + mode3;
   			}
   		}
   		
   		if (mode4 != null) {
   			if (mode4.length != 0) {
   				url = url + "&pageSize=" + mode4;
   			}
   		}
   		if (mode5 != null) {
   			if (mode5.length != 0) {
   				url = url + "&phonealllist=" + mode5;
   			}
   		}
   		return url;
    }
    
    function phoneListFunction(mode1, mode2, mode3, mode4, mode5, mode6) {
    	
   		var url = "user_1?idpl="+ mode1;
   		
   		url = urlMaker(url, mode2, mode3, mode4, mode5, mode6);

   		window.location = url;
    }
    
    function phoneAllListFunction(mode1, mode2, mode3, mode4, mode5, mode6) {
    	
   		var url = "user_1?idpl=";
   		if (mode1 != null) {
   			url += mode1;
   		} else {
   			url += "0";
   		}

   		url = urlMaker(url, mode2, mode3, mode4, mode5, mode6);

   		window.location = url;
    }

    
    function insertUserFunction(mode1, mode2, mode3, mode4, mode5, mode6) {
   		var url = "user_1?userinsert="+ mode1;
   		
   		url = urlMaker(url, mode2, mode3, mode4, mode5, mode6);

   		window.location = url;
    }
    
    function updateUserFunction(mode1, mode2, mode3, mode4, mode5, mode6) {
   		var url = "user_1?userup="+ mode1;
   		
   		url = urlMaker(url, mode2, mode3, mode4, mode5, mode6);

   		window.location = url;
    }
    
    function insertPhoneFunction(mode1, mode2, mode3, mode4, mode5, mode6, mode7) {

   		var url = "user_1?idpl="+ mode1 +"&phinsert="+ mode2;
   		
   		url = urlMaker(url, mode3, mode4, mode5, mode6, mode7);

   		window.location = url;
    }

    function deleteUserFunction(mode1, mode2, mode3, mode4, mode5, mode6) {
       	var conf = confirm("해당 유저를 삭제하면 유저에게 등록된 모든 정보가 삭제됩니다.\n\n정말로 삭제하시겠습니까?");
    	if(conf) {
    		var url = "user_delete?userdel="+ mode1;
    		
       		url = urlMaker(url, mode2, mode3, mode4, mode5, mode6);

    		window.location = url;
    	}
    }
    

    
    function updatePhoneFunction(mode1, mode2, mode3, mode4, mode5, mode6, mode7) {

   		var url = "user_1?idpl="+ mode1 +"&phup="+ mode2;
   		
   		url = urlMaker(url, mode3, mode4, mode5, mode6, mode7);

   		window.location = url;
    }
    

    function deletePhoneFunction(mode1, mode2, mode3, mode4, mode5, mode6, mode7) {
       	var conf = confirm("해당 연락처를 삭제하려고 합니다.\n\n정말로 삭제하시겠습니까?");
    	if(conf) {
    		var url = "phone_delete?idpl="+ mode1 +"&phdel="+ mode2;
    		
       		url = urlMaker(url, mode3, mode4, mode5, mode6, mode7);

    		window.location = url;
    	}
    }
    
    function paginationFunction(mode1, mode2, mode3, mode4) {
   		var url = "user_1?page="+ mode1 +"&pageSize="+ mode2;
   		if (mode3 != null) {
   			if(mode3.length != 0) {
   				url = url + "&searchusername=" + mode3;
   			}
   		}
   		
   		if (mode4 != null) {
   			if (mode4.length != 0) {
   				url = url + "&searchphonenumber=" + mode4;
   			}
   		}
   		window.location = url;
    }
    
    function moveFocus(next) {
    	if(event.keyCode == 13) {
    		document.getElementById(next).focus();
    	}
    }

</SCRIPT>
</head>

<body onload="usersearch.searchusername.focus();">

<div style="width:1500">
<table class="ui yellow table">
	<tr align=center >
		<td cellspacing=2 cellpadding=2 border=1 >
			<h1><a href="user_1" style="text-decoration:none">고객 관리 프로그램</a></h1>
		</td>
	</tr>
</table>

<table class="ui purple table">
<FORM METHOD="POST" name="usersearch" action="user_search" style="margin:0">
	<input type=hidden name=page value="${page }">
	<input type=hidden name=pageSize value="${pageSize }">
	<tr>
		<td width=1150 align=right>&emsp;</td>
		<td>
			<div><span style="color:red"><strong>유저명 검색</strong></span></div>
		</td>
		<td>
			<div class="ui action input">
				<input type="text" placeholder="사용자명을 입력하세요" name=searchusername value="${searchusername}">
				<button class="ui icon button">
				  <i class="search icon"></i>
				</button>
				&ensp;
			</div>
		</td>
	</tr>
</FORM>
<FORM METHOD="POST" name="phonesearch" action="phone_search">
	<input type=hidden name=page value="${page }">
	<input type=hidden name=pageSize value="${pageSize }">
	<tr>
		<td width=1150 align=right>page:${page }/${paginationVO.totalpage }</td>
		<td>
			<div><span style="color:red"><strong>연락처 검색</strong></span></div>
		</td>
		<td>
			<div class="ui action input">
				<input type="text" placeholder="연락처를 입력하세요" name=searchphonenumber value="${searchphonenumber}">
				<button class="ui icon button">
				  <i class="search icon"></i>
				</button>
				&ensp;
			</div>
		</td>
	</tr>
</FORM>
</table>

<FORM METHOD="POST" name="fm">
	<input type=hidden name=page value="${page }">
	<input type=hidden name=pageSize value="${pageSize }">
	<input type=hidden name=searchusername value="${searchusername }"><!-- 무한루프 가능하도록... -->
	<input type=hidden name=searchphonenumber value="${searchphonenumber }">

<table class="ui yellow table"><!-- 유저 리스트 테이블 시작 -->
<thead>
	<tr>
		<th width=200 style="text-align:center;"><div><strong><span style="color:red">사용자명</span></strong></div></th>
		<th width=100 style="text-align:center;"><div><strong><span style="color:red">나이</span></strong></div></th>
		<th width=100 style="text-align:center;"><div><strong><span style="color:red">성별</span></strong></div></th>
		<th width=300 style="text-align:left;"><div><strong><span style="color:red">&emsp;&emsp;&emsp;&emsp;&emsp;주소1</span></strong></div></th>
		<th width=450 style="text-align:left;"><div><strong><span style="color:red">&emsp;&emsp;&emsp;&emsp;&emsp;주소2</span></strong></div></th>
		<th width=100 style="text-align:center;"><div><strong><span style="color:red">수정</span></strong></div></th>
		<th width=100 style="text-align:center;"><div><strong><span style="color:red">삭제</span></strong></div></th>
		<c:choose>
			<c:when test="${!phonealllist}">
				<!-- 연락처 버튼이 클릭되어 있지 않을 때 -->
				<th width=150 style="text-align:center;"><div><input type=button value="연락처" OnClick='phoneAllListFunction("${idpl }", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "true");' class="ui inverted violet button"></div></th>
			</c:when>
			<c:otherwise>
				<th width=150 style="text-align:center;"><div><input type=button value="연락처" OnClick='phoneAllListFunction("${idpl }","${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "false");' class="ui violet button"></div></th>
			</c:otherwise>
		</c:choose>
	</tr>
</thead>
<tbody>	
	<c:forEach items="${users}" var="user"><!-- 유저 한 명의 정보 뿌리는 forEach문 시작 -->
      	<tr><!-- 사용자 한 줄 씩 출력할 tr 시작 -->
			<!-- 사용자 수정 눌렀을 때 userup 변수를 파라미터로 받아서 input태그로 나오도록 choose문.. -->
			<c:choose>
				<c:when test="${userup == user.id}">
				<!-- 사용자 수정 라인 시작 -->
					<input type=hidden name=userup value="${user.id}">
					<input type=hidden name=phonealllist value="${phonealllist}">
					<td width=200 style="text-align:center;">
						<div class="ui input focus">
							<input type="text" name="name" id="name" value="${user.name }" onkeydown="moveFocus('age');" style="text-align:center; width:170;">
						</div>
					</td>
					<td width=100 style="text-align:center;">
						<div class="ui input focus">
							<input type=number name="age" id="age" value="${user.age }" onkeydown="moveFocus('sex');" style="text-align:center; width:70;">
						</div>
					</td>
					<td width=100 style="text-align:center;">
						<div>
							<select name="sex" id="sex" onkeydown="moveFocus('address1');" class="ui dropdown">
						    	<option value="남" <c:if test="${user.sex eq '남'}">SELECTED</c:if>><strong>남</strong></option>
						    	<option value="여" <c:if test="${user.sex eq '여'}">SELECTED</c:if>><strong>여</strong></option>
							</select>
						</div>
					</td>
					<td width=300 style="text-align:left;">
						<div class="ui input focus">
							<input type=text name="address1" id="address1" value="${user.address1 }" onkeydown="moveFocus('address2');" style="text-align:left; width:270;">
						</div>	
					</td>
					<td width=450 style="text-align:left;">
						<div class="ui input focus">
							<input type=text name="address2" id="address2" value="${user.address2 }" onkeydown="moveFocus('userupdatesubmit');" style="text-align:left; width:420;">
						</div>
					</td>
					<td width=100 style="text-align:center;"><div><input type=button id="userupdatesubmit" value="등록" OnClick="submitFunction('user_update');" class="ui inverted orange button"></div></td>
					<td width=100 style="text-align:center;"><div><input type=button value="삭제" OnClick='deleteUserFunction(${user.id}, "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted purple button"></div></td>
					<td width=150 style="text-align:center;"><div><input type=button value="리스트" OnClick='phoneListFunction(${user.id}, "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "false");' class="ui inverted violet button"></div></td>
				</c:when>
				<c:otherwise>
				<!-- 사용자 리스트 출력 라인 시작 -->
					<td width=200 style="text-align:center;"><div>${user.name }</div></td>
					<td width=100 style="text-align:center;"><div>${user.age }</div></td>
					<td width=100 style="text-align:center;"><div>${user.sex }</div></td>
					<td width=300 style="text-align:left;"><div>${user.address1 }</div></td>
					<td width=450 style="text-align:left;"><div>${user.address2 }</div></td>
					<td width=100 style="text-align:center;"><div><input type=button value="수정" OnClick='updateUserFunction(${user.id}, "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted orange button"></div></td>
					<td width=100 style="text-align:center;"><div><input type=button value="삭제" OnClick='deleteUserFunction(${user.id}, "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted purple button"></div></td>
					<td width=150 style="text-align:center;"><div><input type=button value="리스트" OnClick='phoneListFunction(${user.id}, "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "false");' class="ui inverted violet button"></div></td>
				<!-- 사용자 리스트 출력 라인 끝 -->	
				</c:otherwise>
			</c:choose>
		</tr><!-- 사용자 한 줄 씩 출력할 tr 종료 -->
		
		<c:choose>
			<c:when test="${phonealllist == true}">
								<tr><!-- 모든 핸드폰 리스트를 보여줌. 연락처 리스트 테이블이 들어갈 tr 시작 -->
						<td colspan=8 style="border-bottom:1px solid black;">
							<div width=770><!-- 연락처 리스트 테이블을 감싸줄 div 시작 -->
								<table class="ui blue table"><!-- 연락처 리스트 테이블 시작 -->
									<tr>
										<c:choose>
											<c:when test="${fn:length(user.phoneList) == 0}"><!-- 연락처 리스트가 없으면 rowspan을 +2를 해줘야하고 그 외에는 1을 해줘야한다.-->
												<td rowspan="${fn:length(user.phoneList)+2}" width=200 style="text-align:center;"><div></div></td><!-- 연락처 테이블 공백부분임 -->
											</c:when>
											<c:otherwise>
												<td rowspan="${fn:length(user.phoneList)+1}" width=200 style="text-align:center;"><div></div></td><!-- 연락처 테이블 공백부분임 -->
											</c:otherwise>
										</c:choose>
										
										<td width=250 style="text-align:center;"><div><strong><span style="color:red">연락처번호</span></strong></div></td>
										<td width=120 style="text-align:center;"><div><strong><span style="color:red">통신사</span></strong></div></td>
										<td width=100 style="text-align:center;"><div><strong><span style="color:red">수정</span></strong></div></td>
										<td width=100 style="text-align:center;"><div><strong><span style="color:red">삭제</span></strong></div></td>
									<c:choose>
										<c:when test="${phinsert && idpl == user.id}"><!-- 해당 유저에게 연락처 추가 할 때 -->
											<td width=500><div>&emsp;</div></td>
											<!-- 여기서는 아무것도 하지 출력하지 않는다! -->
										</c:when>
										<c:otherwise><!-- 리스트를 보거나 수정하기만 할 때는 연락처 등록 버튼이 떠있어야 한다. -->
											<td width=500 rowspan="${fn:length(user.phoneList)+2}" style="text-align:left;" valign=bottom><div><input type=button value="연락처등록" OnClick='insertPhoneFunction(${user.id}, "true", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted violet button"></div></td>
										</c:otherwise>
									</c:choose>
									
									</tr>
									<c:forEach items="${user.phoneList}" var="phone" varStatus="status">
										<tr>
											<!-- 연락처 수정 눌렀을 때 phup 변수를 파라미터로 받아서 input태그로 나오도록 choose문.. -->
											<c:choose>
												<c:when test="${phup == phone.id }">
												<!-- 연락처 수정 라인 시작 -->
												<!--<td width=140></td>연락처 테이블 공백부분이었는데 윗 줄 첫 칸의 rowspan 때문에 필요없어짐 -->
													<input type=hidden name=phonealllist value="true">
													<input type=hidden name=idpl value="${user.id}">
													<input type=hidden name=phid value="${phone.id}">
													
													<td width=250 style="text-align:center;">
														<div class="ui input focus">
															<input type=text name="phonenumber" id="phonenumber" onkeydown="moveFocus('telecom');" value="${phone.phonenumber}" style="text-align:center; width:220;">
														</div>
													</td>
													<td width=120 style="text-align:center;">
														<div>
															<select name="telecom" id="telecom" onkeydown="moveFocus('phoneupdatesubmit');" class="ui dropdown">
															    <option value="KT" <c:if test="${phone.telecom eq 'KT'}">SELECTED</c:if>><strong>KT</strong></option>
															    <option value="SKT" <c:if test="${phone.telecom eq 'SKT'}">SELECTED</c:if>><strong>SKT</strong></option>
															    <option value="LGU+" <c:if test="${phone.telecom eq 'LGU+'}">SELECTED</c:if>><strong>LGU+</strong></option>
															    <option value="알뜰폰" <c:if test="${phone.telecom eq '알뜰폰'}">SELECTED</c:if>><strong>알뜰폰</strong></option>
															    <option value="유선" <c:if test="${phone.telecom eq '유선'}">SELECTED</c:if>><strong>유선</strong></option>
															</select>
														</div>
													</td>
													<td width=100 style="text-align:center;"><div><input type=button id="phoneupdatesubmit" value="등록" OnClick="submitFunction('phone_update');" class="ui inverted orange button"></div></td>
													<td width=100 style="text-align:center;"><div><input type=button value="삭제" OnClick='deletePhoneFunction(${user.id}, "${phone.id}", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted purple button"></div></td>
												<!-- 연락처 리스트 출력 라인 끝 -->
												</c:when>
												<c:otherwise>
													<!-- 연락처 리스트 출력 라인 시작 -->
												<!--<td width=140></td>연락처 테이블 공백부분이었는데 윗 줄 첫 칸의 rowspan 때문에 필요없어짐 -->
													<td width=250 style="text-align:center;">
													<div>
														<script>
															var phonenumber= '<c:out value="${phone.phonenumber}"/>'
															document.write(phoneNum(phonenumber));
														</script>
	
													</div>
													</td>
													<td width=120 style="text-align:center;"><div>${phone.telecom }</div></td>
													<td width=100 style="text-align:center;"><div><input type=button value="수정" OnClick='updatePhoneFunction(${user.id}, "${phone.id}", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted orange button"></div></td>
													<td width=100 style="text-align:center;"><div><input type=button value="삭제" OnClick='deletePhoneFunction(${user.id}, "${phone.id}", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted purple button"></div></td>
												<!-- 연락처 리스트 출력 라인 끝 -->	
												</c:otherwise>
											</c:choose>
										</tr>
	
					 				</c:forEach>
					      			<c:if test="${fn:length(user.phoneList) == 0}"><!-- 해당 유저에게 phonelist가 없을 때 if 시작 -->
						      				<tr>
						      					<td width=340 height=58 colspan=4 class="error"><div>&emsp;&emsp;&emsp;<i class="attention icon"></i><span style="color:red">&emsp;연락처가 등록되지 않은 고객입니다</span></div></td>
						      				</tr>
									</c:if><!-- 해당 유저에게 phonelist가 없을 때 if 종료-->
									<c:if test="${phinsert && idpl == user.id }"><!-- 해당 유저에게 연락처를 추가할 때 -->
										<input type=hidden name=idpl value="${user.id}">
										<input type=hidden name=phonealllist value="true">
					      				<tr>
					      					<td width=200 style="text-align:center;"><div><span style="color:red;">연락처등록</span></div></td>
					      					<td width=250 style="text-align:center;">
		      									<div class="ui input focus">
													<input type=text name="phonenumber" id="phonenumber" onkeydown="moveFocus('telecom');" style="text-align:center; width:140;">
												</div>
					      					</td>
											<td width=120 style="text-align:center;">
												<div>
													<select name="telecom" id="telecom" onkeydown="moveFocus('phoneinsertsubmit');" class="ui dropdown">
													    <option value="KT"><strong>KT</strong></option>
													    <option value="SKT"><strong>SKT</strong></option>
													    <option value="LGU+"><strong>LGU+</strong></option>
													    <option value="알뜰폰"><strong>알뜰폰</strong></option>
													    <option value="유선"><strong>유선</strong></option>
													</select>
												</div>
											</td>
											<td width=100 style="text-align:center;"><div><input type=button id="phoneinsertsubmit" value="등록" OnClick="submitFunction('phone_insert');" class="ui inverted orange button"></div></td>
											<td width=100 style="text-align:center;"><div><input type=button value="취소" OnClick="history.back();" class="ui inverted purple button"></div></td>
<!-- 											<td width=500></td> -->
					      				</tr>
									</c:if><!-- 해당 유저에게 연락처 추가 완료-->
								</table><!-- 연락처 리스트 테이블 종료 -->
							</div><!-- 연락처 리스트 테이블을 감싸줄 div 종료 -->
						</td>
					</tr><!-- 연락처 리스트 테이블이 들어갈 tr 종료 -->
			</c:when>
			<c:when test="${idpl == user.id }">
				<tr><!-- 연락처 리스트 테이블이 들어갈 tr 시작 -->
						<td colspan=8 style="border-bottom:1px solid black;">
							<div width=1000><!-- 연락처 리스트 테이블을 감싸줄 div 시작 -->
								<table class="ui blue table"><!-- 연락처 리스트 테이블 시작 -->
									<tr>
										<c:choose>
											<c:when test="${fn:length(user.phoneList) == 0}"><!-- 연락처 리스트가 없으면 rowspan을 +2를 해줘야하고 그 외에는 1을 해줘야한다.-->
												<td rowspan="${fn:length(user.phoneList)+2}" width=200 style="text-align:center;"><div></div></td><!-- 연락처 테이블 공백부분임 -->
											</c:when>
											<c:otherwise>
												<td rowspan="${fn:length(user.phoneList)+1}" width=200 style="text-align:center;"><div></div></td><!-- 연락처 테이블 공백부분임 -->
											</c:otherwise>
										</c:choose>
										
										<td width=250 style="text-align:center;"><div><strong><span style="color:red">연락처번호</span></strong></div></td>
										<td width=120 style="text-align:center;"><div><strong><span style="color:red">통신사</span></strong></div></td>
										<td width=100 style="text-align:center;"><div><strong><span style="color:red">수정</span></strong></div></td>
										<td width=100 style="text-align:center;"><div><strong><span style="color:red">삭제</span></strong></div></td>
									<c:choose>
										<c:when test="${phinsert}"><!-- 해당 유저에게 연락처 추가 할 때 -->
											<td width=500><div>&emsp;</div></td>
											<!-- 여기서는 아무것도 하지 출력하지 않는다! -->
										</c:when>
										<c:otherwise><!-- 리스트를 보거나 수정하기만 할 때는 연락처 등록 버튼이 떠있어야 한다. -->
											<td width=500 rowspan="${fn:length(user.phoneList)+2}" style="text-align:left;" valign=bottom><div><input type=button value="연락처등록" OnClick='insertPhoneFunction(${user.id}, "true", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted violet button"></div></td>
										</c:otherwise>
									</c:choose>
									
									</tr>
									<c:forEach items="${user.phoneList}" var="phone" varStatus="status">
										<tr>
											<!-- 연락처 수정 눌렀을 때 phup 변수를 파라미터로 받아서 input태그로 나오도록 choose문.. -->
											<c:choose>
												<c:when test="${phup == phone.id }">
												<!-- 연락처 수정 라인 시작 -->
												<!--<td width=140></td>연락처 테이블 공백부분이었는데 윗 줄 첫 칸의 rowspan 때문에 필요없어짐 -->
													<input type=hidden name=idpl value="${user.id}">
													<input type=hidden name=phid value="${phone.id}">
													<td width=250 style="text-align:center;">
														<div class="ui input focus">
															<input type=text name="phonenumber" id="phonenumber" onkeydown="moveFocus('telecom');" value="${phone.phonenumber}" style="text-align:center; width:140;">
														</div>
													</td>
													<td width=120 style="text-align:center;">
														<div>
															<select name="telecom" id="telecom" onkeydown="moveFocus('phoneupdatesubmit');" class="ui dropdown">
															    <option value="KT" <c:if test="${phone.telecom eq 'KT'}">SELECTED</c:if>><strong>KT</strong></option>
															    <option value="SKT" <c:if test="${phone.telecom eq 'SKT'}">SELECTED</c:if>><strong>SKT</strong></option>
															    <option value="LGU+" <c:if test="${phone.telecom eq 'LGU+'}">SELECTED</c:if>><strong>LGU+</strong></option>
															    <option value="알뜰폰" <c:if test="${phone.telecom eq '알뜰폰'}">SELECTED</c:if>><strong>알뜰폰</strong></option>
															    <option value="유선" <c:if test="${phone.telecom eq '유선'}">SELECTED</c:if>><strong>유선</strong></option>
															</select>
														</div>
													</td>
													<td width=100 style="text-align:center;"><div><input type=button id="phoneupdatesubmit" value="등록" OnClick="submitFunction('phone_update');" class="ui inverted orange button"></div></td>
													<td width=100 style="text-align:center;"><div><input type=button value="삭제" OnClick='deletePhoneFunction(${user.id}, "${phone.id}", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted purple button"></div></td>
												<!-- 연락처 리스트 출력 라인 끝 -->
												</c:when>
												<c:otherwise>
													<!-- 연락처 리스트 출력 라인 시작 -->
												<!--<td width=140></td>연락처 테이블 공백부분이었는데 윗 줄 첫 칸의 rowspan 때문에 필요없어짐 -->
													<td width=250 style="text-align:center;">
													<div>
														<script>
															var phonenumber= '<c:out value="${phone.phonenumber}"/>'
															document.write(phoneNum(phonenumber));
														</script>
	
													</div>
													</td>
													<td width=120 style="text-align:center;"><div>${phone.telecom }</div></td>
													<td width=100 style="text-align:center;"><div><input type=button value="수정" OnClick='updatePhoneFunction(${user.id}, "${phone.id}", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted orange button"></div></td>
													<td width=100 style="text-align:center;"><div><input type=button value="삭제" OnClick='deletePhoneFunction(${user.id}, "${phone.id}", "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted purple button"></div></td>
												<!-- 연락처 리스트 출력 라인 끝 -->	
												</c:otherwise>
											</c:choose>
										</tr>
	
					 				</c:forEach>
					      			<c:if test="${fn:length(user.phoneList) == 0}"><!-- 해당 유저에게 phonelist가 없을 때 if 시작 -->
						      				<tr>
						      					<td width=570 height=58 colspan=4 class="error"><div>&emsp;&emsp;&emsp;<i class="attention icon"></i><span style="color:red">&emsp;연락처가 등록되지 않은 고객입니다</span></div></td>
						      				</tr>
									</c:if><!-- 해당 유저에게 phonelist가 없을 때 if 종료-->
									<c:if test="${phinsert}"><!-- 해당 유저에게 연락처를 추가할 때 -->
										<input type=hidden name=idpl value="${user.id}">
					      				<tr>
					      					<td width=200 style="text-align:center;"><div><span style="color:red;">연락처등록</span></div></td>
					      					<td width=250 style="text-align:center;">
		      									<div class="ui input focus">
													<input type=text name="phonenumber" id="phonenumber" onkeydown="moveFocus('telecom');" style="text-align:center; width:140;">
												</div>
					      					</td>
											<td width=120 style="text-align:center;">
												<div>
													<select name="telecom" id="telecom" onkeydown="moveFocus('phoneinsertsubmit');" class="ui dropdown">
													    <option value="KT"><strong>KT</strong></option>
													    <option value="SKT"><strong>SKT</strong></option>
													    <option value="LGU+"><strong>LGU+</strong></option>
													    <option value="알뜰폰"><strong>알뜰폰</strong></option>
													    <option value="유선"><strong>유선</strong></option>
													</select>
												</div>
											</td>
											<td width=100 style="text-align:center;"><div><input type=button id="phoneinsertsubmit" value="등록" OnClick="submitFunction('phone_insert');" class="ui inverted orange button"></div></td>
											<td width=100 style="text-align:center;"><div><input type=button value="취소" OnClick="history.back();" class="ui inverted purple button"></div></td>
											<td width=500></td>
					      				</tr>
									</c:if><!-- 해당 유저에게 phonelist가 없을 때 if 종료-->
								</table><!-- 연락처 리스트 테이블 종료 -->
							</div><!-- 연락처 리스트 테이블을 감싸줄 div 종료 -->
						</td>
					</tr><!-- 연락처 리스트 테이블이 들어갈 tr 종료 -->
			</c:when>
			
		</c:choose>

	</c:forEach><!-- 유저 한 명의 정보 뿌리는 forEach문 종료 -->
									
	<tr><!-- 테이블 마지막 줄에 사용자 등록 라인 시작 -->
		<!-- 유저등록버튼을 출력하거나 유저등록창을 나타낼 c:choose 태그 시작 -->
		<c:choose>
			<c:when test="${userinsert}"><!-- 유저등록버튼을 누른 상황일 때 : 테이블 마지막 줄을 input 창으로 변경 -->
				<input type=hidden name=phonealllist value="${phonealllist}">
				<td style="text-align:center;">
    				<div class="ui input focus">
						<input type=text name="name" id="name" onkeydown="moveFocus('age');" style="text-align:center; width:170;">
					</div>
				</td>
				<td style="text-align:center;">
    				<div class="ui input focus">
						<input type=number name="age" id="age" onkeydown="moveFocus('sex');" style="text-align:center; width:70;">
					</div>
				</td>
				<td style="text-align:center;">
					<div>
						<select name="sex" id="sex" onkeydown="moveFocus('address1');"  class="ui dropdown">
						    <option value="남">남</option>
						    <option value="여">여</option>
						</select>
					</div>
				</td>
				<td style="text-align:center;">
					<div class="ui input focus">
						<input type=text name="address1" id="address1" onkeydown="moveFocus('address2');"  style="text-align:left; width:270;">
					</div>
				</td>
				<td style="text-align:center;">
					<div class="ui input focus">
						<input type=text name="address2" id="address2" onkeydown="moveFocus('userinsertsubmit');"  style="text-align:left; width:420;">
					</div>
				</td>
				<td colspan=2 style="text-align:left;"><div><input type=button id="userinsertsubmit"  value="사용자등록완료" OnClick="submitFunction('user_insert');" class="ui inverted orange button"></div></td>
				<td style="text-align:right;"><div><input type=button value="취소" OnClick="history.back();"  class="ui inverted orange button">&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;</div></td>
			</c:when>
			<c:otherwise><!-- 유저등록버튼을 누른 상황이 아닐 때 : colspan 때린 td 하나면 됨 -> 잘 안되서 그냥 td/td 씀-->
				<td colspan=8 style="text-align:right;"><div><input type=button value="사용자등록" OnClick='insertUserFunction(true, "${searchusername}", "${searchphonenumber}", "${page }", "${pageSize }", "${phonealllist }");' class="ui inverted orange button">&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;&thinsp;</div></td>
			</c:otherwise>
		</c:choose>
		<!-- 유저등록버튼을 출력하거나 유저등록창을 나타낼 c:choose 태그 종료 -->

	</tr><!-- 테이블 마지막 줄에 사용자 등록 라인 종료 -->
	</tbody>
</table><!-- 유저 리스트 테이블 종료 -->

<!-- 페이지네이션 테이블 시작 -->
<div style="width:1500">

<table>
<tr>
	<td width=1500 align=center>
		[
		<a href="#" onclick='paginationFunction("${paginationVO.first }", "${pageSize }", "${searchusername}", "${searchphonenumber}")' style="text-decoration:none"><STRONG><span style="color:blue"><<</span></STRONG></a>&emsp;
		<a href="#" onclick='paginationFunction("${paginationVO.prev }", "${pageSize }", "${searchusername}", "${searchphonenumber}")' style="text-decoration:none"><STRONG><span style="color:green"><</span></STRONG></a>&emsp;
		
		      <c:forEach var="i" begin="${paginationVO.start }" end="${paginationVO.end }" step="1">
		      &ensp;
		      	<c:choose>
			      	<c:when test="${page == i }">
			      		<STRONG><span style="color:black">${i}</span></STRONG>
			      	</c:when>
			        <c:otherwise>
			        	<a href="#" onclick='paginationFunction("${i }", "${pageSize }", "${searchusername}", "${searchphonenumber}")' style="text-decoration:none"><span style="color:red">${i}</span></a>
			    	</c:otherwise>
		        </c:choose>
		      &ensp;
		      </c:forEach>

		&emsp;<a href="#" onclick='paginationFunction("${paginationVO.next }", "${pageSize }", "${searchusername}", "${searchphonenumber}")' style="text-decoration:none"><STRONG><span style="color:green">></span></STRONG></a>
		&emsp;<a href="#" onclick='paginationFunction("${paginationVO.last }", "${pageSize }", "${searchusername}", "${searchphonenumber}")' style="text-decoration:none"><STRONG><span style="color:blue">>></span></STRONG></a>
		]
	</td>
</tr>
</table>


</FORM>
</div>

</body>
</html>