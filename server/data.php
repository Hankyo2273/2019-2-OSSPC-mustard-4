<?php

while($post = mysqli_fetch_assoc($result)){
    ?>
    <div class="post-id" id="<?php echo $post['id']; ?>">
        <h3><a href=""><?php echo $post['user_id']; ?></a></h3>
        <p><?php echo $post['score']; ?></p>
        <div class="text-right">
            <button class="btn btn-success">Read More</button>
        </div>
        <hr style="margin-top:5px;">
    </div>
    <?php
}
?>