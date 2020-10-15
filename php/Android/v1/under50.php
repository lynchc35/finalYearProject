<?php

//under40.php

	define('DB_HOST', 'localhost');
	define('DB_USER', 'root');
	define('DB_PASSWORD', '');
	define('DB_NAME', 'android');
	

	$conn = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

	//Checking if any error occured while connecting
	if (mysqli_connect_errno()) {
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
		die();
	}
			$id = $_POST["organisationID"];


    $query = "SELECT COUNT(a.applicantID), b.applicationID, c.jobListingID, d.organisationID FROM applicants a, application b, jobListing c, organisations d WHERE a.applicantID = b.applicantID AND b.jobListingID = c.jobListingID AND c.organisationID = d.organisationID AND d.organisationID = $id AND (age = '40' OR age = '41' OR age = '42' OR age = '43' OR age = '44' OR age = '45' OR age = '46' OR age = '47' OR age = '48' OR age = '49');";

    $result = mysqli_query($conn, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0) {
        while($row = mysqli_fetch_assoc($result)) {
        	$response['count'] = $row['COUNT(a.applicantID)'];
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
