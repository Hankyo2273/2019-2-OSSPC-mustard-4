<?php
$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if($conn){}else{exit();} // db 접속실패시 접속 끊음
## 일반모드 기록


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






## 시간모드 기록
$sqlTime = "select * from react_php_crud.time group by score desc limit 1";
$sqlTimeNum = "select * from react_php_crud.time";


?>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>머쓱타드</title>

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


        <form class="form-inline my-2 my-lg-0" action="./result.php" method="get">
            <input class="form-control mr-sm-2" type="text" placeholder="유저이름 검색" aria-label="Search" name="id">
            <button class="btn btn-outline-warning my-2 my-sm-0" type="submit" onsubmit="">검색</button>
        </form>
    </div>
</nav>


<br>
<br>

<main role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-3" style="text-align: center">Muxgtard 테트리스</h1>
            <p style="text-align: center">일반모드, 타임어택모드, 맵모드 별로 랭킹을 조회해보세요</p>
            <p style="text-align: center"><a class="btn btn-primary btn-lg" href="#" role="button" >소개글 더보기</a></p>
        </div>
    </div>

    <div class="container">
        <!-- Example row of columns -->
        <div class="row">
            <div class="col-md-4">
                <h2>일반모드</h2>
                <div class="card border-success">
                    <div class="container" >
                        기록 등록 유저수 <span class="badge badge-success"><?php echo GetGeneralNum($conn);?></span>
                        <div></div>
                        현재 1위 <span class="badge badge-success"><?php GetGeneralTopUserInfo('user_id');?></span>
                        <div></div>
                        스코어 <span class="badge badge-success"><?php GetGeneralTopUserInfo('score'); ?></span>
                    </div>
                </div>
                <p><a class="btn btn-secondary" href="./nomal.php" role="button">랭킹 보기 &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>타임어택모드</h2>
                <div class="card border-warning">
                    <div class="container" >
                        기록 등록 유저수 <span class="badge badge-warning"><?php echo GetTimeNum($conn) ?></span>
                        <div></div>
                        현재 1위 <span class="badge badge-warning"><?php GetTimeTopUserInfo('user_id'); ?></span>
                        <div></div>
                        스코어 <span class="badge badge-warning"><?php GetTimeTopUserInfo('score'); ?></span>
                    </div>
                </div>
                <p><a class="btn btn-secondary" href="./time.php" role="button">랭킹 보기 &raquo;</a></p>
            </div>
            <div class="col-md-4">
                <h2>맵모드</h2>
                <div class="card border-info">
                    <div class="container" >
                        기록 등록 유저수 <span class="badge badge-info"><?php echo GetMapNum() ?></span>
                        <div></div>
                        현재 1위 <span class="badge badge-info"><?php GetMapModeTopUserInfo('user_id'); ?></span>
                        <div></div>
                        경과시간 <span class="badge badge-info"><?php GetMapModeTopUserInfo('time'); ?></span>
                    </div>
                </div>
                <p><a class="btn btn-secondary" href="./map.php" role="button">랭킹 보기 &raquo;</a></p>
            </div>
        </div>
        <hr>
    </div> <!-- /container -->
</main>

<footer class="container">
    <p>&copy; OSSP tetris project created by Muxgtard  </p>
</footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</html>
