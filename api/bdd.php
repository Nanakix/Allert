<?php
try
{
	$bdd = new PDO('mysql:host=localhost;dbname=allert;charset=utf8', 'root', 'mysql');
}
catch (Exception $e)
{
        die('Erreur : ' . $e->getMessage());
}
?>