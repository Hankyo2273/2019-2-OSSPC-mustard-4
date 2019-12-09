<?php

$conn = mysqli_connect("localhost", "min", "alstlrdl1!", "react_php_crud");
if($conn){}else{exit();} // db 접속실패시 접속 끊음




?>


<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Hello, world!</title>
</head>
<body>
<h1>일반모드</h1>

<table class="table">

    <thead>
    <tr>
        <th>#순위</th>
        <th>#아이디</th>
        <th>#스코어</th>
    </tr>
    </thead>
    <tbody>
    <?php

    $nomal_sql = "select * from react_php_crud.nomal order by score desc ";
    $i = 1;
    $result = mysqli_query($conn, $nomal_sql);
     while ($row = mysqli_fetch_assoc($result)){ ?>
    <tr>
        <th scope="row"><?php echo $i++ ?></th>
        <td><?php echo $row['user_id']; ?></td>
        <td><?php echo $row['score']; ?></td>
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

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
