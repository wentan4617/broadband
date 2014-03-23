 var geocoder;
 var map;
 window.onload=function(){
	$("map_canvas").style.display='none';
  initialize();
}
function initialize() {//初始化
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(-36.88554, 174.71380840000006);
    var myOptions = {
      zoom: 15,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map($("map_canvas"), myOptions);
    
   var input = document.getElementById('address');
   new google.maps.places.Autocomplete(input);

      
    
 //按下enter键执行  
 enterPress();
}
function enterPress(){//按下enter键
   $("address").onkeyup = function(e) {
  if (!e) var e = window.event;
  if (e.keyCode != 13) return;
  $("go").click();
 }
}
function $(id){
  return document.getElementById(id);
}

/**
* LatLngControl class displays the LatLng and pixel coordinates
* underneath the mouse within a container anchored to it.
* @param {google.maps.Map} map Map to add custom control to.
*/
function LatLngControl(map) {
	/**
	* Offset the control container from the mouse by this amount.
	*/
	this.ANCHOR_OFFSET_ = new google.maps.Point(8, 8);

	/**
	* Pointer to the HTML container.
	*/
	this.node_ = this.createHtmlNode_();

	// Add control to the map. Position is irrelevant.
	map.controls[google.maps.ControlPosition.TOP].push(this.node_);

	// Bind this OverlayView to the map so we can access MapCanvasProjection
	// to convert LatLng to Point coordinates.
	this.setMap(map);

	// Register an MVC property to indicate whether this custom control
	// is visible or hidden. Initially hide control until mouse is over map.
	this.set('visible', false);

}

	// Extend OverlayView so we can access MapCanvasProjection.
	LatLngControl.prototype = new google.maps.OverlayView();
	LatLngControl.prototype.draw = function() {};

	/**
	* @private
	* Helper function creates the HTML node which is the control container.
	* @return {HTMLDivElement}
	*/
	LatLngControl.prototype.createHtmlNode_ = function() {
		var divNode = document.createElement('div');
		divNode.id = 'latlng-control';
		divNode.index = 9999;
		return divNode;
	};


/**
* Called on the intiial pageload.
*/