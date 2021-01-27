<?php
error_reporting(0);

function createParcel($form){
    $errors = '';

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
        // For debugging locally -> http://127.0.0.1:8080/parcel
        // For production -> https://cloudcomputinga2.ts.r.appspot.com/parcel
        $ch = curl_init('https://cloudcomputinga2.ts.r.appspot.com/parcel');

        $jsonData = array(
            'pickupAddress' =>  $form['sourceAddress'],
            'dropOffAddress' =>  $form['destinationAddress'],
            'description' =>  $form['description'],
            'time' => $form['time'],
            'userId' => $form['userId'],
            'receiverName' => $form['receiverName']
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
    $json = file_get_contents('https://cloudcomputinga2.ts.r.appspot.com/parcel');
    $parseResponse = json_decode($json, true);
    return isset($parseResponse['response']) ? $parseResponse['response'] : [];
}

function viewParcel($parcelId){
    $json = file_get_contents('https://cloudcomputinga2.ts.r.appspot.com/parcel/' . $parcelId);
    $parseResponse = json_decode($json, true);

    return isset($parseResponse['response']) ? $parseResponse['response'] : [];
}

function applyParcel($parcelId, $driverId){
    $ch = curl_init('https://cloudcomputinga2.ts.r.appspot.com/parcel/' . $parcelId);
    $jsonData = ['driverId' =>  $driverId];
    $jsonDataEncoded = json_encode($jsonData);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
    curl_setopt($ch, CURLOPT_HEADER, false);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_exec($ch);
    curl_close($ch);
    return true;
}

function deleteParcel($parcelId, $userId){
    $ch = curl_init('https://cloudcomputinga2.ts.r.appspot.com/parcel/' . $parcelId);
    $jsonData = ['userId' =>  $userId];
    $jsonDataEncoded = json_encode($jsonData);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
    curl_setopt($ch, CURLOPT_HEADER, false);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    curl_close($ch);
    $parseResponse = json_decode($result, true);
    return isset($parseResponse['status']) ? $parseResponse['status'] : [];
}

function getUserParcel($userId){
    $json = file_get_contents('https://cloudcomputinga2.ts.r.appspot.com/user/parcel/' . $userId);
    $parseResponse = json_decode($json, true);
    return isset($parseResponse['response']) ? $parseResponse['response'] : [];
}

function reportParcel($parcelId){
    $ch = curl_init('https://cloudcomputinga2.ts.r.appspot.com/user/parcel/report/' . $parcelId);
    $jsonData = ['userId' =>  ''];
    $jsonDataEncoded = json_encode($jsonData);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
    curl_setopt($ch, CURLOPT_HEADER, false);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    curl_close($ch);
    $parseResponse = json_decode($result, true);
    return json_decode($parseResponse, true);
}

function getToBeDeliveredParcel($userId){
    $json = file_get_contents('https://cloudcomputinga2.ts.r.appspot.com/toDeliver/' . $userId);
    $parseResponse = json_decode($json, true);
    return isset($parseResponse['response']) ? $parseResponse['response'] : [];
}


function deliveredParcel($parcelId){
    file_get_contents('https://cloudcomputinga2.ts.r.appspot.com/delivered/' . $parcelId);
    return true;
}

function refundParcel($parcelId){
    $ch = curl_init('https://cloudcomputinga2.ts.r.appspot.com/refund/' . $parcelId);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type:application/json'));
    curl_setopt($ch, CURLOPT_HEADER, false);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $result = curl_exec($ch);
    curl_close($ch);
    $parseResponse = json_decode($result, true);
    return json_decode($parseResponse, true);
}