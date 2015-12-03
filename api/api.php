<?php
    $functions = array("signaler", "voter", "getDangers", "getType", "getTypes");

    function signaler()
    {
        if(!((empty($_POST['id'])) || (empty($_POST['pos'])) || (empty($_POST['type'])) || (empty($_POST['description']))))
        {
            
        }
    }

    function voter()
    {
        if(!((empty($_POST['id-phone'])) || (empty($_POST['id-danger'])) || (empty($_POST['vrai'])) || (empty($_POST['pos']))))
        {
            
        }
    
    }

    function getDangers()
    {
        if(!(empty($_POST['pos1'])) || (empty($_POST['pos2']))))
        {
            
        }
    
    }

    function getType()
    {
        if(!((empty($_POST['id']))))
        {
            
        }

    }

    function getTypes()
    {
        
    }

    if(!empty($_POST))      
    { 
        if(!empty($_POST['f']))
        {
            $func = $_POST['f'];
            $functions[$func]();
            
    }



?>