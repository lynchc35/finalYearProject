<?php

//displayJobListings.php
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

//END

//START
//https://stackoverflow.com/questions/43815378/android-volley-select-data-from-mysql-database-by-id-specified-by-user/43818941
	$id = $_POST["applicantID"];
    $jid = $_POST["jobListingID"];

    $query = "SELECT * FROM application WHERE applicantID = '$id' AND jobListingID = '$jid'";

    $result = mysqli_query($conn, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0) {
        while($row = mysqli_fetch_assoc($result)) {
            $response['applicationID'] = $row['applicationID'];
            $response['applicantID'] = $row['applicantID'];
            $response['jobListingID'] = $row['jobListingID'];
            $response['additional'] = $row['additional'];
            $response['error'] = false;
			$response['message'] = "Application loaded successfully";
        }
    }else{
    	 $response['error'] = true;
			$response['message'] = "Error";
    }

    echo json_encode(array($response));
    mysqli_close($conn);
//END
