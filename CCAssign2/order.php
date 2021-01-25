<?php require_once('includes/parcel.inc.php'); ?>

<?php
$errors = '';
if(isset($_POST['createParcelForm'])) {
    $errors = createParcel($_POST);
    if (!empty($errors)) {
        echo '<script type="text/javascript">alert("'.$errors.'");</script>';
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
    <script src="scripts/firebaseInit.js"></script>
    <script src="scripts/cookies.js"></script>
</head>
<body>
<?php require_once('includes/header.inc.php'); ?>

<div class="container-fluid">
    <div class="row">
      <?php require_once('includes/navbar.inc.php'); ?>
      <!--Main Content-->
      <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
          <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                    align-items-center pt-3 pb-2 mb-3 border-bottom">
            <h1 class="h2">Create Order</h1>
          </div>
          <form method="post" enctype="multipart/form-data">

              <input type="hidden" name="userId" id="userId" value="" />
              <!--pickup information-->
            <div class="form-row">
            <h4>Pick up information</h4>
              <div class="form-group col-md-6">
                <label for="Sender nav-item">Sender Name</label>
                  <input type="text" class="form-control" id="senderName" name="senderName" placeholder="Sender Name">
              </div>
            </div>

            <div class="form-group">
                <label for="sourceAddress">Address</label>
                <input type="text" class="form-control" id="sourceAddress" name="sourceAddress" placeholder="1234 Main St">
                <label for="description">Description</label>
                <input type="text" class="form-control" id="description" name="description" placeholder="Description">
                <label for="time">Time</label>
                <input type="datetime-local" class="form-control" id="time" name="time" placeholder="Time">
            </div>

            <!--Border-->
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"></div>
            <!--dropoff information-->
            <h4>Drop off information</h4>
            <div class="form-group col-md-6">
                <label for="Receiver nav-item">Receiver Name </label>
                <input type="text" class="form-control" id="receiverName" name="receiverName" placeholder="Receiver Name">
            </div>
            <div class="form-group">
                <label for="destinationAddress">Destination Address</label>
                <input type="text" class="form-control" id="destinationAddress"
                       name="destinationAddress" placeholder="1234 Main St">
              </div>
              <div class="form-row">
                  <div class="col-sm-10">
                    <button type="submit" name="createParcelForm"  value="createParcelForm" class="btn btn-success">Create Order</button>
                  </div>
                  <script>
                      document.getElementById('userId').value = getCookie('userId');
                  </script>
              </div>
          </form>
    </main>
  </div>

</div>
</body>
</html>


