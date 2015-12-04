<?php
    // Reporte toutes les erreurs PHP
    error_reporting(-1);

    // Database settings
    $db_password = 'mysql';

    $functions = array("signaler", "voter", "getDangers", "getTypeWithId", "getTypes");

    function signaler()
    {
        if( isset($_GET['id_type']) && $_GET['id_type'] != NULL &&
            isset($_GET['pos_latitude']) && $_GET['pos_latitude'] != NULL &&
            isset($_GET['pos_longitude']) && $_GET['pos_longitude'] != NULL &&
            isset($_POST['description']) && $_POST['description'] != NULL)
        {
            try
            {
                $bdd = new PDO('mysql:host=localhost;dbname=allert;charset=utf8', 'root', 'mysql');
            }
            catch(Exception $e)
            {
                die('Erreur : '.$e->getMessage());
            }

            $reponse = $bdd->prepare('INSERT INTO  Danger VALUES(:id, :pos_longitude, :pos_latitude, :description, :type, :vote_vrai, :vote_faux, :ratio, :date)');

            $reponse->execute(array(
                'id' => $_GET['id_categorie'],
                'pos_longitude' => $_GET['pos_longitude'],
                'pos_latitude' => $_GET['pos_latitude'],
                'description' => $_POST['description'],
                'type' => $_GET['id_type'],
                'vote_vrai' => '',
                'vote_faux' => '',
                'ratio' => '',
                'date' => ''));
                
            //echo json_encode($reponse->fetchAll(PDO::FETCH_ASSOC));
            echo 'OK';
            $reponse->closeCursor();
        }
        else
        {
            echo 'ERROR';
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