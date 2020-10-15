<?php

/*
https://www.youtube.com/watch?v=8Ou1ERM2MOw&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=4
*/

require_once '../includes/DbOperations.php';

$response = array();

//web services getting request from server

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
		isset($_POST['username']) and 
			isset($_POST['password']) and
				isset($_POST['orgName']) and
					isset($_POST['location']) and
						isset($_POST['industry']) and
							isset($_POST['email']) and
								isset($_POST['phoneNumber']) and
									isset($_POST['url']) 
	){

		//insert to database
		//new db object
		$db = new DbOperations();


		//https://www.youtube.com/watch?v=tVGjM-dCeos&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=8
		$result = $db-> createOrg($_POST['username'], $_POST['password'],$_POST['orgName'], $_POST['location'], $_POST['industry'], $_POST['email'], $_POST['phoneNumber'], $_POST['url']);

		//1 means success (in DbOperations), 2 means error, 0 means already exists in DB
		if($result == 1 ){
			$response['error'] = false;
			$response['message'] = "Organisation registered successfully";
		} elseif($result == 2){
			$response['error'] = true;
			$response['message'] = "Some error occurred, please try again";
		} elseif ($result==0) {
			$response['error'] = true;
			$response['message'] = "It seems you are already registered, please choose a different email and username";
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
