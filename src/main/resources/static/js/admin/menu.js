/**
 * 
 */
$(function(){
	
	$(".menu").click(memuClicked);
	//관리자페이지 메뉴클릭시 컨트롤러
	//$(".menu-wrap a").click(memuItemClicked);
});
//메뉴 목록 클릭시 처리 a 태그
function memuItemClicked(event){
	event.preventDefault();
	//a href 를 비동기요청
	$.get(
		$(this).attr("href"),
		function(resultMain){
			$("main>.wrap").html(resultMain);
		}
	);
}

//메뉴클릭시 세부메뉴 목록 보이고-감추기
function memuClicked(){
	var check=$(this).hasClass("open");
	$(".menu").removeClass("open");//나머지는 다 닫고
	if(!check){
		$(this).addClass("open");
	}
}

//카테고리 등록
function categorySubmitted(){
	var formData=$("#form-category").serialize();
	$.ajax({
		url:"/admin/category",
		type: "POST",
		data: formData,
		success:function(){
			$("a[href='/admin/category/new']").trigger("click");
			alert("등록완료");
		}
	});
}


function category2List(selectTag){
	var parentNo=$(selectTag).val();
	if(parentNo=="")return;
	//console.log("parentNo : "+ parentNo);
	$.ajax({
		url: `/admin/category/${parentNo}`,
		type: "PATCH",
		success: function(result){
			
			$(selectTag).next().html(result);
		}
	});
}


