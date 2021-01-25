<?php require_once('includes/parcel.inc.php'); ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
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
            <h1 class="h2">View Orders</h1>
        </div>

        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Sender Name</th>
                <th scope="col">Receiver Name</th>
                <th scope="col">Pickup Address</th>
                <th scope="col">Destination Address</th>
                <th scope="col">Description</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach(viewParcels() as $value)
                if($value["accepted"] != true || $value["delivered"] != true) { ?>
                    <td>
                        <?= $value['pickupAddress'] ?>
                    </td>

                    <td>
                        <?= $value['dropOffAddress'] ?>
                    </td>

                    <td>
                        <?= $value['description'] ?>
                    </td>

                    <td>
                        <?= $value['time']  ?>
                    </td>

                <?php }?>
            </tbody>
        </table>
        
    </main>
  </div>
</div>
</body>
</html>


