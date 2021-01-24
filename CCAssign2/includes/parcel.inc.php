<?php

function createParcel($form): string{
    $errors = '';
    console_log($errors);

    $key = 'senderName';
    if(!isset($form[$key])){
        $errors = 'Please enter your name';
    }

    $key = 'sourceAddress';
    if(!isset($form[$key])){
        $errors = 'Please enter your source address';
    }

    $key = 'destinationAddress';
    if(!isset($form[$key])){
        $errors = 'Please enter your destination address';
    }

    $key = 'receiverName';
    if(!isset($form[$key])){
        $errors = 'Please enter your receiver\'s name';
    }

    $key = 'description';
    if(!isset($form[$key])){
        $errors = 'Please enter your description';
    }

    $key = 'time';
    if(!isset($form[$key])){
        $errors = 'Please enter your time';
    }

    if(empty($userId)){
        $errors = 'It appears that you are not logged in';
    }

    $apiCall = file_get_contents('https://cloudcomputinga2.ts.r.appspot.com?pickupAddress=' .
        $form['sourceAddress'] . 'dropOffAddress=' . $form['destinationAddress'] . 'description=' . $form['description'] .
        'time=' . $form['time'] . 'userId=' . $form['userId'] . 'file=' . $_FILES['file']);

    $parseResponse = json_decode($apiCall);
    if($parseResponse -> {'status'} != 200){
        $errors = $parseResponse -> {'response'};
    }
    console_log($errors);
    return $errors;
}




function console_log($output) {
    if (is_array($output))
        $output = implode(',', $output);
    echo "<script>console.log('Debug Objects: " . $output . "' );</script>";
}
