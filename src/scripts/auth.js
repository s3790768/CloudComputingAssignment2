var auth = new firebaseui.auth.AuthUI(firebase.auth());
auth.start('#auth-container', {
    signInOptions: [
        firebase.auth.EmailAuthProvider.PROVIDER_ID,
        firebase.auth.GoogleAuthProvider.PROVIDER_ID
    ],
});

var uiConfig = {
    callbacks: {
        signInSuccessWithAuthResult: function(authResult, redirectUrl) {
            return true;
        },
        uiShown: function() {
            document.getElementById('loading').style.display = 'none';
        }
    },
    signInFlow: 'popup',
    signInSuccessUrl: 'index.php',
    signInOptions: [
        firebase.auth.GoogleAuthProvider.PROVIDER_ID,
        firebase.auth.EmailAuthProvider.PROVIDER_ID
    ]
};
auth.start('#auth-container', uiConfig);

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        var displayName = user.displayName;
        document.getElementById('userName').textContent = 'Hello ' + displayName;
    }
}, function(error) {
    console.log(error);
});