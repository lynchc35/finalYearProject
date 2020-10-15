<?php

//deleteApplicant.php code structure copied & edited
//START
define('DB_HOST', 'localhost');
	define('DB_USER', 'root');
	define('DB_PASSWORD', '');
	define('DB_NAME', 'android');
	

	$conn = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}

	$id = $_POST["interviewID"];

    $query = "DELETE FROM `interviews` WHERE `interviews`.`interviewID` = '$id'";

    $result = mysqli_query($conn, $query);

    $response = array();
	$response['error'] = false;
	$response['message'] = "Interview deleted successfully";

    echo json_encode($response);
    mysqli_close($conn);
    
//END
