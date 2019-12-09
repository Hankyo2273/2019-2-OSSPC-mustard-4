<?php

$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");

$json_object = array();

if($conn){}else{exit();} // db 접속실패시 접속 끊음
$sql = "select * from react_php_crud.users where user_id='$_GET[id]'";
$result = mysqli_query($conn, $sql);
if($result){
    $num = mysqli_num_rows($result);
    if($num == 0){ // 동일한 아이디 존재 하지 않는 경우
        $json_object = array("success" => "ok");
        echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
        exit;
    }else{ // 동일한 아이디가 존재하는 경우
        $json_object = array("success" => "no");
        echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
        exit;
    }
}else{

}


?>