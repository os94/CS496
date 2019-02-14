<?php
$id=$_POST['id'];
$pw=$_POST['pw'];
$pwc=$_POST['pwc'];
$name=$_POST['name'];
$email=$_POST['email'];

if($pw!=$pwc)
{
	echo '<script language="javascript">';
	echo 'alert("Diffrent Password !")';
	echo '</script>';
	echo "<meta http-equiv='refresh' content='0; url=../index.html'>";
	exit();
}
if($id==NULL || $pw==NULL || $name==NULL || $email==NULL)
{
	echo '<script language="javascript">';
	echo 'alert("Fill in the Blank !")';
	echo '</script>';
	echo "<meta http-equiv='refresh' content='0; url=../index.html'>";
	exit();
}

$conn = new mysqli("localhost", "root", "apmsetup", "gdvbs");
if($conn->connect_error){
	die("connection failed: " . $conn->connect_error);
}

$check = "SELECT * from user_info WHERE userid = '$id'";
$result = $conn->query($check);
if($result->num_rows==1) {
	echo '<script language="javascript">';
	echo 'alert("Already existing ID !")';
	echo '</script>';
	echo "<meta http-equiv='refresh' content='0; url=../index.html'>";
	exit();
}

$sql = "INSERT INTO user_info (userid, userpw, name, email)
VALUES ('$id', '$pw', '$name', '$email')";
if ($conn->query($sql) === TRUE) {
    echo '<script language="javascript">';
	echo 'alert("Create Successfully !")';
	echo '</script>';
	echo "<meta http-equiv='refresh' content='0; url=../index.html'>";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();

?>