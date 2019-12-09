<?php
/**
 * @author minshik_kim
 * @TODO 유저로 부터 게임종료시의 점수정보, 아이디, 플레이한 게임모드 정보를 받아
 *
 * kmss6905가 일반모드 게임플레이를 시작함
게임플레이를 시작한후 가령 150을 딴후 게임이 종료됨
종료됨가 동시에 서버로 해당 150점로 보냄
서버에서는 해당 어떤 모드인지 구분한후 해당 모드에서
 * 이미 플레이 했을 경우와 그렇지 않았을 경우로 두가지로 나뉨
1. 이미 플레이 한 경우( num != 0)
1.1 이미 보유하고 있는 점수가 150 점 보다 높은 경우에는(즉, 이미 더 높은 기록 보유)
해당 기록을 다시 수정(UPDATE) 하지 않으며, 서버에서 받은 150의 점수는 전체 일반모드에서 몇등인지 랭킹과 기록갱신이라는 문구를 전달해줌
{"ranking" : "23", info" : "row"}
1.2 보유하고 있는 점수가 클라이언트로 받은 점수보다 낮은 경우(기록 갱신) 이때는 해당 유저의 모드 스코어를 갱신한다.(UPDATE)
{"ranking" : "23", info" : "high"}

2.플레이한 기록이 없는 경우(num == 0)
2.1 insert 문실행 , 클라이언트로 부터 받은 점수를 db에 넣음. 넣을 점수 포함해서 해당 랭킹정보와 new 기록이라는 문구를 보내준다.
response {"ranking" : "23", "info" : "new" }
 */
$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if(!$conn){echo "실패";};

$user_id = (String)$_POST["user_id"];
$score = (int)$_POST["score"];
$mode = (int)$_POST["mode"];
$time = (String)$_POST["time"];



$general_select_sql_ = "select * from react_php_crud.nomal where user_id='$user_id'";
$time_select_sql_ = "select * from react_php_crud.time where user_id='$user_id'";
$map_select_sql_ = "select * from react_php_crud.map where user_id='$user_id'";



function get_ranking($conn ,$score, $mode, $time){
    $rankingArray = array();
    switch ($mode){
        case 1: // 일반모드
            $sql3 = "select * from react_php_crud.nomal";
            if($result3 = mysqli_query($conn, $sql3)) {

                while ($row = mysqli_fetch_assoc($result3)) {
//                    echo $row['score'];
                    array_push($rankingArray, $row['score']);
                }
                array_push($rankingArray, $score); // 마지막점수 넣고
                rsort($rankingArray);

                $arrlength = count($rankingArray);
//                echo $arrlength;
                $rank = 0;
                for ($x = 0; $x < $arrlength; $x++) {
                    if ($rankingArray[$x] > $score) {
                        $rank++;
                    } else {
                        $rank ++;
                        break;
                    }
                }
                return (String)$rank;
            }
            break;
        case 2: // 타임모드
            $sql3 = "select * from react_php_crud.time";
            if($result3 = mysqli_query($conn, $sql3)) {

                while ($row = mysqli_fetch_assoc($result3)) {
                    array_push($rankingArray, $row['score']);
                }
                array_push($rankingArray, $score); // 마지막점수 넣고
                rsort($rankingArray);

                $arrlength = count($rankingArray);
                $rank = 0;
                for ($x = 0; $x < $arrlength; $x++) {
                    if ($rankingArray[$x] > $score) {
                        $rank++;
                    } else {
                        $rank++;
                        break;
                    }
                }
//                        echo "랭킹은 : ".$rank."위";
//        echo json_encode($json_object = array(
//            "ranking" => (String)$rank,
//            "info" => "row"
//        ), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
                return (String)$rank;
            }
            break;
        case 3: // 맵모드
            $sql3 = "select * from react_php_crud.map order by time asc";
            if($result3 = mysqli_query($conn, $sql3)) {
                while ($row = mysqli_fetch_assoc($result3)) {
                    array_push($rankingArray, $row['time']);
                }
                array_push($rankingArray, $time); // 클라이언트로 부터 받은 경과시간 정보를 넣음
                usort($rankingArray, function($a,$b){
                    return strtotime($a) - strtotime($b); // if not worked. return strtotime($a[0]) - strtotime($b[0]);
                });


                $arrlength = count($rankingArray); // array 의 사이즈
                $rank = 1;
                for ($x = 0; $x < $arrlength; $x++) {
                    if (strtotime($rankingArray[$x]) < strtotime($time)) {
                        $rank++;
                    } else {
//                        $rank++;
                        break;
                    }
                }
//                        echo "랭킹은 : ".$rank."위";
//        echo json_encode($json_object = array(
//            "ranking" => (String)$rank,
//            "info" => "row"
//        ), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
                return (String)$rank;
            }
            break;
    }

};


