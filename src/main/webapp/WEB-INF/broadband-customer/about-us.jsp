<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map_canvas { height: 100% }
    </style>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBVW1VF22QhBHMntGWSOt1Vqi5l88cPak8&sensor=true&region=NZ">
    </script>
    <script type="text/javascript">

		var addresses = ['${mapAddress}'];

		var geocoder;
		var map;
		function initialize() {
			geocoder = new google.maps.Geocoder();
			var mapOptions = {
				zoom: 15,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			}
			for(var i=0; i<addresses.length; i++){
				console.log(addresses[i]);
				geocoder.geocode({ 'address': addresses[i]}, function(results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
						map.setCenter(results[0].geometry.location);
						var marker = new google.maps.Marker({
							map: map,
							position: results[0].geometry.location
						});
					} else {
						alert("Geocode was not successful for the following reason: " + status);
					}
				});
			}
			map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
		}

    </script>
  </head>
  <body onload="initialize()">
    <div id="map_canvas" style="margin:50px 0 0 50px; width:500px; height:360px"></div>
  </body>
</html>