<?php
include 'http://15.164.218.103/php-react/add-point.php';

$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if($conn){}else{exit();} // db 접속실패시 접속 끊음

$id = $_GET['id'];

$user_id = (String)$_POST["user_id"];
$score = (int)$_POST["score"];
$mode = (int)$_POST["mode"];




function GetGeneralNum(){
    $sqlGeneralNum = "select * from react_php_crud.nomal";
    $result = mysqli_query($GLOBALS['conn'],$sqlGeneralNum);
    $num = mysqli_num_rows($result);
    return $num;
}

function GetGeneralTopUserInfo($what){
    $sql = "select * from react_php_crud.nomal where score = (select max(score) from react_php_crud.nomal)";
    $result = mysqli_query($GLOBALS['conn'], $sql);
    while($row = mysqli_fetch_assoc($result)){
        echo $row[$what];
    }
}

function GetTimeNum(){
    $sqlGeneralNum = "select * from react_php_crud.time";
    $result = mysqli_query($GLOBALS['conn'], $sqlGeneralNum);
    $num = mysqli_num_rows($result);
    return $num;
}

function GetTimeTopUserInfo($what){
    $sql = "select * from react_php_crud.time where score = (select max(score) from react_php_crud.time)";
    $result = mysqli_query($GLOBALS['conn'], $sql);
    while($row = mysqli_fetch_assoc($result)){
        echo $row[$what];
    }
}

function GetMapNum(){
    $sqlGeneralNum = "select * from react_php_crud.map";
    $result = mysqli_query($GLOBALS['conn'], $sqlGeneralNum);
    $num = mysqli_num_rows($result);
    return $num;
}

function GetMapModeTopUserInfo($what){
    $sql = "select * from react_php_crud.map where time = (select min(time) from react_php_crud.map)";
    $result = mysqli_query($GLOBALS['conn'], $sql);
    while($row = mysqli_fetch_assoc($result)){
        echo $row[$what];
    }
}


function isUser(){
    $sql = "select * from react_php_crud.users where user_id = '$_GET[id]'";
    $result = mysqli_query($GLOBALS['conn'], $sql);
    $num = mysqli_num_rows($result);
    if($num != 0)
        return true;
    else
        return false;
}


?>


<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>유저 검색</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/jumbotron/">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


    <style type="text/css">
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        .jumbotron{
            /*background-image: url(https://mblogthumb-phinf.pstatic.net/MjAxODA3MDlfMTMx/MDAxNTMxMTE1MDUyMzA1.AbJvBOhqQwzq6XF78LWPAAU3tXe7yMGLM2EZsjUNdjAg.KT0i8hUiGyDCDm7_ZR_hvLgrc-U2Oi7nykxY-ANokxgg.JPEG.pikachusl/36751506_493962347725111_8505883185823875072_n.jpg?type=w800);*/
            background-color: yellow;
        }

        .navbar-brand{
            font-weight: bold;
        }

    </style>
    <!-- Custom styles for this template -->
    <link href="jumbotron.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="./home.php" ></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="./home.php">Home <span class="sr-only">(current)</span></a>
            </li>
        </ul>


        <form class="form-inline my-2 my-lg-0" action="./result.php">
            <input class="form-control mr-sm-2" type="text" placeholder="유저이름 검색" aria-label="Search" name="id">
            <button class="btn btn-outline-warning my-2 my-sm-0" type="submit">검색</button>
        </form>
    </div>
</nav>

<br>
<br>