//get_ranking($conn, $score, $mode);

switch ($mode){
    case 1: //일반모드
        if($result = mysqli_query($conn, $general_select_sql_)){
            if($num = mysqli_num_rows($result) != 0){ // 기록이 있다면
                $row = mysqli_fetch_assoc($result);
//                echo $row['score'];

                    if($row['score'] >= $score){ // 기록 갱신 하지 못하는 경우
                        $ranking = get_ranking($conn, $score, $mode,$time);
                        $json_object = array(
                            "mode" => (String)$mode,
                            "ranking" => $ranking,
                            "info" => "low"
                        );
                        echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                        exit;
                    } else { // 기록갱신하는 경우
                        $ranking = get_ranking($conn, $score, $mode,$time);
                        $sql = "update react_php_crud.nomal set score = '$score' where user_id = '$user_id'";//기록갱신
                        if($result = mysqli_query($conn, $sql)){ // 갱신)
                            $json_object = array(
                                "mode" => (String)$mode,
                                "ranking" => $ranking,
                                "info" => "high"
                            );

                            echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                            exit;
                        }

                    }

            }else{ // 기록이 없다면 기록을 추가한다.
                $ranking = get_ranking($conn, $score, $mode,$time);
                $sql = "insert into react_php_crud.nomal(user_id, score) values ('$user_id', '$score')";
                if($result = mysqli_query($conn, $sql)){ // 삽입성공
                    $json_object = array(
                        "mode" => $mode,
                        "ranking" => $ranking,
                        "info" => "new"
                    );
                }
                echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
            }

        }
        break;
    case 2: // 타임모드
        if($result = mysqli_query($conn, $time_select_sql_)){
            if($num = mysqli_num_rows($result) != 0){ // 기록이 있다면
                $row = mysqli_fetch_assoc($result);
                if($row['score'] >= $score){ // 기록 갱신 하지 못하는 경우
                    $json_object = array(
                        "mode" => $mode,
                        "ranking" => get_ranking($conn, $score, $mode, $time),
                        "info" => "low"
                    );
                    echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                    exit;

                } else { // 기록갱신하는 경우

                    $ranking = get_ranking($conn, $score, $mode,$time);
                    $sql = "update react_php_crud.time set score = '$score' where user_id = '$user_id'";//기록갱신
                    if($result = mysqli_query($conn, $sql)){ // 갱신)
                        $json_object = array(
                            "mode" => (String)$mode,
                            "ranking" => $ranking,
                            "info" => "high"
                        );

                        echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                        exit;
                    }
                }

            }else{ // 기록이 없다면 기록을 추가한다.
                $ranking = get_ranking($conn, $score, $mode,$time);
                $sql = "insert into react_php_crud.time(user_id, score) values ('$user_id', '$score')";
                if($result = mysqli_query($conn, $sql)){ // 삽입성공
                    $json_object = array(
                        "mode" => $mode,
                        "ranking" => $ranking,
                        "info" => "new"
                    );

                    echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                    exit;
                }
            }

        }
        break;
    case 3: // 맵모드
        if($result = mysqli_query($conn, $map_select_sql_)){
            if($num = mysqli_num_rows($result) != 0){ // 기록이 있다면
                $row = mysqli_fetch_assoc($result);
                if(strtotime($row['time']) <= strtotime($time)){ // 기록 갱신 하지 못하는 경우
                    $json_object = array(
                        "mode" => $mode,
                        "ranking" => get_ranking($conn, $score, $mode, $time),
                        "info" => "low"
                    );

                    echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                    exit;
                } else { // 기록갱신하는 경우
                    $ranking = get_ranking($conn, $score, $mode, $time);
                    $sql = "update react_php_crud.map set time = '$time' where user_id = '$user_id'";//기록갱신
                    if($result = mysqli_query($conn, $sql)){
                        $json_object = array(
                            "mode" => (String)$mode,
                            "ranking" => $ranking,
                            "info" => "high"
                        );

                        echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                        exit;
                    }
                }

            }else{ // 기록이 없다면 기록을 추가한다.
                $ranking = get_ranking($conn, $score, $mode, $time);
                $sql = "insert into react_php_crud.map(user_id, time) values ('$user_id', '$time')";
                if($result = mysqli_query($conn, $sql)){ // 삽입성공
                    $json_object = array(
                        "mode" => $mode,
                        "ranking" => $ranking,
                        "info" => "new"
                    );
                }
                echo json_encode($json_object, JSON_UNESCAPED_UNICODE + JSON_PRETTY_PRINT);
                exit;
            }

        }
        break;
}




?>