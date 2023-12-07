

function marker(roadAddress) {

    var addressArray = addressXYSearch(roadAddress);

    for (var i = 0; i < 3; i++) {
        console.re
    }

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(addressArray[0], addressArray[1]), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


    var marker = new kakao.maps.Marker({
        position: map.getCenter()
    });
    marker.setMap(map);

    $("#roadAddress").append(addressArray[2]);

}

function addressXYSearch(roadAddress) {
    var geocoder = new kakao.maps.services.Geocoder();

    var callback = function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            console.log(result);
            var xCoordinate = result[0].address.x;
            var yCoordinate = result[0].address.y;
            var addressName = result[0].address_name;
        }
        var addressArray = [xCoordinate, yCoordinate, address_name];
        return addressArray;
    };

    geocoder.addressSearch(roadAddress, callback);
}