<main role="main">
    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <div class="container">
            <div></div>
            <h1 class="display-3" style="text-align: center">랭킹조회</h1>
        </div>
    </div>

    <?php if(!isUser()){ ?>
    <div class="container">
        <h2>검색결과가 없습니다</h2>
        <h5>다시 검색해주세요</h5>
    </div>
    <?php }else{ ?>
    <div class="container">
        <h2 align="center"><?php echo $_GET['id']; ?></h2>
        <h5 align="center">님의 랭킹입니다</h5>
    </div>

        <br>
    <?php
        $sql = "select * from react_php_crud.nomal where user_id = '$_GET[id]'";
        $result = mysqli_query($conn, $sql);
        while ($row = mysqli_fetch_assoc($result)){
            $score = $row['score'];
        }

       $sql2  = "select user_id, score, ranking from (select user_id,score,
                                         @vRank := @vRank + 1 AS ranking from react_php_crud.nomal AS p, (select @vRank :=0) AS r ORDER BY score DESC) AS CNT WHERE user_id = '$_GET[id]'";
        $result2 = mysqli_query($conn, $sql2);
        while ($row = mysqli_fetch_assoc($result2)){
//            echo '<h1>'.$row['ranking'].'</h1>';
            $ranking = $row['ranking'];
        }


        ?>
    <div class="container">
        <h1>일반모드</h1>
        <table class="table">
            <thead class="table-primary">
                <th class="text-center" scope="col">#순위( 전체순위 )</th>
                <th class="text-center" scope="col">#스코어</th>
            </thead>
            <tbody>
                <tr>
                    <?php if($ranking <= 3) {?>
                        <th class="text-center" scope="row">
                            <?php if($ranking == 1){ ?>
                                <h4><span class="badge badge-danger">1st Player</span></h4>
                                <?php echo $ranking ?> (  <?php echo GetGeneralNum(); ?> )
                            <?php }else if($ranking == 2){ ?>
                                <h4><span class="badge badge-secondary">2nd Player</span></h4>
                                <?php echo $ranking ?> (  <?php echo GetGeneralNum(); ?> )
                            <?php }else if($ranking == 3){ ?>
                                <h4><span class="badge badge-secondary">3th Player</span></h4>
                                <?php echo $ranking ?> (  <?php echo GetGeneralNum(); ?> )
                            <?php }?>
                        </th>
                    <?php }else{?>
                        <th class="text-center" scope="row">
                            <?php if($ranking != null){ ?>
                                <?php echo $ranking ?> (  <?php echo GetGeneralNum(); ?> )
                            <?php }?>
                        </th>
                    <?php } ?>
                    <td class="text-center" style="font-weight: bold"><?php if($score != null){?> <?php echo $score; }else{?> 없음 <?php }?></td>
                </tr>
            </tbody>
        </table>
    </div> <!-- /container -->

    <div class="container">
        <h1>맵모드</h1>
        <table class="table">
            <thead class="table-primary">
            <th class="text-center" scope="col">#순위( 전체순위 )</th>
            <th class="text-center" scope="col">#경과시간</th>
            </thead>
            <tbody>
            <?php
            $sql = "select * from react_php_crud.map where user_id = '$_GET[id]'";
            $result = mysqli_query($conn, $sql);
            while ($row = mysqli_fetch_assoc($result)){
                $map_time = $row['time'];
            }

            $sql3  = "select user_id, time, ranking from (select user_id, time,
                                         @vRank := @vRank + 1 AS ranking from react_php_crud.map AS p, (select @vRank :=0) AS r ORDER BY time ASC) AS CNT WHERE user_id = '$_GET[id]'";
            $result3 = mysqli_query($conn, $sql3);
            while ($row = mysqli_fetch_assoc($result3)){
//            echo '<h1>'.$row['ranking'].'</h1>';
                $ranking3 = $row['ranking'];
            }

            ?>
            <tr>
                <?php if($ranking3 <= 3) {?>
                    <th class="text-center" scope="row">
                        <?php if($ranking3 == 1){ ?>
                            <h4><span class="badge badge-danger">1st Player</span></h4>
                            <?php echo $ranking3 ?> (  <?php echo GetMapNum(); ?> )
                        <?php }else if($ranking3 == 2){ ?>
                            <h4><span class="badge badge-secondary">2nd Player</span></h4>
                            <?php echo $ranking3 ?> (  <?php echo GetMapNum(); ?> )
                        <?php }else if($ranking3 == 3){ ?>
                            <h4><span class="badge badge-secondary">3th Player</span></h4>
                            <?php echo $ranking3 ?> (  <?php echo GetMapNum(); ?> )
                        <?php }?>
                    </th>
                <?php }else{?>
                    <th class="text-center" scope="row">
                        <?php if($ranking3 != null){ ?>
                            <?php echo $ranking3 ?> (  <?php echo GetMapNum(); ?> )
                        <?php }?>
                    </th>
                <?php } ?>
                <td class="text-center" style="font-weight: bold"><?php if($map_time != null){?> <?php echo $map_time; }else{?> 없음 <?php }?></td>
            </tr>
            </tbody>
        </table>
    </div> <!-- /container -->

        <div class="container">
            <h1>타임어택모드</h1>
            <table class="table">
                <thead class="table-primary">
                <th class="text-center" scope="col">#순위( 전체순위 )</th>
                <th class="text-center" scope="col">#스코어</th>
                </thead>
                <tbody>

                <?php
                $sql = "select * from react_php_crud.time where user_id = '$_GET[id]'";
                $result = mysqli_query($conn, $sql);
                while ($row = mysqli_fetch_assoc($result)){
                    $score_time = $row['score'];
                }

                $sql2  = "select user_id, score, ranking from (select user_id,score,
                                         @vRank := @vRank + 1 AS ranking from react_php_crud.time AS p, (select @vRank :=0) AS r ORDER BY score DESC) AS CNT WHERE user_id = '$_GET[id]'";

                $result2 = mysqli_query($conn, $sql2);
                while ($row = mysqli_fetch_assoc($result2)){
//            echo '<h1>'.$row['ranking'].'</h1>';
                    $ranking2 = $row['ranking'];
                }


                ?>
                <tr>
                    <?php if($ranking2 <= 3) {?>
                        <th class="text-center" scope="row">
                            <?php if($ranking2 == 1){ ?>
                                <h4><span class="badge badge-danger">1st Player</span></h4>
                                <?php echo $ranking2 ?> (  <?php echo GetTimeNum(); ?> )
                            <?php }else if($ranking2 == 2){ ?>
                                <h4><span class="badge badge-secondary">2nd Player</span></h4>
                                <?php echo $ranking2 ?> (  <?php echo GetTimeNum(); ?> )
                            <?php }else if($ranking2 == 3){ ?>
                                <h4><span class="badge badge-secondary">3th Player</span></h4>
                                <?php echo $ranking2 ?> (  <?php echo GetTimeNum(); ?> )
                            <?php } ?>
                        </th>
                    <?php }else{?>
                        <th class="text-center" scope="row">
                            <?php echo $ranking2 ?> (  <?php echo GetTimeNum(); ?> )
                        </th>
                    <?php } ?>
                    <td class="text-center" style="font-weight: bold"><?php if($score_time != null){?> <?php echo $score_time; }else{?> 없음 <?php }?></td>
                </tr>
                </tbody>
            </table>
        </div> <!-- /container -->

    <?php } ?>
</main>

<footer class="container">
    <p>&copy; OSSP tetris project created by Muxgtard  </p>
</footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</html>



