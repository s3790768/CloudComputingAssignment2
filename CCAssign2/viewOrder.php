<?php require_once('includes/parcel.inc.php'); ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
    <script type="text/javascript" src="scripts/table-click.js"></script>
    <link rel="stylesheet" href="styles/category.css">

</head>
<body>
<?php require_once('includes/header.inc.php'); ?>

<div class="container-fluid">
  <div class="row">
      <?php require_once('includes/navbar.inc.php'); ?>
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <h1 class="h2">View Orders</h1>
        </div>

        <table class="table table-striped table-hover" id="tableLog">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Pickup Address</th>
                <th scope="col">Destination Address</th>
                <th scope="col">Description</th>
                <th scope="col">Time</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach(viewParcels() as $value){?>
                    <tr data-href="parcelDetails.php?id=<?= $value['parcelId']; ?>">
                        <td>
                            <?= htmlspecialchars_decode($value['pickupAddress']) ?>
                        </td>

                    <td>
                        <?= htmlspecialchars_decode($value['dropOffAddress']) ?>
                    </td>

                    <td>
                        <?= htmlspecialchars_decode($value['description']) ?>
                    </td>

                    <td>
                        <?= htmlspecialchars_decode($value['time'])  ?>
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