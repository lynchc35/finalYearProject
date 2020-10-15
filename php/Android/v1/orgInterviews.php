<?php

//START
//displayApplicationsPerOrg.php

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
	$stmt = $conn->prepare("SELECT a.interviewID, a.outcome, a.location, a.time, a.additional, a.feedback, b.fName, b.lName, b.applicantID, c.roleTitle, d.applicationID, c.jobListingID FROM interviews a, applicants b, jobListing c, application d WHERE d.applicantID = b.applicantID AND d.jobListingID = c.jobListingID AND a.applicationID = d.applicationID AND c.organisationID = '$id';");
	
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($interviewID, $outcome, $location, $time, $additional, $feedback, $fName, $lName, $applicantID, $roleTitle, $applicationID, $jobListingID);
	
	$appListings = array(); 
	
	//traversing through all the result 
	while($stmt->fetch()){
		$temp = array();
		$temp['location'] = $location; 
		$temp['outcome'] = $outcome; 
		$temp['time'] = $time; 
		$temp['additional'] = $additional; 
		$temp['feedback'] = $feedback; 
		$temp['fName'] = $fName; 
		$temp['lName'] = $lName; 
		$temp['applicantID'] = $applicantID;
		$temp['roleTitle'] = $roleTitle; 
		$temp['interviewID'] = $interviewID; 
		$temp['applicationID'] = $applicationID; 
		$temp['jobListingID'] = $jobListingID; 

		array_push($appListings, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($appListings);

	//END
