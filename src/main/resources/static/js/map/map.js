/**
 * 
 */

$(function(){
	kakaomapProcess();//카카오맵
});

function kakaomapProcess(){
	var data = [];
	$(".address").each(function(index) {
    var address = $(this).val();
    var name = $(".name").eq(index).val(); // 같은 인덱스에 해당하는 .name 요소의 값을 가져옵니다.

    // 주소와 이름을 객체로 묶어 배열에 추가합니다.
    data.push({
        address: address,
        name: name
    });
});
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(37.657088, 127.0622366), // 지도의 중심좌표
        level: 10, // 지도의 확대 레벨
        
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 
// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();
// 주소로 좌표를 검색합니다
var bounds = new kakao.maps.LatLngBounds();

  data.forEach(function (datas) { //추가한 코드
    // 주소로 좌표를 검색합니다
    
    geocoder.addressSearch(datas.address, function(result, status) {
	
    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords,
            title: datas.name
        });
         marker.setMap(map);
          bounds.extend(coords); 

       /* // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">'+datas.name+'</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다*/
        
         map.setBounds(bounds);
    } 
});   
})	; 

  
}
