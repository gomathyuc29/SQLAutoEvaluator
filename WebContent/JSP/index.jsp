<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SQL Code Evaluator</title>
</head>
<body>
	<h2 style="text-align: center;">Xplore SQL Code Evaluator</h2>

	<form method="post" enctype="multipart/form-data" id=form1
		name="uploadForm" onsubmit="return submitFunc()"
		action="<%=request.getContextPath()%>/UploadFileServlet">

		<div align="center">
			<table>
				<tr>
					<td>Select Candidate's Solution files(.zip):</td>
					<td><input type="file" name="file1" id="file1" /></td>
				</tr>
				<tr>
					<td>Select Candidate details(.xls):</td>
					<td><input type="file" name="file2" id="file2" /></td>
				</tr>
				<tr>
					<td>Select Test Cases File(.zip):</td>
					<td><input type="file" name="file3" id="file3" /></td>
				</tr>
				<tr>
					<td colspan="4"><input type="submit" name="submit" id="sumit"
						value="Upload Files" /></td>
				</tr>
			</table>
		</div>
	</form>
</body>


<script type="text/javascript">
<!-- Validation to check all files are browsed, no two files are same, all files have valid extensions
	function submitFunc(form) {
		var e = 0;
		var f1 = document.getElementById('file1').value;
		var f2 = document.getElementById('file2').value;
		var f3 = document.getElementById('file3').value;

		if (!(document.getElementById('file1').value)) {
			alert("Candidate's Solution zipped file not selected. Select to proceed");
			return false;
		}

		else {
			e = e + 1;
		}

		if (!(document.getElementById('file2').value)) {
			alert("Candidate details file not selected. Select to proceed");
			return false;
		} else {
			e = e + 1;
		}
		if (!(document.getElementById('file3').value)) {
			alert("Test cases zipped file not selected. Select to proceed");
			return false;
		} else {
			e = e + 1;
		}

		if (e == 3) {
			if (document.getElementById('file1').value == document
					.getElementById('file2').value
					|| document.getElementById('file2').value == document
							.getElementById('file3').value
					|| document.getElementById('file1').value == document
							.getElementById('file3').value) {
				alert("Upload distinct files")
				return false;
			}

			else if (f1.split('.').pop() != 'zip'
					|| f2.split('.').pop() != 'xls'
					|| f3.split('.').pop() != 'zip') {
				alert("Select the correct File extensions")
				return false;
			} 
		} else
			return false;

	}
</script>
</html>