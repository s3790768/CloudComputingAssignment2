<?php

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
        $errors = 'Please enter your receiver\'s name';
    }

    if(!isset($form['description'])){
        $errors = 'Please enter your description';
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
        // For production -> https://cloudcomputinga2.ts.r.appspot.com
        $ch = curl_init('http://127.0.0.1:8080/parcel');

        $jsonData = array(
            'pickupAddress' =>  htmlspecialchars(rawurlencode($form['sourceAddress'])),
            'dropOffAddress' =>  htmlspecialchars(rawurlencode($form['destinationAddress'])),
            'description' =>  htmlspecialchars(rawurlencode($form['description'])),
            'time' => htmlspecialchars(rawurlencode($form['time'])),
            'userId' => htmlspecialchars(rawurlencode($form['userId']))
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
        if($parseResponse['status'] != 200){
            $errors = $parseResponse['response'];
        }
    }

    return $errors;
}




function console_log($output) {
    if (is_array($output))
        $output = implode(',', $output);
    echo "<script>console.log('Debug Objects: " . $output . "' );</script>";
}
