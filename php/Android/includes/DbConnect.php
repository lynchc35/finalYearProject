<?php

//START
//https://www.youtube.com/watch?v=RK8Xrk-4bwM&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=2
	/**
	 * 
	 */
	class DbConnect
	{
		private $con;
		function __construct()
		{
			
		}

		//connecting to the database using the constants in Constants.php

		function connect(){
			include_once dirname(__FILE__).'/Constants.php';
			$this->con = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

			if(mysqli_connect_errno()){
				echo "Failed to connect with database".mysql_connect_err();
			}

			return $this->con;
		}
	}

//END