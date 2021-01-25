<?php require_once('includes/parcel.inc.php');
$parcel = viewParcel($_GET['id'])
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
    <link rel="stylesheet" href="styles/category.css">
    <script src="scripts/cookies.js"></script>

</head>
<body>
    <?php require_once('includes/header.inc.php'); ?>
    <div class="container-fluid">
        <div class="row">
            <?php require_once('includes/navbar.inc.php'); ?>
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <table class="table table-striped table-hover" id="tableLog">
                    <tr>
                        <td>
                            <b>Pickup:</b> <?= $parcel['pickupAddress'] ?>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Drop off:</b> <?= $parcel['dropOffAddress'] ?>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Description:</b> <?= $parcel['description'] ?>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Pick up time:</b> <?= $parcel['time']  ?>
                        </td>
                    </tr>
                </table>
                <?php
                $userId = $parcel['userId']; ?>
                <button id="editButton" type="button" class="btn btn-success">edit<i class="fas fa-edit"></i></button>
                <button id="deleteButton" type="button" class="btn btn-danger">delete<i class="far fa-trash-alt"></i></button>
                <script>
                    const userId = '<?php echo $parcel['userId'] ;?>';
                    if(userId != getCookie("userId")){
                        document.getElementById("editButton").style.display = "none"
                        document.getElementById("deleteButton").style.display = "none"
                    }
                </script>

            </main>
        </div>
    </div>
</body>
</html>