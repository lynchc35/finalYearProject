<?php

//COPIED RegisterOrg.php ADJUSTED ACCORDINGLY
//START

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

	if(
		//check user has provided all required values
		isset($_POST['workHistory1Position']) and 
			isset($_POST['workHistory1Organisation']) and
				isset($_POST['workHistory1NumberOfYears']) and
					isset($_POST['workHistory1Duties']) and
						isset($_POST['workHistory2Position']) and 
							isset($_POST['workHistory2Organisation']) and
								isset($_POST['workHistory2NumberOfYears']) and
									isset($_POST['workHistory2Duties']) and
										isset($_POST['education1Institution']) and
											isset($_POST['education1Certification']) and
												isset($_POST['education2Institution']) and
													isset($_POST['education2Certification']) and
														isset($_POST['skills']) and
															isset($_POST['awards']) and
																isset($_POST['publications']) and
																	isset($_POST['interests']) and
																		isset($_POST['additional']) and
																			isset($_POST['applicantID'])
	){

		$db = new DbOperations();

		$result = $db-> createCV($_POST['workHistory1Position'], $_POST['workHistory1Organisation'], $_POST['workHistory1NumberOfYears'], $_POST['workHistory1Duties'], $_POST['workHistory2Position'], $_POST['workHistory2Organisation'], $_POST['workHistory2NumberOfYears'], $_POST['workHistory2Duties'], $_POST['education1Institution'], $_POST['education1Certification'], $_POST['education2Institution'], $_POST['education2Certification'], $_POST['skills'], $_POST['awards'], $_POST['publications'], $_POST['interests'], $_POST['additional'], $_POST['applicantID']);		
			$response['error'] = false;
			$response['message'] = "CV uploaded successfully";
		
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