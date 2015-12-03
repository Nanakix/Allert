<?php
    require("bdd.php");

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
        echo 'getTypes';
        $reponse = $bdd->query('SELECT * FROM TypeDanger');

        // On affiche chaque entrée une à une
        while ($donnees = $reponse->fetch())
        {
        ?>
            <p>
            <?php echo $donnees['id']; ?><br />
            <?php echo $donnees['nom']; ?><br />
            <?php echo $donnees['coef_de_gravite']; ?> <br />
           </p>
        <?php
        }

        $reponse->closeCursor(); // Termine le traitement de la requête
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