 <?php 
require "conn.php";
$user_name = $_POST["user_name"];
$user_pass = $_POST["password"];
$mysql_qry = "select * from wpdv_users where user_login like '$user_name' and user_pass like '$user_pass';";            
$result = mysqli_query($conn ,$mysql_qry);
if(mysqli_num_rows($result) > 0) {
echo "Done";
}
else {
echo "login not success";
}
 
?>