<?php

//COPIED createInterview.php
//START

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
				isset($_POST['location']) and
					isset($_POST['time']) and
						isset($_POST['additional'])  and
							isset($_POST['outcome']) and
									isset($_POST['interviewID'])
											
	){

		$db = new DbOperations();

		$result = $db-> updateInterview($_POST['location'], $_POST['time'], $_POST['additional'], $_POST['outcome'], $_POST['interviewID']);		
			$response['error'] = false;
			$response['message'] = "Interview updated successfully";
		
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