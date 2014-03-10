<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link
	href="http://chorus-viewer.ufbmaps.co.nz/viewer-chorus/css/viewer.css?v=1.2.144"
	media="all" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://chorus-viewer.ufbmaps.co.nz/viewer-chorus/js/bootstrap.js"></script>

<div class="modal fade" id="chorus-map-model" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 710px">
		<div class="modal-content">
			<div class="modal-header">&nbsp;
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			</div>
			<div class="modal-body">
				<div id="wivolo-search"></div>
				<div id="wivolo-map-wrapper"></div>
				<div id="wivolo-location-result"></div>
				<div id="wivolo-legend"></div>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	//<![CDATA[

	var bootstrapper, options;

	options = {
		host : 'http://chorus-viewer.ufbmaps.co.nz',
		showLocationResult : true,
		search : 'geocode',
		layers : 'ufb'
	};

	bootstrapper = new wialus.Bootstrap(options);

	//]]>
</script>