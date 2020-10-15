<?php

//code copied and altered from registerOrg.php

require_once '../includes/DbOperations.php';

$response = array();

//web services getting request from server

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
		isset($_POST['jobListingID']) and 
			isset($_POST['applicantID']) and
				isset($_POST['additionalCV'])
	){

		//insert to database
		//new db object
		$db = new DbOperations();


		//https://www.youtube.com/watch?v=tVGjM-dCeos&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=8
		$result = $db-> createApplication($_POST['jobListingID'], $_POST['applicantID'], $_POST['additionalCV']);

		//1 means success (in DbOperations), 2 means error, 0 means already exists in DB
		if($result == 1 ){
			$response['error'] = false;
			$response['message'] = "Application submitted successfully";
		} elseif($result == 2){
			$response['error'] = true;
			$response['message'] = "Some error occurred, please try again";
		} 

	}else{
		$response['error'] = true;
		$response['message'] = "Required fields are missing";
	}

}else{
	$response['error'] = true;
	$response['message'] = "Invalid Request";
}

//convert to json to be displayed inside the browser
echo json_encode($response);
