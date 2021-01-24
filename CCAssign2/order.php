
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
            <h1 class="h2">Create Order</h1>
        </div>
        <form>
            <!--pickup information-->
            <div class="form-row">
            <h4>Pick up information</h4>
              <div class="form-group col-md-6">
                <label for="Sender nav-item">Sender Name </label>
                <input type="name" class="form-control" id="senderName" placeholder="Sender Name">
              </div>
            </div>
            
            <div class="form-group">
              <label for="inputAddress">Address</label>
              <input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St">
            </div>
            <div class="form-group">
              <label for="inputAddress2">Address 2</label>
              <input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor">
            </div>
            <div class="form-row">
              <div class="form-group col-md-6">
                <label for="inputCity">City</label>
                <input type="text" class="form-control" id="inputCity">
              </div>
              <div class="form-row">
                <div class="form-group col-md-6">
                  <label for="state">State</label>
                  <input type="text" class="form-control" id="state">
                </div>
              <div class="form-group col-md-2">
                <label for="inputZip">Zip</label>
                <input type="text" class="form-control" id="inputZip">
              </div>
            </div>

            <!--Border-->
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"></div>
            <!--dropoff information-->
            <h4>Drop off information</h4>
            <div class="form-group col-md-6">
                <label for="Sender nav-item">Receiver Name </label>
                <input type="name" class="form-control" id="senderName" placeholder="Receiver Name">
            </div>
            <div class="form-group">
                <label for="inputAddress"> Destination Address</label>
                <input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St">
              </div>
              <div class="form-group">
                <label for="inputAddress2">Address 2</label>
                <input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor">
              </div>
              <div class="form-row">
                <div class="form-group col-md-6">
                  <label for="inputCity">City</label>
                  <input type="text" class="form-control" id="inputCity">
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                      <label for="state">State</label>
                      <input type="text" class="form-control" id="state">
                    </div>
                <div class="form-group col-md-2">
                  <label for="inputZip">Zip</label>
                  <input type="text" class="form-control" id="inputZip">
                </div>

                <label class="form-label" for="customFile">Upload Parcel Image</label>
                <input type="file" class="form-control" id="customFile" />
                <p>  <p>
                <div class="col-sm-10">
                    <button type="submit" class="btn btn-success">Create Order</button>
                  </div>
              </div>
        </form>
    </main>
  </div>
</div>
</body>
</html>


