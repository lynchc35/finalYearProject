<?php

//CODE TAKEN FROM REGISTERORG.PHP AND ALTERED
//START
	require_once '../includes/DbOperations.php';

	$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		isset($_POST['roleTitle']) and 
			isset($_POST['organisationID']) and
				isset($_POST['location']) and
					isset($_POST['roleRequirements']) and
						isset($_POST['roleDuties']) and
							isset($_POST['salary']) and
								isset($_POST['listingStatus']) 
	){

		$db = new DbOperations();

		$result = $db-> createJob($_POST['roleTitle'], $_POST['organisationID'],$_POST['location'], $_POST['roleRequirements'], $_POST['roleDuties'], $_POST['salary'], $_POST['listingStatus']);
		if($result == 1 ){
			$response['error'] = false;
			$response['message'] = "Job Listing uploaded successfully";
		} elseif($result == 2){
			$response['error'] = true;
			$response['message'] = "Some error occurred, please try again";
		} 

	}else{
		$response['error'] = true;
		$response['message'] = "Required fields are missing";
	}

}	else{
	$response['error'] = true;
	$response['message'] = "Invalid Request";
	}

echo json_encode($response);

//END
