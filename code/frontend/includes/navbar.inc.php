<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="justify-content-end" id="navbarCollapse">
        <div id="userName"></div>
    </div>
</nav>

<script>
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            var displayName = user.displayName;
            document.getElementById('userName').textContent = 'Hello ' + displayName + '!';
            document.getElementById('userName').style.color = '#FFFFFF'
        }
    }, function(error) {
        console.log(error);
    });
</script>