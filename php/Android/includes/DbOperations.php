<?php
/*https://www.youtube.com/watch?v=-I80bma4uWo&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=3*/

	/**
	 * 
	 */
	class DbOperations
	{
		private $con;

		function __construct()
		{

			//connecting to the Db
			
			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();
		}

		/*CRUD - C*/
		//creating i.e. registering an Organisation

		/*************** ORGANISATIONS CRUD ****************/

		public function createOrg($username, $password, $orgName, $location, $industry, $email, $phoneNumber, $url){
			/*check if org already exists in the DB via username & email
			//https://www.youtube.com/watch?v=tVGjM-dCeos&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=8*/
			if($this->isOrgExist($username, $email, $phoneNumber)){
				return 0;
			}elseif($this->isApplicantExist($username, $email, $phoneNumber)){
				return 0;
			} else{
			//encrypts the password
			//$password = md5($pass);
			//SQL query to enter row into organisations table in database
			$stmt = $this->con->prepare("INSERT INTO `organisations` (`organisationID`, `username`, `password`, `orgName`, `location`, `industry`, `email`, `phoneNumber`, `url`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?);");
			$stmt->bind_param("sssssss",$username,$password,$orgName,$location,$industry,$email,$phoneNumber, $url);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		}
	}

	public function updateOrg($orgName, $username, $password, $location, $industry, $email, $phoneNumber, $url, $organisationID){

		if($this->isOrgExistUpdate($organisationID,$username,$email,$phoneNumber)){
				return 0;
			} elseif($this->isApplicantExist($username, $email, $phoneNumber)){
				return 0;
			} else {
			$stmt = $this->con->prepare("UPDATE `organisations` SET `orgName` = ?, `username` = ?, `password` = ?, `location` = ?, `industry` = ?, `email` = ?, `phoneNumber` = ?, `url` = ? WHERE `organisations`.`organisationID` = ?;");
			$stmt->bind_param("ssssssssi",$orgName,$username,$password,$location,$industry,$email,$phoneNumber,$url,$organisationID);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		}
	}

	

	//https://www.youtube.com/watch?v=RF4_0fyN8YY&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=9
	public function orgLogin($username, $password){
		//$password = md5($pass);
		$stmt = $this->con->prepare("SELECT organisationID FROM organisations WHERE username = ? AND password = ?");
		$stmt->bind_param("ss", $username, $password);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	//https://www.youtube.com/watch?v=RF4_0fyN8YY&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=9
	public function getOrgByUsername($username){
		$stmt = $this->con->prepare("SELECT * FROM organisations WHERE username = ?");
		$stmt->bind_param("s",$username);
		$stmt->execute();
		return $stmt->get_result()->fetch_assoc(); 
	}

	private function isOrgExist($username, $email, $phoneNumber){
			$stmt = $this->con->prepare("SELECT organisationID FROM organisations WHERE username = ? OR email = ? OR phoneNumber = ?");
			$stmt->bind_param("sss", $username, $email, $phoneNumber);
			$stmt->execute();
			$stmt->store_result();
			//if query returns a value then the org already exists on the DB
			return $stmt->num_rows > 0;
		}

	private function isOrgExistUpdate($organisationID,$username,$email,$phoneNumber){
			$stmt = $this->con->prepare("SELECT organisationID FROM organisations WHERE organisationID != ? AND (username = ? OR email = ? OR phoneNumber = ?)");
			$stmt->bind_param("isss", $organisationID,$username,$email,$phoneNumber);
			$stmt->execute();
			$stmt->store_result();
			//if query returns a value then the org already exists on the DB
			return $stmt->num_rows > 0;
		}

		/*************** APPLICANTS CRUD ****************/

	//https://www.youtube.com/watch?v=RF4_0fyN8YY&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=9
	public function applicantLogin($username, $password){
		//$password = md5($pass);
		$stmt = $this->con->prepare("SELECT applicantID FROM applicants WHERE username = ? AND password = ?");
		$stmt->bind_param("ss", $username, $password);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	//https://www.youtube.com/watch?v=RF4_0fyN8YY&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=9
	public function getApplicantByUsername($username){
		$stmt = $this->con->prepare("SELECT * FROM applicants WHERE username = ?");
		$stmt->bind_param("s",$username);
		$stmt->execute();
		return $stmt->get_result()->fetch_assoc(); 
	}


//duplicated the Organisation code & altered
		public function createApplicant($username, $password, $fName, $lName, $age, $address, $email, $phoneNumber, $breakReason, $locationPref, $industryPref, $rolePref){
			/*check if org already exists in the DB via username & email
			//https://www.youtube.com/watch?v=tVGjM-dCeos&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=8*/
			if($this->isApplicantExist($username,$email,$phoneNumber)){
				return 0;
			} else if ($this->isOrgExist($username,$email,$phoneNumber)){

				return 0;

			}else {
			//encrypts the password
			//$password = md5($pass);
				
			//SQL query to enter row into organisations table in database
			$stmt = $this->con->prepare("INSERT INTO `applicants` (`applicantID`, `username`, `password`, `fName`, `lName`, `age`, `address`, `email`, `phoneNumber`, `breakReason`, `locationPref`, `industryPref`, `rolePref`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			$stmt->bind_param("ssssssssssss",$username,$password,$fName,$lName,$age,$address,$email,$phoneNumber,$breakReason,$locationPref,$industryPref,$rolePref);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		}
	}

//update
	public function updateApplicant($username, $password, $fName, $lName, $age, $address, $email, $phoneNumber, $breakReason, $locationPref, $industryPref, $rolePref, $applicantID){
			$applicantID = $applicantID+0;
			if($this->isApplicantExistUpdate($applicantID,$username,$email,$phoneNumber)){
				return 0;
			} else if ($this->isOrgExist($username,$email,$phoneNumber)){

				return 0;

			}else{

			$stmt = $this->con->prepare("UPDATE `applicants` SET `username` = ?, `password` = ?, `fName` = ?, `lName` = ?, `age` = ?, `address` = ?, `email` = ?, `phoneNumber` = ?, `breakReason` = ?, `locationPref` = ?, `industryPref` = ?, `rolePref` = ? WHERE `applicants`.`applicantID` = ?;");
			$stmt->bind_param("ssssssssssssi",$username,$password,$fName,$lName,$age,$address,$email,$phoneNumber,$breakReason,$locationPref,$industryPref,$rolePref,$applicantID);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		}
	}

//duplicated the Organisation code & altered
		private function isApplicantExist($username, $email, $phoneNumber){
			$stmt = $this->con->prepare("SELECT applicantID FROM applicants WHERE username = ? OR email = ? OR phoneNumber = ?");
			$stmt->bind_param("sss", $username, $email, $phoneNumber);
			$stmt->execute();
			$stmt->store_result();
			//if query returns a value then the org already exists on the DB
			return $stmt->num_rows > 0;
		}

private function isApplicantExistUpdate($applicantID,$username,$email,$phoneNumber){
			$stmt = $this->con->prepare("SELECT applicantID FROM applicants WHERE applicantID != ? AND (username = ? OR email = ? OR phoneNumber = ?) ");
			$stmt->bind_param("isss", $applicantID,$username,$email,$phoneNumber);
			$stmt->execute();
			$stmt->store_result();
			//if query returns a value then the org already exists on the DB
			return $stmt->num_rows > 0;
		}

/******************* JOB LISTING CRUD *******************/
//copy & pasted the ORG code above & altered
public function createJob($roleTitle, $organisationID, $location, $roleRequirements, $roleDuties, $salary, $listingStatus){

			//START
			//https://coderwall.com/p/l7guxq/how-to-convert-string-to-integer-php-mysql
			$organisationID= $organisationID +0;
			//END

			//SQL query to enter row into organisations table in database
			$stmt = $this->con->prepare("INSERT INTO `jobListing` (`jobListingID`, `roleTitle`, `organisationID`, `location`, `roleRequirements`, `roleDuties`, `salary`, `listingStatus`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);");
			$stmt->bind_param("sssssss",$roleTitle,$organisationID,$location,$roleRequirements,$roleDuties,$salary,$listingStatus);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		}

		//updateCV altered
		public function updateJob($roleTitle, $location, $roleRequirements, $roleDuties, $salary, $listingStatus, $jobListingID){

			$jobListingID = $jobListingID+0;

			$stmt = $this->con->prepare("UPDATE `jobListing` SET `roleTitle` = ?, `location` = ?, `roleRequirements` = ?, `roleDuties` = ?, `salary` = ?, `listingStatus` = ? WHERE `jobListing`.`jobListingID` = ?;");
			$stmt->bind_param("ssssssi",$roleTitle,$location,$roleRequirements,$roleDuties,$salary,$listingStatus,$jobListingID);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		
	}

		//copied from ORG code
		public function readJob($jobListingID){
		$stmt = $this->con->prepare("SELECT * FROM jobListing WHERE jobListingID = ?");
		$stmt->bind_param("i", $jobListingID);
		$stmt->execute();
		$stmt->store_result();
		return $stmt->num_rows > 0;
	}

	//https://www.youtube.com/watch?v=RF4_0fyN8YY&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=9
	public function getJobByID($jobListingID){
		$stmt = $this->con->prepare("SELECT * FROM jobListing WHERE jobListingID = ?");
		$stmt->bind_param("i",$jobListingID);
		$stmt->execute();
		return $stmt->get_result()->fetch_assoc(); 
	}

/*************** CV CRUD *****************/
//coped createOrg function above & altered
//START
public function createCV($workHistory1Position, $workHistory1Organisation, $workHistory1NumberOfYears, $workHistory1Duties, $workHistory2Position, $workHistory2Organisation, $workHistory2NumberOfYears, $workHistory2Duties, $education1Institution, $education1Certification, $education2Institution, $education2Certification, $skills, $awards, $publications, $interests, $additional, $applicantID){
			//line from createJob function above
			//START
			$applicantID = $applicantID+0;
			//END
			$stmt = $this->con->prepare("UPDATE `applicants` SET `workHistory1Position` = ?, `workHistory1Organisation` = ?, `workHistory1NumberOfYears` = ?, `workHistory1Duties` = ?, `workHistory2Position` = ?, `workHistory2Organisation` = ?, `workHistory2NumberOfYears` = ?, `workHistory2Duties` = ?, `education1Institution` = ?, `education1Certification` = ?, `education2Institution` = ?, `education2Certification` = ?, `skills` = ?, `awards` = ?, `publications` = ?, `interests` = ?, `additional` = ? WHERE `applicants`.`applicantID` = ?;");
			$stmt->bind_param("sssssssssssssssssi",$workHistory1Position,$workHistory1Organisation,$workHistory1NumberOfYears,$workHistory1Duties,$workHistory2Position,$workHistory2Organisation,$workHistory2NumberOfYears, $workHistory2Duties, $education1Institution, $education1Certification, $education2Institution, $education2Certification, $skills, $awards, $publications, $interests, $additional, $applicantID);
			if($stmt->execute()){
				return true;
			}else{
				return false;
			}
		}
//END

//coped createCV function above & altered
//START
public function updateCV($workHistory1Position, $workHistory1Organisation, $workHistory1NumberOfYears, $workHistory1Duties, $workHistory2Position, $workHistory2Organisation, $workHistory2NumberOfYears, $workHistory2Duties, $education1Institution, $education1Certification, $education2Institution, $education2Certification, $skills, $awards, $publications, $interests, $additional, $applicantID){
			//line from createJob function above
			//START
			$applicantID = $applicantID+0;
			//END
			$stmt = $this->con->prepare("UPDATE `applicants` SET `workHistory1Position` = ?, `workHistory1Organisation` = ?, `workHistory1NumberOfYears` = ?, `workHistory1Duties` = ?, `workHistory2Position` = ?, `workHistory2Organisation` = ?, `workHistory2NumberOfYears` = ?, `workHistory2Duties` =  ?, `education1Institution` = ?, `education1Certification` = ?, `education2Institution` = ?, `education2Certification` = ?, `skills` = ?, `awards` = ?, `publications` = ?, `interests` = ?, `additional` = ? WHERE `applicants`.`applicantID` = ?;");
			$stmt->bind_param("sssssssssssssssssi",$workHistory1Position,$workHistory1Organisation,$workHistory1NumberOfYears,$workHistory1Duties,$workHistory2Position,$workHistory2Organisation,$workHistory2NumberOfYears, $workHistory2Duties, $education1Institution, $education1Certification, $education2Institution, $education2Certification, $skills, $awards, $publications, $interests, $additional, $applicantID);
			if($stmt->execute()){
				return true;
			}else{
				return false;
			}
		}
//END

/*************** Application CRUD *****************/

//createCV method altered
public function createApplication($jobListingID, $applicantID, $additional){
	//because the java code requires these parameters to be passed as Strings, the following two lines converts them back to int
	$applicantID = $applicantID+0;
	$jobListingID = $jobListingID+0;
	$stmt = $this->con->prepare("INSERT INTO `application` (`applicationID`, `jobListingID`, `applicantID`, `additional`) VALUES (NULL, ?, ?, ?);");
	$stmt->bind_param("iis",$jobListingID,$applicantID, $additional);
	if($stmt->execute()){
		return true;
	}else{
		return false;
	}

}

//createOrg
//START
public function createInterview($applicationID, $location, $time, $additional, $outcome){
	$applicationID = $applicationID+0;
			$stmt = $this->con->prepare("INSERT INTO `interviews` (`interviewID`, `applicationID`, `location`, `time`,`additional`,`outcome` ) VALUES (NULL, ?, ?, ?, ?, ?);");
			$stmt->bind_param("issss",$applicationID,$location,$time,$additional,$outcome);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		}
//END

		//updateCV altered
		public function updateInterview($location, $time, $additional, $outcome, $interviewID){

			$interviewID = $interviewID+0;

			$stmt = $this->con->prepare("UPDATE `interviews` SET `location` = ?, `time` = ?, `additional` = ?, `outcome` = ? WHERE `interviewID` = ?;");
			$stmt->bind_param("ssssi",$location,$time,$additional,$outcome,$interviewID);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		
	}


		//updateCV altered
		public function updateCoverLetter($additionalCV, $applicationID){

			$applicationID = $applicationID+0;

			$stmt = $this->con->prepare("UPDATE `application` SET `additional` = ? WHERE `applicationID` = ?;");
			$stmt->bind_param("si", $additionalCV ,$applicationID);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		
	}

//updateCV altered
		public function addFeedback($feedback, $interviewID){

			$interviewID = $interviewID+0;

			$stmt = $this->con->prepare("UPDATE `interviews` SET `feedback` = ? WHERE `interviews`.`interviewID` = ?;");
			$stmt->bind_param("si",$feedback,$interviewID);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		
	}

	//addFeedback altered
		public function addOutcome($outcome, $interviewID){

			$interviewID = $interviewID+0;

			$stmt = $this->con->prepare("UPDATE `interviews` SET `outcome` = ? WHERE `interviews`.`interviewID` = ?;");
			$stmt->bind_param("si",$outcome,$interviewID);
			if($stmt->execute()){
				//return 1;
				return true;
			}else{
				//return 2;
				return false;
			}
		
	}

}