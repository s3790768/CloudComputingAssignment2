<?php require_once('includes/parcel.inc.php'); ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
    <script src="scripts/cookies.js"></script>
    <script type="text/javascript" src="scripts/table-click.js"></script>
    <link rel="stylesheet" href="styles/table.css">
</head>
<body>
<?php require_once('includes/header.inc.php'); ?>


<!--SideBar-->
<div class="container-fluid">
  <div class="row">
      <?php require_once('includes/navbar.inc.php'); ?>
<!--Main Content-->
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <h1 class="h2">Order Status</h1>
        </div>
        <table class="table table-striped table-hover" id="tableLog">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Order ID</th>
                <th scope="col">Receiver Name</th>
                <th scope="col">Status</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach(getUserParcel($_COOKIE['userId']) as $value){?>
                <tr data-href="parcelDetails.php?id=<?= $value['parcelId']; ?>">
                    <td>
                        <?= $value['parcelId'] ?>
                    </td>

                    <td>
                        <?= $value['receiverName'] ?>
                    </td>

                    <td>
                        <?php if($value['isDelivered']){
                            echo 'Delivered';
                        } else if($value['isAccepted']){
                            echo 'Accepted but not Delivered';
                        } else {
                            echo 'No driver has accepted this order yet...';
                        }?>
                    </td>

                </tr>
                <?php
            }?>
            </tbody>
        </table>


    </main>
  </div>
</div>

</body>
</html>


