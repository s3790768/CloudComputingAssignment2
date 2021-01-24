
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
            <h1 class="h2">Order Status</h1>
        </div>
        <table class="table">
            <thead class="thead-dark">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Order ID</th>
                <th scope="col">Receiver Name</th>
                <th scope="col">Status</th>
                
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>9759681</td>
                <td>Mark</td>
                <td>Confirmed</td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>9659633</td>
                <td>Jacob</td>
                <td>Pending</td>
              </tr>
              <tr>
                <th scope="row">3</th>
                <td>9768979</td>
                <td>Larry</td>
                <td>Confirmed</td>
              </tr>
            </tbody>
          </table>
        
    </main>
  </div>
</div>

</body>
</html>


