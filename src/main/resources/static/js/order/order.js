$(function(){
	/*totalPrice();*/
	$("#btn-payment").click(paymentClicked);
})

function paymentClicked(){
	paymentReady();
}

function paymentReady(){
	var cartItems=$(".cart-item");
	var totalItemPrice=$("#totalPrice").text();

	
	var goodsName=$(cartItems[0]).find(".itemName").text()
	
	if(cartItems.length>1){
		goodsName+= "외 "+(cartItems.length-1)+"건"
	}
	var email=$("#userEmail").val();
	var userName=$("#userName").val();
	payment(goodsName, totalItemPrice, email, userName)
}

function payment(goodsName, totalItemPrice, email, userName){
	var r=confirm(totalItemPrice+"원 결제 하시겠습니까?");
	var uid= "ORD"+new Date().getTime()
	if(!r){
		return;
	}
	var IMP = window.IMP;
	IMP.init("imp74400321")
	
	IMP.request_pay({
		pg: "kakaopay.TC0ONETIME",
        pay_method: "card",
        merchant_uid: uid,
        name: goodsName,
        amount: totalItemPrice,
        buyer_email: email,
        buyer_name: userName
        },function(rsp){
			msg="";
			if(rsp.success){
				console.log(rsp);
				msg="결제가 완료 되었습니다.";
			}else { 
				msg="결제가 실패하였습니다."
			}
			alert(msg);
        orderSave(uid);
	});
	/*장바구니 비우고 index로*/
}

//SM 결제 완료 후 order, order item에 작성//
function orderSave(uid){
	$.ajax({
		url:"/cart/orderSave",
		type:"POST",
		data:{uid:uid},
		beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           jqXHR.setRequestHeader(header, token);
		},
		success:function(result){
			cartAllDelete();
		}
	})
}
//Order에 저장 후 cart 비우기
function cartAllDelete(){
	$.ajax({
		url:"/cart/del",
		type:"DELETE",
		beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           jqXHR.setRequestHeader(header, token);
		},
		success:function(result){
			location.href="/cart";
		}
	})
}

//총금액 계산 후 html id="totalPrice"에 넣기///
function totalPrice(){
	var itemPrices = document.querySelectorAll(".itemPrice");
	var totalPriceElement = document.getElementById("totalPrice");
	var totalItemsPrice=0;
	
	for(var i=0; i<itemPrices.length; i++){
		var itemPrice=parseInt(itemPrices[i].textContent.replace(/,/, ""));
		totalItemsPrice+=itemPrice;
	}
	
	totalPriceElement.textContent =totalItemsPrice.toLocaleString();
	
}
//////////////

function addCart(button){
	
	var gno = $(button).siblings('.gno').val();
	
	var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
	$.ajax({
		url:"/cart",
		data:{gno : gno},
		type:"post",
		success:function(result){
			if(result==false){
				location.href="/cart/store";
			}else{
				var check=confirm("카드에 담았습니다.<br>카드로 이동?");
				if(check){
				location.href="/cart";
			}
			}
			
		}
	
	})
	
}
//수량 증가 버튼
function addCount(button){
	
	var gno = $(button).siblings('.gno').val();
	
	var token = $("meta[name='_csrf']").attr('content');
    var header = $("meta[name='_csrf_header']").attr('content');
    if(token && header) {
        $(document).ajaxSend(function(event, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    }
	$.ajax({
		url:"/cart",
		data:{gno : gno},
		type:"post",
		success:function(result){
			
				location.href="/cart";
			
		}
	
	})
	
}
//수량 감소 버튼ㅇ
function deleteCount(button){
	
	
	var gno = $(button).siblings('.gno').val();
	
	
	$.ajax({
		url:"/cart/countDown",
		data:{gno : gno	},
		type:"post",
		beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           jqXHR.setRequestHeader(header, token);
		},
		success:function(result){
			location.href="/cart";
		}
	
	})
}
///장바구니에서 상품 제거
function deleteItem(button){
	
	var gno = $(button).siblings('.gno').val();
	
	$.ajax({
		url:"/cart/delete/"+gno,
		type:"DELETE",
		beforeSend: function (jqXHR, settings) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           jqXHR.setRequestHeader(header, token);
		},
		success:function(result){
			location.href="/cart";
		}
		
	})
}


///
function scrollCart(gno){
	var scrollValue = $(".cart").scrollTop()
	if(!$(button).hasClass("cart-RBtn")){
		$("#showCart").html(result)
		var offset=$("#showCart .gno[value='"+gno+"']").parent().position().top
		$(".cart").scrollTop(offset-200)
	}else if($(button).hasClass("cart-RBtn")){
		$("#showCart").html(result)
		$(".cart").scrollTop(scrollValue)
	}
}


