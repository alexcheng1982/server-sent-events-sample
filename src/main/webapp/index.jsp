<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSE Sample</title>
<link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css">
<style type="text/css">
    #box-container {
        position: relative;
        width : 800px;
        height : 600px;
    }
    #box {
        width : 10px;
        height : 10px;
        background-color : #f00;
        position : absolute;
    }
</style>
</head>
<body>
<div class="row-fluid">
    <div class="row10 offset2">
        <div class="page-header">
            <h1>Random Movement<small> HTML 5 server sent events sample</small></h1>
        </div>
    </div>
    <div id="box-container" class="row10 offset2">
        <div id="box"></div>
    </div>
</div>
<script type="text/javascript" src="scripts/jquery-1.7.1.js"></script>
<script type="text/javascript" src="scripts/eventsource.js"></script>
<script type="text/javascript">
var es = new EventSource('sse/movement');
es.addEventListener('message', function(e) {
	var pos = e.data.split(','), x = pos[0], y = pos[1];
	$('#box').css({
		left : x + 'px',
		top : y + 'px'
	});
});
</script>
</body>
</html>