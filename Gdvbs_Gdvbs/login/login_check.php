<?php
session_start();
$id=$_POST['id'];
$pw=$_POST['pw'];

$conn = new mysqli("localhost", "root", "apmsetup", "test");
if($conn->connect_error){
	die("connection failed: " . $conn->connect_error);
}

$check = "SELECT * FROM user_info WHERE userid='$id'";
$result = $conn->query($check);
if($result->num_rows==1){
	$row = $result->fetch_array(MYSQLI_ASSOC);
	if($row['userpw']==$pw){
		$_SESSION['userid']=$id;
		if(isset($_SESSION['userid'])){
			//header("Loaction: ./main.php");
			echo "<meta http-equiv='refresh' content='0; url=../gdvbs.html'>";
		} else {
			echo "session save failed";
		}
	} else {
		echo '<script language="javascript">';
		echo 'alert("Wrong ID or Password !")';
		echo '</script>';
		echo "<meta http-equiv='refresh' content='0; url=../index.html'>";
	}
} else {
		echo '<script language="javascript">';
		echo 'alert("Wrong ID or Password !")';
		echo '</script>';
		echo "<meta http-equiv='refresh' content='0; url=../index.html'>";
}

?>