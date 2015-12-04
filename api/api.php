<?php
    // Reporte toutes les erreurs PHP
    error_reporting(-1);

    $functions = array("signaler", "voter", "getDangers", "getTypeWithId", "getTypes");

    function signaler()
    {
        if(!((empty($_POST['id'])) || (empty($_POST['pos'])) || (empty($_POST['type'])) || (empty($_POST['description']))))
        {
            
        }
    }

    /*
    function voter()
    {
        if(!((empty($_POST['id-phone'])) || (empty($_POST['id-danger'])) || (empty($_POST['vrai'])) || (empty($_POST['pos']))))
        {
            
        }
    
    }
    */
    function getDangers()
    {
        try
        {
            $bdd = new PDO('mysql:host=localhost;dbname=allert;charset=utf8', 'root', 'mysql');
        }
        catch(Exception $e)
        {
            die('Erreur : '.$e->getMessage());
        }

        $reponse = $bdd->query('SELECT * FROM Danger');
        echo json_encode($reponse->fetchAll(PDO::FETCH_ASSOC));

        $reponse->closeCursor(); 
    }

    function getTypeWithId()
    {
        if(isset($_GET['id']) && $_GET['id'] != NULL)
        try
        {
            $bdd = new PDO('mysql:host=localhost;dbname=allert;charset=utf8', 'root', 'mysql');
        }
        catch(Exception $e)
        {
            die('Erreur : '.$e->getMessage());
        }

        $reponse = $bdd->prepare('SELECT * FROM TypeDanger WHERE id = :id');
        $reponse->execute(array('id' => $_GET['id']));
        echo json_encode($reponse->fetchAll(PDO::FETCH_ASSOC));

        $reponse->closeCursor();
    }

    function getTypes()
    {
        try
        {
            $bdd = new PDO('mysql:host=localhost;dbname=allert;charset=utf8', 'root', 'mysql');
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