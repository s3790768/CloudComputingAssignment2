<?php require_once('includes/parcel.inc.php'); ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
    <script type="text/javascript" src="scripts/table-click.js"></script>
    <link rel="stylesheet" href="styles/table.css">
</head>
<body>
<!--SideBar-->
<div class="container-fluid">
    <?php require_once('includes/header.inc.php'); ?>
    <div class="row">
    <?php require_once('includes/navbar.inc.php'); ?>
<!--Main Content-->
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
      
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <h1 class="h2">Your Jobs</h1>
        </div>
        <table class="table table-striped table-hover" id="tableLog">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Receiver Name</th>
                <th scope="col">Destination Address</th>
                <th scope="col">Description</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach(getToBeDeliveredParcel($_COOKIE['userId']) as $value){?>
                <tr data-href="parcelDetails.php?id=<?= $value['parcelId']; ?>">
                    <td>
                        <?= htmlspecialchars_decode($value['receiverName']) ?>
                    </td>

                    <td>
                        <?= htmlspecialchars_decode($value['dropOffAddress']) ?>
                    </td>

                    <td>
                        <?= htmlspecialchars_decode($value['description']) ?>
                    </td>
                </tr>
                <?php }?>
            </tbody>
        </table>


    </main>
  </div>
</div>

</body>
</html>


