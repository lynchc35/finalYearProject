<?php

//COPIED addFeedback.php
//START

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values						
								isset($_POST['outcome']) and
									isset($_POST['interviewID']) 
	){

		$db = new DbOperations();

		$result = $db-> addOutcome($_POST['outcome'], $_POST['interviewID']);		
			$response['error'] = false;
			$response['message'] = "Outcome submitted successfully";
		
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