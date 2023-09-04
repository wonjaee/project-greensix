/**
 * 
 */
/*//////////////////category button///////////////////////////*/
function cateBtnClicked(delBtn){
	$(delBtn).siblings(".cateDelHide").addClass("open");
	$(delBtn).removeClass("open");
	$(delBtn).addClass("hide");
	$(delBtn).parents(".mainList").siblings().children(".category-btn").removeClass("hide")
	$(delBtn).parents(".sublist").siblings().children(".category-btn").removeClass("hide")
	$(delBtn).parents(".mainList").siblings().children(".cateDelHide").removeClass("open")
	$(delBtn).parents(".sublist").siblings().children(".cateDelHide").removeClass("open")
	
 }

/*//////////////////category delete///////////////////////////*/
function cateDel(btn){
	var no =$(btn).parents().siblings("#cateNo").val();
	console.log(no);
	var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
	$.ajax({
		url:`/admin/category/${no}`,
		type:"DELETE",
		success:function(resultHref){
			location.href = resultHref;
		}
	
	})

}

/*//////////////////category update///////////////////////////*/

function cateUpdateBtn(btn){
	//cateUpdateBtn이 onclick되면 실행
	
	//no 와 name 값을 변수에 저장
	var no =$(btn).parents().siblings("#cateNo").val();
	var cateNameUpdate = $(btn).parent().siblings("#cateName").val();
	//alert(cateNameUpdate);
	
	//ajax csrf토큰
	var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
	//Json 
	var dataToSend = {name: cateNameUpdate};
	console.log(dataToSend);
	
  // 카테고리 update
  $.ajax({
    url: `/admin/category/${no}`,
    type: "PUT",
   	data: dataToSend,
    success: function (result) {
		//update 성공후 redirect 
     	location.href = result;
    }
  });
}



