
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
      <!--img-->
      <img class="d-block w-100" src="/img/rider.jpg" alt="First slide">
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Overview</h1>
      </div>
        <p>This is the Rider dashboard where riders can manage their deliveries as they desire.
        </p>
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
          <h1 class="h2">How to use our service</h1>
      </div>
          <p>
            Step 1 <br>Go to "View Jobs" to view pending deliveries.
            <br>
            Step 2 <br>Go to "My Jobs" to view selected jobs and edit/delete current jobs.
            <br>
            Step 3 <br>Go to "Order Status" and change the status from pending to completed once the delivery is complete.

          </p>
  
    </main>
  </div>
</div>
</body>
</html>


