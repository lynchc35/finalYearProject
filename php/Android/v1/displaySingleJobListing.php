<?php

//START
//https://www.youtube.com/watch?v=Yw7Lx9wqyGs

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
	$stmt = $conn->prepare("SELECT a.jobListingID, a.roleTitle, a.organisationID, a.location, a.roleRequirements, a.roleDuties, a.salary, a.listingStatus, b.orgName, b.url FROM jobListing a, organisations b WHERE a.organisationID = b.organisationID AND jobListingID = '$id'");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($jobListingID, $roleTitle, $organisationID, $location, $roleRequirements, $roleDuties, $salary, $listingStatus, $orgName, $url);
	
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
		$temp['orgName'] = $orgName; 
		$temp['url'] = $url; 
		array_push($jobListings, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($jobListings);

	//END