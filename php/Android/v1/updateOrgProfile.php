<?php

//COPIED & PASTED RegisterOrg.php and altered

require_once '../includes/DbOperations.php';

$response = array();

//web services getting request from server

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
				isset($_POST['orgName']) and
					isset($_POST['username']) and 
						isset($_POST['password']) and
							isset($_POST['location']) and
								isset($_POST['industry']) and
									isset($_POST['email']) and
										isset($_POST['phoneNumber']) and 
												isset($_POST['url']) and 
													isset($_POST['organisationID'])
	){

		//insert to database
		//new db object
		$db = new DbOperations();


		//https://www.youtube.com/watch?v=tVGjM-dCeos&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=8
		$result = $db-> updateOrg($_POST['orgName'], $_POST['username'], $_POST['password'], $_POST['location'], $_POST['industry'], $_POST['email'], $_POST['phoneNumber'], $_POST['url'], $_POST['organisationID']);

		//1 means success (in DbOperations), 2 means error, 0 means already exists in DB
		if($result == 1 ){
			$response['error'] = false;
			$response['message'] = "Organisation Profile updated successfully";
		} elseif($result == 2){
			$response['error'] = true;
			$response['message'] = "Some error occurred, please try again";
		} elseif ($result==0) {
			$response['error'] = true;
			$response['message'] = "These details are already taken!";
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
