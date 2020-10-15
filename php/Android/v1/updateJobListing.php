<?php

//COPIED updateCV.php ADJUSTED ACCORDINGLY
//START

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		isset($_POST['roleTitle']) and 
				isset($_POST['location']) and
					isset($_POST['roleRequirements']) and
						isset($_POST['roleDuties']) and
							isset($_POST['salary']) and
								isset($_POST['listingStatus']) and
									isset($_POST['jobListingID']) 
	){

		$db = new DbOperations();

		$result = $db-> updateJob($_POST['roleTitle'],$_POST['location'], $_POST['roleRequirements'], $_POST['roleDuties'], $_POST['salary'], $_POST['listingStatus'], $_POST['jobListingID']);		
			$response['error'] = false;
			$response['message'] = "Job Listing updated successfully";
		
	}else{
		$response['error'] = true;
		$response['message'] = "Required fields are missing";
	}

}else{
	$response['error'] = true;
	$response['message'] = "Invalid Request";
}

echo json_encode($response);
//END