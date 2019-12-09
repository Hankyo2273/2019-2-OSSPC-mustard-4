<?php

$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if($conn){}else{exit();} // db 접속실패시 접속 끊음


$time = "00:11:30";
$time2 = "10:22:40";

if(strtotime($time) < strtotime($time2)){
    echo 'true';
}else{
    echo 'false';
}





$rankingArray = array();
$sql3 = "select * from react_php_crud.map order by time asc";
if($result3 = mysqli_query($conn, $sql3)) {
    while ($row = mysqli_fetch_assoc($result3)) {
        array_push($rankingArray, $row['time']);
    }
    array_push($rankingArray, $time); // 클라이언트로 부터 받은 경과시간 정보를 넣음

//    print_r($rankingArray)



    usort($rankingArray, function($a,$b){
        echo strtotime($a) - strtotime($b);
        return strtotime($a) - strtotime($b); // if not worked. return strtotime($a[0]) - strtotime($b[0]);
    });

    print_r($rankingArray);

//    usort($rankingArray, function($a,$b){ // 가장 빠른 경과 순으로 오름차순 정렬
//        $ab = strtotime($a);
//        $bd = strtotime($b);
//        if($ab == $bd) {
//            return 0;
//        }
//        return $ab > $bd ? -1 : 1;
//    });




//
//    $arrlength = count($rankingArray); // array 의 사이즈
//    $rank = 0;
//    for ($x = 0; $x < $arrlength; $x++) {
//        if (strtotime($rankingArray[$x]) <= strtotime($time)) {
//            $rank++;
//        } else {
//            $rank++;
//            break;
//        }
//    }
////                        echo "랭킹은 : ".$rank."위";
//        echo json_encode($json_object = array(
//            "ranking" => (String)$rank,
//            "info" => "row"
//        ), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
//    echo (String)$rank;
}








//if(strtotime("00:50:40") >= strtotime("00:50:39"))
//    echo 'true';
// else
//    echo 'false';
//
//
//
//$arr = array();
//$sql = "select * from react_php_crud.map order by time asc";
//if($result = mysqli_query($conn, $sql)){
//    while ($row = mysqli_fetch_assoc($result)){
////        echo $row['time'].'</br>';
//        array_push($arr, $row['time']);
//    }
//}
//
////print_r($arr);
//
//usort($arr, function($a,$b){
//    $ab = strtotime($a);
//    $bd = strtotime($b);
//    if($ab == $bd) {
//        return 0;
//    }
//    return $ab < $bd ? -1 : 1;
//});
//print_r($arr);

/**
 *  * kmss6905가 맵 게임플레이를 시작함
게임플레이를 시작한후 가령 40:30 만에 게임을 클리어함
 * 종료된가 동시에 서버로 40:30 을 보냄
서버에서는 해당 어떤 모드인지 구분한후 해당 모드에서
 * update 는 랭킹을 갱신했을 경우에만 이루어짐(즉 최단 시간이 나오느냐의 유무)
 * 이미 플레이 했을 경우와 그렇지 않았을 경우로 두가지로 나뉨
1. 이미 플레이 한 경우( num != 0)
1.1 이미 보유하고 있는 경과시간이 클라로부터 받은 경과시간 40:30 보다 작은 경우에는(즉 , 기록을 갱신하지 못하는 경우)
해당 기록을 다시 수정(UPDATE) 하지 않으며, 서버에서 받은 40:30 의 시간은 전체 맵모드에서 몇등인지 랭킹과 기록갱신이라는 문구를 전달해줌
{"ranking" : "23", info" : "row"}
 *
1.2 보유하고 있는 경과시간이 클라이언트로 받은 경과시간보다 클 경우(기록 갱신) 이때는 해당 유저의 모드 경과시간을 갱신한다.(UPDATE)
{"ranking" : "23", info" : "high"}
 *
 *
2.플레이한 기록이 없는 경우(num == 0)
2.1 insert 문실행 , 클라이언트로 부터 받은 시간을 db에 넣음. 넣을 시간을 포함해서 해당 랭킹정보와 new 기록이라는 문구를 보내준다.
response {"ranking" : "23", "info" : "new" }
 *
 */
