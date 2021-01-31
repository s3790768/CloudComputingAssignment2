<?php require_once('includes/parcel.inc.php');
$parcelId = $_GET['id'];
$parcel = viewParcel($parcelId);
if(isset($_POST['bookParcel'])) {
    if(applyParcel($parcelId, $_POST['userId'])){
        echo "<script>window.location.href='parcelDetails.php?id=$parcelId';</script>";
    }
}

if(isset($_POST['reportParcel'])){
    if(reportParcel($parcelId)['status'] == 200){
        echo "<script>window.location.href='viewOrder.php';</script>";
        exit();
    } else {
        echo '<script>alert("There was an issue reporting this listing")</script>';
    }
}

if(isset($_POST['refundParcel'])) {
    refundParcel($parcelId);
}

if(isset($_POST['deliveredParcel'])) {
    if(deliveredParcel($parcelId) == true) {
        echo "<script>window.location.href='viewJobs.php';</script>";
        exit();
    }
}

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
    <script src="scripts/cookies.js"></script>
</head>
<body>
    <?php require_once('includes/header.inc.php'); ?>
    <script src="scripts/firebaseInit.js"></script>
    <script src="scripts/file.js"></script>
    <div class="container-fluid">
        <div class="row">
            <?php require_once('includes/navbar.inc.php'); ?>
            <form enctype="multipart/form-data" method="post">
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
                    <tr>
                        <td>
                            <b>Sender Name:</b> <?= $parcel['senderName']  ?>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <b>Receiver Name:</b> <?= $parcel['receiverName']  ?>
                        </td>
                    </tr>
                </table>
                <?php
                $userId = $parcel['userId']; ?>

                <img id="parcelImage" width="30%" height="30%">

                <div>
                    <div id="fileText"
                         style="display: none">Please upload 1 picture as proof of delivery</div>
                    <div>
                        <input type="file" id="parcelDeliveredImage"
                               style="display: none"
                               name="parcelDeliveredImage" accept=".jpg, .jpeg, .png">
                    </div>
                </div>

                <button id="reportButton" name="reportParcel"  value="reportParcel" type="submit" class="btn btn-danger">Report</button>
                <button id="bookParcel" name="bookParcel"  value="bookParcel" type="submit" class="btn btn-success">Apply</button>
                <button id="refundParcel" name="refundParcel"  value="refundParcel" type="submit" class="btn btn-success">Refund</button>


                <button id="deliveredParcel"
                        name="deliveredParcel" style="display: none"
                        value="deliveredParcel"
                        onclick="upload()"
                        class="btn btn-success">Delivered</button>

                <input type="hidden" name="userId" id="userId" value="" />
                <script>
                    document.getElementById('userId').value = getCookie('userId');
                    const userId = '<?php echo $parcel['userId'] ;?>';
                    const driverId = '<?php echo $parcel['driverId'] ;?>';
                    const isAccepted = '<?php echo $parcel['hasAccepted'] ;?>';
                    const hasDelivered = '<?php echo $parcel['hasDelivered'] ;?>';

                    if(hasDelivered == true){
                        document.getElementById("refundParcel").style.display = "none"
                    }

                    if(userId == getCookie("userId")){
                        // Only show report button if user didn't post this
                        document.getElementById("reportButton").style.display = "none"
                        document.getElementById("bookParcel").style.display = "none"
                        download()
                    } else {
                        document.getElementById("refundParcel").style.display = "none"
                    }

                    if(isAccepted == true){
                        document.getElementById("bookParcel").style.display = "none"
                    }

                    if(driverId == getCookie("userId")){
                        document.getElementById("deliveredParcel").style.display = "initial"
                        document.getElementById("parcelDeliveredImage").style.display = "initial"
                        document.getElementById("fileText").style.display = "initial"
                    }
                </script>
            </main>
            </form>
        </div>
    </div>
</body>
</html>