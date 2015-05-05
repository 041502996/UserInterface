<?php
$con = mysql_connect("localhost","root","");
mysql_select_db("character",$con);

$return_arr = array();
$fetch = mysql_query("SELECT * FROM character_information"); 

while ($row = mysql_fetch_array($fetch, MYSQL_ASSOC)) {
    $row_array['char_id'] = $row['char_id'];
    $row_array['char_title'] = $row['char_title'];
    $row_array['char_md5'] = $row['char_md5'];
	$row_array['char_icon'] = $row['char_icon'];
	$row_array['char_good'] = $row['char_good'];
	$row_array['char_vid'] = $row['char_vid'];
    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>