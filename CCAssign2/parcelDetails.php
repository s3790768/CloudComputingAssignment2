<?php require_once('includes/parcel.inc.php');
$parcelId = $_GET['id'];
$parcel = viewParcel($parcelId);
if(isset($_POST['bookParcel'])) {
    applyParcel($parcelId, $_POST['userId']);
}
if(isset($_POST['deleteParcel'])){
    if(deleteParcel($parcelId, $_POST['userId']) != 200){
        echo '<script>alert("There was an issue deleting")</script>';
    } else {
        header("Location: viewOrder.php");
        exit();
    }
}

if(isset($_POST['reportParcel'])){
    if(reportParcel($parcelId)['status'] == 200){
        header("Location: viewOrder.php");
        exit();
    } else {
        echo '<script>alert("There was an issue reporting this listing")</script>';
    }
}

if(isset($_POST['refundParcel'])) {
    if(refundParcel($parcelId)['status'] == 200) {
        header("Location: viewOrder.php");
        exit();
    } else {
        echo '<script>alert("There was an issue refunding")</script>';
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require_once('includes/head.inc.php'); ?>
    <link rel="stylesheet" href="styles/category.css">
    <script src="scripts/cookies.js"></script>

</head>
<body>
    <?php require_once('includes/header.inc.php'); ?>
    <div class="container-fluid">
        <div class="row">
            <?php require_once('includes/navbar.inc.php'); ?>
            <form method="post">
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
                    <button id="reportButton" name="reportParcel"  value="reportParcel" type="submit" class="btn btn-danger">Report</button>
                    <button id="bookParcel" name="bookParcel"  value="bookParcel" type="submit" class="btn btn-success">Apply</button>
                    <button id="refundParcel" name="refundParcel"  value="refundParcel" type="submit" class="btn btn-success">Refund</button>
                    <input type="hidden" name="userId" id="userId" value="" />
                    <script>
                        document.getElementById('userId').value = getCookie('userId');
                        const userId = '<?php echo $parcel['userId'] ;?>';
                        if(userId == getCookie("userId")){
                            // Only show report button if user didn't post this
                            document.getElementById("reportButton").style.display = "none"
                            document.getElementById("bookParcel").style.display = "none"
                        } else {
                            document.getElementById("refundParcel").style.display = "none"
                        }
                    </script>
            </main>
            </form>
        </div>
    </div>
</body>
</html>