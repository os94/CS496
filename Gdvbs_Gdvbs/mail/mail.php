<?php
include "Sendmail.php";
include "../login/login_check.php";
include "index2.php";
ob_start();
ob_end_clean();

$conn2 = new mysqli("localhost", "root", "apmsetup", "gdvbs");
if($conn2->connect_error){
	die("connection failed: " . $conn2->connect_error);
}
$id = $_SESSION['userid'];
$email = "SELECT * FROM user_info WHERE userid='$id'";
$result2 = $conn2->query($email);
if($result2->num_rows==1){
	$row2 = $result2->fetch_array(MYSQLI_ASSOC);
	$value = $row2['email'];
}

$count = count($_SESSION["shopping_cart"]);

$sendmail = new Sendmail();
$to=$value;
$from="GDVBS_GDVBS"; 
$subject="주문 내역입니다."; 
$body= "감사합니다! 주문 내역을 보내드립니다.\nMay the GOODVIBES always be with you! Thank you :)"."\r\n"."\r\n";
for ($i=0; $i < $count; $i++) { 
$body .= "제품명 :  ".$_SESSION["shopping_cart"][$i]["item_name"]."\r\n"; 
$body .= "개수 :  ".$_SESSION["shopping_cart"][$i]["item_quantity"]." 개"."\r\n";
$body .= "단가 :  $ ".$_SESSION["shopping_cart"][$i]["item_price"]."\r\n"."\r\n";
}
$body .="____________________________________"."\r\n"."\r\n";
$body .= "총 가격 :  $ ".$total."\r\n"."\r\n"."\r\n";
$body .= "입금은 현재 계좌이체로만 받고 있습니다. 이 점 양해하여 주시고 앞으로도 좋은 날만 가득하시길 바랍니다. 사랑해요.";
$sendmail->send_mail($to, $from, $subject, $body) ?>