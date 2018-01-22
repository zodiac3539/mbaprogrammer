<!--
	file upload test

	Libraries: 
		commons-fileupload-1.2.2.jar
		commons-io-2.4.jar

	References:
		Commons FileUpload
		http://commons.apache.org/proper/commons-fileupload/

		Using FileUpload
		http://commons.apache.org/proper/commons-fileupload/using.html

		The answer at
		http://stackoverflow.com/questions/5700172/jsp-ajax-file-uploading
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<html>
	<head>
		<meta http-equiv="Content-Type" 
			content="text/html; charset=UTF-8"/>
		<title>File Upload Practice</title>
		<!-- use jquery for ajax -->
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<style>
			.frame {
				position: absolute;
				top: -9999px;
				left: -9999px;
			}
			.progress-bar {
				height: 30px;
				width: 300px;
				display: none;
				border: 1px solid blue;
			}
			.progress {
				background-color: blue;
				height: 100%;
				width: 0px;
			}
		</style>
		<script type="text/javascript">
			var progress;
			function upload () {
				// avoid concurrent processing
				if (progress) return;
				var uploadform = document.getElementById('uploadform'),
					time = new Date().getTime();
				uploadform.action = './UploadServlet?time=' + time;
				uploadform.target = 'target-frame';
				uploadform.submit();
				startProgressbar(time);
			}
			function startProgressbar (startTime) {
				// display progress bar
				$('.progress-bar').css('display', 'block');
				// start timer
				progress = setInterval(function () {
					// ask progress
					$.ajax({
						type: "GET",
						url: "./UploadServlet",
						data: {time: startTime},
						success: function (data, textStatus,jqXHR ) {
							// get progress from response data
							var d = eval('(' + data + ')'),
								uploadprogress = parseInt(d.progress[startTime]);
							// change progress width
							$('.progress').css('width', uploadprogress+'px');
							if (uploadprogress == 100) { // upload finished
								// stop timer
								clearInterval(progress);
								setTimeout(function () {
									// hide progress bar
									$('.progress-bar').css('display', '');
									$('.progress').css('width', '');
									// clear timer variable
									progress = null;
								}, 1000);
							}
						}
					})
				}, 1000);
			}
			
			function filelists() {
		        console.log('invoked.');
				$.getJSON( "./FileListServlet", { command: "like" } )
		        .done(function( data ) {
		        	console.log('we got data.');
		        	console.log(data);
		            //listnum = parseInt(data.filename.length);
		            //alert( listnum );
		            
		            ret = 'File List<BR><BR>';
		            
		            var lens = data.result.length;
		            console.log(lens);
		            for(i=1;i<lens;i++) {
		            	ret = ret + '<a href=../upload/' + data.result[i].filename + ' target=_BLANK>' 
		            	      + data.result[i].filename +'</a><BR>';
		            }
		            
		        	//correct = parseInt(data.results.correct);
		            //wrong = parseInt(data.results.wrong);
		        	//ret = oxprint(correct, wrong);
		        	//var temp = '#oxoutput_' + seq;
		        	$(listout).fadeOut();
		        	$(listout).html(ret);
		        	$(listout).fadeIn();
		        });

			}
		</script>
	</head>
	<body>
		<form id="uploadform" method="POST" enctype="multipart/form-data">
			File to upload: <input type="file" name="upfile"><br/>
			Notes about the file: <input type="text" name="note"><br/>
			<br/>
			<input type="button" onclick="upload()" value="Press"> to upload the file!
		</form>
		<iframe id="target-frame" name="target-frame" class="frame"></iframe>
		<!-- progress bar -->
		<br><br>
		<div class="progress-bar">
			<div class="progress"></div>
		</div>
		<BR>
		<BR>
		<input type="button" value="File List" onclick="filelists()"/><BR>
		<BR>
		<div id="listout"></div>
	</body>
</html>
