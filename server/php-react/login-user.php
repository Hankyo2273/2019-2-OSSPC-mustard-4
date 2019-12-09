<?php

$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if($conn){}else{exit();} // db 접속실패시 접속 끊음
$json_array = array();

$id = $_POST['id'];
$pwd = $_POST['pwd'];

$sql = "select * from react_php_crud.users where user_id = '$id' and user_pwd = '$pwd'";
if($result = mysqli_query($conn, $sql)){
    if($num = mysqli_num_rows($result) == 1){
        $json_array = array(
            "success" => "ok"
        );

        echo json_encode($json_array, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
        exit;
    }else{
        $json_array = array(
            "success" => "fail"
        );

        echo json_encode($json_array, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
        exit;
    }
}







?>