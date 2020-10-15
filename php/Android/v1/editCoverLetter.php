<?php

//COPIED createInterview.php
//START

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
				isset($_POST['additionalCV']) and
					isset($_POST['applicationID'])
	){

		$db = new DbOperations();

		$result = $db-> updateCoverLetter($_POST['additionalCV'], $_POST['applicationID']);		
			$response['error'] = false;
			$response['message'] = "Cover Letter updated successfully";
		
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