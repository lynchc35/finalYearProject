<?php

//COPIED RegisterOrg.php ADJUSTED ACCORDINGLY
//START

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
			isset($_POST['applicationID']) and
				isset($_POST['location']) and
					isset($_POST['time']) and
						isset($_POST['additional']) and
							isset($_POST['outcome'])						 
	){

		$db = new DbOperations();

		$result = $db-> createInterview($_POST['applicationID'], $_POST['location'], $_POST['time'], $_POST['additional'], $_POST['outcome']);		
			$response['error'] = false;
			$response['message'] = "Interview scheduled successfully";
		
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