

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
		$apid = $_POST["applicationID"];



    $query = "SELECT a.fName, a.email AS aEmail, b.orgName, c.roleTitle, b.email, d.applicationID FROM applicants a, organisations b, jobListing c, application d WHERE d.jobListingID = c.jobListingID AND a.applicantID = $aid AND b.organisationID = $oid AND d.applicationID = $apid;";

    $result = mysqli_query($conn, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0) {
        while($row = mysqli_fetch_assoc($result)) {
        	$response['fName'] = $row['fName'];
        	$response['aEmail'] = $row['aEmail'];
        	$response['orgName'] = $row['orgName'];
        	$response['roleTitle'] = $row['roleTitle'];
        	$response['oEmail'] = $row['email'];
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
