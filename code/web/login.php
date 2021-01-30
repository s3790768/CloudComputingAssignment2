<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <?php require_once('includes/head.inc.php'); ?>
    <script src="scripts/firebaseInit.js"></script>
    <script src="scripts/auth.js"></script>
	<script type="text/javascript" src="scripts/cookies.js"></script>
    <script>
        document.cookie = "userId" +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    </script>
</head>
<body>

<div id="auth-container"></div>
<div id="loading">Loading...</div>

</body>
</html>