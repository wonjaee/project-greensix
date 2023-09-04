/**
 * 
 */


var today;

// 출발일과 도착일을 저장할 변수를 선언합니다
var startDate = null;
var endDate = null;
var startTitle="할인시작";
var endTitle="할인종료";

$(function(){
	//서버시간으로
	$.ajax({
		url:"/common/today",
		success:function(dateString){
			console.log(">> "+dateString);
			today = new Date(dateString);
			dateCreate(today);
			//변경테스트
			//dateCreate(new Date("2023-12-01"));
			$(".select-info .today.reset").attr("date",dateString);
			//클릭이벤트 등록
   			$(".day>div:not(.before) span").click(dayClicked);
		}
	});
});
function dateCreate(currentDate) {
	/////////////////////////////////////////////////////////////////
	// 현재 날짜로부터 년도와 월을 추출
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth() + 1; // getMonth()는 0부터 시작하므로 1을 더함

    // 이번 달 달력 생성
    generateCalendar(currentYear, currentMonth, $(".calendar.first"));

    // 다음 달 계산
    let nextYear = currentYear;
    let nextMonth = currentMonth + 1;
    if (nextMonth > 12) {
        nextYear++;
        nextMonth = 1;
    }

    // 다음 달 달력 생성
    generateCalendar(nextYear, nextMonth, $(".calendar.next"));
    
    //아이콘에 날짜세팅
    prevAndNext(currentYear,currentMonth);
}
function prevAndNext(currentYear, currentMonth){
	let prevYear=currentYear;
	let prevMonth=currentMonth - 1;
	if (prevMonth < 1) {
        prevYear--;
        prevMonth = 12;
    }
	//전월아이콘에 날짜세팅
	$(".icon.icon-prev").attr("date", `${prevYear}-${prevMonth.toString().padStart(2, '0')}-01`);
	
    let nextYear=currentYear;
	let nextMonth=currentMonth + 1;
	if (nextMonth > 12) {
        nextYear++;
        nextMonth = 1;
    }
    //다음월아이콘에 날짜세팅
	$(".icon.icon-next").attr("date", `${nextYear}-${nextMonth.toString().padStart(2, '0')}-01`);
	
	
}

function dateProcess(target){
	
	var dateValue=$(target).attr("date");//2023-09-01
	//console.log(dateStr);
	//새로운 달력생성
	dateCreate( new Date(`${dateValue}`));
	
	//클릭이벤트 등록
   	$(".day>div:not(.before) span").click(dayClicked);
   	
  
   	//이미 등록중이면  
   	if(startDate!=null && endDate != null){
		//console.log(">>>> "+startDate+" -> "+endDate);
		pickSpan();
	}
}

function generateCalendar(year, month, $calendar) {
	
	//console.log(">>>"+month);
	
    const formattedMonth = month.toString().padStart(2, '0'); // 7월을 '07'로 포맷
    // p 태그에 년과 월을 설정
    $calendar.find(".calendar-title p").html(
		`<span>${year}</span><span class="txt">년</span> 
		<span>${month}</span><span class="txt">월</span>`
		);
    // input 태그의 value 속성에 년과 월을 설정
    $calendar.find(".calendar-title input").val(`${year}-${formattedMonth}`);
	
	monthAllCheck($calendar.find(".calendar-title input"));
	
	const currentDay = today.getDate();
    const currentMonth = today.getMonth() + 1;

    // 월의 첫 번째 날 가져오기
    const firstDayOfMonth = new Date(year, month - 1, 1);
    // 월의 첫 번째 날의 요일을 구하기 위해 getDay() 메서드를 사용
    const firstDayOfWeek = firstDayOfMonth.getDay(); // 일요일(0)부터 토요일(6)까지의 값
    // 월의 마지막 날 가져오기
    const lastDayOfMonth = new Date(year, month, 0);

    // 토요일(6)을 기준으로 7일씩 묶은 배열 생성
    const weeks = [];
    let currentWeek = [];

    // 첫 번째 요일의 위치에 빈 요소(<span>)를 추가하여 빈 칸 만들기
    for (let i = 0; i < firstDayOfWeek; i++) {
        currentWeek.push(""); // 빈 문자열("") 대신 빈 요소(<span>)를 추가
    }

    for (let day = 1; day <= lastDayOfMonth.getDate(); day++) {
        currentWeek.push(day);

        if (currentWeek.length === 7 || day === lastDayOfMonth.getDate()) {
            weeks.push(currentWeek);
            currentWeek = [];
        }
    }

    // 달력에 날짜 추가
    $calendar.find(".calendar-day").empty();
    weeks.forEach(week => {
        const $li = $("<li class='day flex'></li>");
        week.forEach(day => {
			
            const $div = $("<div></div>");
            if (day !== "") {
				const formattedDay = day.toString().padStart(2, '0'); // 7월을 '07'로 포맷
                $div.html(`<span date="${year}-${formattedMonth}-${formattedDay}">${day}</span>`);
            }
            if ((day + firstDayOfMonth.getDay()) % 7 === 0) {
                $div.addClass("sat");
            } else if ((day + firstDayOfMonth.getDay() - 1) % 7 === 0) {
                $div.addClass("sun");
            }
            //console.log("year: "+year);
            //console.log("today.getYear(): "+today.getFullYear());
            if (year==today.getFullYear() && day == currentDay && month == currentMonth) {
                $div.addClass("today");
                $div.append("<p>TODAY</p>"); // <p> 태그 추가
            } else if (
					year<today.getFullYear() || 
            		(year==today.getFullYear() && month < currentMonth) || 
            		(year==today.getFullYear() && month == currentMonth && day < currentDay)  ) {
                $div.addClass("before"); // "before" 클래스 추가
            }
            $li.append($div);
        });
        //<ol class="calendar-day"> 내부에 li태그 동적생성
        $calendar.find(".calendar-day").append($li);
    });
    
   
}	

