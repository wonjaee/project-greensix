/**
 * 
 */
	$(function(){
			$("#btn-update").click(clickedBtnUpdate);
			$("#btn-cancle").click(clickedBtnCancle);
			$(".update-form").hide();
			
	});
function submitCheck(){
	var name=$(".title").val()
	var content=$("content").val()
	if(name==null||name==""){
		alert("제목을 작성해주세요")
		return false;

	}else if(content==null||content==""){
		alert("내용을 작성해주세요")
		return false;
	}else{
		return true;
	}
}
function submitCheckNotice(){
	var bool=submitCheck()
	if(bool==false) return;
}	
	function clickedBtnUpdate(){
		$(".basic").hide();
		$(".update-form").show();
	}
	
	function clickedBtnCancle(){
		$(".basic").show();
		$(".update-form").hide();
	}
