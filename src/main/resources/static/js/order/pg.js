/**
 * 
 */
$(function(){
	$("#btn-payment").click(paymentBtnClicked);
});

function paymentBtnClicked(){
	var orderItems=$(".order-item");
	var eaPriceTot=$(".total-price").text();
	
	var goodsName=$(orderItems[0]).text();
	if(orderItems.length>1){
		goodsName += "외" + (orderItems.length-1)+"건"
	};
	var userName=$(".email").text();
	
	payment(goodsName, eaPriceTot, userName);
};

//payment(goodsName,eaPriceTot,email, userName, phone, buyer_addr,buyer_postcode)
function payment(goodsName, eaPriceTot, userName){
	var IMP = window.IMP; 
    IMP.init("imp74400321"); 
    
    IMP.request_pay({ // param
          pg: "kakaopay.TC0ONETIME",
          pay_method: "card",
          merchant_uid: "ORD"+new Date().getTime(),
          name: goodsName,
          amount: eaPriceTot,
          //buyer_email: email,
          buyer_name: userName,
          //buyer_tel: phone,
          //buyer_addr: buyer_addr,
          //buyer_postcode: buyer_postcode
      }, function (rsp) { // callback
     	  msg="";
          if (rsp.success) {
              // 결제 성공 시 로직,
              console.log(rsp);
              msg="결제가 완료 되었습니다.";
              //dB에 정보 저장
              $("#form-order").submit();
              
          } else {
              // 결제 실패 시 로직,
              msg="결제에 실패하였습니다.";
          }
          alert(msg);
      });
};