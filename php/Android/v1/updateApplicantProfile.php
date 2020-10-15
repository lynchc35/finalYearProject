<?php

//COPY & PASTED THE 'registerApplicant.php' FILE CODE & ALTERED TO SUIT UPDATE

require_once '../includes/DbOperations.php';

$response = array();

//web services getting request from server
if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
			//isset($_POST['password']) and
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
													isset($_POST['rolePref']) and
														isset($_POST['applicantID'])
	){

		$db = new DbOperations();


		$result = $db-> updateApplicant($_POST['username'],$_POST['password'],$_POST['fName'], $_POST['lName'], $_POST['age'], $_POST['address'], $_POST['email'],$_POST['phoneNumber'], $_POST['breakReason'], $_POST['locationPref'], $_POST['industryPref'], $_POST['rolePref'], $_POST['applicantID']);

		//1 means success (in DbOperations), 2 means error, 0 means already exists in DB
		if($result == 1 ){
			$response['error'] = false;
			$response['message'] = "Applicant details updated successfully";
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

echo json_encode($response);
