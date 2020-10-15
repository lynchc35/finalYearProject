<?php

//START
//displayApplicationsPerJob.php

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
	$stmt = $conn->prepare("SELECT a.applicationID, a.jobListingID, a.applicantID, b.roleTitle, c.fName, c.lName, a.additional FROM application a, jobListing b, applicants c WHERE a.jobListingID = b.jobListingID AND a.applicantID = c.applicantID AND b.organisationID = '$id';");
	
	//https://www.youtube.com/watch?v=Yw7Lx9wqyGs
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($applicationID, $jobListingID, $applicantID, $roleTitle, $fName, $lName, $additional);
	
	$appListings = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
		$temp['applicationID'] = $applicationID; 
		$temp['jobListingID'] = $jobListingID; 
		$temp['applicantID'] = $applicantID; 
		$temp['roleTitle'] = $roleTitle; 
		$temp['fName'] = $fName; 
		$temp['lName'] = $lName; 
		$temp['additional'] = $additional; 
		array_push($appListings, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($appListings);

	//END
