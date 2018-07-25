<?php
session_start();

$_SESSION = array();
session_unset();

$res=session_destroy(); //remove all session variables
if($res){
	//if logout success, go login page
	//header('Loaction: ./main.php');
	echo "<meta http-equiv='refresh' content='0; url=../index.html'>";
}

?>