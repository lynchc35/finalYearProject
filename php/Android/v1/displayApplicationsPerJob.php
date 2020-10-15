<?php

//START
//displayJobListingsPerOrg.php

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

	$id = $_POST["jobListingID"];
	
	//creating a query
	$stmt = $conn->prepare("SELECT a.applicationID, a.jobListingID, a.applicantID, b.fName, b.lName, b.email, b.phoneNumber, a.additional FROM application a, applicants b WHERE a.applicantID = b.applicantID AND a.jobListingID = '$id'");
	
	//https://www.youtube.com/watch?v=Yw7Lx9wqyGs
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($applicationID, $jobListingID, $applicantID, $fName, $lName, $email, $phoneNumber, $additional);
	
	$appListings = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
		$temp['applicationID'] = $applicationID; 
		$temp['jobListingID'] = $jobListingID; 
		$temp['applicantID'] = $applicantID; 
		$temp['fName'] = $fName; 
		$temp['lName'] = $lName; 
		$temp['email'] = $email; 
		$temp['phoneNumber'] = $phoneNumber; 
		$temp['additional'] = $additional; 
		array_push($appListings, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($appListings);

	//END
