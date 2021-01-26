<?php
error_reporting(0);

function createParcel($form){
    $errors = '';

    if(!isset($form['senderName'])){
        $errors = 'Please enter your name';
    }

    if(!isset($form['sourceAddress'])){
        $errors = 'Please enter your source address';
    }

    if(!isset($form['destinationAddress'])){
        $errors = 'Please enter your destination address';
    }

    if(!isset($form['receiverName'])){
        $errors ='Please enter your receiver\'s name';
    }

    if(!isset($form['description'])){
        $errors ='Please enter your description';
    }

    if(!isset($form['time'])){
        $errors = 'Please enter your time';
    }

    if(!isset($form['userId'])){
        $errors = 'It appears that you are not logged in';
    }
    if (empty($errors)){
        // TODO: Update the URL before submission
        // For debugging locally -> http://127.0.0.1:8080/parcel
        // For production -> https://cloudcomputinga2.ts.r.appspot.com/parcel
        $ch = curl_init('http://127.0.0.1:8080/parcel');

        $jsonData = array(
            'pickupAddress' =>  $form['sourceAddress'],
            'dropOffAddress' =>  $form['destinationAddress'],
            'description' =>  $form['description'],
            'time' => $form['time'],
            'userId' => $form['userId']
        );

        $jsonDataEncoded = json_encode($jsonData);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
        curl_setopt($ch, CURLOPT_HEADER, false);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        $result = curl_exec($ch);
        curl_close($ch);
        $parseResponse = json_decode($result, true);
        $errors = $parseResponse;
    }
    return $errors;
}

function viewParcels(){
    // TODO: Update this URL
    // For debugging locally -> http://127.0.0.1:8080/parcel
    // For production -> https://cloudcomputinga2.ts.r.appspot.com/parcel
    $json = file_get_contents('http://127.0.0.1:8080/parcel');
    $parseResponse = json_decode($json, true);
    return isset($parseResponse['response']) ? $parseResponse['response'] : [];
}

function viewParcel($parcelId){
    // TODO: Update this URL
    $json = file_get_contents('http://127.0.0.1:8080/parcel/' . $parcelId);
    $parseResponse = json_decode($json, true);
    return isset($parseResponse['response']) ? $parseResponse['response'] : [];
}

function applyParcel($parcelId, $driverId){
    // TODO: Update this URL
    $ch = curl_init('http://127.0.0.1:8080/parcel/' . $parcelId);
    $jsonData = ['driverId' =>  $driverId];
    $jsonDataEncoded = json_encode($jsonData);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
    curl_setopt($ch, CURLOPT_HEADER, false);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_exec($ch);
    curl_close($ch);
}

function deleteParcel($parcelId, $userId){
    // TODO: Update this URL
    $ch = curl_init('http://127.0.0.1:8080/parcel/' . $parcelId);
    $jsonData = ['userId' =>  $userId];
    $jsonDataEncoded = json_encode($jsonData);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
    curl_setopt($ch, CURLOPT_HEADER, false);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    curl_close($ch);
    $parseResponse = json_decode($result, true);
    return isset($parseResponse['status']) ? $parseResponse['status'] : [];
}

function console_log($output) {
    if (is_array($output))
        $output = implode(',', $output);
    echo "<script>console.log('Debug Objects: " . $output . "' );</script>";
}
