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

    $query = "SELECT * FROM applicants WHERE applicantID = '$id'";

    $result = mysqli_query($conn, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0) {
        while($row = mysqli_fetch_assoc($result)) {
            $response['fName'] = $row['fName'];
            $response['lName'] = $row['lName'];
            $response['email'] = $row['email'];
            $response['phoneNumber'] = $row['phoneNumber'];
            $response['workHistory1Position'] = $row['workHistory1Position'];
            $response['workHistory1Organisation'] = $row['workHistory1Organisation'];
            $response['workHistory1NumberOfYears'] = $row['workHistory1NumberOfYears'];
            $response['workHistory1Duties'] = $row['workHistory1Duties'];
            $response['workHistory2Position'] = $row['workHistory2Position'];
            $response['workHistory2Organisation'] = $row['workHistory2Organisation'];
            $response['workHistory2NumberOfYears'] = $row['workHistory2NumberOfYears'];
            $response['workHistory2Duties'] = $row['workHistory2Duties'];
            $response['education1Institution'] = $row['education1Institution'];
            $response['education1Certification'] = $row['education1Certification'];
            $response['education2Institution'] = $row['education2Institution'];
            $response['education2Certification'] = $row['education2Certification'];
            $response['skills'] = $row['skills'];
            $response['awards'] = $row['awards'];
            $response['publications'] = $row['publications'];
            $response['interests'] = $row['interests'];
            $response['additional'] = $row['additional'];
            $response['error'] = false;
			$response['message'] = "CV loaded successfully";
        }
    }else{
    	 $response['error'] = true;
			$response['message'] = "Error";
    }

    echo json_encode(array($response));
    mysqli_close($conn);
//END
