<?php
$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if($conn){}else{exit();} // db 접속실패시 접속 끊음

$json_object = array();
$sql = "insert into react_php_crud.users(user_id, user_pwd) VALUES ('$_POST[id]', '$_POST[pwd]')";
if($result = mysqli_query($conn, $sql)){
     $json_object = array("success" => "ok");
     echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
     exit;
}else{
    $json_object = array("success" => "no");
    echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
    exit;
}




?>