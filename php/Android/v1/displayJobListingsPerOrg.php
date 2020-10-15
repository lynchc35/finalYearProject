<?php
/*
//retrieveCV.php
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
	$id = $_POST["organisationID"];

    $query = "SELECT * FROM jobListing WHERE organisationID = '$id'";

    $result = mysqli_query($conn, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0) {
        while($row = mysqli_fetch_assoc($result)) {
            $response['jobListingID'] = $row['jobListingID'];
            $response['roleTitle'] = $row['roleTitle'];
            $response['organisationID'] = $row['organisationID'];
            $response['location'] = $row['location'];
            $response['roleRequirements'] = $row['roleRequirements'];
            $response['roleDuties'] = $row['roleDuties'];
            $response['salary'] = $row['salary'];
            $response['listingStatus'] = $row['listingStatus'];
            $response['error'] = false;
			$response['message'] = "Jobs loaded successfully";
        }
    }else{
    	 $response['error'] = true;
			$response['message'] = "Error";
    }

    echo json_encode(array($response));
    mysqli_close($conn);
//END
*/


//START
//displayJobListings.php

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

	$id = $_POST["organisationID"];
	
	//creating a query
	$stmt = $conn->prepare("SELECT * FROM jobListing WHERE organisationID = '$id'");
	
	//https://www.youtube.com/watch?v=Yw7Lx9wqyGs
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($jobListingID, $roleTitle, $organisationID, $location, $roleRequirements, $roleDuties, $salary, $listingStatus);
	
	$jobListings = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
		$temp['jobListingID'] = $jobListingID; 
		$temp['roleTitle'] = $roleTitle; 
		$temp['organisationID'] = $organisationID; 
		$temp['location'] = $location; 
		$temp['roleRequirements'] = $roleRequirements; 
		$temp['roleDuties'] = $roleDuties; 
		$temp['salary'] = $salary; 
		$temp['listingStatus'] = $listingStatus; 
		array_push($jobListings, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($jobListings);

	//END
