function upload(){
    var url_string = window.location.href
    var url = new URL(url_string);
    var parcelId = url.searchParams.get("id");
    var metadata = {
        contentType: 'image/jpeg'
    };
    var storageRef = firebase.storage().ref();
    let fileUpload = document.getElementById("parcelDeliveredImage").files[0];
    var uploadTask = storageRef.child(parcelId).put(fileUpload, metadata);
    uploadTask.on('state_changed',
        (snapshot) => {
            switch (snapshot.state) {
                case firebase.storage.TaskState.PAUSED:
                    break;
                case firebase.storage.TaskState.RUNNING:
                    break;
            }
        },
        (error) => {
            alert("There was an issue uploading your files")
        },
        () => {
            uploadTask.snapshot.ref.getDownloadURL().then((downloadURL) => { });
        }
    );

}


function download() {
    var url_string = window.location.href
    var url = new URL(url_string);
    var parcelId = url.searchParams.get("id");
    var storageRef = firebase.storage().ref();
    storageRef.child(parcelId).getDownloadURL()
        .then((url) => {
            // `url` is the download URL for 'images/stars.jpg'

            // This can be downloaded directly:
            var xhr = new XMLHttpRequest();
            xhr.responseType = 'blob';
            xhr.onload = (event) => {
                var blob = xhr.response;
            };
            xhr.open('GET', url);
            xhr.send();

            // Or inserted into an <img> element
            var img = document.getElementById('parcelImage');
            img.setAttribute('src', url);
        })
        .catch((error) => {
            // Handle any errors
        });
}