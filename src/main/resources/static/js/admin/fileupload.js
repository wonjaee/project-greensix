/**
 * 
 */
$(function() {
	categoryShow($(".category"));
})
function submitCheck(){
	var cate=$(".category2 > select").val()
	var name=$("#name").val()
	var price=$("#price").val()
	var content=$("#summernote").val()
	if(cate==null||cate==""){
		alert("카테고리를 지정해주세요")
		return false;
	}else if(name==null||name==""){
		alert("상품명을 작성해주세요")
		return false;
	}else if(price==null||price==""){
		alert("상품가격을 지정해주세요")
		return false;
	}else if(content==null||content==""){
		alert("상품설명을 작성해주세요")
		return false;
	}else{
		return true;
	}
}


function categoryShow(tag) {
	var parentNo = $(tag).attr("value");
	var tagVal=$(tag).val()
	console.log(tagVal)
	if (parentNo == 0) {
		$.ajax({
			url: `/product/category/${parentNo}`,
			success: function(result) {
				$(".category").html(result)
			}
		})
	}
	else if(tagVal!=null) {
		$.ajax({
			url: `/product/category/${tagVal}`,
			success: function(result) {
				$(".category2").html(result)
				$(".category2 > select").removeAttr("onchange")
				$(".category > select").removeAttr("name")
			}
		})
	}
}

function goodsSummited() {
	var bool=submitCheck()
	if(bool==false) return;
	
	var data = $("#form-goods").serialize();

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	//console.log(data);
	$.ajax({
		url: "/admin/goods",
		type: "post",
		data: data,
		success: function(result) {
			/*$("a[href='/admin/goods/new']").trigger("click");*/
			location.href = "new"
			alert("등록완료");
		}
	});

}

function tempUpload(fileEl) {
	console.log($(fileEl));
	var fileData = $(fileEl)[0].files[0];
	var formData = new FormData();
	formData.append("temp", fileData);

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	$.ajax({
		url: "/admin/goods/temp-img",
		type: "POST",
		contentType: false,
		processData: false,
		data: formData,
		success: function(resultMap) {
			$(fileEl).parent().css("background-image", `url(${resultMap.url})`).addClass("enabled");
			$(fileEl).parents(".img-wrap").find(".url").val(resultMap.url);
			$(fileEl).parents(".img-wrap").find(".orgName").val(resultMap.orgName);
			$(fileEl).parents(".img-wrap").find(".newName").val(resultMap.newName);
			$(fileEl).parents(".img-wrap").find(".bucketKey").val(resultMap.bucketKey);

			var def = $(fileEl).parents(".img-wrap").find(".def").val();
			if (def == "true") return;//대표이미지이면 함수종료

			//추가이미지일떄 태그추가	
			var addLength = $(".add-img>.img-wrap").length;
			console.log("추가이미지 개수:" + addLength);
			var targetIdx = $(fileEl).parents(".img-wrap").index() + 1;
			console.log("수정하는 이미지 위치:" + targetIdx);
			//추가하면 안되는경우 
			//1.태그가 5개가 완료된경우
			//2. 5개만들기전 이전이미지를 수정하면
			if (addLength >= 5 || targetIdx < addLength) return;

			var appendTag = `
			 <div class="img-wrap">
				<label class="pre-img"><input type="file" onchange="tempUpload(this)"></label>
				<input type="hidden" class="bucketKey" name="bucketKey">
				<input type="hidden" class="orgName" name="orgName">
				<input type="hidden" class="newName" name="newName">
				<input type="hidden" class="url" name="url">
				<input type="hidden" class="def" name="def" value="false">
			 </div>
			 `;
			$(".add-img").append(appendTag);
			console.log("태그추가됨~");


		}
	});
}
