/**
 * 회원가입 정규식
 */
//정규식
var check =[
	{
		item: "이메일",
		regex:/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
		comment: " 예) test@test.co.kr, test@test.com"
	},
	{
		item: "비밀번호",
		regex:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/,
		comment: "대문자,소문자,숫자 조합 8자리이상"
		
	},
	{
		item: "이름",
		regex:/^[가-힣]{2,6}$/,
		comment: "한글 2글자이상 6자리까지만 허용"
	}
]
var msg="";
//이메일,비밀번호,이름 유효성 검사 함수
function validateInput(target, idx) {
	
	//console.log(check[idx].regex.test("test01@test.com"));
	//*
	idx=parseInt(idx);
	//alert(check[idx])
	var inputText = $(target).val().trim();
	if (inputText == ""){//비어있을때
		msg= check[idx].item
		if(idx==1) msg+="를 "
		else msg+="을 "
		msg+="입력해주세요.";
		$(target).val("");
	}else if ( !(check[idx].regex.test(inputText)) ){//유효성검사 실패시
		msg= "유효한 "+check[idx].item+" 형식이 아닙니다. "+check[idx].comment;
		
		
		if(idx==1){
			msg+=""
		}
	}else if(idx==0 && existEmailCheck(inputText)){
		msg="이미 존재하거나 탈퇴한 회원입니다.";
	}else{
		//문제가 없는경우
		checkTrue(target);
		$(target).parents("li").find(".msg").html("");
		return;
	}
	
	
	//에러메세지 출력
	msgPrint(target);
	//*/
}
function checkTrue(target){
	$(target).siblings(".required").prop('checked', true); // 체크됨
}
function msgPrint(target){
	$(target).parents("li").find(".msg").html(msg);
	$(target).siblings(".required").prop('checked', false); // 체크해제됨
}

function validateRePass(target){
	var rePass=$(target).val()
	var pass=$("#pass").val()
	if(rePass==""){
		msg="비밀번호 재확인 입력하지 않았습니다.";
		msgPrint(target);
		return;
	}else if(pass==rePass){
		checkTrue(target);
		$(target).parents("li").find(".msg").html("");
		return;
	}
	msg="비밀번호가 일치하지 않습니다.";
	msgPrint(target);
}

// 회원가입 폼 유효성 검사 함수
function validateForm() {
	var checkall=true;
	$("#form-signup .required").each(function() {
	    // 체크박스마다 실행할 코드 작성
	    if (!$(this).is(":checked")) {
			console.log("체크누락!")
	        msg="필수입력 항목 입니다.";
	        msgPrint($(this));
	        checkall=false;
	        return;
	    } 
	});
	return checkall;
}

function existEmailCheck(email){
	var isExist=false;
	$.ajax({
			url:"/common/email-check",
			async:false, //동기호출??: success 함수가 실행할때까지 기다렸다 return
			data: {email: email},
			success: function(result){
				isExist=result;
			}
		});
	return isExist;
}

function changeForm1(btn){
	$(btn).parents().siblings("li").removeClass("target");
	$(btn).parents().addClass("target");
	$("#signin-form").hide()
	$("#signup-form").show()
}
function changeForm2(btn){
	$(btn).parents().siblings("li").removeClass("target");
	$(btn).parents().addClass("target");
	$("#signup-form").hide()
	$("#signin-form").show()
}



///*****문제구간*****

/*
existEmailCheck(inputText)
	//resolve(resutl) 처리시 then
	.then(function(result){
		isExist=result;
		alert(isExist);
	})
	.catch(function(error){
		console.error(error);
	});
		
function existEmailCheck(email){
	//비동기 요청한 결과를 리턴받고 싶을때 
	return new Promise(function(resolve, reject){
		$.ajax({
			url:"/common/email-check",
			data: {email: email},
			success: function(result){
				//존재하면 true:사용불가
				//존재하지않으면false:사용가능
				resolve(result)
			},
			error: function(error){
				reject(error);
			}
		});
	});
}
//*/

