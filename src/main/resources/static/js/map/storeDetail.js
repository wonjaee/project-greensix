/**
 * 
 */
var lat ; // 위도
var lng ; // 경도
var name;
var add;
var latitude;
var longitude;


$(function(){
	
	kakaomapProcess();//카카오맵
	
        

   
        if (navigator.geolocation) {
            //위치 정보를 얻기
            navigator.geolocation.getCurrentPosition (function(pos) {
                latitude = pos.coords.latitude;     // 위도
                longitude = pos.coords.longitude; // 경도
                
            });
        } else {
            alert("이 브라우저에서는 Geolocation이 지원되지 않습니다.")
        }

       
    
});

function kakaomapProcess(){
	 name = $(".detail > li:nth-child(1) > span").text();
	 add = $(".detail >li:nth-child(2) > span").text();
	 
	
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(37.657088, 127.0622366), // 지도의 중심좌표
        level: 3
        
    };


var map = new kakao.maps.Map(mapContainer, mapOption); 
var geocoder = new kakao.maps.services.Geocoder();

 geocoder.addressSearch(add, function(result, status) {
	
    // 정상적으로 검색이 완료됐으면 
     if (status === kakao.maps.services.Status.OK) {

        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        lat = result[0].y; // 위도
		lng = result[0].x; // 경도

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });
         marker.setMap(map);
          

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">'+name+'</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        
         map.setCenter(coords);
    } 

});



}
//위경도->주소 로 바꾸는 함수
function getAddr(lat, lng) {
    return new Promise((resolve, reject) => {
        let geocoder = new kakao.maps.services.Geocoder();
        let coord = new kakao.maps.LatLng(lat, lng);

        let callback = function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                let address = result[0].address.address_name;
                resolve(address); // 주소 값을 반환합니다.
            } else {
                reject(new Error("주소 변환 실패"));
            }
        };

        geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
    });
}
function loadSearch(){
	var fromAdd;
	//add에는 주소 정보가 있고 /, 를 빼고 싶음
	var toAdd = add.substring(0,add.indexOf(","));
	
	getAddr(latitude, longitude).then((address) => {
    // 변환된 주소 값을 받아와서 원하는 대로 사용합니다.
    	
	window.open('https://map.kakao.com/?sName='+address+'&eName='+toAdd)

	/*window.location = "https://map.kakao.com/?sName="+fromAdd+"&eName="+toAdd;*/
	});
	
	/*window.location = "https://map.kakao.com/link/to/"+result+","+lat+","+lng;*/
	
}

function haha(){
	var hoho=$(".store-info")
	hoho.animate({width:0},'slow')
}