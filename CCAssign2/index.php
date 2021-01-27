<?php
switch (@parse_url($_SERVER['REQUEST_URI'])['path']) {
    case '/login.php':
    case '/':
        require 'login.php';
        break;
    case '/main.php':
        require 'main.php';
        break;
    case '/checkout.php':
        require 'checkout.php';
        break;
    case '/parcelDetails.php':
        require 'parcelDetails.php';
        break;
    case '/viewJobs.php':
        require 'viewJobs.php';
        break;
    case '/viewOrder.php':
        require 'viewOrder.php';
        break;
    case '/order.php':
        require 'order.php';
        break;
    case '/orderStatus.php':
        require 'orderStatus.php';
        break;
    default:
        http_response_code(404);
        exit('Not Found');
}
