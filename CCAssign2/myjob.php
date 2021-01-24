
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
            <h1 class="h2">My Jobs</h1>
        </div>
        <table class="table">
            <thead class="thead-dark">
              <tr>
                <th scope="col">#</th>
                <th scope="col">Sender Name</th>
                <th scope="col">Receiver Name</th>
                <th scope="col">Pickup Address</th>
                <th scope="col">Destination Address</th>
                <th scope="col">Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>John</td>
                <td>Mark</td>
                <td>29 Clarandon st.</td>
                <td>99 view dr.</td>
                <td>
                  <button type="button" class="btn btn-danger">delete<i class="fas fa-edit"></i></button>
                </td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>Wayne</td>
                <td>Jacob</td>
                <td>16 lygon st.</td>
                <td>22 westminister dr.</td>
                <td>
                  <button type="button" class="btn btn-danger">delete<i class="fas fa-edit"></i></button>
                </td>
              </tr>
            </tbody>
          </table>
        
      
    </main>
  </div>
</div>
</body>
</html>


