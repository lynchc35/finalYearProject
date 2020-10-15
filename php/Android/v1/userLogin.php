<?php
//https://www.youtube.com/watch?v=RF4_0fyN8YY&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=9

require_once '../includes/DbOperations.php';

$response = array();


if($_SERVER['REQUEST_METHOD']=='POST'){

	if(isset($_POST['username']) and isset($_POST['password'])){

		$db = new DbOperations;

		if($db->orgLogin($_POST['username'], $_POST['password'])){
			$user = $db->getOrgByUsername($_POST['username']);
			$response['error'] = false;
			$response['organisationID'] = $user['organisationID'];
			$response['username'] = $user['username'];
			$response['password'] = $user['password'];
			$response['orgName'] = $user['orgName'];
			$response['location'] = $user['location'];
			$response['industry'] = $user['industry'];
			$response['email'] = $user['email'];
			$response['phoneNumber'] = $user['phoneNumber'];
			$response['url'] = $user['url'];


		}elseif ($db->applicantLogin($_POST['username'], $_POST['password'])){
			$user = $db->getApplicantByUsername($_POST['username']);
			$response['error'] = false;
			$response['applicantID'] = $user['applicantID'];
			$response['username'] = $user['username'];
			$response['password'] = $user['password'];
			$response['fName'] = $user['fName'];
			$response['lName'] = $user['lName'];
			$response['age'] = $user['age'];
			$response['address'] = $user['address'];
			$response['email'] = $user['email'];
			$response['phoneNumber'] = $user['phoneNumber'];
			$response['breakReason'] = $user['breakReason'];
			$response['locationPref'] = $user['locationPref'];
			$response['industryPref'] = $user['industryPref'];
			$response['rolePref'] = $user['rolePref'];
		}else{
		$response['error'] = true;
		$response['message'] = "Invalid username or password";
		}

 	}else{

		$response['error'] = true;
		$response['message'] = "Required fields are missing";

	}
}

echo json_encode($response);