/**
 * 
 */

$(function(){
	$("#order-wait").click(orderWait)
	$("#order-processing").click(orderProcessing)
	$("#order-cancel").click(orderCancel)
	$("#order-end").click(orderEnd)
	//$("input[name='order']:radio").change(changeStatus)
})

function changeStatus(btn){
	var status= $(btn).val()
	var storeNo= $(".storeNo").val()
	$.ajax({
		url:"/store",
		type:"PUT",
		data:
		{status:status,
		storeNo: storeNo},
		beforeSend: function (jqXHR) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           jqXHR.setRequestHeader(header, token);
		}
		
	})
}

 function changeForm1(btn){
	$(btn).parents().siblings().children("a").removeClass("target");
	$(btn).addClass("target");
	
 }

 
 function orderWait(){
	 $.ajax({
			url:"/franchisee/orderwait",
			success:function(result){
				$("#wait").html(result);
			}
			
		})

 }
 
 function orderProcessing(){
	
		 
		$.ajax({
			url:"/franchisee/processing",
			success:function(result){
				$("#wait").html(result);
			}
			
		})
	 ;
 }
  function orderCancel(){
	 
		 
		$.ajax({
			url:"/franchisee/cancel",
			success:function(result){
				$("#wait").html(result);
			}
			
		})
	 ;
 }
    function orderEnd(){
	
		 
		$.ajax({
			url:"/franchisee/end",
			success:function(result){
				$("#wait").html(result);
			}
			
		})
	 ;
 }
 function orderBtnClicked(){
	 $.ajax({
		 url:"/franchisee/processing",
		 success:function(result){
			 $("#wait").html(result);
		 }
	 })
 }
 