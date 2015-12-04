<?php
    // Reporte toutes les erreurs PHP
    error_reporting(-1);

    $functions = array("signaler", "voter", "getDangers", "getType", "getTypes");

    /*function signaler()
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
    */
    function getTypes()
    {
        try
        {
            $bdd = new PDO('mysql:host=localhost;dbname=allert;charset=utf8', 'root', '');
        }
        catch(Exception $e)
        {
            die('Erreur : '.$e->getMessage());
        }

        $reponse = $bdd->query('SELECT * FROM TypeDanger');
        echo json_encode($reponse->fetchAll(PDO::FETCH_ASSOC));

        $reponse->closeCursor();
    }

    /* Appelle la fonction d'e l'api appropriée */
    if(isset($_GET['f']) && $_GET['f'] != NULL)
    {
        /* La fonction est valide, on l'appelle */
        if(in_array($_GET['f'], $functions))
        {
            $_GET['f']();
        }
        /* La fonction n'existe pas dans l'api */
        else
        {
            echo 'invalid function';
        }
    }
    /* Aucune fonction n'a été fournie à l'api */
    else
    {
        echo "error no function specified";
    }
?>