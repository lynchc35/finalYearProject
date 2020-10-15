<?php

//COPIED createInterview.php
//START

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values						
								isset($_POST['feedback']) and
									isset($_POST['interviewID']) 
	){

		$db = new DbOperations();

		$result = $db-> addFeedback($_POST['feedback'], $_POST['interviewID']);		
			$response['error'] = false;
			$response['message'] = "Feedback inputted successfully";
		
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