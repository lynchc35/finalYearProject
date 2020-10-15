

<?php

//Illness.php
//START

	define('DB_HOST', 'localhost');
	define('DB_USER', 'root');
	define('DB_PASSWORD', '');
	define('DB_NAME', 'android');
	

	$conn = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}

		$oid = $_POST["organisationID"];
		$aid = $_POST["applicantID"];
		$jid = $_POST["jobListingID"];



    $query = "SELECT a.orgName, a.email, b.fName, b.lName, c.roleTitle, d.additional FROM organisations a, applicants b, jobListing c, application d WHERE a.organisationID = $oid AND b.applicantID = $aid AND c.jobListingID = $jid AND c.jobListingID = d.jobListingID;";

    $result = mysqli_query($conn, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0) {
        while($row = mysqli_fetch_assoc($result)) {
        	$response['orgName'] = $row['orgName'];
        	$response['email'] = $row['email'];
        	$response['fName'] = $row['fName'];
        	$response['lName'] = $row['lName'];
        	$response['roleTitle'] = $row['roleTitle'];
        	$response['additional'] = $row['additional'];
            $response['error'] = false;
			$response['message'] = "Success";
        }
    }else{
    	 $response['error'] = true;
			$response['message'] = "Error";
    }

    echo json_encode(array($response));
    mysqli_close($conn);
//END
