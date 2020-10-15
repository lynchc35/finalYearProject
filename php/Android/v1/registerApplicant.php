<?php

//COPY & PASTED THE 'registerOrg.php' FILE CODE & ALTERED TO SUIT APPLICANTS

require_once '../includes/DbOperations.php';

$response = array();

//web services getting request from server
if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
		isset($_POST['username']) and 
			isset($_POST['password']) and
				isset($_POST['fName']) and
					isset($_POST['lName']) and
						isset($_POST['age']) and
							isset($_POST['address']) and
								isset($_POST['email']) and
									isset($_POST['phoneNumber']) and
										isset($_POST['breakReason']) and 
											isset($_POST['locationPref']) and
												isset($_POST['industryPref']) and
													isset($_POST['rolePref']) 
	){

		//insert to database
		//new db object
		$db = new DbOperations();


		//https://www.youtube.com/watch?v=tVGjM-dCeos&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=8
		$result = $db-> createApplicant($_POST['username'], $_POST['password'],$_POST['fName'], $_POST['lName'], $_POST['age'], $_POST['address'], $_POST['email'], $_POST['phoneNumber'], $_POST['breakReason'], $_POST['locationPref'], $_POST['industryPref'], $_POST['rolePref']);

		//1 means success (in DbOperations), 2 means error, 0 means already exists in DB
		if($result == 1 ){
			$response['error'] = false;
			$response['message'] = "Applicant registered successfully";
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
