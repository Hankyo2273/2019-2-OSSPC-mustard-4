<?php



$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if($conn){}else{exit();} // db 접속실패시 접속 끊음



?>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>머쓱타드 테트리스 게임순위 - 맵모드</title>

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
            /*background-image: url(https://nypc.github.io/2017/images/1508639518891_1.jpg);*/
            background-color: yellow;
        }

        .navbar-brand{
            font-weight: bold;
        }


        a:link { color: black; text-decoration: none;}
        a:visited { color: black; text-decoration: none;}
        a:hover { color: #CD214F; text-decoration: yellow;}

    </style>
    <!-- Custom styles for this template -->
    <link href="jumbotron.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="./home.php" >Muxgtard </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="./home.php">Home <span class="sr-only">(current)</span></a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" method="get" action="./result.php">
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
            <h3 class="display-3" style="text-align: center">맵모드 랭킹</h3>
        </div>
    </div>

    <!-- 일반모드 랭킹 테이블 -->
    <table class="table">

        <thead class="table-dark">
        <tr>
            <th style="text-align: center">#순위</th>
            <th style="text-align: center">#아이디</th>
            <th style="text-align: center">#경과시간</th>
        </tr>
        </thead>
        <tbody>
        <?php

        $nomal_sql = "select * from react_php_crud.map order by time asc ";
        $i = 1;
        $result = mysqli_query($conn, $nomal_sql);
        while ($row = mysqli_fetch_assoc($result)){ ?>
            <tr>
                <th scope="row" style="text-align: center">

                    <?php if($i == 1){ ?>
                        <div style="background-color: gold">1</div>
                    <?php }else if($i == 2){ ?>
                        <div style="background-color: silver">2</div>
                    <?php }else if($i == 3){ ?>
                        <div style="background-color: saddlebrown">3</div>
                    <?php }else{ echo $i; } ?>

                    <?php $i++; ?>
                </th>
                <td class="user_id" style="text-align: center"><a id="link"  style="font-weight:bold" href="./result.php?id=<?php echo $row['user_id']; ?>"><?php echo $row['user_id']; ?></a></td>
                <td style="text-align: center"><?php echo $row['time']; ?></td>
            </tr>
        <?php  } ?>



        <!--    <tr>-->
        <!--        <th scope="row">2</th>-->
        <!--        <td>hawdong</td>-->
        <!--        <td>850</td>-->
        <!--    </tr>-->
        <!--    <tr>-->
        <!--        <th scope="row">3</th>-->
        <!--        <td>HK</td>-->
        <!--        <td>700</td>-->
        <!--    </tr>-->
        <!--    <tr>-->
        <!--        <th scope="row">4</th>-->
        <!--        <td>admin</td>-->
        <!--        <td>500</td>-->
        <!--    </tr>-->
        <!--    <tr>-->
        <!--        <th scope="row">5</th>-->
        <!--        <td>kmss690522</td>-->
        <!--        <td>150</td>-->
        <!--    </tr>-->
        </tbody>
    </table>



</main>

<footer class="container">
    <p>&copy; OSSP tetris project created by Muxgtard  </p>
</footer>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</html>
