/**
 * 
 */

// 선택한 날짜에 원형 배경을 추가하는 함수입니다
function addPickTitle($target, isStartOrEnd) {
  $target.addClass("selected");
  if($target.next('.pick-title').length > 0)return;
  
  if(isStartOrEnd){
	  $target.after(`<p class="pick-title">${startTitle}</p>`);
  }else{
	  $target.after(`<p class="pick-title">${endTitle}</p>`);
  }
}
function clearPickInfo(){
	removePickData();
	$(".calendar .month-all").find(":checkbox").prop("checked",false);
	$("#sale-start-date").val("");
	$("#sale-end-date").val("");
	$("#salePrice").val("");
}
// 이전에 선택한 날짜에 있는 원형 배경과 "오늘" 텍스트를 제거하는 함수입니다
function removePickData() {
  $(".day>div span.selected").removeClass("selected");
  $("span[date].pick-span").removeClass("pick-span");
  $(".pick-title").remove();
  
  $(".date").text("-------");
  //$(".calendar ").find(":checkbox").prop("checked", false);
   startDate = null;
   endDate = null;
 
}

// .day div 요소의 클릭 이벤트 핸들러입니다
function dayClicked() {
	
  var $selectedDate = $(this);

  // 출발일과 도착일이 모두 설정되었는지 확인합니다
  if (startDate && endDate) {
    removePickData();
  }

  // 출발일이 설정되지 않았을 경우, 해당 날짜를 출발일로 설정합니다
  if (!startDate) {
    startDate = $selectedDate.attr("date");
    addPickTitle($(this), true);
    printDate(startDate,true)
  }
  // 출발일이 설정되었고 도착일이 설정되지 않았을 경우, 해당 날짜를 도착일로 설정합니다
  else if (!endDate) {
    endDate = $selectedDate.attr("date");
    
    if(endDate<startDate){
		/*
		alert(`${startTitle}일 이후 날짜로 선택하세요`);
		endDate=null;
		*/
		removePickData();
		startDate = $selectedDate.attr("date");
	    addPickTitle($(this), true);
	    printDate(startDate,true)
		return;
	}
    
    addPickTitle($(this), false);
    printDate(endDate, false);
    //출발-도착 사이 날짜 백그라운드 설정
    pickSpan();
    
    //월전체 체크
    monthAllCheck($(this).parents(".calendar").find(":checkbox"));
  }//elseIF
  console.log(startDate +" -> "+ endDate);
}
function printDate(targetDateStr, isStart){
	const targetDate=new Date(targetDateStr);
	
	// 요일을 텍스트로 변환
	const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
	const dayOfWeekText = daysOfWeek[targetDate.getDay()];
	var month=targetDate.getMonth()+1;
	var day=targetDate.getDate();
	// 포맷 출력
	const formattedDate = `${targetDate.getFullYear()}.${month < 10 ? '0' + month : month}.${day < 10 ? '0' + day : day}(${dayOfWeekText})`;
	if(isStart){
		$(".start-date").text(formattedDate);
	}else{
		$(".end-date").text(formattedDate);
	}

}
function pickSpan(){
	
	$("span[date]").each(function() {
      var dateValue = $(this).attr("date");
      if (dateValue == startDate){
	  	addPickTitle($(this), true);
	  }else if(dateValue == endDate){
		addPickTitle($(this), false);
	  }
	  
	  if (dateValue > startDate && dateValue < endDate) {
        //사이 구간에 pick-span 클래스명 적용 
        $(this).addClass("pick-span");
      }
    });
    
}
function monthAllCheck($checkBox){
	
	$checkBox.prop("checked", false);
	
	if(startDate!=null && endDate !=null){
		var yearMonth=$checkBox.val();
		var year=yearMonth.substring(0,4);
		var month=yearMonth.slice(-2);
		
		var lastDayOfMonth=getLastDayOfMonth(year, month-1);
		var sYearMonth=startDate.substring(0,7);
		var eYearMonth=endDate.substring(0,7);
		//console.log("startDate:"+startDate+", endDate:"+endDate);
		//console.log("sYearMonth:"+sYearMonth+", eYearMonth:"+eYearMonth);
		//같은 년월 확인
		if(sYearMonth!=eYearMonth)return;
		
		var sDay=startDate.slice(-2);
		var eDay=endDate.slice(-2);
		//console.log(sDay+":"+eDay);
		if(yearMonth==sYearMonth && lastDayOfMonth==eDay && sDay == "01") {
			$checkBox.prop("checked", true);
		}
		
		const todayYear = today.getFullYear();
		const todayMonth = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고, padStart로 2자리로 만듦
		const todayYearMonth = `${todayYear}-${todayMonth}`;
		console.log("yearMonth:"+yearMonth);
		console.log("todayYearMonth:"+todayYearMonth);
		if(yearMonth==todayYearMonth && lastDayOfMonth==eDay && sDay == today.getDate()) {
			//console.log("월전체 확인");
			$checkBox.prop("checked", true);
		}
		
	}
}


