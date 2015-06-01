<?php
$con = mysql_connect("localhost","root","");
mysql_select_db("character",$con);

$whereId = "";
if(isset($_GET['id']))
{
	$whereId = str_replace("ID", $_GET['id'], " WHERE char_id = ID");
}
$return_arr = array();
$query = str_replace("WHEREID", $whereId, "SELECT * FROM character_information WHEREID");
$fetch = mysql_query($query); 

if($fetch != null)
{
	while ($row = mysql_fetch_array($fetch, MYSQL_ASSOC)) {
		$row_array['char_id'] = $row['char_id'];
		$row_array['char_title'] = $row['char_title'];
		$row_array['char_md5'] = $row['char_md5'];
		$row_array['char_icon'] = $row['char_icon'];
		$row_array['char_good'] = $row['char_good'];
		$row_array['char_vid'] = $row['char_vid'];
		array_push($return_arr,$row_array);
	}
}

echo json_encode($return_arr);
?>