//월전체 선택 클릭시 처리
function pickTargetMonthAll(target){
	//리셋
	removePickData();
	
	if($(target).find(":checked").length==0)return;
	
	//다른체크박스가 체크되어 있으면 체크박스 해제
	//calendar.first > li.calendar-title.flex.center > div > label > input[type=checkbox]
	$(".calendar  :checkbox").not($(target).find(":checkbox")).prop("checked",false);
	
	//$(".calendar :checkbox").prop("checked",false);
	//$(target).find(":checkbox").prop("checked",true);
	
	//해달월 전체 적용
	const yearMonthString = $(target).find("input").val();
	
	const year = parseInt(yearMonthString.substring(0, 4));
	const month = parseInt(yearMonthString.slice(-2)); // 1을 빼서 월을 0부터 11로 표현
	const targetDate = new Date(year, month-1);
	
	if(year<today.getFullYear())return;
	if(month<today.getMonth()+1)return;
	
	const lastDay = getLastDayOfMonth(year, month-1);
	let startDay=1;
	
	if(year==today.getFullYear() && month==today.getMonth()+1){
		startDay=today.getDate();
	}
	
	const formattedMonth = month.toString().padStart(2, '0');
	const formattedStartDay = startDay.toString().padStart(2, '0');
	
	//시작날짜 표기
	startDate =`${year}-${formattedMonth}-${formattedStartDay}`;
	printDate(startDate, true);
	
	//종료날짜 표기 
	endDate =`${year}-${formattedMonth}-${lastDay}`;
	printDate(endDate, false);
	
	
	//구간처리
	pickSpan();
}

//윤년판단
function isLeapYear(year) {
  return (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0;
}
//마지막 날 리턴 (0~11 까지만)
function getLastDayOfMonth(year, month) {
  if (month === 1) { // February
    return isLeapYear(year) ? 29 : 28;
  } else if (month === 3 || month === 5 || month === 8 || month === 10) {
    return 30;
  } else {
    return 31;
  }
}

function calendarSelected(){
	console.log(startDate +":"+endDate);
	if(startDate==null){
		$("#sale-start-date").val("");
		$("#sale-end-date").val("");
		alert("할인날짜가 적용되지 않았습니다.");
		return;	
	}else if(endDate==null){
		$("#sale-start-date").val("");
		$("#sale-end-date").val("");
		alert("종료일이 적용되지 않았습니다.");
		return;	
	}
	$("#sale-start-date").val(startDate);
	$("#sale-end-date").val(endDate);
	$("#calendar-form").slideUp();
}
function showCalendar(){
	$("#calendar-form").slideDown();
	$("#saleDate").css("display","inline");
}
function saleClicked(){
	$(".sale-wrap").show();
}
function saleCancle(){
	$(".sale-wrap").hide();
	
	clearPickInfo()
}
function dateClicked(){
	$("#saleDate-wrap").show();
}
function dateCancel(){
	$("#saleDate-wrap").hide();
	clearPickInfo();
}

var saleInfo=$("#sale-info > label > input:checked").val()
var dur=$("#sale-duration > label > input[type=radio]:checked").val()
$(function(){
	if(saleInfo ==="true"){
		saleClicked()
	}
	if(dur==="true"){
		dateClicked()
	}
})